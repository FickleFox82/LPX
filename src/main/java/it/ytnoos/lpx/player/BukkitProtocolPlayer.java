/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx.player;

import com.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.CheckResultInfo;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.player.PrinterData;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.player.PrinterDataImpl;
import it.ytnoos.lpx.we;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class BukkitProtocolPlayer
implements ProtocolPlayer {
    private final LPXBukkit lpxBukkit;
    private final Map<Object, CheckContainer<?>> checks;
    private final User user;
    private final PrinterData printerData;
    @Nullable
    private Player player;
    private boolean X = false;
    private boolean r = false;
    private boolean p = false;
    private boolean n = false;
    private ReceivedPacket receivedPacket;
    private long K;
    private long Z;
    private long W;
    private long U;
    private long z;
    private long x;
    private long T = 0L;
    private long D = 0L;
    private double O = 0.0;
    private final AtomicBoolean atomicBoolean = new AtomicBoolean();
    private volatile long Y;
    private static final String BYPASS_PERMISSION = "lpx.bypass";

    public BukkitProtocolPlayer(LPXBukkit lPXBukkit, User user) {
        this.lpxBukkit = lPXBukkit;
        this.user = user;
        this.checks = lPXBukkit.getCheckManager().createPlayerChecks(this);
        this.printerData = new PrinterDataImpl(lPXBukkit, this);
    }

    @Override
    public Optional<Player> getPlayer() {
        if (this.player != null) {
            return Optional.of(this.player);
        }
        UUID uUID = this.user.getUUID();
        if (uUID == null) {
            return Optional.empty();
        }
        Player player = Bukkit.getPlayer((UUID)uUID);
        if (player == null) {
            return Optional.empty();
        }
        this.player = player;
        this.X = player.hasPermission(BYPASS_PERMISSION);
        return Optional.of(player);
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public String getUsername() {
        return this.user.getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.user.getUUID();
    }

    @Override
    public PrinterData getPrinterData() {
        return this.printerData;
    }

    @Override
    public boolean isBedrock() {
        return this.p;
    }

    @Override
    public void setBedrock(boolean bl) {
        this.p = bl;
    }

    @Override
    public boolean isProcessingPayload() {
        return this.n;
    }

    @Override
    public void setProcessingPayload(boolean bl) {
        this.n = bl;
    }

    public LPXBukkit getLpxBukkit() {
        return this.lpxBukkit;
    }

    @Override
    public Map<Object, CheckContainer<?>> getChecks() {
        return this.checks;
    }

    @Override
    public CheckContainer<?> getChecks(Object object) {
        CheckContainer<?> checkContainer = this.checks.get(object);
        return checkContainer != null ? checkContainer : CheckManager.EMPTY_CONTAINER;
    }

    @Override
    public boolean hasAnyCheck(Object object, boolean bl) {
        for (Check check : this.getChecks(object)) {
            if (check.isPreVia() != bl) continue;
            return true;
        }
        return false;
    }

    @Override
    public CheckResultInfo handleChecks(Object object, Object object2) {
        return this.handleChecks(object, object2, false);
    }

    @Override
    public CheckResultInfo handleChecks(Object object, Object object2, boolean bl) {
        CheckResultInfo checkResultInfo = new CheckResultInfo(CheckResult.ALLOW);
        for (Check check : this.getChecks(object)) {
            if (check.isPreVia() != bl) continue;
            CheckResult checkResult = check.handle(object2);
            CheckResult checkResult2 = checkResult.merge(checkResultInfo.result());
            if (checkResult2 != checkResultInfo.result()) {
                checkResultInfo = new CheckResultInfo(checkResult2, check);
            }
            if (checkResult2 != CheckResult.DENY) continue;
            return checkResultInfo;
        }
        return checkResultInfo;
    }

    @Override
    public <T> Check<T> getCheck(Object object, CheckType checkType, LPX lPX) {
        for (Check check : this.checks.get(object)) {
            if (check.getType() != checkType) continue;
            return check;
        }
        return null;
    }

    @Override
    public ReceivedPacket getLastReceivedPacket() {
        return this.receivedPacket;
    }

    @Override
    public long getLastAlert() {
        return this.K;
    }

    @Override
    public long getLastPunish() {
        return this.Z;
    }

    @Override
    public long getLastTeleport() {
        return this.W;
    }

    @Override
    public long getLastPortalTeleport() {
        return this.U;
    }

    @Override
    public long getLastShears() {
        return this.z;
    }

    @Override
    public long getLastContainerInteract() {
        return this.x;
    }

    @Override
    public void setLastReceivedPacket(ReceivedPacket receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    @Override
    public void updateLastPunish() {
        this.Z = TimeUtils.current();
    }

    public boolean tF() {
        return this.atomicBoolean.compareAndSet(false, true);
    }

    public long tC() {
        return this.Y;
    }

    public void ks(boolean bl) {
        if (bl) {
            this.Y = TimeUtils.current();
        }
        this.atomicBoolean.set(false);
    }

    @Override
    public void updateLastAlert() {
        this.K = TimeUtils.current();
    }

    @Override
    public void updateLastTeleport() {
        this.W = TimeUtils.current();
    }

    @Override
    public void updateLastPortalTeleport() {
        this.U = TimeUtils.current();
    }

    @Override
    public void updateLastShears() {
        this.z = TimeUtils.current();
    }

    @Override
    public void updateLastContainerInteract() {
        this.x = TimeUtils.current();
    }

    @Override
    public long incrementPacketCount() {
        return ++this.T;
    }

    @Override
    public long getPacketCount() {
        return this.T;
    }

    @Override
    public long getLastKeepAliveSent() {
        return this.D;
    }

    @Override
    public void setLastKeepAliveSent(long l) {
        this.D = l;
    }

    @Override
    public double getPlayerPing() {
        return this.O;
    }

    @Override
    public void setPlayerPing(double d) {
        this.O = d;
    }

    @Override
    public void flag(Check<?> check, Object object, FlagInfo ... flagInfoArray) {
        this.lpxBukkit.getAlertManager().flag(this, check, flagInfoArray);
    }

    @Override
    public void punish(Check<?> check) {
        this.lpxBukkit.getAlertManager().punish(this, check);
    }

    @Override
    public void kick(String string) {
        if (this.isKicked()) {
            return;
        }
        this.r = true;
        this.lpxBukkit.forceKick(this, string);
    }

    @Override
    public boolean isKicked() {
        return this.r || !this.getPlayer().isPresent();
    }

    @Override
    public boolean ignoreChecks() {
        return this.p || this.lpxBukkit.getConfigManager().getOptionsConfig().isBypassPermission() && this.hasExemptPermission();
    }

    @Override
    public boolean hasExemptPermission() {
        return this.X;
    }

    @Override
    public LPX lpx() {
        return this.lpxBukkit;
    }

    private static we a(we we2) {
        return we2;
    }
}

