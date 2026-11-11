/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandlerContext
 */
package it.ytnoos.lpx.common.utils;

import com.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTByte;
import com.github.retrooper.packetevents.protocol.nbt.NBTByteArray;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import com.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import com.github.retrooper.packetevents.protocol.nbt.NBTInt;
import com.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTLong;
import com.github.retrooper.packetevents.protocol.nbt.NBTLongArray;
import com.github.retrooper.packetevents.protocol.nbt.NBTShort;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import it.ytnoos.lpx._c;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class NettyUtils {
    private static final long a = _c.a(8959606608779926359L, -3737479101959035411L, MethodHandles.lookup().lookupClass()).a(276077265567103L);
    private static final String[] b;
    private static final String[] c;
    private static final Map d;
    private static final long[] e;
    private static final Integer[] f;
    private static final Map g;

    private NettyUtils() {
    }

    public static int VQ(NBTCompound nBTCompound, int n, long l) {
        long l2 = l;
        long l3 = (l2 = a ^ l2) ^ 0x4F9445A9FBA9L;
        if (nBTCompound == null) {
            return 0;
        }
        int n2 = 0;
        for (Map.Entry<String, NBT> entry : nBTCompound.getTags().entrySet()) {
            if (0L < l2) {
                n2 += entry.getKey().getBytes(StandardCharsets.UTF_8).length;
            }
            n2 += NettyUtils.r7(l3, entry.getValue(), n);
        }
        return n2;
    }

    public static int r7(long l, NBT nBT, int n) {
        long l2 = l;
        int n2 = n;
        long l3 = l2 = a ^ l2;
        long l4 = l3 ^ 0x4ADED0BB8245L;
        long l5 = l3 ^ 0x4F9445A9FBA9L;
        Object var12_7 = null;
        if (n2 >= NettyUtils.b("a", (int)10547, (long)(0x52FCEA9E020106EEL ^ l2))) {
            throw new RuntimeException((String)((Object)NettyUtils.a("r", (int)30345, (long)(0x57B66A2ED3E03042L ^ l2))));
        }
        NBT nBT2 = nBT;
        if (l2 > 1L) {
            if (nBT2 instanceof NBTCompound) {
                return NettyUtils.VQ((NBTCompound)nBT, ++n2, l5);
            }
            nBT2 = nBT;
        }
        Object v2 = var12_7;
        if (l2 >= 1L) {
            if (nBT2 instanceof NBTList) {
                int n3 = 0;
                for (NBT nBT3 : ((NBTList)nBT).getTags()) {
                    if (0L > l2) continue;
                    n3 += NettyUtils.r7(l4, nBT3, n2);
                }
                return n3;
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (0L < l2) {
            if (nBT2 instanceof NBTByte) {
                return 1;
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 > 0L) {
            if (nBT2 instanceof NBTByteArray) {
                return ((NBTByteArray)nBT).getValue().length;
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 > 1L) {
            if (nBT2 instanceof NBTDouble) {
                return (int)NettyUtils.b("a", (int)26649, (long)(0x76670BE3F2A047C7L ^ l2));
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 > 1L) {
            if (nBT2 instanceof NBTFloat) {
                return 4;
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 > 0L) {
            if (nBT2 instanceof NBTInt) {
                return 4;
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 >= 1L) {
            if (nBT2 instanceof NBTIntArray) {
                return ((NBTIntArray)nBT).getValue().length * 4;
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 >= 1L) {
            if (nBT2 instanceof NBTLong) {
                return (int)NettyUtils.b("a", (int)27472, (long)(0x6DF5E91C54AF448CL ^ l2));
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 > 1L) {
            if (nBT2 instanceof NBTLongArray) {
                return ((NBTLongArray)nBT).getValue().length * NettyUtils.b("a", (int)27472, (long)(0x6DF5E91C54AF448CL ^ l2));
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (l2 >= 0L) {
            if (nBT2 instanceof NBTShort) {
                return 2;
            }
            nBT2 = nBT;
            v2 = var12_7;
        }
        if (nBT2 instanceof NBTString) {
            return ((NBTString)nBT).getValue().getBytes(StandardCharsets.UTF_8).length;
        }
        throw new RuntimeException((String)((Object)NettyUtils.a("r", (int)16900, (long)(0x7B5475907F9A84CEL ^ l2))) + nBT.getClass().getSimpleName());
    }

    public static int f2(long l, ItemStack itemStack) {
        long l2 = l;
        long l3 = (l2 = a ^ l2) ^ 0x71C68C7D18CFL;
        return NettyUtils.VQ(itemStack.getNBT(), 0, l3);
    }

    public static boolean containsString(NBTCompound nBTCompound, String string) {
        for (NBT nBT : nBTCompound.getTags().values()) {
            if (!NettyUtils.containsString(nBT, string)) continue;
            return true;
        }
        return false;
    }

    public static boolean containsString(NBT nBT, String string) {
        if (nBT instanceof NBTString) {
            return ((NBTString)nBT).getValue().contains(string);
        }
        if (nBT instanceof NBTCompound) {
            return NettyUtils.containsString((NBTCompound)nBT, string);
        }
        if (nBT instanceof NBTList) {
            for (NBT nBT2 : ((NBTList)nBT).getTags()) {
                if (!NettyUtils.containsString(nBT2, string)) continue;
                return true;
            }
        }
        return false;
    }

    public static void closeChannel(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.channel().close();
        channelHandlerContext.channel().flush();
    }

    public static void closeChannel(Channel channel) {
        channel.close();
        channel.flush();
    }

    public static void KJ(ByteBuf byteBuf) {
        byteBuf.readBytes(byteBuf.readableBytes());
    }

    public static void Km(ByteBuf byteBuf) {
        ByteBuf byteBuf2 = byteBuf;
        if (byteBuf2.refCnt() >= 1) {
            byteBuf2 = byteBuf2.readBytes(byteBuf2.readableBytes());
        }
        NettyUtils.KO(byteBuf2);
    }

    public static void KO(ByteBuf byteBuf) {
        if (byteBuf.refCnt() > 0) {
            byteBuf.release(byteBuf.refCnt());
        }
    }

    public static long getSize(Object object) {
        return ByteBufHelper.capacity(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    static {
        d = new HashMap(13);
        long l = a ^ 0x3A299B582974L;
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
        String string = "\u00d0@\u00b9\u00ac\u0086H/\u001a\u001d\u0015p\u0098H\u00ef\u00ad\"\u00d5*\u00c8_@^\u0084\u00e4\u00a5\u00a4\u00ac\u00cb\u00d6:1X\u00c2x\u00cfu\u0011\u0090\u00c2s \u0094\u00a2\u001b\u0087\u0003[\u009d\u00fb*>\u009e\u0007\u00a6 \u00e6%\u00d0\u00e0\u00b0kT\u00e7K\u008b\u00fd\u00a5X\u001b\u0001e%2";
        int n2 = "\u00d0@\u00b9\u00ac\u0086H/\u001a\u001d\u0015p\u0098H\u00ef\u00ad\"\u00d5*\u00c8_@^\u0084\u00e4\u00a5\u00a4\u00ac\u00cb\u00d6:1X\u00c2x\u00cfu\u0011\u0090\u00c2s \u0094\u00a2\u001b\u0087\u0003[\u009d\u00fb*>\u009e\u0007\u00a6 \u00e6%\u00d0\u00e0\u00b0kT\u00e7K\u008b\u00fd\u00a5X\u001b\u0001e%2".length();
        int n3 = 40;
        int n4 = -1;
        while (true) {
            int n5 = ++n4;
            byte[] byArray3 = cipher.doFinal(string.substring(n5, n5 + n3).getBytes("ISO-8859-1"));
            stringArray[n++] = NettyUtils.a(byArray3).intern();
            if ((n4 += n3) >= n2) break;
            n3 = string.charAt(n4);
        }
        b = stringArray;
        c = new String[2];
        g = new HashMap(13);
        Cipher cipher2 = Cipher.getInstance("DES/CBC/NoPadding");
        SecretKeyFactory secretKeyFactory2 = SecretKeyFactory.getInstance("DES");
        byte[] byArray4 = new byte[8];
        byte[] byArray5 = byArray4;
        byArray4[0] = (byte)(l >>> 56);
        for (int i = 1; i < 8; ++i) {
            byArray5 = byArray5;
            byArray5[i] = (byte)(l << i * 8 >>> 56);
        }
        cipher2.init(2, (Key)secretKeyFactory2.generateSecret(new DESKeySpec(byArray5)), new IvParameterSpec(new byte[8]));
        long[] lArray = new long[3];
        int n6 = 0;
        String string2 = "\u00e5@\u00c7V\u00ca\u00a9FU\u00f3e\u0018\u008fV\u0006\u001f\u00caZ\u0012\u0098z\u0007Q\u00f4\u0003";
        int n7 = "\u00e5@\u00c7V\u00ca\u00a9FU\u00f3e\u0018\u008fV\u0006\u001f\u00caZ\u0012\u0098z\u0007Q\u00f4\u0003".length();
        int n8 = 0;
        do {
            byte[] byArray6 = string2.substring(n8, n8 += 8).getBytes("ISO-8859-1");
            int n9 = n6++;
            long l2 = ((long)byArray6[0] & 0xFFL) << 56 | ((long)byArray6[1] & 0xFFL) << 48 | ((long)byArray6[2] & 0xFFL) << 40 | ((long)byArray6[3] & 0xFFL) << 32 | ((long)byArray6[4] & 0xFFL) << 24 | ((long)byArray6[5] & 0xFFL) << 16 | ((long)byArray6[6] & 0xFFL) << 8 | (long)byArray6[7] & 0xFFL;
            byte[] byArray7 = cipher2.doFinal(new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2});
            lArray[n9] = ((long)byArray7[0] & 0xFFL) << 56 | ((long)byArray7[1] & 0xFFL) << 48 | ((long)byArray7[2] & 0xFFL) << 40 | ((long)byArray7[3] & 0xFFL) << 32 | ((long)byArray7[4] & 0xFFL) << 24 | ((long)byArray7[5] & 0xFFL) << 16 | ((long)byArray7[6] & 0xFFL) << 8 | (long)byArray7[7] & 0xFFL;
        } while (n8 < n7);
        e = lArray;
        f = new Integer[3];
    }

    private static RuntimeException a(RuntimeException runtimeException) {
        return runtimeException;
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
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x5FFF;
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
                throw new RuntimeException("it/ytnoos/lpx/i7", exception);
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
            NettyUtils.c[n2] = NettyUtils.a(((Cipher)objectArray[0]).doFinal(byArray2));
        }
        return c[n2];
    }

    private static Object a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        String string2 = NettyUtils.a(n, l);
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
            throw new RuntimeException("it/ytnoos/lpx/i7" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    private static int b(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x36E9;
        if (f[n2] == null) {
            byte[] byArray;
            byte[] byArray2 = new byte[]{(byte)(l >>> 56), (byte)(l >>> 48), (byte)(l >>> 40), (byte)(l >>> 32), (byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l};
            long l2 = e[n2];
            byte[] byArray3 = new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2};
            Long l3 = Thread.currentThread().getId();
            Object[] objectArray = (Object[])g.get(l3);
            try {
                if (objectArray == null) {
                    objectArray = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    g.put(l3, objectArray);
                }
                DESKeySpec dESKeySpec = new DESKeySpec(byArray2);
                SecretKey secretKey = ((SecretKeyFactory)objectArray[1]).generateSecret(dESKeySpec);
                Cipher cipher = (Cipher)objectArray[0];
                cipher.init(2, (Key)secretKey, (IvParameterSpec)objectArray[2]);
                byArray = cipher.doFinal(byArray3);
            }
            catch (Exception exception) {
                throw new RuntimeException("it/ytnoos/lpx/i7", exception);
            }
            int n3 = (byArray[4] & 0xFF) << 24 | (byArray[5] & 0xFF) << 16 | (byArray[6] & 0xFF) << 8 | byArray[7] & 0xFF;
            NettyUtils.f[n2] = n3;
        }
        return f[n2];
    }

    private static int b(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        int n2 = NettyUtils.b(n, l);
        MethodHandle methodHandle = MethodHandles.constant(Integer.TYPE, n2);
        mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, Integer.TYPE, Long.TYPE));
        return n2;
    }

    private static CallSite b(MethodHandles.Lookup lookup, String string, MethodType methodType) {
        MutableCallSite mutableCallSite = new MutableCallSite(methodType);
        try {
            mutableCallSite.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(cfr_ldc_1().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string), methodType));
        }
        catch (Exception exception) {
            throw new RuntimeException("it/ytnoos/lpx/i7" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_0() {
        try {
            return MethodHandles.lookup().findStatic(NettyUtils.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_1() {
        try {
            return MethodHandles.lookup().findStatic(NettyUtils.class, "b", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }
}

