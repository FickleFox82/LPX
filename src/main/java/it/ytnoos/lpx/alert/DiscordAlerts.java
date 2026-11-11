/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package it.ytnoos.lpx.alert;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.config.DiscordConfig;
import it.ytnoos.lpx.utils.DiscordWebhook;
import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscordAlerts {
    private static final Logger log = LogManager.getLogger(DiscordAlerts.class);
    private static final Pattern URL_PATTERN = Pattern.compile("(?:https?://)?(?:\\w+\\.)?\\w+\\.\\w+/api(?:/v\\d+)?/webhooks/(\\d+)/([\\w-]+)(?:/(?:\\w+)?)?");
    private final LPXBukkit lpxBukkit;
    private Color color;
    private String url;

    public DiscordAlerts(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    public void KW() {
        DiscordConfig discordConfig = this.lpxBukkit.getConfigManager().getDiscordConfig();
        if (!discordConfig.isEnabled()) {
            return;
        }
        String string = discordConfig.getUrl();
        if (string == null || string.isEmpty()) {
            return;
        }
        Matcher matcher = URL_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Webhook URL is not valid! " + string);
        }
        this.color = Color.decode(discordConfig.Aj());
        this.url = string;
    }

    public void sendAlert(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfoArray) {
        Object object2;
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        if (this.url == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object2 : this.lpxBukkit.getConfigManager().getDiscordConfig().getContent()) {
            stringBuilder.append((String)object2).append("\n");
        }
        Object object3 = this.lpxBukkit.getAlertManager().replace(stringBuilder.toString(), protocolPlayer, check, flagInfoArray2);
        object3 = ((String)object3).replaceAll("\u00a7[a-zA-Z0-9]", "");
        object3 = ((String)object3).replace("_", "\\_");
        object2 = new DiscordWebhook(this.url);
        ((DiscordWebhook)object2).p2(new DiscordWebhook.EmbedObject().Su("https://i.stack.imgur.com/Fzh0w.png").SE("https://crafthead.net/helm/" + protocolPlayer.getUsername()).hH(this.color).Sp("**LPX**").Si((String)object3).TS(Instant.now()));
        try {
            ((DiscordWebhook)object2).execute();
        }
        catch (IOException iOException) {
            if (iOException.getMessage() == null || !iOException.getMessage().contains("429")) {
                this.lpxBukkit.getLogger().log(Level.WARNING, "Unable to send alert to Discord due to IOException: " + iOException.getMessage() + ", Payload: " + ((DiscordWebhook)object2).getJson(), iOException);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.lpxBukkit.getLogger().log(Level.WARNING, "Unable to send alert to Discord: " + illegalArgumentException.getMessage() + ", Payload: " + ((DiscordWebhook)object2).getJson(), illegalArgumentException);
        }
        catch (Exception exception) {
            this.lpxBukkit.getLogger().log(Level.WARNING, "An unexpected error occurred while trying to send Discord alert: " + ((DiscordWebhook)object2).getJson(), exception);
        }
    }
}

