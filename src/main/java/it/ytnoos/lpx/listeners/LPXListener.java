/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx.listeners;

import com.github.retrooper.packetevents.event.PacketEvent;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.event.simple.ReadSlotsEvent;
import com.github.retrooper.packetevents.exception.PacketLimitException;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCraftRecipeRequest;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCustomClickAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPickItemFromBlock;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPickItemFromEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSelectBundleItem;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSetDisplayedRecipe;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSetTestBlock;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTestInstanceBlockAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateSign;
import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import io.netty.buffer.ByteBuf;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.CheckResultInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.common.utils.NettyUtils;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.logger.PacketEventsExceptionHandler;
import it.ytnoos.lpx.player.BukkitProtocolPlayer;
import it.ytnoos.lpx.we;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class LPXListener {
    protected static final Map<PacketTypeCommon, Function<PacketReceiveEvent, PacketWrapper<?>>> WRAPPER_FACTORIES;
    private static final long g;
    private final LPXBukkit lpxBukkit;
    private final Map<String, Long> WRAPPER_FACTORIES2 = new ConcurrentHashMap<String, Long>();

    public LPXListener(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    void handleReceive(PacketReceiveEvent packetReceiveEvent, boolean bl) {
        User user = packetReceiveEvent.getUser();
        ProtocolPlayer protocolPlayer = this.lpxBukkit.getRegistry().getPlayer(user).orElse(null);
        if (protocolPlayer == null) {
            return;
        }
        try {
            this.Au(packetReceiveEvent, protocolPlayer, bl);
        }
        catch (RuntimeException runtimeException) {
            protocolPlayer.setLastReceivedPacket(null);
            String string = packetReceiveEvent.getPacketType().getName();
            String string2 = protocolPlayer.getUsername();
            this.lpxBukkit.getAlertManager().getLogWriter().execute(() -> {
                if (!PacketEventsExceptionHandler.shouldLog(this.WRAPPER_FACTORIES2, string2, System.currentTimeMillis())) {
                    return;
                }
                this.lpxBukkit.getAlertManager().getLogWriter().YD(() -> "Error while handling " + string + " sent by " + string2, runtimeException);
            });
        }
    }

    private void Au(PacketReceiveEvent packetReceiveEvent, ProtocolPlayer protocolPlayer, boolean bl) {
        PacketWrapper<?> packetWrapper;
        Function<PacketReceiveEvent, PacketWrapper<?>> function;
        User user = packetReceiveEvent.getUser();
        if (bl && packetReceiveEvent.getPacketType() == PacketType.Play.Client.KEEP_ALIVE) {
            protocolPlayer.setPlayerPing(System.currentTimeMillis() - protocolPlayer.getLastKeepAliveSent());
        }
        if (protocolPlayer.getPacketCount() < 10L) {
            protocolPlayer.incrementPacketCount();
        }
        if (bl || protocolPlayer.getLastReceivedPacket() == null) {
            protocolPlayer.setLastReceivedPacket(new ReceivedPacket(user.getName(), user.getClientVersion(), this.lpxBukkit.getPacketTickMonitor().getCurrentTick(), packetReceiveEvent.getPacketType(), NettyUtils.getSize(packetReceiveEvent.getByteBuf()), null));
        }
        if (this.handleChecks(packetReceiveEvent, protocolPlayer, ByteBuf.class, packetReceiveEvent.getByteBuf(), bl) == CheckResult.DENY) {
            protocolPlayer.setLastReceivedPacket(null);
            return;
        }
        if (this.handleChecks(packetReceiveEvent, protocolPlayer, PacketPlayReceiveEvent.class, packetReceiveEvent, bl) == CheckResult.DENY) {
            protocolPlayer.setLastReceivedPacket(null);
            return;
        }
        PacketTypeCommon packetTypeCommon = packetReceiveEvent.getPacketType();
        if (packetTypeCommon == PacketType.Play.Client.PLAYER_POSITION || packetTypeCommon == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION || packetTypeCommon == PacketType.Play.Client.PLAYER_ROTATION) {
            packetTypeCommon = PacketType.Play.Client.PLAYER_FLYING;
        }
        if ((function = WRAPPER_FACTORIES.get(packetTypeCommon)) == null) {
            return;
        }
        try {
            packetWrapper = function.apply(packetReceiveEvent);
        }
        catch (RuntimeException runtimeException) {
            throw new we(runtimeException);
        }
        if (protocolPlayer.getLastReceivedPacket().getPacket() == null) {
            protocolPlayer.getLastReceivedPacket().setPacket(packetWrapper);
        }
        if (packetReceiveEvent.isCancelled()) {
            protocolPlayer.setLastReceivedPacket(null);
            return;
        }
        if (this.handleChecks(packetReceiveEvent, protocolPlayer, packetTypeCommon, packetWrapper, bl) == CheckResult.DENY) {
            protocolPlayer.setLastReceivedPacket(null);
        }
    }

    CheckResult handleDecode(PacketEvent packetEvent, boolean bl) {
        ProtocolPacketEvent protocolPacketEvent;
        if (packetEvent instanceof ItemStackDecodeEvent) {
            protocolPacketEvent = ((ItemStackDecodeEvent)packetEvent).getEvent();
        } else if (packetEvent instanceof NBTDecodeEvent) {
            protocolPacketEvent = ((NBTDecodeEvent)packetEvent).getEvent();
        } else if (packetEvent instanceof ReadSlotsEvent) {
            protocolPacketEvent = ((ReadSlotsEvent)packetEvent).getEvent();
        } else {
            return CheckResult.ALLOW;
        }
        if (!(protocolPacketEvent instanceof PacketReceiveEvent)) {
            return CheckResult.ALLOW;
        }
        PacketReceiveEvent packetReceiveEvent = (PacketReceiveEvent)protocolPacketEvent;
        if (packetReceiveEvent.isCancelled()) {
            return CheckResult.ALLOW;
        }
        if (!(packetReceiveEvent instanceof PacketReceiveEvent)) {
            return CheckResult.ALLOW;
        }
        User user = packetReceiveEvent.getUser();
        if (user == null || user.getUUID() == null) {
            return CheckResult.ALLOW;
        }
        ProtocolPlayer protocolPlayer = this.lpxBukkit.getRegistry().getPlayer(user).orElse(null);
        if (protocolPlayer == null) {
            return CheckResult.ALLOW;
        }
        if (packetEvent instanceof ItemStackDecodeEvent) {
            return this.handleChecks(packetReceiveEvent, protocolPlayer, ItemStackDecodeEvent.class, packetEvent, bl);
        }
        if (packetEvent instanceof NBTDecodeEvent) {
            return this.handleChecks(packetReceiveEvent, protocolPlayer, NBTDecodeEvent.class, packetEvent, bl);
        }
        return this.handleChecks(packetReceiveEvent, protocolPlayer, ReadSlotsEvent.class, packetEvent, bl);
    }

    public CheckResult handleChecks(PacketReceiveEvent packetReceiveEvent, ProtocolPlayer protocolPlayer, Object object, Object object2, boolean bl) {
        CheckResult checkResult;
        block7: {
            block6: {
                CheckResultInfo checkResultInfo = protocolPlayer.handleChecks(object, object2, bl);
                checkResult = checkResultInfo.result();
                boolean bl2 = false;
                if (checkResult != CheckResult.DENY) break block6;
                packetReceiveEvent.setCancelled(true);
                PacketTypeCommon packetTypeCommon = packetReceiveEvent.getPacketType();
                if (packetTypeCommon == PacketType.Play.Client.CREATIVE_INVENTORY_ACTION || packetTypeCommon == PacketType.Play.Client.CLICK_WINDOW) {
                    this.resyncInventory(protocolPlayer);
                }
                if (this.lpxBukkit.getConfigManager().getOptionsConfig().Ti()) {
                    Check<?> check = checkResultInfo.lastCheck();
                    String string = check != null ? "[" + String.valueOf((Object)check.getType().getId()) + "] " : "";
                    this.lpxBukkit.getAlertManager().getLogWriter().writeString(() -> string + "Cancelling " + packetReceiveEvent.getPacketType().getName() + " sent by " + protocolPlayer.getUsername());
                }
                if (!bl2) break block7;
            }
            if (checkResult == CheckResult.CHANGE) {
                packetReceiveEvent.markForReEncode(true);
            }
        }
        return checkResult;
    }

    private void handleDecodeFailure(PacketReceiveEvent packetReceiveEvent, ProtocolPlayer protocolPlayer, RuntimeException runtimeException, boolean bl) {
        packetReceiveEvent.setCancelled(true);
        protocolPlayer.setLastReceivedPacket(null);
        if (protocolPlayer.isKicked()) {
            return;
        }
        PacketLimitException packetLimitException = LPXListener.XE(runtimeException);
        if (packetLimitException != null) {
            this.handleChecks(packetReceiveEvent, protocolPlayer, PacketLimitException.class, packetLimitException, bl);
            return;
        }
        String string = packetReceiveEvent.getPacketType().getName();
        String string2 = protocolPlayer.getUsername();
        boolean bl2 = this.lpxBukkit.getConfigManager().getOptionsConfig().isKickOnException();
        if (bl2) {
            protocolPlayer.kick(this.lpxBukkit.getConfigManager().getMessagesConfig().getKick());
        }
        this.lpxBukkit.getAlertManager().getLogWriter().execute(() -> {
            if (!bl2 && !PacketEventsExceptionHandler.shouldLog(this.WRAPPER_FACTORIES2, string2, System.currentTimeMillis())) {
                return;
            }
            String string3 = runtimeException.getMessage();
            String string4 = bl2 ? ", kicking" : ", cancelled";
            String string5 = string2;
            String string6 = string;
            this.lpxBukkit.getLogger().warning("Invalid " + string6 + " sent by " + string5 + string4 + ": " + string3 + " (full stacktrace saved in LPX logs)");
            this.lpxBukkit.getAlertManager().getLogWriter().YD(() -> "Invalid " + string + " sent by " + string2, runtimeException);
        });
    }

    @Nullable
    private static PacketLimitException XE(Throwable throwable) {
        for (Throwable throwable2 = throwable; throwable2 != null; throwable2 = throwable2.getCause()) {
            if (!(throwable2 instanceof PacketLimitException)) continue;
            PacketLimitException packetLimitException = (PacketLimitException)throwable2;
            return packetLimitException;
        }
        return null;
    }

    private void resyncInventory(ProtocolPlayer protocolPlayer) {
        if (!(protocolPlayer instanceof BukkitProtocolPlayer)) {
            return;
        }
        BukkitProtocolPlayer bukkitProtocolPlayer = (BukkitProtocolPlayer)protocolPlayer;
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return;
        }
        if (!bukkitProtocolPlayer.tF()) {
            return;
        }
        long l = 1000L - TimeUtils.difference(bukkitProtocolPlayer.tC());
        Runnable runnable = () -> bukkitProtocolPlayer.ks(false);
        if (l <= 0L) {
            FoliaScheduler.getEntityScheduler().run((Entity)player, this.lpxBukkit.getPlugin(), object -> {
                player.updateInventory();
                bukkitProtocolPlayer.ks(true);
            }, runnable);
        } else {
            FoliaScheduler.getEntityScheduler().runDelayed((Entity)player, this.lpxBukkit.getPlugin(), object -> {
                player.updateInventory();
                bukkitProtocolPlayer.ks(true);
            }, runnable, (l + 49L) / 50L);
        }
    }

    static {
        g = 1000L;
        WRAPPER_FACTORIES = new HashMap();
        WRAPPER_FACTORIES.put(PacketType.Play.Client.CLICK_WINDOW, WrapperPlayClientClickWindow::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION, WrapperPlayClientCreativeInventoryAction::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT, WrapperPlayClientPlayerBlockPlacement::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.UPDATE_SIGN, WrapperPlayClientUpdateSign::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PLAYER_FLYING, WrapperPlayClientPlayerFlying::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PLAYER_POSITION, WrapperPlayClientPlayerFlying::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION, WrapperPlayClientPlayerFlying::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PLAYER_ROTATION, WrapperPlayClientPlayerFlying::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PLUGIN_MESSAGE, WrapperPlayClientPluginMessage::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.TAB_COMPLETE, WrapperPlayClientTabComplete::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.EDIT_BOOK, WrapperPlayClientEditBook::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PLAYER_DIGGING, WrapperPlayClientPlayerDigging::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.CRAFT_RECIPE_REQUEST, WrapperPlayClientCraftRecipeRequest::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.SET_DISPLAYED_RECIPE, WrapperPlayClientSetDisplayedRecipe::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.SELECT_BUNDLE_ITEM, WrapperPlayClientSelectBundleItem::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PICK_ITEM_FROM_BLOCK, WrapperPlayClientPickItemFromBlock::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.PICK_ITEM_FROM_ENTITY, WrapperPlayClientPickItemFromEntity::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.TEST_INSTANCE_BLOCK_ACTION, WrapperPlayClientTestInstanceBlockAction::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.SET_TEST_BLOCK, WrapperPlayClientSetTestBlock::new);
        WRAPPER_FACTORIES.put(PacketType.Play.Client.CUSTOM_CLICK_ACTION, WrapperPlayClientCustomClickAction::new);
    }
}

