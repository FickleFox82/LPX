/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx._c;
import java.lang.invoke.MethodHandles;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class wz
extends Enum<wz> {
    public static final /* enum */ wz HORIZONTAL;
    public static final /* enum */ wz VERTICAL;
    private static final /* synthetic */ wz[] $VALUES;
    private static PacketWrapper[] packetWrappers;

    public static wz[] values() {
        return (wz[])$VALUES.clone();
    }

    public static wz valueOf(String string) {
        return Enum.valueOf(wz.class, string);
    }

    private static /* synthetic */ wz[] $values() {
        return new wz[]{HORIZONTAL, VERTICAL};
    }

    /*
     * Enabled aggressive block sorting
     */
    static {
        long l = _c.a(-1269814869535109326L, 7486774275729970544L, MethodHandles.lookup().lookupClass()).a(258863045104456L) ^ 0x5CB133CD1026L;
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        byte[] byArray = new byte[8];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)(l >>> 56);
        for (int i = 1; i < 8; ++i) {
            byArray2 = byArray2;
            byArray2[i] = (byte)(l << i * 8 >>> 56);
        }
        cipher.init(2, (Key)secretKeyFactory.generateSecret(new DESKeySpec(byArray2)), new IvParameterSpec(new byte[8]));
        String[] stringArray = new String[2];
        int n = 0;
        String string = "0`\u00813\u0080\u00ce\u00af\u00b0c\u00f3[\u00e9k\u0007\u00c3\u00eb\u0010\u0092qb\t\u00b4\u00b0#6kC\u0094\u00df9\u00a1e\u00f5";
        int n2 = "0`\u00813\u0080\u00ce\u00af\u00b0c\u00f3[\u00e9k\u0007\u00c3\u00eb\u0010\u0092qb\t\u00b4\u00b0#6kC\u0094\u00df9\u00a1e\u00f5".length();
        int n3 = 16;
        int n4 = -1;
        while (true) {
            int n5 = ++n4;
            byte[] byArray3 = cipher.doFinal(string.substring(n5, n5 + n3).getBytes("ISO-8859-1"));
            stringArray[n++] = wz.a(byArray3).intern();
            if ((n4 += n3) >= n2) {
                HORIZONTAL = new wz();
                VERTICAL = new wz();
                $VALUES = wz.$values();
                return;
            }
            n3 = string.charAt(n4);
        }
    }

    public static void wb(PacketWrapper[] packetWrapperArray) {
    }

    public static PacketWrapper[] getPacketWrappers() {
        return packetWrappers;
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

