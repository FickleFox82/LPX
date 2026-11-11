/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.protocol;

import com.github.retrooper.packetevents.protocol.component.ComponentType;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTByteArray;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTLongArray;
import com.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReceivedPacket {
    private static final ThreadLocal<NumberFormat> FORMAT;
    private static final int MAX_ORIGINAL_NBT;
    private final String playerName;
    private final ClientVersion clientVersion;
    private final long createdAtNanos;
    private final long receivedAtTick;
    private final PacketTypeCommon packetType;
    private final long size;
    private Object packet;
    private long elapsedTime;
    private String packetString;
    private List<NBT> originalNbtData;

    public ReceivedPacket(String string, ClientVersion clientVersion, long l, PacketTypeCommon packetTypeCommon, long l2, Object object) {
        this.playerName = string;
        this.clientVersion = clientVersion;
        this.receivedAtTick = l;
        this.packetType = packetTypeCommon;
        this.createdAtNanos = System.nanoTime();
        this.size = l2;
        this.packet = object;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public long getReceivedAtTick() {
        return this.receivedAtTick;
    }

    public ClientVersion getClientVersion() {
        return this.clientVersion;
    }

    public String getClientVersionName() {
        return this.clientVersion.getReleaseName();
    }

    public PacketTypeCommon getPacketType() {
        return this.packetType;
    }

    public Object getPacket() {
        return this.packet;
    }

    public void setPacket(Object object) {
        this.packet = object;
    }

    public long getSize() {
        return this.size;
    }

    public void addOriginalNbt(NBT nBT) {
        if (this.originalNbtData == null) {
            this.originalNbtData = new ArrayList<NBT>();
        }
        if (this.originalNbtData.size() < 1000) {
            this.originalNbtData.add(nBT);
        }
    }

    public long getElapsedTime() {
        if (this.elapsedTime == 0L) {
            this.elapsedTime = System.nanoTime() - this.createdAtNanos;
        }
        return this.elapsedTime;
    }

    public String getFormattedElapsedTime() {
        return FORMAT.get().format((double)this.getElapsedTime() / 1000000.0);
    }

    public String toString() {
        if (this.packetString == null) {
            this.packetString = this.buildPacketString().replace('\n', ' ');
            this.originalNbtData = null;
        }
        return this.packetString;
    }

    private String buildPacketString() {
        if (this.packet == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : this.packet.getClass().getDeclaredFields()) {
            if (!field.getDeclaringClass().equals(this.packet.getClass())) continue;
            try {
                field.setAccessible(true);
                Object object = field.get(this.packet);
                stringBuilder.append(field.getName()).append("=");
                ReceivedPacket.appendValue(object, stringBuilder);
                stringBuilder.append(" ");
            }
            catch (IllegalAccessException illegalAccessException) {}
        }
        if (this.originalNbtData != null && !this.originalNbtData.isEmpty()) {
            stringBuilder.append("originalNbt=[");
            boolean bl = true;
            for (NBT nBT : this.originalNbtData) {
                if (!bl) {
                    stringBuilder.append(", ");
                }
                bl = false;
                ReceivedPacket.appendNbt(nBT, stringBuilder);
            }
            stringBuilder.append("] ");
        }
        return stringBuilder.toString();
    }

    private static void appendValue(Object object, StringBuilder stringBuilder) {
        if (object instanceof ItemStack) {
            ItemStack itemStack = (ItemStack)object;
            ReceivedPacket.appendItemStack(itemStack, stringBuilder);
        } else if (object instanceof Map) {
            Map map = (Map)object;
            ReceivedPacket.appendMap(map, stringBuilder);
        } else if (object instanceof Optional) {
            Optional optional = (Optional)object;
            if (optional.isPresent()) {
                ReceivedPacket.appendValue(optional.get(), stringBuilder);
            } else {
                stringBuilder.append("empty");
            }
        } else if (object instanceof Object[]) {
            Object[] objectArray = (Object[])object;
            ReceivedPacket.appendArray(objectArray, stringBuilder);
        } else if (object instanceof NBT) {
            NBT nBT = (NBT)object;
            ReceivedPacket.appendNbt(nBT, stringBuilder);
        } else if (object instanceof byte[]) {
            byte[] byArray = (byte[])object;
            stringBuilder.append(Arrays.toString(byArray));
        } else {
            stringBuilder.append(object);
        }
    }

    private static void appendItemStack(ItemStack itemStack, StringBuilder stringBuilder) {
        stringBuilder.append("[type=").append(itemStack.getType().getName());
        NBTCompound nBTCompound = itemStack.getNBT();
        if (nBTCompound != null && !nBTCompound.isEmpty()) {
            stringBuilder.append(", nbt=");
            ReceivedPacket.appendNbt(nBTCompound, stringBuilder);
        }
        if (itemStack.hasComponentPatches()) {
            ReceivedPacket.appendComponentPatches(itemStack, stringBuilder);
        }
        stringBuilder.append("]");
    }

    private static void appendComponentPatches(ItemStack itemStack, StringBuilder stringBuilder) {
        ClientVersion clientVersion = itemStack.getVersion();
        for (Map.Entry<ComponentType<?>, Optional<?>> entry : itemStack.getComponents().getPatches().entrySet()) {
            stringBuilder.append(", ").append(entry.getKey().getName()).append("=");
            if (entry.getValue().isPresent()) {
                try {
                    ComponentType<?> componentType = entry.getKey();
                    ReceivedPacket.appendNbt(componentType.encode(entry.getValue().get(), clientVersion), stringBuilder);
                }
                catch (Exception exception) {
                    stringBuilder.append(entry.getValue().get());
                }
                continue;
            }
            stringBuilder.append("REMOVED");
        }
    }

    private static void appendMap(Map<?, ?> map, StringBuilder stringBuilder) {
        stringBuilder.append("{");
        boolean bl = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            bl = false;
            stringBuilder.append(entry.getKey()).append("=");
            ReceivedPacket.appendValue(entry.getValue(), stringBuilder);
        }
        stringBuilder.append("}");
    }

    private static void appendArray(Object[] objectArray, StringBuilder stringBuilder) {
        if (objectArray.length == 0) {
            return;
        }
        stringBuilder.append("[");
        boolean bl = true;
        for (Object object : objectArray) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            bl = false;
            ReceivedPacket.appendValue(object, stringBuilder);
        }
        stringBuilder.append("]");
    }

    private static void appendNbt(NBT nBT, StringBuilder stringBuilder) {
        if (nBT instanceof NBTCompound) {
            NBTCompound nBTCompound = (NBTCompound)nBT;
            if (nBTCompound.isEmpty()) {
                return;
            }
            stringBuilder.append("[");
            boolean bl = true;
            for (Map.Entry<String, NBT> entry : nBTCompound.getTags().entrySet()) {
                if (!bl) {
                    stringBuilder.append(", ");
                }
                bl = false;
                stringBuilder.append(entry.getKey()).append("=");
                ReceivedPacket.appendNbt(entry.getValue(), stringBuilder);
            }
            stringBuilder.append("]");
        } else if (nBT instanceof NBTList) {
            NBTList nBTList = (NBTList)nBT;
            if (nBTList.isEmpty()) {
                stringBuilder.append("[]");
                return;
            }
            boolean bl = true;
            for (NBT nBT2 : nBTList.getTags()) {
                if (!bl) {
                    stringBuilder.append(", ");
                }
                bl = false;
                ReceivedPacket.appendNbt(nBT2, stringBuilder);
            }
        } else if (nBT instanceof NBTNumber) {
            NBTNumber nBTNumber = (NBTNumber)nBT;
            stringBuilder.append(nBTNumber.getAsNumber());
        } else if (nBT instanceof NBTString) {
            NBTString nBTString = (NBTString)nBT;
            stringBuilder.append(nBTString.getValue());
        } else if (nBT instanceof NBTByteArray) {
            NBTByteArray nBTByteArray = (NBTByteArray)nBT;
            stringBuilder.append(Arrays.toString(nBTByteArray.getValue()));
        } else if (nBT instanceof NBTIntArray) {
            NBTIntArray nBTIntArray = (NBTIntArray)nBT;
            stringBuilder.append(Arrays.toString(nBTIntArray.getValue()));
        } else if (nBT instanceof NBTLongArray) {
            NBTLongArray nBTLongArray = (NBTLongArray)nBT;
            stringBuilder.append(Arrays.toString(nBTLongArray.getValue()));
        } else {
            stringBuilder.append(nBT);
        }
    }

    static {
        MAX_ORIGINAL_NBT = 1000;
        FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("0.000"));
    }
}

