/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemLore;
import com.github.retrooper.packetevents.protocol.component.builtin.item.WrittenBookContent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.util.Filterable;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.KeybindComponent;
import net.kyori.adventure.text.TranslatableComponent;

public class ItemH
extends PlayerCheck<ItemStackDecodeEvent> {
    private int h;
    private int l;

    public ItemH(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.h = this.getConfig().getInt(this.getPath("max-translate"), 1);
        this.l = this.getConfig().getInt(this.getPath("max-keybind"), 1);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        WrittenBookContent writtenBookContent;
        if (!protocolPlayer.usesComponents()) {
            return CheckResult.ALLOW;
        }
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        CheckResult checkResult = CheckResult.ALLOW;
        Component component = itemStack.getComponentOr(ComponentTypes.CUSTOM_NAME, null);
        if (component != null && (checkResult = this.z8(component, "custom_name", itemStackDecodeEvent)) == CheckResult.DENY) {
            return checkResult;
        }
        Component component2 = itemStack.getComponentOr(ComponentTypes.ITEM_NAME, null);
        if (component2 != null && (checkResult = this.z8(component2, "item_name", itemStackDecodeEvent)) == CheckResult.DENY) {
            return checkResult;
        }
        ItemLore itemLore = itemStack.getComponentOr(ComponentTypes.LORE, null);
        if (itemLore != null) {
            for (int i = 0; i < itemLore.getLines().size(); ++i) {
                checkResult = this.z8(itemLore.getLines().get(i), "lore[" + i + "]", itemStackDecodeEvent);
                if (checkResult != CheckResult.DENY) continue;
                return checkResult;
            }
        }
        if ((writtenBookContent = (WrittenBookContent)itemStack.getComponentOr(ComponentTypes.WRITTEN_BOOK_CONTENT, null)) != null) {
            for (int i = 0; i < writtenBookContent.getPages().size(); ++i) {
                Filterable<Component> filterable = writtenBookContent.getPages().get(i);
                checkResult = this.z8(filterable.getRaw(), "page[" + i + "]", itemStackDecodeEvent);
                if (checkResult == CheckResult.DENY) {
                    return checkResult;
                }
                if (filterable.getFiltered() == null || (checkResult = this.z8(filterable.getFiltered(), "page_filtered[" + i + "]", itemStackDecodeEvent)) != CheckResult.DENY) continue;
                return checkResult;
            }
        }
        return checkResult;
    }

    private CheckResult z8(Component component, Object object, ItemStackDecodeEvent itemStackDecodeEvent) {
        boolean bl;
        int[] nArray = new int[2];
        this.countComponents(component, nArray);
        boolean bl2 = nArray[0] > this.h;
        boolean bl3 = bl = nArray[1] > this.l;
        if (!bl2 && !bl) {
            return CheckResult.ALLOW;
        }
        this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", object), FlagInfo.of("translate", nArray[0]), FlagInfo.of("keybind", nArray[1])});
        return CheckResult.DENY;
    }

    private boolean shouldStop(int[] nArray) {
        int[] nArray2 = nArray;
        return nArray2[0] > this.h || nArray2[1] > this.l;
    }

    private void countComponents(Component component, int[] nArray) {
        int[] nArray2 = nArray;
        if (this.shouldStop(nArray2)) {
            return;
        }
        if (component instanceof TranslatableComponent) {
            TranslatableComponent translatableComponent = (TranslatableComponent)component;
            nArray2[0] = nArray2[0] + 1;
            for (Component component2 : translatableComponent.args()) {
                if (this.shouldStop(nArray2)) {
                    return;
                }
                this.countComponents(component2, nArray2);
            }
        }
        if (component instanceof KeybindComponent) {
            nArray2[1] = nArray2[1] + 1;
        }
        for (Component component3 : component.children()) {
            if (this.shouldStop(nArray2)) {
                return;
            }
            this.countComponents(component3, nArray2);
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

