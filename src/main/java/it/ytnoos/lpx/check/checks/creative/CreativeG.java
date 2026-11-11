/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.creative;

import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CreativeG
extends PlayerCheck<WrapperPlayClientCreativeInventoryAction> {
    public CreativeG(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        int n;
        int n2;
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        NBTCompound nBTCompound = wrapperPlayClientCreativeInventoryAction.getItemStack().getNBT();
        if (nBTCompound == null) {
            return CheckResult.ALLOW;
        }
        NBTCompound nBTCompound2 = nBTCompound.getCompoundTagOrNull("BlockEntityTag");
        if (nBTCompound2 == null) {
            return CheckResult.ALLOW;
        }
        NBTNumber nBTNumber = nBTCompound2.getNumberTagOrNull("x");
        if (nBTNumber == null) {
            return CheckResult.ALLOW;
        }
        NBTNumber nBTNumber2 = nBTCompound2.getNumberTagOrNull("z");
        if (nBTNumber2 == null) {
            return CheckResult.ALLOW;
        }
        try {
            n2 = nBTNumber.getAsInt();
            n = nBTNumber2.getAsInt();
        }
        catch (Exception exception) {
            return CheckResult.ALLOW;
        }
        int n3 = n2 / 16;
        int n4 = n / 16;
        World world = player.getWorld();
        if (!world.isChunkLoaded(n3, n4)) {
            this.Fn(wrapperPlayClientCreativeInventoryAction, new FlagInfo[]{FlagInfo.of("world", world.getName()), FlagInfo.of("x", n2), FlagInfo.of("z", n), FlagInfo.of("chunk-x", n3), FlagInfo.of("chunk-z", n4)});
        }
        return CheckResult.ALLOW;
    }
}

