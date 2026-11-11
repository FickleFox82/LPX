/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.player.BukkitProtocolPlayer;
import it.ytnoos.lpx.utils.string.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class ItemE
extends PlayerCheck<NBTDecodeEvent> {
    private static final String[] DEFAULT_OCCURRENCES = new String[]{"translate,3", "with,3", "extra,15", "{,15"};
    private boolean clear;
    private Map<String, Integer> occurrences;

    public ItemE(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.clear = this.getBoolean("clear", true);
        this.occurrences = new HashMap<String, Integer>();
        for (String string : this.getStringList("occurrences", DEFAULT_OCCURRENCES)) {
            String[] stringArray = string.split(",");
            int n = stringArray.length;
            if (n < 2) {
                ((BukkitProtocolPlayer)this.player).getLpxBukkit().getLogger().warning(() -> string + " is not a valid ItemE parameter!");
                continue;
            }
            try {
                this.occurrences.put(stringArray[0], Integer.parseInt(stringArray[1]));
            }
            catch (NumberFormatException numberFormatException) {
                ((BukkitProtocolPlayer)this.player).getLpxBukkit().getLogger().severe("Second parameter for ItemE must be a number!");
            }
        }
    }

    public CheckResult OP(Object object, NBTDecodeEvent nBTDecodeEvent) {
        if (!(nBTDecodeEvent.getNbt() instanceof NBTCompound)) {
            return CheckResult.ALLOW;
        }
        NBTList<NBTString> nBTList = ((NBTCompound)nBTDecodeEvent.getNbt()).getStringListTagOrNull("pages");
        if (nBTList == null) {
            return CheckResult.ALLOW;
        }
        for (int i = 0; i < nBTList.size(); ++i) {
            NBTString nBTString = nBTList.getTag(i);
            String string = nBTString.getValue();
            for (Map.Entry<String, Integer> entry : this.occurrences.entrySet()) {
                int n = StringUtils.countMatches(string, entry.getKey());
                if (n <= entry.getValue()) continue;
                if (this.clear) {
                    nBTString.setValue("");
                }
                this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("pages", nBTList.size()), FlagInfo.of("page-index", i), FlagInfo.of("page-length", string.getBytes().length), FlagInfo.of("occurrence", entry.getKey()), FlagInfo.of("matches", n)});
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }
}

