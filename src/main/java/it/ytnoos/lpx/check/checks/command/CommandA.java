/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package it.ytnoos.lpx.check.checks.command;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.utils.string.StringUtils;
import it.ytnoos.lpx.we;
import java.util.List;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandA
extends PlayerCheck<PlayerCommandPreprocessEvent> {
    private static final String[] t = new String[]{"//calc", "//calculate", "//eval", "//evaluate", "//solve", "/hd readtext", "/holo readtext", "/hologram readtext", "/holograms readtext", "/holographicdisplays readtext", "/pex promote", "/pex demote", "/promote", "/demote", "/execute", "/mv ^", "/mv help ^", "/mvhelp ^", "/mv <", "/mv help <", "/mvhelp <", "/$"};
    private List<String> commands;

    public CommandA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.commands = this.getStringList("commands", t);
    }

    protected CheckResult z2(Object object, PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        String string = playerCommandPreprocessEvent.getMessage();
        Object var10_4 = null;
        if (string == null || this.commands == null) {
            return CheckResult.ALLOW;
        }
        for (String string2 : this.commands) {
            if (StringUtils.J0(string, string2)) {
                playerCommandPreprocessEvent.setCancelled(true);
                this.Fn(playerCommandPreprocessEvent, new FlagInfo[]{FlagInfo.of("command", string)});
                return CheckResult.DENY;
            }
            if (var10_4 == null) continue;
            PacketWrapper[] cfr_ignored_0 = new PacketWrapper[1];
            break;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

