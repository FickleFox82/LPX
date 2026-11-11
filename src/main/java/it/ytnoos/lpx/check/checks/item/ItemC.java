/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class ItemC
extends PlayerCheck<NBTDecodeEvent> {
    private boolean stripDisallowed;

    public ItemC(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    private static String stripDisallowed(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (!ItemC.isValidChar(c)) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static boolean isValidChar(int n) {
        if (n < 32) {
            return false;
        }
        return switch (Character.getType((char)n)) {
            case 0, 15, 16, 18, 19 -> false;
            default -> true;
        };
    }

    private static boolean isValid(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (ItemC.isValidChar(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    public void loadOptions() {
        this.stripDisallowed = this.getBoolean("remove-ascii", true);
    }

    public CheckResult HA(Object object, NBTDecodeEvent nBTDecodeEvent) {
        NBT nBT = nBTDecodeEvent.getNbt();
        if (!(nBT instanceof NBTCompound)) {
            return CheckResult.ALLOW;
        }
        NBTList<NBTString> nBTList = ((NBTCompound)nBT).getStringListTagOrNull("pages");
        if (nBTList == null) {
            return CheckResult.ALLOW;
        }
        boolean bl = false;
        for (int i = 0; i < nBTList.size(); ++i) {
            NBTString nBTString = nBTList.getTag(i);
            String string = nBTString.getValue();
            if (ItemC.isValid(nBTString.getValue())) continue;
            if (this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("pages", nBTList.size()), FlagInfo.of("page-index", i), FlagInfo.of("page-length", string.length())})) {
                return CheckResult.DENY;
            }
            if (!this.stripDisallowed) continue;
            nBTString.setValue(ItemC.stripDisallowed(string));
            bl = true;
        }
        return bl ? CheckResult.CHANGE : CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

