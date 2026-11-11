/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 */
package it.ytnoos.lpx.common.check;

import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemBees;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import it.ytnoos.lpx._c;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.check.options.PersonalCheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.we;
import java.lang.invoke.MethodHandles;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class PlayerCheck<T>
implements Check<T> {
    protected final ProtocolPlayer player;
    protected final PersonalCheckOptions options;
    private static String[] t;
    private static final long z;
    private static final String PATH_SEPARATOR;

    protected PlayerCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        this.player = protocolPlayer;
        this.options = new PersonalCheckOptions(checkOptions);
        this.loadOptions();
    }

    private void flag(Object object, FlagInfo[] flagInfoArray) {
        this.player.flag(this, object, flagInfoArray);
    }

    protected boolean Fn(Object object, FlagInfo[] flagInfoArray) {
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        this.options.increaseVl();
        this.options.setBuffer(this.options.getBuffer() * (double)this.options.getCheckOptions().getBufferMultiplier());
        this.flag(object, flagInfoArray2);
        if (this.options.getVl() >= this.options.getCheckOptions().getMaxVL()) {
            if (this.options.getCheckOptions().isPunish()) {
                this.player.punish(this);
            }
            this.options.setVl(0);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        this.options.reset();
    }

    protected boolean F6(Object object, FlagInfo[] flagInfoArray) {
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        return this.addBuffer(object, 1.0, flagInfoArray2);
    }

    protected boolean addBuffer(Object object, double d, FlagInfo[] flagInfoArray) {
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        this.options.setBuffer(Math.min((double)this.options.getCheckOptions().getMaxBuffer(), this.options.getBuffer() + d));
        if (this.options.getBuffer() >= (double)this.options.getCheckOptions().getMaxBuffer()) {
            this.Fn(object, flagInfoArray2);
            return true;
        }
        return false;
    }

    protected void zl() {
        if (this.options.getBuffer() <= 0.0) {
            return;
        }
        this.options.setBuffer(Math.max(0.0, this.options.getBuffer() - (double)this.options.getCheckOptions().getBufferDecay()));
    }

    protected abstract CheckResult check2(ProtocolPlayer var1, WrapperPlayClientEditBook var2);

    @Override
    public void loadOptions() {
    }

    @Override
    public CheckResult handle(T t) {
        if (this.options.getCheckOptions().isEnabled() && !this.player.ignoreChecks()) {
            return this.check2(this.player, (WrapperPlayClientEditBook)t);
        }
        return CheckResult.ALLOW;
    }

    @Override
    public PersonalCheckOptions getOptions() {
        return this.options;
    }

    @Override
    public CheckType getType() {
        return this.options.getCheckOptions().getType();
    }

    protected String getPath(String string) {
        return this.options.getCheckOptions().getPath() + PATH_SEPARATOR + string;
    }

    protected FileConfiguration getConfig() {
        return this.options.getCheckOptions().getConfig();
    }

    protected boolean getBoolean(String string, boolean bl) {
        return this.getConfig().getBoolean(this.getPath(string), bl);
    }

    protected int getInt(String string, int n) {
        return this.getConfig().getInt(this.getPath(string), n);
    }

    protected long getLong(String string, long l) {
        return this.getConfig().getLong(this.getPath(string), l);
    }

    protected double getDouble(String string, double d) {
        return this.getConfig().getDouble(this.getPath(string), d);
    }

    protected List getStringList(String string, String[] stringArray) {
        String string2 = this.getPath(string);
        if (this.getConfig().isSet(string2)) {
            return this.getConfig().getStringList(string2);
        }
        return new ArrayList<String>(Arrays.asList(stringArray));
    }

    protected void handleNBT(long l, ItemStack itemStack, Consumer consumer) {
        long l2 = l;
        l2 = z ^ l2;
        if (itemStack == null) {
            return;
        }
        NBTCompound nBTCompound = itemStack.getNBT();
        if (l2 >= 1L) {
            // empty if block
        }
        if (nBTCompound != null) {
            consumer.accept(nBTCompound);
        }
        itemStack.getComponent(ComponentTypes.CUSTOM_DATA).ifPresent(consumer);
        itemStack.getComponent(ComponentTypes.ENTITY_DATA).ifPresent(consumer);
        itemStack.getComponent(ComponentTypes.BUCKET_ENTITY_DATA).ifPresent(consumer);
        itemStack.getComponent(ComponentTypes.BLOCK_ENTITY_DATA).ifPresent(consumer);
        itemStack.getComponent(ComponentTypes.BEES).ifPresent(itemBees -> {
            for (ItemBees.BeeEntry beeEntry : itemBees.getBees()) {
                consumer.accept(beeEntry.getEntityData());
            }
        });
        itemStack.getComponent(ComponentTypes.DEBUG_STICK_STATE).ifPresent(debugStickState -> {});
    }

    public static void _f(String[] stringArray) {
    }

    public static String[] zN() {
        return t;
    }

    /*
     * Enabled aggressive block sorting
     */
    static {
        z = _c.a(-3174215763872042634L, 8482857004403749137L, MethodHandles.lookup().lookupClass()).a(213391283956661L);
        String[] cfr_ignored_0 = new String[3];
        long l = z ^ 0x3067BEFC7FF5L;
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        byte[] byArray = new byte[8];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)(l >>> 56);
        int n = 1;
        while (true) {
            if (n >= 8) {
                cipher.init(2, (Key)secretKeyFactory.generateSecret(new DESKeySpec(byArray2)), new IvParameterSpec(new byte[8]));
                byte[] byArray3 = cipher.doFinal("\u00b1\u0019\tQW\u0088$T\u00e9\u00d2\u0015\u00b9\u009cVP\u00af".getBytes("ISO-8859-1"));
                PATH_SEPARATOR = PlayerCheck.a(byArray3).intern();
                return;
            }
            byArray2 = byArray2;
            byArray2[n] = (byte)(l << n * 8 >>> 56);
            ++n;
        }
    }

    private static we b(we we2) {
        return we2;
    }

    private static String a(byte[] byArray) {
        int n = 0;
        int n2 = byArray.length;
        char[] cArray = new char[n2];
        for (int i = 0; i < n2; ++i) {
            char c;
            int n3 = 0xFF & byArray[i];
            if (n3 < 192) {
                cArray[n++] = (char)n3;
                continue;
            }
            if (n3 < 224) {
                c = (char)((char)(n3 & 0x1F) << 6);
                n3 = byArray[++i];
                c = (char)(c | (char)(n3 & 0x3F));
                cArray[n++] = c;
                continue;
            }
            if (i >= n2 - 2) continue;
            c = (char)((char)(n3 & 0xF) << 12);
            n3 = byArray[++i];
            c = (char)(c | (char)(n3 & 0x3F) << 6);
            n3 = byArray[++i];
            c = (char)(c | (char)(n3 & 0x3F));
            cArray[n++] = c;
        }
        return new String(cArray, 0, n);
    }
}

