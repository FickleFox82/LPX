/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.utils;

import com.github.retrooper.packetevents.wrapper.e1;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCraftRecipeRequest;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import it.ytnoos.lpx._c;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public abstract class ItemUtils_w7<T>
extends PlayerCheck<T> {
    private static List<?> Z;
    private static String B;
    private static final String[] a;
    private static final String[] b;
    private static final Map c;

    public ItemUtils_w7(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public CheckResult check2(ProtocolPlayer protocolPlayer, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        long l = 55871527293617L;
        if (Z == null) {
            return CheckResult.ALLOW;
        }
        int n = this.getRecipeId2((WrapperPlayClientCraftRecipeRequest)((Object)wrapperPlayClientEditBook));
        int n2 = Z.size();
        if (n < 0 || n >= n2) {
            this.Fn(wrapperPlayClientEditBook, new FlagInfo[]{FlagInfo.of((String)((Object)ItemUtils_w7.a("l", (int)24015, (long)(0x4FCA282837372001L ^ l))), n), FlagInfo.of((String)((Object)ItemUtils_w7.a("l", (int)23962, (long)(0x5150448EFEFF2055L ^ l))), n2)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    public abstract int getRecipeId2(WrapperPlayClientCraftRecipeRequest var1);

    /*
     * Unable to fully structure code
     */
    static {
        block13: {
            block12: {
                var9 = _c.a(2574631836881896634L, -8107688862622377857L, MethodHandles.lookup().lookupClass()).a(77689374345803L) ^ 32902030012521L;
                ItemUtils_w7.c = new HashMap<K, V>(13);
                var0_1 = Cipher.getInstance("DES/CBC/PKCS5Padding");
                v0 = SecretKeyFactory.getInstance("DES");
                v1 = new byte[8];
                v2 = v1;
                v1[0] = (byte)(var9 >>> 56);
                for (var1_2 = 1; var1_2 < 8; ++var1_2) {
                    v2 = v2;
                    v2[var1_2] = (byte)(var9 << var1_2 * 8 >>> 56);
                }
                var0_1.init(2, (Key)v0.generateSecret(new DESKeySpec(v2)), new IvParameterSpec(new byte[8]));
                var7_3 = new String[6];
                var5_4 = 0;
                var4_5 = "n\u00cb\u0003\u00b1\u00bd\u0017\u00a8\u00a5\u00a2\u00b5\u00f7\u00b9lx\u008b\u00cb\u00c1V\u008c1r\u00e8\u00a4u+\u0019\u00c4\u00a5\u00f7@&mH\u0010X&\u00fb\u00ed\u00e5\u0014\u00fb\u00f2g\u00c7\u00a4\u00ab\u00bb\"mm\u00be\u00e2\u00c4\u00b9\u0094\u00ff\u0007\u0000\u00d6\u00b6\u00fc \u0005\u00dfE\u0018\u00aboe\t\u00a2\u00b9\u0084Ja'\u0083\n`\u00c7E\u000eju\u00fdK\u0091\u00c1\u00ae\u00f3\u00c9tiB\u00fc^>D\u009b\u00d5\u00a5\u00a4!\u00d6\u00a1 \u001a\u00d0\u00866\u00b6\u00f4\u008e\u0092\u001b\u00a3\u00efu\u00fa.G\u00ec\u00e50\u00a0\u00c8Z\u0092\u00c5\u00a8\u00b4b.\u00f0C\u00e9)<\u0018T.\u0017\u00e9\u00f7\u00b4\u00e8\u00fc\u00e6\u00df\u00d0\\\u009e\u0091\u00ee5\u00d2j\u00cb\"\u00a1Pe\u0017";
                var6_6 = "n\u00cb\u0003\u00b1\u00bd\u0017\u00a8\u00a5\u00a2\u00b5\u00f7\u00b9lx\u008b\u00cb\u00c1V\u008c1r\u00e8\u00a4u+\u0019\u00c4\u00a5\u00f7@&mH\u0010X&\u00fb\u00ed\u00e5\u0014\u00fb\u00f2g\u00c7\u00a4\u00ab\u00bb\"mm\u00be\u00e2\u00c4\u00b9\u0094\u00ff\u0007\u0000\u00d6\u00b6\u00fc \u0005\u00dfE\u0018\u00aboe\t\u00a2\u00b9\u0084Ja'\u0083\n`\u00c7E\u000eju\u00fdK\u0091\u00c1\u00ae\u00f3\u00c9tiB\u00fc^>D\u009b\u00d5\u00a5\u00a4!\u00d6\u00a1 \u001a\u00d0\u00866\u00b6\u00f4\u008e\u0092\u001b\u00a3\u00efu\u00fa.G\u00ec\u00e50\u00a0\u00c8Z\u0092\u00c5\u00a8\u00b4b.\u00f0C\u00e9)<\u0018T.\u0017\u00e9\u00f7\u00b4\u00e8\u00fc\u00e6\u00df\u00d0\\\u009e\u0091\u00ee5\u00d2j\u00cb\"\u00a1Pe\u0017".length();
                var3_7 = 32;
                var2_8 = -1;
lbl19:
                // 2 sources

                while (true) {
                    v3 = ++var2_8;
                    v4 = var4_5.substring(v3, v3 + var3_7);
                    v5 = -1;
                    break block12;
                    break;
                }
lbl24:
                // 1 sources

                while (true) {
                    var7_3[var5_4++] = ItemUtils_w7.b(var8_9).intern();
                    if ((var2_8 += var3_7) < var6_6) {
                        var3_7 = var4_5.charAt(var2_8);
                        ** continue;
                    }
                    var4_5 = "B6\u008b\u0002Y\u00ca\u0099\u0007\u009d\u0094\u00de\u00a7c.\n\u00e0\u00d4Q\u001e\u00f1\u001e\u0018\u0010\u00d3(3\u00e1\u00be\u00845\u00b9\u0004H9R\u0080Z{\u00b42U\u00acRF+\u00de\u00be.\u0090\u00d3Q\u0097\\3r\u00b3\u00e9\u00c9h%)U\u0011\u0090\u00a5";
                    var6_6 = "B6\u008b\u0002Y\u00ca\u0099\u0007\u009d\u0094\u00de\u00a7c.\n\u00e0\u00d4Q\u001e\u00f1\u001e\u0018\u0010\u00d3(3\u00e1\u00be\u00845\u00b9\u0004H9R\u0080Z{\u00b42U\u00acRF+\u00de\u00be.\u0090\u00d3Q\u0097\\3r\u00b3\u00e9\u00c9h%)U\u0011\u0090\u00a5".length();
                    var3_7 = 24;
                    var2_8 = -1;
lbl33:
                    // 2 sources

                    while (true) {
                        v6 = ++var2_8;
                        v4 = var4_5.substring(v6, v6 + var3_7);
                        v5 = 0;
                        break block12;
                        break;
                    }
                    break;
                }
lbl38:
                // 1 sources

                while (true) {
                    var7_3[var5_4++] = ItemUtils_w7.b(var8_9).intern();
                    if ((var2_8 += var3_7) < var6_6) {
                        var3_7 = var4_5.charAt(var2_8);
                        ** continue;
                    }
                    break block13;
                    break;
                }
            }
            var8_9 = var0_1.doFinal(v4.getBytes("ISO-8859-1"));
            switch (v5) {
                default: {
                    ** continue;
                }
                ** case 0:
lbl50:
                // 1 sources

                ** continue;
            }
        }
        ItemUtils_w7.a = var7_3;
        ItemUtils_w7.b = new String[6];
        try {
            var11_10 = Class.forName((String)ItemUtils_w7.a("l", (int)13429, (long)(2622613139759668370L ^ var9)));
            var12_12 = var11_10.getMethod((String)ItemUtils_w7.a("l", (int)14097, (long)(4110105563125817335L ^ var9)), new Class[0]).invoke(null, new Object[0]);
            v7 = new Class[]{};
            v8 = var12_12.getClass();
            var13_13 = v8.getMethod(e1.b((String)ItemUtils_w7.a("l", (int)32430, (long)(7759753062182835789L ^ var9)), v8, v7), v7).invoke(var12_12, new Object[0]);
            v9 = var13_13.getClass();
            var14_14 = v9.getDeclaredField(e1.c(v9, (String)ItemUtils_w7.a("l", (int)18733, (long)(5880534611615605199L ^ var9))));
            var14_14.setAccessible(true);
            ItemUtils_w7.Z = (List)var14_14.get(var13_13);
        }
        catch (Exception var11_11) {
            var11_11.printStackTrace();
        }
    }

    public static void BS(String string) {
    }

    public static String tq() {
        return null;
    }

    private static we a(we we2) {
        return we2;
    }

    private static String b(byte[] byArray) {
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

    private static String a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x6F7C;
        if (b[n2] == null) {
            Object[] objectArray;
            try {
                Long l2 = Thread.currentThread().getId();
                objectArray = (Object[])c.get(l2);
                if (objectArray == null) {
                    objectArray = new Object[]{Cipher.getInstance("DES/CBC/PKCS5Padding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    c.put(l2, objectArray);
                }
            }
            catch (Exception exception) {
                throw new RuntimeException("it/ytnoos/lpx/w7", exception);
            }
            byte[] byArray = new byte[8];
            byArray[0] = (byte)(l >>> 56);
            for (int i = 1; i < 8; ++i) {
                byArray[i] = (byte)(l << i * 8 >>> 56);
            }
            DESKeySpec dESKeySpec = new DESKeySpec(byArray);
            SecretKey secretKey = ((SecretKeyFactory)objectArray[1]).generateSecret(dESKeySpec);
            ((Cipher)objectArray[0]).init(2, (Key)secretKey, (IvParameterSpec)objectArray[2]);
            byte[] byArray2 = a[n2].getBytes("ISO-8859-1");
            ItemUtils_w7.b[n2] = ItemUtils_w7.b(((Cipher)objectArray[0]).doFinal(byArray2));
        }
        return b[n2];
    }

    private static Object a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        String string2 = ItemUtils_w7.a(n, l);
        MethodHandle methodHandle = MethodHandles.constant(String.class, string2);
        mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, Integer.TYPE, Long.TYPE));
        return string2;
    }

    private static CallSite a(MethodHandles.Lookup lookup, String string, MethodType methodType) {
        MutableCallSite mutableCallSite = new MutableCallSite(methodType);
        try {
            mutableCallSite.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(cfr_ldc_0().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string), methodType));
        }
        catch (Exception exception) {
            throw new RuntimeException("it/ytnoos/lpx/w7" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_0() {
        try {
            return MethodHandles.lookup().findStatic(ItemUtils_w7.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }
}

