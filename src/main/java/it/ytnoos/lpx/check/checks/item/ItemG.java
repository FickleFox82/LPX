/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.util.Map;

public class ItemG
extends PlayerCheck<NBTDecodeEvent> {
    private static final int D = 200;
    private int Q;
    private int k;
    private boolean legacyClient;

    public ItemG(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.Q = this.getConfig().getInt(this.getPath("max-translate"), 1);
        this.k = this.getConfig().getInt(this.getPath("max-keybind"), 1);
        this.legacyClient = this.player.getUser().getClientVersion().isOlderThan(ClientVersion.V_1_16) && PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_16);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, NBTDecodeEvent nBTDecodeEvent) {
        if (protocolPlayer.usesComponents()) {
            return CheckResult.ALLOW;
        }
        return this.checkNbt("", nBTDecodeEvent.getNbt(), nBTDecodeEvent);
    }

    private CheckResult checkNbt(String string, NBT nBT, NBTDecodeEvent nBTDecodeEvent) {
        if (nBT instanceof NBTString) {
            NBTString nBTString = (NBTString)nBT;
            return this.checkString(string, nBTString, nBTDecodeEvent);
        }
        if (nBT instanceof NBTList) {
            NBTList nBTList = (NBTList)nBT;
            for (NBT nBT2 : nBTList.getTags()) {
                CheckResult checkResult = this.checkNbt(string, nBT2, nBTDecodeEvent);
                if (checkResult != CheckResult.DENY) continue;
                return checkResult;
            }
            return CheckResult.ALLOW;
        }
        if (nBT instanceof NBTCompound) {
            NBTCompound nBTCompound = (NBTCompound)nBT;
            for (Map.Entry<String, NBT> entry : nBTCompound.getTags().entrySet()) {
                CheckResult checkResult = this.checkNbt(entry.getKey(), entry.getValue(), nBTDecodeEvent);
                if (checkResult != CheckResult.DENY) continue;
                return checkResult;
            }
            return CheckResult.ALLOW;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult checkString(String string, NBTString nBTString, NBTDecodeEvent nBTDecodeEvent) {
        int n;
        String string2 = nBTString.getValue();
        if (string2.isEmpty() || string2.charAt(0) == '\"') {
            return CheckResult.ALLOW;
        }
        if (!string2.contains("{") || !string2.contains("}")) {
            return CheckResult.ALLOW;
        }
        int n2 = this.countKey(string2, "translate", this.Q + 1);
        if (n2 == -1) {
            this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string), FlagInfo.of("visited", "translate")});
            return CheckResult.DENY;
        }
        if (n2 > this.Q) {
            n = this.countKey(string2, "with", this.Q + 1);
            if (n == -1) {
                this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string), FlagInfo.of("visited", "with")});
                return CheckResult.DENY;
            }
            if (n > this.Q) {
                this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string), FlagInfo.of("translate", n2), FlagInfo.of("with", n)});
                return CheckResult.DENY;
            }
        }
        if ((n = this.countKey(string2, "keybind", this.k + 1)) == -1) {
            this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string), FlagInfo.of("visited", "keybind")});
            return CheckResult.DENY;
        }
        if (n > this.k) {
            this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string), FlagInfo.of("keybind", n)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private int countKey(String string, String string2, int n) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = string2.length();
        while ((n3 = string.indexOf(string2, n3)) != -1) {
            boolean bl;
            if (++n4 > 200) {
                return -1;
            }
            int n6 = n3 > 0 ? (int)string.charAt(n3 - 1) : 123;
            int n7 = n3 + n5 < string.length() ? (int)string.charAt(n3 + n5) : 32;
            boolean bl2 = bl = n6 == 34 && (n7 == 34 || n7 == 92);
            if (!bl && this.legacyClient) {
                boolean bl3 = bl = n6 == 39 && n7 == 39 || ItemG.isSeparator(n6) && n7 == 58;
            }
            if (bl && ++n2 >= n) {
                return n2;
            }
            n3 += n5;
        }
        return n2;
    }

    private static boolean isSeparator(int n) {
        return n == 123 || n == 44 || n == 32 || n == 10 || n == 9;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

