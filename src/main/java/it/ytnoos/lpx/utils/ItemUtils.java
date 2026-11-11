/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.utils;

import com.github.retrooper.packetevents.protocol.item.HashedStack;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx._c;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class ItemUtils {
    private static final long a;
    private static final String[] b;
    private static final String[] c;
    private static final Map d;

    private ItemUtils(long l) {
        l = a ^ l;
        throw new UnsupportedOperationException((String)((Object)ItemUtils.a("h", (int)1418, (long)(0x5E31FFA1BE9B91D5L ^ l))));
    }

    public static Optional T8(ItemStack itemStack) {
        NBTCompound nBTCompound = itemStack.getNBT();
        if (nBTCompound == null) {
            return Optional.empty();
        }
        NBTCompound nBTCompound2 = nBTCompound.getCompoundTagOrNull("display");
        if (nBTCompound2 == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(nBTCompound2.getStringTagValueOrNull("Name"));
    }

    public static Optional TT(ItemStack itemStack) {
        NBTCompound nBTCompound = itemStack.getNBT();
        if (nBTCompound == null) {
            return Optional.empty();
        }
        NBTCompound nBTCompound2 = nBTCompound.getCompoundTagOrNull("display");
        if (nBTCompound2 == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(nBTCompound2.getStringListTagOrNull("Lore"));
    }

    public static boolean isEmpty(ItemStack itemStack) {
        boolean bl = itemStack.getComponents().getPatches().isEmpty();
        NBTCompound nBTCompound = itemStack.getNBT();
        boolean bl2 = nBTCompound == null || nBTCompound.isEmpty();
        return bl && bl2;
    }

    public static boolean isEmpty(HashedStack hashedStack) {
        return hashedStack.getComponents().getAddedComponents().isEmpty();
    }

    /*
     * Unable to fully structure code
     */
    static {
        block11: {
            block10: {
                ItemUtils.a = _c.a(-4239377266867373554L, -5868198909491761447L, MethodHandles.lookup().lookupClass()).a(131052332584961L);
                ItemUtils.d = new HashMap<K, V>(13);
                var0 = ItemUtils.a ^ 137167372958921L;
                var2_1 = Cipher.getInstance("DES/CBC/PKCS5Padding");
                v0 = SecretKeyFactory.getInstance("DES");
                v1 = new byte[8];
                v2 = v1;
                v1[0] = (byte)(var0 >>> 56);
                for (var3_2 = 1; var3_2 < 8; ++var3_2) {
                    v2 = v2;
                    v2[var3_2] = (byte)(var0 << var3_2 * 8 >>> 56);
                }
                var2_1.init(2, (Key)v0.generateSecret(new DESKeySpec(v2)), new IvParameterSpec(new byte[8]));
                var9_3 = new String[5];
                var7_4 = 0;
                var6_5 = "\u008b\u009a\u00cf*\u0081ko\u0081\u008d\u00abow$y\u00a0\u0095\u00fb\u00f0\u00f8\u00f0\u00d9\u00cf\u007fdK\u0010\u00f0\u00ef\u008e<m6\u009a\u00e0\u00a8u%h\u00f6$R\u00d9H\u0096I\u00f5\\\u00a1\u0091K\u00da\u000fr\u0083\u00aa\u00a0\u001a\u0011\u00b3\u00b2\u00a7Y=\u0003\u0010\u00af\u007f-.\u00c8^x\u00be@\u0091\u00cc7\u001d\u008f\u00f4\u00ae\u0010\u00d2\u0013\u00eb=P\u0012\u0092\u00ed\u00e1+\u00f3w\u0086\u00c7\u0087\u008c";
                var8_6 = "\u008b\u009a\u00cf*\u0081ko\u0081\u008d\u00abow$y\u00a0\u0095\u00fb\u00f0\u00f8\u00f0\u00d9\u00cf\u007fdK\u0010\u00f0\u00ef\u008e<m6\u009a\u00e0\u00a8u%h\u00f6$R\u00d9H\u0096I\u00f5\\\u00a1\u0091K\u00da\u000fr\u0083\u00aa\u00a0\u001a\u0011\u00b3\u00b2\u00a7Y=\u0003\u0010\u00af\u007f-.\u00c8^x\u00be@\u0091\u00cc7\u001d\u008f\u00f4\u00ae\u0010\u00d2\u0013\u00eb=P\u0012\u0092\u00ed\u00e1+\u00f3w\u0086\u00c7\u0087\u008c".length();
                var5_7 = 64;
                var4_8 = -1;
lbl20:
                // 2 sources

                while (true) {
                    v3 = ++var4_8;
                    v4 = var6_5.substring(v3, v3 + var5_7);
                    v5 = -1;
                    break block10;
                    break;
                }
lbl25:
                // 1 sources

                while (true) {
                    var9_3[var7_4++] = ItemUtils.a(var10_9).intern();
                    if ((var4_8 += var5_7) < var8_6) {
                        var5_7 = var6_5.charAt(var4_8);
                        ** continue;
                    }
                    var6_5 = "\u0010e\u00cf[\u009c\u00e2\u00feI\u00fe\u0005\u0001i$\u0099t\u00c3\u0010\u0099J\u00cc5\u0007\u00a7\u009e\u008b[\u008d\u00d6\u00a5\u0097A\u00e5<";
                    var8_6 = "\u0010e\u00cf[\u009c\u00e2\u00feI\u00fe\u0005\u0001i$\u0099t\u00c3\u0010\u0099J\u00cc5\u0007\u00a7\u009e\u008b[\u008d\u00d6\u00a5\u0097A\u00e5<".length();
                    var5_7 = 16;
                    var4_8 = -1;
lbl34:
                    // 2 sources

                    while (true) {
                        v6 = ++var4_8;
                        v4 = var6_5.substring(v6, v6 + var5_7);
                        v5 = 0;
                        break block10;
                        break;
                    }
                    break;
                }
lbl39:
                // 1 sources

                while (true) {
                    var9_3[var7_4++] = ItemUtils.a(var10_9).intern();
                    if ((var4_8 += var5_7) < var8_6) {
                        var5_7 = var6_5.charAt(var4_8);
                        ** continue;
                    }
                    break block11;
                    break;
                }
            }
            var10_9 = var2_1.doFinal(v4.getBytes("ISO-8859-1"));
            switch (v5) {
                default: {
                    ** continue;
                }
                ** case 0:
lbl51:
                // 1 sources

                ** continue;
            }
        }
        ItemUtils.b = var9_3;
        ItemUtils.c = new String[5];
    }

    private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
        return unsupportedOperationException;
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

    private static String a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x4837;
        if (c[n2] == null) {
            Object[] objectArray;
            try {
                Long l2 = Thread.currentThread().getId();
                objectArray = (Object[])d.get(l2);
                if (objectArray == null) {
                    objectArray = new Object[]{Cipher.getInstance("DES/CBC/PKCS5Padding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    d.put(l2, objectArray);
                }
            }
            catch (Exception exception) {
                throw new RuntimeException("it/ytnoos/lpx/qf", exception);
            }
            byte[] byArray = new byte[8];
            byArray[0] = (byte)(l >>> 56);
            for (int i = 1; i < 8; ++i) {
                byArray[i] = (byte)(l << i * 8 >>> 56);
            }
            DESKeySpec dESKeySpec = new DESKeySpec(byArray);
            SecretKey secretKey = ((SecretKeyFactory)objectArray[1]).generateSecret(dESKeySpec);
            ((Cipher)objectArray[0]).init(2, (Key)secretKey, (IvParameterSpec)objectArray[2]);
            byte[] byArray2 = b[n2].getBytes("ISO-8859-1");
            ItemUtils.c[n2] = ItemUtils.a(((Cipher)objectArray[0]).doFinal(byArray2));
        }
        return c[n2];
    }

    private static Object a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        String string2 = ItemUtils.a(n, l);
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
            throw new RuntimeException("it/ytnoos/lpx/qf" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_0() {
        try {
            return MethodHandles.lookup().findStatic(ItemUtils.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }
}

