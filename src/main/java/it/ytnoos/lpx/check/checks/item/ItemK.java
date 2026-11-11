/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ChargedProjectiles;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemAdventurePredicate;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemContainerContents;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemProfile;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemTool;
import com.github.retrooper.packetevents.protocol.component.builtin.item.WritableBookContent;
import com.github.retrooper.packetevents.protocol.component.builtin.item.WrittenBookContent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.util.Filterable;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.Examiner;

public class ItemK
extends PlayerCheck<ItemStackDecodeEvent> {
    private int maxContainerItems;
    private int maxTotalNestedItems;
    private int maxProfileProperties;
    private int maxProfileTotalBytes;
    private int maxChargedProjectiles;
    private int maxToolRules;
    private int maxCanBreakPredicates;
    private int maxCanBreakProperties;
    private int maxBookPages;
    private int maxBookPageBytes;

    public ItemK(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.maxContainerItems = this.getInt("max-container-items", 96);
        this.maxTotalNestedItems = this.getInt("max-total-nested-items", 256);
        this.maxProfileProperties = this.getInt("max-profile-properties", 16);
        this.maxProfileTotalBytes = this.getInt("max-profile-total-bytes", 8192);
        this.maxChargedProjectiles = this.getInt("max-charged-projectiles", 16);
        this.maxToolRules = this.getInt("max-tool-rules", 96);
        this.maxCanBreakPredicates = this.getInt("max-can-break-predicates", 192);
        this.maxCanBreakProperties = this.getInt("max-can-break-properties", 256);
        this.maxBookPages = this.getInt("max-book-pages", 100);
        this.maxBookPageBytes = this.getInt("max-book-page-bytes", 3072);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (!protocolPlayer.usesComponents()) {
            return CheckResult.ALLOW;
        }
        return this.bI(itemStackDecodeEvent.getItemStack(), new VisitState(), itemStackDecodeEvent);
    }

    private CheckResult bI(ItemStack itemStack, VisitState visitState, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (itemStack == null || itemStack.isEmpty()) {
            return CheckResult.ALLOW;
        }
        CheckResult checkResult = this.AS(itemStack, itemStackDecodeEvent);
        if (checkResult == CheckResult.DENY) {
            return checkResult;
        }
        checkResult = this.AV(itemStack, itemStackDecodeEvent);
        if (checkResult == CheckResult.DENY) {
            return checkResult;
        }
        checkResult = this.bw(itemStack, visitState, itemStackDecodeEvent);
        if (checkResult == CheckResult.DENY) {
            return checkResult;
        }
        checkResult = this.Ay(itemStack, itemStackDecodeEvent);
        if (checkResult == CheckResult.DENY) {
            return checkResult;
        }
        checkResult = this.Ao(itemStack, itemStackDecodeEvent);
        if (checkResult == CheckResult.DENY) {
            return checkResult;
        }
        return this.bZ(itemStack, visitState, itemStackDecodeEvent);
    }

    private CheckResult AS(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        List<Filterable<String>> list;
        WritableBookContent writableBookContent = itemStack.getComponentOr(ComponentTypes.WRITABLE_BOOK_CONTENT, null);
        if (writableBookContent != null) {
            list = writableBookContent.getPages();
            if (list.size() > this.maxBookPages) {
                this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "writable-book-pages"), FlagInfo.of("pages", list.size()), FlagInfo.of("max-pages", this.maxBookPages)});
                return CheckResult.DENY;
            }
            for (int i = 0; i < list.size(); ++i) {
                CheckResult checkResult = this.Ua("writable-book-page", (Filterable)list.get(i), i, itemStackDecodeEvent);
                if (checkResult != CheckResult.DENY) continue;
                return checkResult;
            }
        }
        if ((list = (WrittenBookContent)itemStack.getComponentOr(ComponentTypes.WRITTEN_BOOK_CONTENT, null)) == null) {
            return CheckResult.ALLOW;
        }
        List<Filterable<Component>> list2 = ((WrittenBookContent)((Object)list)).getPages();
        if (list2.size() > this.maxBookPages) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "written-book-pages"), FlagInfo.of("pages", list2.size()), FlagInfo.of("max-pages", this.maxBookPages)});
            return CheckResult.DENY;
        }
        for (int i = 0; i < list2.size(); ++i) {
            CheckResult checkResult = this.UK("written-book-page", list2.get(i), i, itemStackDecodeEvent);
            if (checkResult != CheckResult.DENY) continue;
            return checkResult;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult AV(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemProfile itemProfile = itemStack.getComponentOr(ComponentTypes.PROFILE, null);
        if (itemProfile == null) {
            return CheckResult.ALLOW;
        }
        List<ItemProfile.Property> list = itemProfile.getProperties();
        if (list.size() > this.maxProfileProperties) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "profile-properties"), FlagInfo.of("properties", list.size()), FlagInfo.of("max-properties", this.maxProfileProperties)});
            return CheckResult.DENY;
        }
        int n = 0;
        for (ItemProfile.Property property : list) {
            n += this.byteSize(property.getName());
            n += this.byteSize(property.getValue());
            n += this.byteSize(property.getSignature());
        }
        if (n > this.maxProfileTotalBytes) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "profile-bytes"), FlagInfo.of("bytes", n), FlagInfo.of("max-bytes", this.maxProfileTotalBytes)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult bw(ItemStack itemStack, VisitState visitState, ItemStackDecodeEvent itemStackDecodeEvent) {
        ChargedProjectiles chargedProjectiles = itemStack.getComponentOr(ComponentTypes.CHARGED_PROJECTILES, null);
        if (chargedProjectiles == null) {
            return CheckResult.ALLOW;
        }
        List<ItemStack> list = chargedProjectiles.getItems();
        if (list.size() > this.maxChargedProjectiles) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "charged-projectiles"), FlagInfo.of("projectiles", list.size()), FlagInfo.of("max-projectiles", this.maxChargedProjectiles)});
            return CheckResult.DENY;
        }
        CheckResult checkResult = this.sP(list.size(), "charged-projectiles", visitState, itemStackDecodeEvent);
        if (checkResult == CheckResult.DENY) {
            return checkResult;
        }
        for (ItemStack itemStack2 : list) {
            checkResult = this.bI(itemStack2, visitState, itemStackDecodeEvent);
            if (checkResult != CheckResult.DENY) continue;
            return checkResult;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult Ay(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemTool itemTool = itemStack.getComponentOr(ComponentTypes.TOOL, null);
        if (itemTool == null) {
            return CheckResult.ALLOW;
        }
        int n = itemTool.getRules().size();
        if (n <= this.maxToolRules) {
            return CheckResult.ALLOW;
        }
        this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "tool-rules"), FlagInfo.of("rules", n), FlagInfo.of("max-rules", this.maxToolRules)});
        return CheckResult.DENY;
    }

    private CheckResult Ao(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemAdventurePredicate itemAdventurePredicate = itemStack.getComponentOr(ComponentTypes.CAN_BREAK, null);
        if (itemAdventurePredicate == null) {
            return CheckResult.ALLOW;
        }
        List<ItemAdventurePredicate.BlockPredicate> list = itemAdventurePredicate.getPredicates();
        if (list.size() > this.maxCanBreakPredicates) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "can-break-predicates"), FlagInfo.of("predicates", list.size()), FlagInfo.of("max-predicates", this.maxCanBreakPredicates)});
            return CheckResult.DENY;
        }
        int n = 0;
        for (ItemAdventurePredicate.BlockPredicate blockPredicate : list) {
            List<ItemAdventurePredicate.PropertyMatcher> list2 = blockPredicate.getProperties();
            if (list2 == null) continue;
            n += list2.size();
        }
        if (n <= this.maxCanBreakProperties) {
            return CheckResult.ALLOW;
        }
        this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "can-break-properties"), FlagInfo.of("properties", n), FlagInfo.of("max-properties", this.maxCanBreakProperties)});
        return CheckResult.DENY;
    }

    private CheckResult bZ(ItemStack itemStack, VisitState visitState, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemContainerContents itemContainerContents = itemStack.getComponentOr(ComponentTypes.CONTAINER, null);
        if (itemContainerContents == null) {
            return CheckResult.ALLOW;
        }
        List<ItemStack> list = itemContainerContents.getItems();
        if (list.size() > this.maxContainerItems) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", "container-items"), FlagInfo.of("items", list.size()), FlagInfo.of("max-items", this.maxContainerItems)});
            return CheckResult.DENY;
        }
        CheckResult checkResult = this.sP(list.size(), "container-items", visitState, itemStackDecodeEvent);
        if (checkResult == CheckResult.DENY) {
            return checkResult;
        }
        for (ItemStack itemStack2 : list) {
            checkResult = this.bI(itemStack2, visitState, itemStackDecodeEvent);
            if (checkResult != CheckResult.DENY) continue;
            return checkResult;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult sP(int n, Object object, VisitState visitState, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (n <= 0) {
            return CheckResult.ALLOW;
        }
        visitState.totalNestedItems += n;
        if (visitState.totalNestedItems <= this.maxTotalNestedItems) {
            return CheckResult.ALLOW;
        }
        this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", object), FlagInfo.of("total-nested-items", visitState.totalNestedItems), FlagInfo.of("max-total-nested-items", this.maxTotalNestedItems)});
        return CheckResult.DENY;
    }

    private CheckResult Ua(String string, Filterable filterable, int n, ItemStackDecodeEvent itemStackDecodeEvent) {
        int n2 = this.byteSize((String)filterable.getRaw());
        if (n2 > this.maxBookPageBytes) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string), FlagInfo.of("page-index", n), FlagInfo.of("page-bytes", n2), FlagInfo.of("max-page-bytes", this.maxBookPageBytes)});
            return CheckResult.DENY;
        }
        String string2 = (String)filterable.getFiltered();
        if (string2 == null) {
            return CheckResult.ALLOW;
        }
        int n3 = this.byteSize(string2);
        if (n3 <= this.maxBookPageBytes) {
            return CheckResult.ALLOW;
        }
        this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string + "-filtered"), FlagInfo.of("page-index", n), FlagInfo.of("page-bytes", n3), FlagInfo.of("max-page-bytes", this.maxBookPageBytes)});
        return CheckResult.DENY;
    }

    private CheckResult UK(String string, Filterable filterable, int n, ItemStackDecodeEvent itemStackDecodeEvent) {
        int n2 = this.componentSize((Component)filterable.getRaw());
        if (n2 > this.maxBookPageBytes) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string), FlagInfo.of("page-index", n), FlagInfo.of("page-bytes", n2), FlagInfo.of("max-page-bytes", this.maxBookPageBytes)});
            return CheckResult.DENY;
        }
        Component component = (Component)filterable.getFiltered();
        if (component == null) {
            return CheckResult.ALLOW;
        }
        int n3 = this.componentSize(component);
        if (n3 <= this.maxBookPageBytes) {
            return CheckResult.ALLOW;
        }
        this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("identifier", string + "-filtered"), FlagInfo.of("page-index", n), FlagInfo.of("page-bytes", n3), FlagInfo.of("max-page-bytes", this.maxBookPageBytes)});
        return CheckResult.DENY;
    }

    private int componentSize(Component component) {
        return this.size(component, this.maxBookPageBytes + 1);
    }

    private int size(Object object, int n) {
        if (object == null || n <= 0) {
            return 0;
        }
        if (object instanceof String) {
            String string = (String)object;
            return Math.min(n, this.byteSize(string));
        }
        if (object instanceof Key) {
            Key key = (Key)object;
            return Math.min(n, this.byteSize(key.asString()));
        }
        if (object instanceof TextColor) {
            TextColor textColor = (TextColor)object;
            return Math.min(n, this.byteSize(textColor.asHexString()));
        }
        if (object instanceof ClickEvent) {
            ClickEvent clickEvent = (ClickEvent)object;
            int n2 = this.size((Object)clickEvent.action(), n);
            if (n2 >= n) {
                return n;
            }
            return n2 + this.size(clickEvent.value(), n - n2);
        }
        if (object instanceof HoverEvent) {
            HoverEvent hoverEvent = (HoverEvent)object;
            int n3 = this.size(hoverEvent.action(), n);
            if (n3 >= n) {
                return n;
            }
            return n3 + this.size(hoverEvent.value(), n - n3);
        }
        if (object instanceof Optional) {
            Optional optional = (Optional)object;
            return this.size(optional.orElse(null), n);
        }
        if (object instanceof Map) {
            Map map = (Map)object;
            int n4 = 0;
            for (Map.Entry entry : map.entrySet()) {
                if ((n4 += this.size(entry.getKey(), n - n4)) >= n) {
                    return n;
                }
                if ((n4 += this.size(entry.getValue(), n - n4)) < n) continue;
                return n;
            }
            return n4;
        }
        if (object instanceof Iterable) {
            Iterable iterable = (Iterable)object;
            int n5 = 0;
            for (Object t : iterable) {
                if ((n5 += this.size(t, n - n5)) < n) continue;
                return n;
            }
            return n5;
        }
        if (object instanceof Examinable) {
            Examinable examinable = (Examinable)object;
            int n6 = 0;
            for (ExaminableProperty examinableProperty : examinable.examinableProperties().toList()) {
                if ((n6 += this.size(examinableProperty.name(), n - n6)) >= n) {
                    return n;
                }
                if ((n6 += Math.min(n - n6, examinableProperty.examine(ComponentExaminer.componentExaminer))) < n) continue;
                return n;
            }
            return n6;
        }
        if (object.getClass().isArray()) {
            int n7 = 0;
            int n8 = Array.getLength(object);
            for (int i = 0; i < n8; ++i) {
                if ((n7 += this.size(Array.get(object, i), n - n7)) < n) continue;
                return n;
            }
            return n7;
        }
        if (object instanceof Enum) {
            Enum enum_ = (Enum)object;
            return Math.min(n, this.byteSize(enum_.name()));
        }
        if (object instanceof Number || object instanceof Boolean || object instanceof Character) {
            return Math.min(n, this.byteSize(String.valueOf(object)));
        }
        return 0;
    }

    private int byteSize(String string) {
        if (string == null) {
            return 0;
        }
        return string.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }

    static final class ComponentExaminer
    implements Examiner<Integer> {
        private static final ComponentExaminer componentExaminer = new ComponentExaminer();

        private ComponentExaminer() {
        }

        public Integer examineProperties(String string, Stream stream) {
            int n = ComponentExaminer.byteSize(string);
            for (ExaminableProperty examinableProperty : stream.toList()) {
                n += ComponentExaminer.byteSize(examinableProperty.name());
                n += examinableProperty.examine(this).intValue();
            }
            return n;
        }

        public Integer examineValue(Object object) {
            if (object == null) {
                return 0;
            }
            if (object instanceof String) {
                String string = (String)object;
                return ComponentExaminer.byteSize(string);
            }
            if (object instanceof Key) {
                Key key = (Key)object;
                return ComponentExaminer.byteSize(key.asString());
            }
            if (object instanceof TextColor) {
                TextColor textColor = (TextColor)object;
                return ComponentExaminer.byteSize(textColor.asHexString());
            }
            if (object instanceof Enum) {
                Enum enum_ = (Enum)object;
                return ComponentExaminer.byteSize(enum_.name());
            }
            if (object instanceof Number || object instanceof Boolean || object instanceof Character) {
                return ComponentExaminer.byteSize(String.valueOf(object));
            }
            if (object instanceof ClickEvent) {
                ClickEvent clickEvent = (ClickEvent)object;
                return this.examineValue((Object)clickEvent.action()) + this.examineString(clickEvent.value());
            }
            if (object instanceof HoverEvent) {
                HoverEvent hoverEvent = (HoverEvent)object;
                return this.examineValue(hoverEvent.action()) + this.examineValue(hoverEvent.value());
            }
            if (object instanceof Optional) {
                Optional optional = (Optional)object;
                return this.examineValue(optional.orElse(null));
            }
            if (object instanceof Examinable) {
                Examinable examinable = (Examinable)object;
                return (Integer)this.examine(examinable);
            }
            if (object instanceof Map) {
                Map map = (Map)object;
                int n = 0;
                for (Map.Entry entry : map.entrySet()) {
                    n += this.examineValue(entry.getKey()).intValue();
                    n += this.examineValue(entry.getValue()).intValue();
                }
                return n;
            }
            if (object instanceof Iterable) {
                Iterable iterable = (Iterable)object;
                int n = 0;
                for (Object t : iterable) {
                    n += this.examineValue(t).intValue();
                }
                return n;
            }
            if (object.getClass().isArray()) {
                int n = 0;
                int n2 = Array.getLength(object);
                for (int i = 0; i < n2; ++i) {
                    n += this.examineValue(Array.get(object, i)).intValue();
                }
                return n;
            }
            return 0;
        }

        public Integer examineBoolean(boolean bl) {
            return ComponentExaminer.byteSize(String.valueOf(bl));
        }

        @Override
        public Integer examine(boolean[] blArray) {
            boolean[] blArray2 = blArray;
            return ComponentExaminer.examineArray(blArray2 == null ? 0 : blArray2.length);
        }

        public Integer Mj(int n) {
            return ComponentExaminer.byteSize(String.valueOf(n));
        }

        public Integer examineBytes(byte[] byArray) {
            byte[] byArray2 = byArray;
            return ComponentExaminer.examineArray(byArray2 == null ? 0 : byArray2.length);
        }

        public Integer NI(int n) {
            return ComponentExaminer.byteSize(String.valueOf((char)n));
        }

        public Integer examineChars(char[] cArray) {
            char[] cArray2 = cArray;
            return cArray2 == null ? 0 : ComponentExaminer.byteSize(new String(cArray2));
        }

        public Integer examineDouble(double d) {
            return ComponentExaminer.byteSize(String.valueOf(d));
        }

        public Integer examineDoubles(double[] dArray) {
            double[] dArray2 = dArray;
            return ComponentExaminer.examineArray(dArray2 == null ? 0 : dArray2.length);
        }

        public Integer examineFloat(float f2) {
            return ComponentExaminer.byteSize(String.valueOf(f2));
        }

        public Integer examineFloats(float[] fArray) {
            float[] fArray2 = fArray;
            return ComponentExaminer.examineArray(fArray2 == null ? 0 : fArray2.length);
        }

        public Integer Y4(int n) {
            return ComponentExaminer.byteSize(String.valueOf(n));
        }

        public Integer examineInts(int[] nArray) {
            int[] nArray2 = nArray;
            return ComponentExaminer.examineArray(nArray2 == null ? 0 : nArray2.length);
        }

        public Integer examineLong(long l) {
            return ComponentExaminer.byteSize(String.valueOf(l));
        }

        public Integer examineLongs(long[] lArray) {
            long[] lArray2 = lArray;
            return ComponentExaminer.examineArray(lArray2 == null ? 0 : lArray2.length);
        }

        public Integer Yl(int n) {
            return ComponentExaminer.byteSize(String.valueOf(n));
        }

        public Integer examineShorts(short[] sArray) {
            short[] sArray2 = sArray;
            return ComponentExaminer.examineArray(sArray2 == null ? 0 : sArray2.length);
        }

        public Integer examineString(String string) {
            return ComponentExaminer.byteSize(string);
        }

        private static int examineArray(int n) {
            return ComponentExaminer.byteSize(String.valueOf(n));
        }

        private static int byteSize(String string) {
            if (string == null) {
                return 0;
            }
            return string.getBytes(StandardCharsets.UTF_8).length;
        }

        private static we a(we we2) {
            return we2;
        }
    }

    static class VisitState {
        private int totalNestedItems;

        private VisitState() {
        }
    }
}

