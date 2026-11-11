/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.utils.string;

import it.ytnoos.lpx._c;
import it.ytnoos.lpx.utils.string.StringUtils;
import it.ytnoos.lpx.we;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class CharSequenceUtils {
    private static final int l = -1;
    static final int g;
    public static final char[] EMPTY_CHAR_ARRAY;
    private static final long a;
    private static final long[] b;
    private static final Integer[] c;
    private static final Map d;

    public static CharSequence subSequence(CharSequence charSequence, int n) {
        return charSequence == null ? null : charSequence.subSequence(n, charSequence.length());
    }

    /*
     * Enabled aggressive block sorting
     */
    static int lastIndexOf(CharSequence charSequence, long l, int n, int n2) {
        block11: {
            int n3;
            char[] cArray;
            int n4;
            Object var10_6;
            long l2;
            block12: {
                int n5;
                block10: {
                    int n6;
                    block9: {
                        l2 = l;
                        n5 = n2;
                        l2 = a ^ l2;
                        var10_6 = null;
                        if (charSequence instanceof String) {
                            return ((String)charSequence).indexOf(n, n5);
                        }
                        n4 = charSequence.length();
                        n6 = n5;
                        if (l2 >= 0L) {
                            if (n6 < 0) {
                                n5 = 0;
                            }
                            n6 = n;
                        }
                        Object v1 = var10_6;
                        if (l2 <= -525592704148416197L) break block9;
                        if (n6 < CharSequenceUtils.a("t", (int)17547, (long)(0x161CEF9AF355C621L ^ l2))) break block10;
                        n6 = n;
                        v1 = var10_6;
                    }
                    if (n6 > CharSequenceUtils.a("t", (int)9182, (long)(0x721BB1135A75A175L ^ l2))) break block11;
                    cArray = Character.toChars(n);
                    n3 = n5;
                    break block12;
                }
                int n7 = n5;
                while (true) {
                    if (n7 >= n4 && l2 >= 0L) {
                        return -1;
                    }
                    char c = charSequence.charAt(n7);
                    Object v3 = var10_6;
                    if (1L < l2) {
                        v3 = var10_6;
                    }
                    if (c == n) {
                        return n7;
                    }
                    ++n7;
                }
            }
            while (n3 < n4 - 1) {
                char c = charSequence.charAt(n3);
                char c2 = charSequence.charAt(n3 + 1);
                Object v4 = var10_6;
                if (1L > l2) continue;
                if (c == cArray[0] && c2 == cArray[1]) {
                    return n3;
                }
                ++n3;
                v4 = var10_6;
            }
        }
        return -1;
    }

    static int indexOf(CharSequence charSequence, CharSequence charSequence2, int n) {
        if (charSequence instanceof String) {
            return ((String)charSequence).indexOf(charSequence2.toString(), n);
        }
        if (charSequence instanceof StringBuilder) {
            return ((StringBuilder)charSequence).indexOf(charSequence2.toString(), n);
        }
        return charSequence instanceof StringBuffer ? ((StringBuffer)charSequence).indexOf(charSequence2.toString(), n) : charSequence.toString().indexOf(charSequence2.toString(), n);
    }

    /*
     * Enabled aggressive block sorting
     */
    static int WL(long l, CharSequence charSequence, int n, int n2) {
        block14: {
            int n3;
            char[] cArray;
            Object var10_6;
            long l2;
            block15: {
                int n4;
                block13: {
                    int n5;
                    int n6;
                    block12: {
                        n4 = n2;
                        l2 = l;
                        l2 = a ^ l2;
                        var10_6 = null;
                        if (charSequence instanceof String) {
                            return ((String)charSequence).lastIndexOf(n, n4);
                        }
                        n6 = charSequence.length();
                        n5 = n4;
                        if (1L < l2) {
                            if (n5 < 0) {
                                return -1;
                            }
                            n5 = n4;
                        }
                        Object v1 = var10_6;
                        if (l2 >= 0L) {
                            if (n5 >= n6) {
                                n4 = n6 - 1;
                            }
                            n5 = n;
                            v1 = var10_6;
                        }
                        if (l2 < 0L) break block12;
                        if (n5 < CharSequenceUtils.a("t", (int)25707, (long)(0x4E62D9EBA2AA946AL ^ l2))) break block13;
                        n5 = n;
                        v1 = var10_6;
                    }
                    if (n5 > CharSequenceUtils.a("t", (int)4056, (long)(0x7D58CD58C8F2FFDBL ^ l2))) break block14;
                    cArray = Character.toChars(n);
                    if (n4 == n6 - 1) {
                        return -1;
                    }
                    n3 = n4;
                    break block15;
                }
                int n7 = n4;
                while (true) {
                    if (n7 < 0 && l2 > 1L) {
                        return -1;
                    }
                    char c = charSequence.charAt(n7);
                    Object v3 = var10_6;
                    if (-5089478024608341691L <= l2) {
                        v3 = var10_6;
                    }
                    if (c == n) {
                        return n7;
                    }
                    --n7;
                }
            }
            while (n3 >= 0) {
                char c = charSequence.charAt(n3);
                char c2 = charSequence.charAt(n3 + 1);
                Object v4 = var10_6;
                if (l2 <= 0L) continue;
                if (cArray[0] == c && cArray[1] == c2) {
                    return n3;
                }
                --n3;
                v4 = var10_6;
            }
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    static int W2(CharSequence var0, CharSequence var1_1, long var2_2, int var4_3) {
        block23: {
            block25: {
                block27: {
                    block26: {
                        block24: {
                            var8_4 = var4_3;
                            var6_5 = var2_2;
                            var10_6 = (var6_5 = CharSequenceUtils.a ^ var6_5) ^ 54154250691400L;
                            var12_7 = null;
                            if (var1_1 == null) break block23;
                            v0 = var0;
                            v1 = var12_7;
                            if (1L > var6_5) break block24;
                            if (v0 == null) break block23;
                            v0 = var1_1;
                            v1 = var12_7;
                        }
                        if (v0 instanceof String) {
                            v2 = var0;
                            if (var6_5 >= 1L) {
                                if (v2 instanceof String) {
                                    return ((String)var0).lastIndexOf((String)var1_1, var8_4);
                                }
                                v2 = var0;
                            }
                            v3 = var12_7;
                            if (0L <= var6_5) {
                                if (v2 instanceof StringBuilder) {
                                    return ((StringBuilder)var0).lastIndexOf((String)var1_1, var8_4);
                                }
                                v2 = var0;
                                v3 = var12_7;
                            }
                            if (v2 instanceof StringBuffer) {
                                return ((StringBuffer)var0).lastIndexOf((String)var1_1, var8_4);
                            }
                        }
                        var13_8 = var0.length();
                        var14_9 = var1_1.length();
                        v4 = var8_4;
                        if (1L < var6_5) {
                            if (v4 > var13_8) {
                                var8_4 = var13_8;
                            }
                            v4 = var8_4;
                        }
                        if (v4 < 0 || var14_9 < 0 || var14_9 > var13_8) break block25;
                        v5 = var14_9;
                        if (7753333181260211238L >= var6_5) {
                            if (v5 == 0) {
                                return var8_4;
                            }
                            v5 = var14_9;
                        }
                        if (v5 <= CharSequenceUtils.a("t", (int)10976, (long)(1118265615117133237L ^ var6_5))) {
                            v6 = var0;
                            if (0L < var6_5) {
                                if (v6 instanceof String) {
                                    return ((String)var0).lastIndexOf(var1_1.toString(), var8_4);
                                }
                                v6 = var0;
                            }
                            if (v6 instanceof StringBuilder) {
                                return ((StringBuilder)var0).lastIndexOf(var1_1.toString(), var8_4);
                            }
                            if (var0 instanceof StringBuffer) {
                                return ((StringBuffer)var0).lastIndexOf(var1_1.toString(), var8_4);
                            }
                        }
                        v7 = var8_4;
                        if (var6_5 > 0L) {
                            v7 = v7 + var14_9;
                        }
                        v8 = var13_8;
                        if (var6_5 < 1L) break block26;
                        if (v7 <= v8) break block27;
                        v7 = var13_8;
                        v8 = var14_9;
                    }
                    var8_4 = v7 - v8;
                }
                var15_10 = var1_1.charAt(0);
                var16_11 = var8_4;
                do lbl-1000:
                // 3 sources

                {
                    v9 = var0.charAt(var16_11);
                    while (v9 == var15_10) {
                        do {
                            if (CharSequenceUtils.YA(var10_6, var0, var1_1, var14_9, var16_11)) {
                                return var16_11;
                            }
                            if (--var16_11 >= 0) ** GOTO lbl-1000
                        } while (var6_5 < 0L);
                        v9 = '\uffffffff';
                        if (var6_5 <= 0L) continue;
                        return v9;
                    }
                } while (--var16_11 >= 0);
                return -1;
            }
            return -1;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean YA(long l, CharSequence charSequence, CharSequence charSequence2, int n, int n2) {
        long l2 = l;
        l2 = a ^ l2;
        int n3 = 1;
        Object var12_7 = null;
        int n4 = n - 1;
        while (n3 <= n4 || l2 > 5547775329308438684L) {
            char c = charSequence.charAt(n2 + n3);
            Object v1 = var12_7;
            if (l2 > 1L) {
                v1 = var12_7;
            }
            if (c != charSequence2.charAt(n3) || charSequence.charAt(n2 + n4) != charSequence2.charAt(n4)) {
                return false;
            }
            ++n3;
            --n4;
        }
        return true;
    }

    public static char[] MT(CharSequence charSequence) {
        int n = StringUtils.length(charSequence);
        if (n == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        if (charSequence instanceof String) {
            return ((String)charSequence).toCharArray();
        }
        char[] cArray = new char[n];
        for (int i = 0; i < n; ++i) {
            cArray[i] = charSequence.charAt(i);
        }
        return cArray;
    }

    static boolean regionMatches(CharSequence charSequence, boolean bl, int n, CharSequence charSequence2, int n2, int n3) {
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return ((String)charSequence).regionMatches(bl, n, (String)charSequence2, n2, n3);
        }
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        int n7 = charSequence.length() - n;
        int n8 = charSequence2.length() - n2;
        if (n >= 0 && n2 >= 0 && n3 >= 0) {
            if (n7 >= n3 && n8 >= n3) {
                while (n6-- > 0) {
                    char c;
                    char c2;
                    char c3;
                    if ((c3 = charSequence.charAt(n4++)) == (c2 = charSequence2.charAt(n5++))) continue;
                    if (!bl) {
                        return false;
                    }
                    char c4 = Character.toUpperCase(c3);
                    if (c4 == (c = Character.toUpperCase(c2)) || Character.toLowerCase(c4) == Character.toLowerCase(c)) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     */
    static {
        block9: {
            block8: {
                CharSequenceUtils.a = _c.a(2925121920706407923L, 2389134768007479264L, MethodHandles.lookup().lookupClass()).a(268919091920807L);
                CharSequenceUtils.d = new HashMap<K, V>(13);
                var0 = CharSequenceUtils.a ^ 13328133771183L;
                var2_1 = Cipher.getInstance("DES/CBC/NoPadding");
                v0 = SecretKeyFactory.getInstance("DES");
                v1 = new byte[8];
                v2 = v1;
                v1[0] = (byte)(var0 >>> 56);
                for (var3_2 = 1; var3_2 < 8; ++var3_2) {
                    v2 = v2;
                    v2[var3_2] = (byte)(var0 << var3_2 * 8 >>> 56);
                }
                var2_1.init(2, (Key)v0.generateSecret(new DESKeySpec(v2)), new IvParameterSpec(new byte[8]));
                var8_3 = new long[6];
                var5_4 = 0;
                var6_5 = "i\u00df\u009f\u00d2\u00dd\u0084 s\u001e'\u0019\u0004\u00d3(\u0016\u0098\u00db\"\u00ce\u00a6\u00e6\u009a\u00f3\u00ba-9@9\u009bD\u00a5\u00a6";
                var7_6 = "i\u00df\u009f\u00d2\u00dd\u0084 s\u001e'\u0019\u0004\u00d3(\u0016\u0098\u00db\"\u00ce\u00a6\u00e6\u009a\u00f3\u00ba-9@9\u009bD\u00a5\u00a6".length();
                var4_7 = 0;
                while (true) {
                    var9_8 = var6_5.substring(var4_7, var4_7 += 8).getBytes("ISO-8859-1");
                    v3 = var8_3;
                    v4 = var5_4++;
                    v5 = ((long)var9_8[0] & 255L) << 56 | ((long)var9_8[1] & 255L) << 48 | ((long)var9_8[2] & 255L) << 40 | ((long)var9_8[3] & 255L) << 32 | ((long)var9_8[4] & 255L) << 24 | ((long)var9_8[5] & 255L) << 16 | ((long)var9_8[6] & 255L) << 8 | (long)var9_8[7] & 255L;
                    v6 = -1;
                    break block8;
                    break;
                }
lbl26:
                // 1 sources

                while (true) {
                    v3[v4] = v7;
                    if (var4_7 < var7_6) ** continue;
                    var6_5 = "\u0007g\u00f0\u00eb\u009b\u0001j`\u00bc\u0013\u0004e}[\u001bI";
                    var7_6 = "\u0007g\u00f0\u00eb\u009b\u0001j`\u00bc\u0013\u0004e}[\u001bI".length();
                    var4_7 = 0;
                    while (true) {
                        var9_8 = var6_5.substring(var4_7, var4_7 += 8).getBytes("ISO-8859-1");
                        v3 = var8_3;
                        v4 = var5_4++;
                        v5 = ((long)var9_8[0] & 255L) << 56 | ((long)var9_8[1] & 255L) << 48 | ((long)var9_8[2] & 255L) << 40 | ((long)var9_8[3] & 255L) << 32 | ((long)var9_8[4] & 255L) << 24 | ((long)var9_8[5] & 255L) << 16 | ((long)var9_8[6] & 255L) << 8 | (long)var9_8[7] & 255L;
                        v6 = 0;
                        break block8;
                        break;
                    }
                    break;
                }
lbl39:
                // 1 sources

                while (true) {
                    v3[v4] = v7;
                    if (var4_7 < var7_6) ** continue;
                    break block9;
                    break;
                }
            }
            var10_9 = v5;
            var12_10 = var2_1.doFinal(new byte[]{(byte)(var10_9 >>> 56), (byte)(var10_9 >>> 48), (byte)(var10_9 >>> 40), (byte)(var10_9 >>> 32), (byte)(var10_9 >>> 24), (byte)(var10_9 >>> 16), (byte)(var10_9 >>> 8), (byte)var10_9});
            v7 = ((long)var12_10[0] & 255L) << 56 | ((long)var12_10[1] & 255L) << 48 | ((long)var12_10[2] & 255L) << 40 | ((long)var12_10[3] & 255L) << 32 | ((long)var12_10[4] & 255L) << 24 | ((long)var12_10[5] & 255L) << 16 | ((long)var12_10[6] & 255L) << 8 | (long)var12_10[7] & 255L;
            switch (v6) {
                default: {
                    ** continue;
                }
                ** case 0:
lbl52:
                // 1 sources

                ** continue;
            }
        }
        CharSequenceUtils.b = var8_3;
        CharSequenceUtils.c = new Integer[6];
        CharSequenceUtils.g = 16;
        CharSequenceUtils.EMPTY_CHAR_ARRAY = new char[0];
    }

    private static we a(we we2) {
        return we2;
    }

    private static int a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x41A3;
        if (c[n2] == null) {
            byte[] byArray;
            byte[] byArray2 = new byte[]{(byte)(l >>> 56), (byte)(l >>> 48), (byte)(l >>> 40), (byte)(l >>> 32), (byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l};
            long l2 = b[n2];
            byte[] byArray3 = new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2};
            Long l3 = Thread.currentThread().getId();
            Object[] objectArray = (Object[])d.get(l3);
            try {
                if (objectArray == null) {
                    objectArray = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    d.put(l3, objectArray);
                }
                DESKeySpec dESKeySpec = new DESKeySpec(byArray2);
                SecretKey secretKey = ((SecretKeyFactory)objectArray[1]).generateSecret(dESKeySpec);
                Cipher cipher = (Cipher)objectArray[0];
                cipher.init(2, (Key)secretKey, (IvParameterSpec)objectArray[2]);
                byArray = cipher.doFinal(byArray3);
            }
            catch (Exception exception) {
                throw new RuntimeException("it/ytnoos/lpx/qs", exception);
            }
            int n3 = (byArray[4] & 0xFF) << 24 | (byArray[5] & 0xFF) << 16 | (byArray[6] & 0xFF) << 8 | byArray[7] & 0xFF;
            CharSequenceUtils.c[n2] = n3;
        }
        return c[n2];
    }

    private static int a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        int n2 = CharSequenceUtils.a(n, l);
        MethodHandle methodHandle = MethodHandles.constant(Integer.TYPE, n2);
        mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, Integer.TYPE, Long.TYPE));
        return n2;
    }

    private static CallSite a(MethodHandles.Lookup lookup, String string, MethodType methodType) {
        MutableCallSite mutableCallSite = new MutableCallSite(methodType);
        try {
            mutableCallSite.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(cfr_ldc_0().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string), methodType));
        }
        catch (Exception exception) {
            throw new RuntimeException("it/ytnoos/lpx/qs" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_0() {
        try {
            return MethodHandles.lookup().findStatic(CharSequenceUtils.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }
}

