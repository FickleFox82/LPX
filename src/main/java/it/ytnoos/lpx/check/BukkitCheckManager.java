/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  io.netty.buffer.ByteBuf
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package it.ytnoos.lpx.check;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.event.simple.ReadSlotsEvent;
import com.github.retrooper.packetevents.exception.PacketLimitException;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.manager.server.VersionComparison;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCraftRecipeRequest;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSetDisplayedRecipe;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.checks.book.Book0;
import it.ytnoos.lpx.check.checks.book.BookA;
import it.ytnoos.lpx.check.checks.book.BookB;
import it.ytnoos.lpx.check.checks.book.BookC;
import it.ytnoos.lpx.check.checks.command.CommandA;
import it.ytnoos.lpx.check.checks.creative.CreativeA;
import it.ytnoos.lpx.check.checks.creative.CreativeB;
import it.ytnoos.lpx.check.checks.creative.CreativeC;
import it.ytnoos.lpx.check.checks.creative.CreativeD;
import it.ytnoos.lpx.check.checks.creative.CreativeE;
import it.ytnoos.lpx.check.checks.creative.CreativeF;
import it.ytnoos.lpx.check.checks.creative.CreativeG;
import it.ytnoos.lpx.check.checks.flood.FloodA;
import it.ytnoos.lpx.check.checks.flood.FloodB;
import it.ytnoos.lpx.check.checks.flood.FloodC;
import it.ytnoos.lpx.check.checks.flood.FloodD;
import it.ytnoos.lpx.check.checks.flood.FloodF;
import it.ytnoos.lpx.check.checks.flood.FloodG;
import it.ytnoos.lpx.check.checks.item.Item0;
import it.ytnoos.lpx.check.checks.item.Item1;
import it.ytnoos.lpx.check.checks.item.Item2;
import it.ytnoos.lpx.check.checks.item.Item3;
import it.ytnoos.lpx.check.checks.item.ItemA;
import it.ytnoos.lpx.check.checks.item.ItemB;
import it.ytnoos.lpx.check.checks.item.ItemC;
import it.ytnoos.lpx.check.checks.item.ItemD;
import it.ytnoos.lpx.check.checks.item.ItemE;
import it.ytnoos.lpx.check.checks.item.ItemF;
import it.ytnoos.lpx.check.checks.item.ItemG;
import it.ytnoos.lpx.check.checks.item.ItemH;
import it.ytnoos.lpx.check.checks.item.ItemJ;
import it.ytnoos.lpx.check.checks.item.ItemK;
import it.ytnoos.lpx.check.checks.netty.NettyA;
import it.ytnoos.lpx.check.checks.payload.PayloadA;
import it.ytnoos.lpx.check.checks.payload.PayloadB;
import it.ytnoos.lpx.check.checks.payload.PayloadC;
import it.ytnoos.lpx.check.checks.payload.PayloadE;
import it.ytnoos.lpx.check.checks.payload.PayloadF;
import it.ytnoos.lpx.check.checks.payload.PayloadG;
import it.ytnoos.lpx.check.checks.place.PlaceA;
import it.ytnoos.lpx.check.checks.place.PlaceB;
import it.ytnoos.lpx.check.checks.place.PlaceC;
import it.ytnoos.lpx.check.checks.position.PositionA;
import it.ytnoos.lpx.check.checks.position.PositionB;
import it.ytnoos.lpx.check.checks.position.PositionC;
import it.ytnoos.lpx.check.checks.position.PositionD;
import it.ytnoos.lpx.check.checks.tab.TabA;
import it.ytnoos.lpx.check.checks.tab.TabB;
import it.ytnoos.lpx.check.checks.window.WindowA;
import it.ytnoos.lpx.check.checks.window.WindowB;
import it.ytnoos.lpx.check.checks.window.WindowC;
import it.ytnoos.lpx.check.checks.window.WindowD;
import it.ytnoos.lpx.check.checks.window.WindowE;
import it.ytnoos.lpx.m2;
import it.ytnoos.lpx.m7;
import it.ytnoos.lpx.mm;
import it.ytnoos.lpx.utils.ItemUtils_w7;
import java.util.List;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BukkitCheckManager
extends CheckManager {
    public static final List<String> BOOK_CHANNELS = Lists.newArrayList((Object[])new String[]{"MC|BEdit", "MC|BSign"});

    public BukkitCheckManager(LPXBukkit lPXBukkit) {
        super(lPXBukkit.getPlugin());
    }

    @Override
    public void registerChecks() {
        ServerVersion serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        boolean bl = serverVersion.is(VersionComparison.OLDER_THAN_OR_EQUALS, ServerVersion.V_1_8_8);
        this.register(ByteBuf.class, (protocolPlayer, checkOptions) -> new NettyA((ProtocolPlayer)protocolPlayer, (CheckOptions)checkOptions, true), CheckType.NETTY_A);
        this.register(ByteBuf.class, (protocolPlayer, checkOptions) -> new NettyA((ProtocolPlayer)protocolPlayer, (CheckOptions)checkOptions, false), CheckType.NETTY_C);
        this.register(PacketLimitException.class, (protocolPlayer, checkOptions) -> new mm((ProtocolPlayer)protocolPlayer, (CheckOptions)checkOptions, true), CheckType.NETTY_B);
        this.register(PacketLimitException.class, (protocolPlayer, checkOptions) -> new mm((ProtocolPlayer)protocolPlayer, (CheckOptions)checkOptions, false), CheckType.NETTY_B);
        if (bl) {
            this.register(PacketType.Play.Client.CLICK_WINDOW, WindowA::new, CheckType.WINDOW_A);
        }
        if (serverVersion.is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_12)) {
            this.register(PacketType.Play.Client.CLICK_WINDOW, WindowB::new, CheckType.WINDOW_B);
        }
        this.register(PacketType.Play.Client.CLICK_WINDOW, WindowC::new, CheckType.WINDOW_C);
        this.register(PacketType.Play.Client.CLICK_WINDOW, WindowD::new, CheckType.WINDOW_D);
        this.register(PacketType.Play.Client.CLICK_WINDOW, WindowE::new, CheckType.WINDOW_E);
        this.register(NBTDecodeEvent.class, Item0::new, CheckType.ITEM_0);
        this.register(ItemStackDecodeEvent.class, Item1::new, CheckType.ITEM_1);
        this.register(PacketType.Play.Client.PICK_ITEM_FROM_BLOCK, Item2::new, CheckType.ITEM_2);
        this.register(PacketType.Play.Client.PICK_ITEM_FROM_ENTITY, Item3::new, CheckType.ITEM_3);
        this.register(ItemStackDecodeEvent.class, ItemA::new, CheckType.ITEM_A);
        this.register(NBTDecodeEvent.class, ItemB::new, CheckType.ITEM_B);
        this.register(NBTDecodeEvent.class, ItemC::new, CheckType.ITEM_C);
        this.register(ItemStackDecodeEvent.class, ItemD::new, CheckType.ITEM_D);
        this.register(NBTDecodeEvent.class, ItemE::new, CheckType.ITEM_E);
        this.register(NBTDecodeEvent.class, ItemF::new, CheckType.ITEM_F);
        this.register(NBTDecodeEvent.class, ItemG::new, CheckType.ITEM_G);
        this.register(ItemStackDecodeEvent.class, ItemH::new, CheckType.ITEM_H);
        this.register(ItemStackDecodeEvent.class, ItemJ::new, CheckType.ITEM_J);
        this.register(ItemStackDecodeEvent.class, ItemK::new, CheckType.ITEM_K);
        this.register(PacketPlayReceiveEvent.class, CreativeA::new, CheckType.CREATIVE_A);
        this.register(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION, CreativeB::new, CheckType.CREATIVE_B);
        this.register(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION, CreativeC::new, CheckType.CREATIVE_C);
        this.register(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION, CreativeD::new, CheckType.CREATIVE_D);
        this.register(ItemStackDecodeEvent.class, CreativeE::new, CheckType.CREATIVE_E);
        this.register(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION, CreativeF::new, CheckType.CREATIVE_F);
        this.register(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION, CreativeG::new, CheckType.CREATIVE_G);
        this.register(PacketPlayReceiveEvent.class, m7::new, CheckType.CREATIVE_H);
        this.register(PacketPlayReceiveEvent.class, m2::new, CheckType.CREATIVE_I);
        this.register(PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT, PlaceA::new, CheckType.PLACE_A);
        this.register(PacketType.Play.Client.UPDATE_SIGN, PlaceB::new, CheckType.PLACE_B);
        if (serverVersion == ServerVersion.V_1_16 || serverVersion == ServerVersion.V_1_16_1 || serverVersion == ServerVersion.V_1_16_2 || serverVersion == ServerVersion.V_1_16_3 || serverVersion == ServerVersion.V_1_16_4 || serverVersion == ServerVersion.V_1_16_5) {
            this.register(PacketType.Play.Client.PLAYER_DIGGING, PlaceC::new, CheckType.PLACE_C);
        }
        this.register(PacketPlayReceiveEvent.class, FloodA::new, CheckType.FLOOD_A);
        this.register(PacketPlayReceiveEvent.class, FloodB::new, CheckType.FLOOD_B);
        this.register(ItemStackDecodeEvent.class, FloodC::new, CheckType.FLOOD_C);
        this.register(ItemStackDecodeEvent.class, FloodD::new, CheckType.FLOOD_D);
        this.register(PlayerCommandPreprocessEvent.class, FloodF::new, CheckType.FLOOD_F);
        this.register(ReadSlotsEvent.class, FloodG::new, CheckType.FLOOD_G);
        this.register(PacketType.Play.Client.PLAYER_FLYING, PositionA::new, CheckType.POSITION_A);
        this.register(PacketType.Play.Client.PLAYER_FLYING, PositionB::new, CheckType.POSITION_B);
        this.register(PacketType.Play.Client.PLAYER_FLYING, PositionC::new, CheckType.POSITION_C);
        this.register(PacketType.Play.Client.PLAYER_FLYING, PositionD::new, CheckType.POSITION_D);
        this.register(PacketType.Play.Client.PLUGIN_MESSAGE, PayloadA::new, CheckType.PAYLOAD_A);
        this.register(PacketType.Play.Client.PLUGIN_MESSAGE, PayloadB::new, CheckType.PAYLOAD_B);
        this.register(PacketType.Play.Client.PLUGIN_MESSAGE, PayloadC::new, CheckType.PAYLOAD_C);
        this.register(PacketType.Play.Client.PLUGIN_MESSAGE, (protocolPlayer, checkOptions) -> new PayloadG((ProtocolPlayer)protocolPlayer, (CheckOptions)checkOptions, true), CheckType.PAYLOAD_D);
        this.register(PacketType.Play.Client.PLUGIN_MESSAGE, (protocolPlayer, checkOptions) -> new PayloadG((ProtocolPlayer)protocolPlayer, (CheckOptions)checkOptions, false), CheckType.PAYLOAD_G);
        if (bl) {
            this.register(PacketType.Play.Client.PLUGIN_MESSAGE, PayloadE::new, CheckType.PAYLOAD_E);
        }
        this.register(PacketType.Play.Client.PLUGIN_MESSAGE, PayloadF::new, CheckType.PAYLOAD_F);
        this.register(PacketType.Play.Client.EDIT_BOOK, BookA::new, CheckType.BOOK_A);
        this.register(PacketType.Play.Client.EDIT_BOOK, BookB::new, CheckType.BOOK_B);
        this.register(PacketType.Play.Client.EDIT_BOOK, BookC::new, CheckType.BOOK_C);
        this.register(PacketType.Play.Client.EDIT_BOOK, Book0::new, CheckType.BOOK_0);
        this.register(PacketType.Play.Client.TAB_COMPLETE, TabA::new, CheckType.TAB_A);
        this.register(PacketType.Play.Client.TAB_COMPLETE, TabB::new, CheckType.TAB_B);
        this.register(PlayerCommandPreprocessEvent.class, CommandA::new, CheckType.COMMAND_A);
        if (serverVersion == ServerVersion.V_1_21_2 || serverVersion == ServerVersion.V_1_21_3) {
            this.register(PacketType.Play.Client.CRAFT_RECIPE_REQUEST, (protocolPlayer, checkOptions) -> new ItemUtils_w7<WrapperPlayClientCraftRecipeRequest>(protocolPlayer, checkOptions){

                public int getRecipeId(WrapperPlayClientCraftRecipeRequest wrapperPlayClientCraftRecipeRequest) {
                    return wrapperPlayClientCraftRecipeRequest.getRecipeId().getId();
                }
            }, CheckType.RECIPE_A);
            this.register(PacketType.Play.Client.SET_DISPLAYED_RECIPE, (protocolPlayer, checkOptions) -> new ItemUtils_w7<WrapperPlayClientSetDisplayedRecipe>(protocolPlayer, checkOptions){

                public int getRecipeId(WrapperPlayClientSetDisplayedRecipe wrapperPlayClientSetDisplayedRecipe) {
                    return wrapperPlayClientSetDisplayedRecipe.getRecipeId().getId();
                }
            }, CheckType.RECIPE_B);
        }
        this.sortByPriority();
    }
}

