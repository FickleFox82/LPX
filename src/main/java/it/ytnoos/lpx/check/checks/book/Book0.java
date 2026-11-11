/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.book;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.nbt.NBTType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.util.ArrayList;

public class Book0
extends PlayerCheck<WrapperPlayClientEditBook> {
    public Book0(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        Object object;
        NBTCompound nBTCompound;
        ItemStack itemStack = wrapperPlayClientEditBook.getItemStack();
        Object object2 = new NBTList<NBTString>(NBTType.STRING);
        if (itemStack == null) {
            nBTCompound = new NBTCompound();
            for (String object3 : wrapperPlayClientEditBook.getPages()) {
                ((NBTList)object2).addTag(new NBTString(object3));
            }
            nBTCompound.setTag("pages", (NBT)object2);
            itemStack = ItemStack.builder().type(ItemTypes.WRITABLE_BOOK).nbt(nBTCompound).amount(1).build();
        } else {
            nBTCompound = itemStack.getNBT();
            if (nBTCompound != null) {
                object = nBTCompound.getStringListTagOrNull("pages");
                if (object != null) {
                    object2 = object;
                }
            } else {
                nBTCompound = new NBTCompound();
                itemStack.setNBT(nBTCompound);
            }
        }
        protocolPlayer.setProcessingPayload(true);
        try {
            object = protocolPlayer.handleChecks(NBTDecodeEvent.class, new NBTDecodeEvent(wrapperPlayClientEditBook, null, nBTCompound), this.isPreVia()).result();
            if (object == CheckResult.DENY) {
                Object object3 = object;
                return object3;
            }
            if ((object = ((CheckResult)((Object)object)).merge(protocolPlayer.handleChecks(ItemStackDecodeEvent.class, new ItemStackDecodeEvent(wrapperPlayClientEditBook, null, itemStack), this.isPreVia()).result())) == CheckResult.DENY) {
                Object object4 = object;
                return object4;
            }
            if (object == CheckResult.CHANGE) {
                ArrayList<String> arrayList = new ArrayList<String>();
                for (NBTString nBTString : ((NBTList)object2).getTags()) {
                    arrayList.add(nBTString.getValue());
                }
                wrapperPlayClientEditBook.setPages(arrayList);
            }
            Object object5 = object;
            return object5;
        }
        finally {
            protocolPlayer.setProcessingPayload(false);
        }
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

