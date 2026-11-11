/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.window;

import com.github.retrooper.packetevents.protocol.item.HashedStack;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.util.Map;
import java.util.Optional;

public class WindowE
extends PlayerCheck<WrapperPlayClientClickWindow> {
    private int maxHashedSlotChanges;
    private int maxHashedComponentsPerStack;

    public WindowE(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.maxHashedSlotChanges = this.getInt("max-hashed-slot-changes", 90);
        this.maxHashedComponentsPerStack = this.getInt("max-hashed-components-per-stack", 128);
    }

    public CheckResult AB(Object object, WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        Map<Integer, Optional<HashedStack>> map = wrapperPlayClientClickWindow.getHashedSlots();
        if (map != null) {
            if (map.size() > this.maxHashedSlotChanges) {
                this.Fn(wrapperPlayClientClickWindow, new FlagInfo[]{FlagInfo.of("hashed-slot-changes", map.size()), FlagInfo.of("max-hashed-slot-changes", this.maxHashedSlotChanges), FlagInfo.of("click-type", wrapperPlayClientClickWindow.getWindowClickType().name())});
                return CheckResult.DENY;
            }
            for (Map.Entry entry : map.entrySet()) {
                CheckResult checkResult = this.lP(wrapperPlayClientClickWindow, "slot[" + String.valueOf(entry.getKey()) + "]", ((Optional)entry.getValue()).orElse(null));
                if (checkResult != CheckResult.DENY) continue;
                return checkResult;
            }
        }
        Optional<HashedStack> optional = wrapperPlayClientClickWindow.getCarriedHashedStack();
        return this.lP(wrapperPlayClientClickWindow, "carried", optional == null ? null : (HashedStack)optional.orElse(null));
    }

    private CheckResult lP(WrapperPlayClientClickWindow wrapperPlayClientClickWindow, Object object, HashedStack hashedStack) {
        int n;
        if (hashedStack == null) {
            return CheckResult.ALLOW;
        }
        int n2 = hashedStack.getComponents().getAddedComponents().size();
        int n3 = n2 + (n = hashedStack.getComponents().getRemovedComponents().size());
        if (n3 > this.maxHashedComponentsPerStack) {
            this.Fn(wrapperPlayClientClickWindow, new FlagInfo[]{FlagInfo.of("identifier", object), FlagInfo.of("item", hashedStack.getItem().getName().toString()), FlagInfo.of("added", n2), FlagInfo.of("removed", n), FlagInfo.of("component-changes", n3), FlagInfo.of("max-component-changes", this.maxHashedComponentsPerStack)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

