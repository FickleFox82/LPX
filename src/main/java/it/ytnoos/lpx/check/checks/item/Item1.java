/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.checks.item.DisableDecodingType;
import it.ytnoos.lpx.check.checks.item.NbtSizeLimit;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Item1
extends PlayerCheck<ItemStackDecodeEvent> {
    private static final String[] DEFAULT_COMPONENTS = new String[]{"custom_name", "lore", "enchantments"};
    private DisableDecodingType disableItemDecoding;
    private NbtSizeLimit nbtSizeLimit;
    private Set<String> creativeExempts;

    public Item1(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.disableItemDecoding = DisableDecodingType.CD(this.getOptions().getCheckOptions().getConfig().getString("options.disable-item-decoding", DisableDecodingType.DEFAULT.name()));
        this.nbtSizeLimit = NbtSizeLimit.bb();
        this.creativeExempts = new HashSet<String>();
        this.creativeExempts.addAll(this.getOptions().getCheckOptions().getConfig().isSet("options.creative-exempts") ? this.getOptions().getCheckOptions().getConfig().getStringList("options.creative-exempts") : Arrays.asList(DEFAULT_COMPONENTS));
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null || protocolPlayer.isProcessingPayload()) {
            return CheckResult.ALLOW;
        }
        boolean bl = player.getGameMode() == GameMode.CREATIVE;
        boolean bl2 = itemStackDecodeEvent.getItemStack().getDecodedSizeBytes() >= this.nbtSizeLimit.getLimit(protocolPlayer);
        switch (this.disableItemDecoding) {
            case NONE: {
                return CheckResult.ALLOW;
            }
            case SMART: {
                if (bl2 && !bl) break;
                return CheckResult.ALLOW;
            }
            case ALL: {
                if (!bl) break;
                return CheckResult.ALLOW;
            }
        }
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        itemStack.setNBT(null);
        if (bl && this.disableItemDecoding == DisableDecodingType.CREATIVE) {
            itemStack.getComponents().getPatches().keySet().removeIf(componentType -> !this.creativeExempts.contains(componentType.getName().getKey()));
        } else {
            itemStack.setComponents(null);
        }
        itemStackDecodeEvent.setItemStack(itemStack);
        return CheckResult.CHANGE;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

