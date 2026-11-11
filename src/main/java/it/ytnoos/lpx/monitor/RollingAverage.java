/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.monitor;

import it.ytnoos.lpx._c;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Key;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class RollingAverage {
    private final BigDecimal[] samples;
    private final int R;
    private int O = 0;
    private int z = 0;
    private static final long a = _c.a(8686105358667700620L, -6518805881484361373L, MethodHandles.lookup().lookupClass()).a(209533644865090L);
    private static final String b;
    private static final long c;

    public RollingAverage(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(b);
        }
        this.R = n;
        this.samples = new BigDecimal[n];
    }

    public void add(BigDecimal bigDecimal) {
        this.samples[this.O] = bigDecimal;
        this.O = (this.O + 1) % this.R;
        if (this.z < this.R) {
            ++this.z;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public double getAverage(long l) {
        long l2 = l;
        l2 = a ^ l2;
        if (this.z == 0) {
            return 0.0;
        }
        BigDecimal bigDecimal = BigDecimal.ZERO;
        int n = 0;
        while (true) {
            BigDecimal bigDecimal2;
            if (n < this.z) {
                bigDecimal2 = bigDecimal;
            } else {
                bigDecimal2 = bigDecimal;
                if (l2 > 0L) {
                    return bigDecimal2.divide(new BigDecimal(this.z), 30, RoundingMode.HALF_UP).doubleValue();
                }
            }
            if (6585810834011133426L > l2) {
                bigDecimal2 = bigDecimal2.add(this.samples[n]);
            }
            bigDecimal = bigDecimal2;
            ++n;
        }
    }

    public BigDecimal Ku() {
        boolean bl = false;
        if (this.z == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = this.samples[0];
        for (int i = 1; i < this.z; ++i) {
            if (this.samples[i].compareTo(bigDecimal) >= 0) continue;
            bigDecimal = this.samples[i];
            if (!bl) continue;
        }
        return bigDecimal;
    }

    public BigDecimal KG() {
        boolean bl = false;
        if (this.z == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = this.samples[0];
        for (int i = 1; i < this.z; ++i) {
            if (this.samples[i].compareTo(bigDecimal) <= 0) continue;
            bigDecimal = this.samples[i];
            if (!bl) continue;
        }
        return bigDecimal;
    }

    public BigDecimal getPercentile(double d) {
        if (this.z == 0) {
            return BigDecimal.ZERO;
        }
        Object[] objectArray = new BigDecimal[this.z];
        System.arraycopy(this.samples, 0, objectArray, 0, this.z);
        Arrays.sort(objectArray);
        int n = (int)Math.ceil(d / 100.0 * (double)this.z);
        return objectArray[Math.max(0, Math.min(n - 1, this.z - 1))];
    }

    public int getCount() {
        return this.z;
    }

    /*
     * Enabled aggressive block sorting
     */
    static {
        long l = a ^ 0x149177AB6A55L;
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
        byte[] byArray3 = cipher.doFinal("tn\u00fa\u0088\f\u00deU\u00a3\u0091=\u000e\u00a4?%\u0011Wa\u00b9+\u0090PGq+".getBytes("ISO-8859-1"));
        b = RollingAverage.a(byArray3).intern();
        Cipher cipher2 = Cipher.getInstance("DES/CBC/NoPadding");
        SecretKeyFactory secretKeyFactory2 = SecretKeyFactory.getInstance("DES");
        byte[] byArray4 = new byte[8];
        byte[] byArray5 = byArray4;
        byArray4[0] = (byte)(l >>> 56);
        int n = 1;
        while (true) {
            if (n >= 8) {
                cipher2.init(2, (Key)secretKeyFactory2.generateSecret(new DESKeySpec(byArray5)), new IvParameterSpec(new byte[8]));
                long l2 = -8765666996962327258L;
                byte[] byArray6 = cipher2.doFinal(new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2});
                c = ((long)byArray6[0] & 0xFFL) << 56 | ((long)byArray6[1] & 0xFFL) << 48 | ((long)byArray6[2] & 0xFFL) << 40 | ((long)byArray6[3] & 0xFFL) << 32 | ((long)byArray6[4] & 0xFFL) << 24 | ((long)byArray6[5] & 0xFFL) << 16 | ((long)byArray6[6] & 0xFFL) << 8 | (long)byArray6[7] & 0xFFL;
                return;
            }
            byArray5 = byArray5;
            byArray5[n] = (byte)(l << n * 8 >>> 56);
            ++n;
        }
    }

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
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

