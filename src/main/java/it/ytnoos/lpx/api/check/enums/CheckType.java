/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check.enums;

import it.ytnoos.lpx.api.check.enums.CheckCategory;
import it.ytnoos.lpx.api.check.enums.CheckID;

public enum CheckType {
    NETTY_A(CheckCategory.NETTY, CheckID.A, "Packet size pre via. (May false with big items on 1.13+, if so disable automatic mode and increase the max size)", 1),
    NETTY_B(CheckCategory.NETTY, CheckID.B, "Packet exceeded decoding limits", 1),
    NETTY_C(CheckCategory.NETTY, CheckID.A, "Packet size post via. (May false with big items on 1.13+, if so disable automatic mode and increase the max size)", 1),
    WINDOW_A(CheckCategory.WINDOW, CheckID.A, "Legacy max slot", 2),
    WINDOW_B(CheckCategory.WINDOW, CheckID.B, "Modern max slot", 2),
    WINDOW_C(CheckCategory.WINDOW, CheckID.C, "Inventory not open", 1),
    WINDOW_D(CheckCategory.WINDOW, CheckID.D, "Lectern crash exploit", 3),
    WINDOW_E(CheckCategory.WINDOW, CheckID.E, "Hashed click stack overload", 4),
    ITEM_0(CheckCategory.ITEM, CheckID.ZERO, "HIDDEN", 1),
    ITEM_1(CheckCategory.ITEM, CheckID.ONE, "HIDDEN", 1),
    ITEM_2(CheckCategory.ITEM, CheckID.TWO, "HIDDEN", 1),
    ITEM_3(CheckCategory.ITEM, CheckID.THREE, "HIDDEN", 1),
    ITEM_A(CheckCategory.ITEM, CheckID.A, "HIDDEN", 2),
    ITEM_B(CheckCategory.ITEM, CheckID.B, "HIDDEN", 3),
    ITEM_C(CheckCategory.ITEM, CheckID.C, "HIDDEN", 4),
    ITEM_D(CheckCategory.ITEM, CheckID.D, "HIDDEN", 5),
    ITEM_E(CheckCategory.ITEM, CheckID.E, "HIDDEN", 6),
    ITEM_F(CheckCategory.ITEM, CheckID.F, "HIDDEN", 7),
    ITEM_G(CheckCategory.ITEM, CheckID.G, "HIDDEN", 8),
    ITEM_H(CheckCategory.ITEM, CheckID.G, "HIDDEN", 9),
    ITEM_J(CheckCategory.ITEM, CheckID.J, "HIDDEN", 11),
    ITEM_K(CheckCategory.ITEM, CheckID.K, "HIDDEN", 12),
    CREATIVE_A(CheckCategory.CREATIVE, CheckID.A, "Packet while not in creative", 1),
    CREATIVE_B(CheckCategory.CREATIVE, CheckID.B, "Illegal enchanted item"),
    CREATIVE_C(CheckCategory.CREATIVE, CheckID.C, "Long item name"),
    CREATIVE_D(CheckCategory.CREATIVE, CheckID.D, "Long lore name"),
    CREATIVE_E(CheckCategory.CREATIVE, CheckID.E, "Book with blacklisted words"),
    CREATIVE_F(CheckCategory.CREATIVE, CheckID.F, "Illegal amount item"),
    CREATIVE_G(CheckCategory.CREATIVE, CheckID.G, "Illegal BlockEntityTag key"),
    CREATIVE_H(CheckCategory.CREATIVE, CheckID.H, "Test instance block action while not in creative", 1),
    CREATIVE_I(CheckCategory.CREATIVE, CheckID.I, "Set test block while not in creative", 1),
    PLACE_A(CheckCategory.PLACE, CheckID.A, "Placed a block too far away"),
    PLACE_B(CheckCategory.PLACE, CheckID.B, "Placed a sign with too long rows"),
    PLACE_C(CheckCategory.PLACE, CheckID.C, "Digging too far away"),
    FLOOD_A(CheckCategory.FLOOD, CheckID.A, "Too many packets per second. (CAN FALSE DURING PLAYER/SERVER LAG)"),
    FLOOD_B(CheckCategory.FLOOD, CheckID.B, "Specific packet flood. (CAN FALSE DURING PLAYER/SERVER LAG)"),
    FLOOD_C(CheckCategory.FLOOD, CheckID.C, "Item flood", 1),
    FLOOD_D(CheckCategory.FLOOD, CheckID.D, "Book flood", 2),
    FLOOD_F(CheckCategory.FLOOD, CheckID.F, "Command flood"),
    FLOOD_G(CheckCategory.FLOOD, CheckID.G, "Map slots flood"),
    POSITION_A(CheckCategory.POSITION, CheckID.A, "AntiCheat crasher", 1),
    POSITION_B(CheckCategory.POSITION, CheckID.B, "Unloaded chunk move", 4),
    POSITION_C(CheckCategory.POSITION, CheckID.C, "High Y", 2),
    POSITION_D(CheckCategory.POSITION, CheckID.D, "High Yaw", 3),
    POSITION_E(CheckCategory.POSITION, CheckID.E, "Constant large deltaXZ", true),
    PAYLOAD_A(CheckCategory.PAYLOAD, CheckID.A, "Empty payload", 1),
    PAYLOAD_B(CheckCategory.PAYLOAD, CheckID.B, "Legacy book payload flood", 2),
    PAYLOAD_C(CheckCategory.PAYLOAD, CheckID.C, "Legacy empty hand", 3),
    PAYLOAD_D(CheckCategory.PAYLOAD, CheckID.D, "Invalid book NBT preVia", 4),
    PAYLOAD_G(CheckCategory.PAYLOAD, CheckID.D, "Invalid book NBT postVia", 4),
    PAYLOAD_E(CheckCategory.PAYLOAD, CheckID.E, "Too many registered channels", 5),
    PAYLOAD_F(CheckCategory.PAYLOAD, CheckID.F, "Purpur beehive location crasher", 6),
    BOOK_A(CheckCategory.BOOK, CheckID.A, "Max title length", 1),
    BOOK_B(CheckCategory.PAYLOAD, CheckID.B, "Modern book payload flood", 2),
    BOOK_C(CheckCategory.PAYLOAD, CheckID.C, "Modern empty hand", 3),
    BOOK_0(CheckCategory.BOOK, CheckID.ZERO, "HIDDEN", Integer.MAX_VALUE),
    TAB_A(CheckCategory.TAB, CheckID.A, "Tab complete exploit", 2),
    TAB_B(CheckCategory.TAB, CheckID.B, "Tab complete length", 1),
    COMMAND_A(CheckCategory.COMMAND, CheckID.A, "Not allowed command", 1),
    RECIPE_A(CheckCategory.RECIPE, CheckID.A, "Invalid recipe ID", 1),
    RECIPE_B(CheckCategory.RECIPE, CheckID.B, "Invalid recipe ID", 1);

    private final CheckCategory category;
    private final CheckID id;
    private final String description;
    private final int priority;
    private final boolean beta;

    private CheckType(CheckCategory checkCategory, CheckID checkID, String string2) {
        this(checkCategory, checkID, string2, 0);
    }

    private CheckType(CheckCategory checkCategory, CheckID checkID, String string2, int n2) {
        this(checkCategory, checkID, string2, n2, false);
    }

    private CheckType(CheckCategory checkCategory, CheckID checkID, String string2, boolean bl) {
        this(checkCategory, checkID, string2, 0, bl);
    }

    private CheckType(CheckCategory checkCategory, CheckID checkID, String string2, int n2, boolean bl) {
        this.category = checkCategory;
        this.id = checkID;
        this.description = string2;
        this.priority = n2;
        this.beta = bl;
    }

    public CheckCategory getCategory() {
        return this.category;
    }

    public CheckID getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPriority() {
        return this.priority;
    }
}

