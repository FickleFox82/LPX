/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  org.bukkit.plugin.Plugin
 */
package it.ytnoos.lpx.logger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.bukkit.plugin.Plugin;

public class LogWriter {
    private static final DateTimeFormatter ddMMYyyy = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final SimpleDateFormat q = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat A = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private final LPXBukkit lpxBukkit;
    private final Plugin plugin;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("lpx-log-writer-%d").build());
    private LocalDate today = LocalDate.now();
    private File file;
    private PrintWriter writer;
    private boolean active = false;

    public LogWriter(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
        this.plugin = lPXBukkit.getPlugin();
    }

    public void KE() {
        this.KP();
        this.deleteOldZips();
    }

    public void writeFlag(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfoArray) {
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        if (!this.lpxBukkit.getConfigManager().getOptionsConfig().isAlertsStore()) {
            return;
        }
        this.executor.execute(() -> this.writeFlagThread(protocolPlayer, check, flagInfoArray2));
    }

    public void writeString(Supplier supplier) {
        this.executor.execute(() -> this.lambda$writeString$1((Supplier)supplier));
    }

    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    public void writeException(Supplier supplier, Throwable throwable) {
        this.executor.execute(() -> this.lambda$writeException$2((Supplier)supplier, throwable));
    }

    public void YD(Supplier supplier, Throwable throwable) {
        this.YK(supplier, throwable);
    }

    private void writeFlagThread(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfoArray) {
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        this.hj(() -> {
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = true;
            for (FlagInfo flagInfo : flagInfoArray2) {
                if (!bl) {
                    stringBuilder.append(", ");
                }
                bl = false;
                stringBuilder.append(flagInfo.getName()).append("=").append(flagInfo.getValue());
            }
            String string = String.valueOf(stringBuilder);
            int n = check.getOptions().getCheckOptions().getMaxVL();
            int n2 = check.getOptions().getVl();
            String string2 = check.getType().getId().name();
            String string3 = check.getType().getCategory().getFormattedName();
            return protocolPlayer.getUsername() + " " + string3 + " " + string2 + " " + n2 + "/" + n + " [" + string + "]";
        });
    }

    private void hj(Supplier supplier) {
        this.setupFile();
        if (!this.active || this.file == null) {
            return;
        }
        this.writer.println("[" + A.format(System.currentTimeMillis()) + "] " + (String)supplier.get());
    }

    private void YK(Supplier supplier, Throwable throwable) {
        this.setupFile();
        if (!this.active || this.file == null) {
            return;
        }
        String string = "[" + A.format(System.currentTimeMillis()) + "] ";
        this.writer.println(string + (String)supplier.get());
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        for (String string2 : stringWriter.toString().split("\\R")) {
            this.writer.println(string + string2);
        }
    }

    private void setupFile() {
        boolean bl;
        boolean bl2 = bl = this.today.getDayOfYear() == LocalDate.now().getDayOfYear();
        if (this.file != null && bl) {
            return;
        }
        if (!bl && this.file != null) {
            if (this.writer != null) {
                this.writer.close();
            }
            this.zipFile(this.file);
        }
        this.today = LocalDate.now();
        this.file = new File(this.plugin.getDataFolder(), "logs/" + q.format(System.currentTimeMillis()) + ".log");
        this.file.getParentFile().mkdirs();
        try {
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
            this.writer = new PrintWriter(new FileWriter(this.file, true), true);
            this.active = true;
        }
        catch (Exception exception) {
            this.plugin.getLogger().log(Level.SEVERE, exception, () -> "Unable to create log file");
        }
    }

    private void zipFile(File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(this.plugin.getDataFolder(), "logs/" + file.getName().split("\\.")[0] + ".zip"));
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);){
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            try (FileInputStream fileInputStream = new FileInputStream(file);){
                int n;
                byte[] byArray = new byte[1024];
                while ((n = fileInputStream.read(byArray)) > 0) {
                    zipOutputStream.write(byArray, 0, n);
                }
            }
            zipOutputStream.closeEntry();
        }
        catch (Exception exception) {
            this.plugin.getLogger().log(Level.SEVERE, exception, () -> "Unable to zip file " + file.getName());
        }
        file.delete();
    }

    public void Ku() {
        this.executor.shutdownNow();
        if (this.writer != null) {
            this.writer.close();
        }
    }

    private void KP() {
        File file = new File(this.plugin.getDataFolder(), "logs");
        if (!file.exists()) {
            return;
        }
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return;
        }
        for (File file2 : fileArray) {
            if (!file2.getName().endsWith(".log")) continue;
            try {
                LocalDate localDate = LocalDate.parse(file2.getName().split("\\.")[0], ddMMYyyy);
                if (localDate.getDayOfYear() == LocalDate.now().getDayOfYear()) continue;
                this.zipFile(file2);
            }
            catch (Exception exception) {}
        }
    }

    private void deleteOldZips() {
        File file = new File(this.plugin.getDataFolder(), "logs");
        if (!file.exists()) {
            return;
        }
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return;
        }
        for (File file2 : fileArray) {
            if (!file2.getName().endsWith(".zip")) continue;
            try {
                LocalDate localDate = LocalDate.parse(file2.getName().split("\\.")[0], ddMMYyyy);
                if (ChronoUnit.DAYS.between(localDate, LocalDate.now()) <= (long)this.lpxBukkit.getConfigManager().getOptionsConfig().getAlertsDays()) continue;
                file2.delete();
            }
            catch (DateTimeParseException dateTimeParseException) {
            }
            catch (SecurityException securityException) {
                this.plugin.getLogger().log(Level.SEVERE, securityException, () -> "Unable to delete file " + file2.getName());
            }
        }
    }

    private /* synthetic */ void lambda$writeException$2(Supplier supplier, Throwable throwable) {
        this.YK(supplier, throwable);
    }

    private /* synthetic */ void lambda$writeString$1(Supplier supplier) {
        this.hj(supplier);
    }
}

