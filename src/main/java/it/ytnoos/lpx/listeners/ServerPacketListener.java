/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.listeners;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketEvent;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.manager.server.VersionComparison;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTabComplete;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ItemStripConfig;
import it.ytnoos.lpx.listeners.LPXListener;
import it.ytnoos.lpx.we;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerPacketListener
extends PacketListenerAbstract {
    private final LPXBukkit lpxBukkit;
    private final LPXListener lpxListener;
    private final boolean J;
    private final boolean N;
    private static final String b = "/lpx";

    public ServerPacketListener(LPXBukkit lPXBukkit, LPXListener lPXListener) {
        this.lpxBukkit = lPXBukkit;
        this.lpxListener = lPXListener;
        this.J = PacketEvents.getAPI().getServerManager().getVersion().is(VersionComparison.OLDER_THAN_OR_EQUALS, ServerVersion.V_1_12_2);
        this.N = PacketEvents.getAPI().getServerManager().getVersion().isNewerThan(ServerVersion.V_1_20_4);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {
        this.lpxListener.handleReceive(packetReceiveEvent, false);
    }

    @Override
    public void onPacketEventExternal(PacketEvent packetEvent) {
        this.lpxListener.handleDecode(packetEvent, false);
    }

    @Override
    public void onPacketSend(PacketSendEvent packetSendEvent) {
        ItemStripConfig itemStripConfig;
        if (packetSendEvent.getPacketType() == PacketType.Play.Server.KEEP_ALIVE) {
            this.lpxBukkit.getRegistry().getPlayer(packetSendEvent.getUser()).ifPresent(protocolPlayer -> protocolPlayer.setLastKeepAliveSent(System.currentTimeMillis()));
        }
        if ((itemStripConfig = this.lpxBukkit.getConfigManager().getOptionsConfig().getItemStripConfig()).isEnabled()) {
            this.Y9(itemStripConfig, packetSendEvent);
        }
        if (!this.J) {
            return;
        }
        if (packetSendEvent.getPacketType() != PacketType.Play.Server.TAB_COMPLETE) {
            return;
        }
        User user = packetSendEvent.getUser();
        if (user == null || user.getUUID() == null) {
            return;
        }
        this.lpxBukkit.getRegistry().getPlayer(user).ifPresent(protocolPlayer -> {
            block0: {
                boolean bl;
                Player player = protocolPlayer.getPlayer().orElse(null);
                if (player != null && this.lpxBukkit.getLpxCommand().hasAnyPermission((CommandSender)player) || !(bl = new WrapperPlayServerTabComplete(packetSendEvent).getCommandMatches().removeIf(commandMatch -> commandMatch.getText().startsWith(b)))) break block0;
                packetSendEvent.markForReEncode(true);
            }
        });
    }

    private void Y9(ItemStripConfig itemStripConfig, PacketSendEvent packetSendEvent) {
        WrapperPlayServerSetSlot wrapperPlayServerSetSlot;
        ItemStack itemStack;
        if (packetSendEvent.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
            ItemStack itemStack2;
            WrapperPlayServerWindowItems wrapperPlayServerWindowItems = new WrapperPlayServerWindowItems(packetSendEvent);
            List<ItemStack> list = wrapperPlayServerWindowItems.getItems();
            boolean bl = false;
            for (int i = 0; i < list.size(); ++i) {
                itemStack2 = this.stripItem(list.get(i), itemStripConfig);
                if (itemStack2 == null) continue;
                list.set(i, itemStack2);
                bl = true;
            }
            ItemStack itemStack3 = wrapperPlayServerWindowItems.getCarriedItem().orElse(null);
            if (itemStack3 != null && (itemStack2 = this.stripItem(itemStack3, itemStripConfig)) != null) {
                wrapperPlayServerWindowItems.setCarriedItem(itemStack2);
                bl = true;
            }
            if (bl) {
                packetSendEvent.markForReEncode(true);
            }
            return;
        }
        if (itemStripConfig.isSetSlot() && packetSendEvent.getPacketType() == PacketType.Play.Server.SET_SLOT && (itemStack = this.stripItem((wrapperPlayServerSetSlot = new WrapperPlayServerSetSlot(packetSendEvent)).getItem(), itemStripConfig)) != null) {
            wrapperPlayServerSetSlot.setItem(itemStack);
            packetSendEvent.markForReEncode(true);
        }
    }

    private ItemStack stripItem(ItemStack itemStack, ItemStripConfig itemStripConfig) {
        block10: {
            NBTCompound nBTCompound;
            boolean bl;
            block9: {
                bl = false;
                if (itemStack == null || itemStack.isEmpty()) {
                    return null;
                }
                if (!this.N) break block9;
                if (itemStack.getComponents() == null || itemStack.getComponents().getPatches() == null) break block10;
                boolean bl2 = itemStack.getComponents().getPatches().keySet().removeIf(componentType -> itemStripConfig.k4().contains(componentType.getName().getKey()));
                if (bl2) {
                    return itemStack;
                }
                if (!bl) break block10;
            }
            if ((nBTCompound = itemStack.getNBT()) != null) {
                boolean bl3 = false;
                for (String string : new ArrayList<String>(nBTCompound.getTags().keySet())) {
                    if (itemStripConfig.kT().contains(string)) {
                        nBTCompound.removeTag(string);
                        bl3 = true;
                    }
                    if (!bl) continue;
                }
                if (bl3) {
                    return itemStack;
                }
            }
        }
        return null;
    }

    private static we a(we we2) {
        return we2;
    }
}

