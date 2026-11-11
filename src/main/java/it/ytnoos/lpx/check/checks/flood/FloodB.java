/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 */
package it.ytnoos.lpx.check.checks.flood;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RepeatedIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.player.BukkitProtocolPlayer;
import it.ytnoos.lpx.we;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;

public class FloodB
extends PlayerCheck<PacketReceiveEvent> {
    private Map<PacketTypeCommon, List<PacketLimit>> packetLimits;

    public FloodB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.packetLimits = new HashMap<PacketTypeCommon, List<PacketLimit>>();
        ConfigurationSection configurationSection = this.getConfig().getConfigurationSection(this.getPath("limits"));
        if (configurationSection != null) {
            for (String string : configurationSection.getKeys(false)) {
                ArrayList<PacketLimit> arrayList = new ArrayList<PacketLimit>();
                if (configurationSection.isList(string)) {
                    for (Map map : configurationSection.getMapList(string)) {
                        arrayList.add(this.Oy(map));
                    }
                } else {
                    ConfigurationSection configurationSection2 = configurationSection.getConfigurationSection(string);
                    if (configurationSection2 != null) {
                        arrayList.add(this.TJ(configurationSection2));
                    }
                }
                if (arrayList.isEmpty()) continue;
                this.xS(string, arrayList);
            }
        } else {
            for (String string : this.getConfig().getStringList(this.getPath("limits"))) {
                String[] stringArray = string.split(",");
                int n = stringArray.length;
                if (n < 2) {
                    ((BukkitProtocolPlayer)this.player).getLpxBukkit().getLogger().warning(() -> string + " is not a valid FloodB parameter!");
                    continue;
                }
                try {
                    int n2 = Integer.parseInt(stringArray[1]);
                    RepeatedIntervalCounter repeatedIntervalCounter = n == 2 ? new RepeatedIntervalCounter(n2, 6, 3) : (n == 3 ? new RepeatedIntervalCounter(n2, Integer.parseInt(stringArray[2]), 3, 1) : new RepeatedIntervalCounter(n2, Integer.parseInt(stringArray[2]), Integer.parseInt(stringArray[3]), Integer.parseInt(stringArray[4])));
                    this.registerFallbackLimit(stringArray[0], new PacketLimit(repeatedIntervalCounter, false, -1L, -1L, false));
                }
                catch (NumberFormatException numberFormatException) {
                    ((BukkitProtocolPlayer)this.player).getLpxBukkit().getLogger().severe("Second and third parameter must be numbers for FloodB!");
                }
            }
        }
    }

    private PacketLimit TJ(ConfigurationSection configurationSection) {
        int n = configurationSection.getInt("max", 20);
        int n2 = configurationSection.getInt("interval", 500);
        int n3 = configurationSection.getInt("periods", 5);
        int n4 = configurationSection.getInt("warnings", 2);
        boolean bl = "drop".equalsIgnoreCase(configurationSection.getString("action", "flag"));
        long l = configurationSection.getLong("min-bytes", -1L);
        long l2 = configurationSection.getLong("max-bytes", -1L);
        boolean bl2 = configurationSection.getBoolean("allow-duplicates", false);
        return new PacketLimit(new RepeatedIntervalCounter(n, n2, n3, n4), bl, l, l2, bl2);
    }

    private PacketLimit Oy(Map map) {
        int n = FloodB.Q0(map, "max", 20);
        int n2 = FloodB.Q0(map, "interval", 500);
        int n3 = FloodB.Q0(map, "periods", 5);
        int n4 = FloodB.Q0(map, "warnings", 2);
        Object v = map.get("action");
        boolean bl = "drop".equalsIgnoreCase(v != null ? String.valueOf(v) : "flag");
        long l = FloodB.LW(map, "min-bytes", -1L);
        long l2 = FloodB.LW(map, "max-bytes", -1L);
        boolean bl2 = FloodB.j2(map, "allow-duplicates", false);
        return new PacketLimit(new RepeatedIntervalCounter(n, n2, n3, n4), bl, l, l2, bl2);
    }

    private static int Q0(Map map, Object object, int n) {
        Object v = map.get(object);
        if (v instanceof Number) {
            Number number = (Number)v;
            return number.intValue();
        }
        return n;
    }

    private static long LW(Map map, Object object, long l) {
        Object v = map.get(object);
        if (v instanceof Number) {
            Number number = (Number)v;
            return number.longValue();
        }
        return l;
    }

    private static boolean j2(Map map, Object object, boolean bl) {
        Object v = map.get(object);
        if (v instanceof Boolean) {
            Boolean bl2 = (Boolean)v;
            return bl2;
        }
        if (v instanceof String) {
            String string = (String)v;
            return Boolean.parseBoolean(string);
        }
        return bl;
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (protocolPlayer.getPrinterData().isUsingPrinter()) {
            return CheckResult.ALLOW;
        }
        PacketProfile packetProfile = this.iI(protocolPlayer, packetReceiveEvent);
        List list = this.go(packetProfile);
        if (list == null) {
            return CheckResult.ALLOW;
        }
        double d = this.getWeight(packetReceiveEvent.getPacketType(), protocolPlayer);
        boolean bl = false;
        long l = System.currentTimeMillis();
        for (PacketLimit packetLimit : list) {
            if (!packetLimit.matchesSize(packetProfile.packetBytes()) || packetLimit.isRepeated(packetProfile.packetBytes(), l)) continue;
            boolean bl2 = packetLimit.counter.increment(d);
            if (bl2) {
                this.Fn(packetReceiveEvent, new FlagInfo[]{FlagInfo.of("packet", packetProfile.packetType().getName()), FlagInfo.of("max-counter", packetLimit.counter.getMaxCounter()), FlagInfo.of("interval", packetLimit.counter.getInterval() + "ms"), FlagInfo.of("max-periods", packetLimit.counter.getMaxPeriods()), FlagInfo.of("max-warnings", packetLimit.counter.getMaxWarnings())});
            }
            if (!packetLimit.E || !bl2 && !packetLimit.counter.isOverLimit()) continue;
            bl = true;
        }
        return bl ? CheckResult.DENY : CheckResult.ALLOW;
    }

    private List go(PacketProfile packetProfile) {
        return this.packetLimits.get(packetProfile.packetType());
    }

    private PacketProfile iI(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        return new PacketProfile(packetReceiveEvent.getPacketType(), this.getPacketSize(protocolPlayer));
    }

    private long getPacketSize(ProtocolPlayer protocolPlayer) {
        return protocolPlayer.getLastReceivedPacket() != null ? protocolPlayer.getLastReceivedPacket().getSize() : -1L;
    }

    private void xS(String string, Object object) {
        String string2 = FloodB.AI(string);
        PacketType.Play.Client client = this.parsePacketType(string2);
        if (client == null) {
            this.warnInvalidPacketKey(string);
            return;
        }
        this.packetLimits.put(client, (List<PacketLimit>)object);
    }

    private void registerFallbackLimit(String string, Object object) {
        String string2 = FloodB.AI(string);
        PacketType.Play.Client client = this.parsePacketType(string2);
        if (client == null) {
            this.warnInvalidPacketKey(string);
            return;
        }
        this.packetLimits.computeIfAbsent(client, packetTypeCommon -> new ArrayList()).add(object);
    }

    private PacketType.Play.Client parsePacketType(String string) {
        try {
            return PacketType.Play.Client.valueOf(string);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    private void warnInvalidPacketKey(String string) {
        ((BukkitProtocolPlayer)this.player).getLpxBukkit().getLogger().warning(() -> "Invalid FloodB packet key '" + string + "' in config, skipping it.");
    }

    private static String AI(String string) {
        return string.trim().toUpperCase(Locale.ROOT);
    }

    private double getWeight(PacketTypeCommon packetTypeCommon, ProtocolPlayer protocolPlayer) {
        if (packetTypeCommon == PacketType.Play.Client.CLICK_WINDOW || packetTypeCommon == PacketType.Play.Client.CREATIVE_INVENTORY_ACTION) {
            return protocolPlayer.itemFloodMultiplier();
        }
        return 1.0;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    record PacketProfile(PacketTypeCommon packetType, long packetBytes) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PacketProfile.class, "A;j", "packetType", "packetBytes"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PacketProfile.class, "A;j", "packetType", "packetBytes"}, this);
        }

        @Override
        public final boolean equals(Object object) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PacketProfile.class, "A;j", "packetType", "packetBytes"}, this, object);
        }
    }

    static final class PacketLimit {
        private final RepeatedIntervalCounter counter;
        private final boolean E;
        private final long D;
        private final long i;
        private final boolean m;
        private long t = Long.MIN_VALUE;
        private long z = Long.MIN_VALUE;

        private PacketLimit(RepeatedIntervalCounter repeatedIntervalCounter, boolean bl, long l, long l2, boolean bl2) {
            this.counter = repeatedIntervalCounter;
            this.E = bl;
            this.D = l;
            this.i = l2;
            this.m = bl2;
        }

        public RepeatedIntervalCounter counter() {
            return this.counter;
        }

        public boolean drop() {
            return this.E;
        }

        public boolean matchesSize(long l) {
            if (l < 0L) {
                return this.D < 0L && this.i < 0L;
            }
            if (this.D >= 0L && l < this.D) {
                return false;
            }
            return this.i < 0L || l <= this.i;
        }

        public boolean isRepeated(long l, long l2) {
            if (!this.m || l < 0L) {
                return false;
            }
            boolean bl = this.t == l && l2 - this.z <= this.counter.getInterval();
            this.t = l;
            this.z = l2;
            return bl;
        }

        private static we a(we we2) {
            return we2;
        }
    }
}

