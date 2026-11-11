/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPickItemFromBlock;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.checks.item.DisableDecodingType;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Item2
extends PlayerCheck<WrapperPlayClientPickItemFromBlock> {
    private DisableDecodingType disableDecodingType;
    private static final String DISABLE_DECODING_PATH = "options.disable-item-decoding";

    public Item2(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.disableDecodingType = DisableDecodingType.CD(this.getOptions().getCheckOptions().getConfig().getString(DISABLE_DECODING_PATH, DisableDecodingType.DEFAULT.name()));
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPickItemFromBlock wrapperPlayClientPickItemFromBlock) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (this.disableDecodingType == DisableDecodingType.CREATIVE && player.getGameMode() == GameMode.CREATIVE) {
            wrapperPlayClientPickItemFromBlock.setIncludeData(false);
            return CheckResult.CHANGE;
        }
        return CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

