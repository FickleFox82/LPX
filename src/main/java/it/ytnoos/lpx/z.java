/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.wrapper.e1;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.utils.AESCipher;
import it.ytnoos.lpx.utils.Constants;
import it.ytnoos.lpx.utils.SecurityUtils;
import it.ytnoos.lpx.utils.StreamUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class z {
    private final LPX \u03e0;
    private final String \u00cb;
    private final boolean \u019e;

    public z(LPX lPX) {
        this.\u03e0 = lPX;
        this.\u00cb = lPX.getPlugin().getConfig().getString("license");
        this.\u019e = lPX.getPlugin().getConfig().getBoolean("force-fallback");
    }

    public void R\u04a1() {
        boolean bl = this.R\u0389();
        if (bl) {
            this.\u03e0.getLogger().log(Level.INFO, "License successfully validated!");
            return;
        }
        this.\u03e0.getLogger().log(Level.SEVERE, "Unable to verify your license!");
        this.\u03e0.disable(false);
    }

    private boolean R\u0389() {
        return this.\u04b2\u0405(0);
    }

    private boolean \u04b2\u0405(int n) {
        int n2 = n;
        ++n2;
        try {
            String string = "http://lpx.ytnoos.it/newlicense";
            if (n2 >= 3 || this.\u019e) {
                string = "http://lpx.ytnoos.it/newlicense";
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(6000);
            httpURLConnection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            StreamUtils.write(dataOutputStream, this.\u00cb.getBytes());
            StreamUtils.write(dataOutputStream, this.\u03e0.getPlugin().getDescription().getVersion().getBytes());
            byte[] byArray = this.tL();
            if (byArray.length == 0) {
                this.\u03e0.getLogger().severe("Failed to find a valid 'LPX.jar' file! Please don't rename your 'LPX.jar' file!");
                return false;
            }
            AESCipher aESCipher = new AESCipher(SecurityUtils.merge(byArray, new Constants().RESOURCE_ID.trim().getBytes()));
            StreamUtils.write(dataOutputStream, aESCipher.p3(("" + this.\u03e0.getPlugin().getServer().getPort()).getBytes()));
            dataOutputStream.close();
            if (httpURLConnection.getResponseCode() != 200) {
                return false;
            }
            byte[] byArray2 = aESCipher.p7(StreamUtils.readByteArray(httpURLConnection.getInputStream()));
            byte[] byArray3 = aESCipher.p7(StreamUtils.readByteArray(httpURLConnection.getInputStream()));
            Class<?> clazz = Class.forName(e1.a(SecurityUtils.gi(byArray2)));
            Object obj = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Class[] classArray = new Class[]{LPXBukkit.class};
            Class<?> clazz2 = clazz;
            Method method = clazz2.getDeclaredMethod(e1.b(SecurityUtils.gi(byArray3), clazz2, classArray), classArray);
            method.setAccessible(true);
            method.invoke(obj, this.\u03e0);
            this.\u03e0.setUpdated(!Boolean.parseBoolean(SecurityUtils.gi(StreamUtils.readByteArray(httpURLConnection.getInputStream()))));
            return true;
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
        catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException generalSecurityException) {
            return false;
        }
        catch (IOException iOException) {
            if (n2 <= 6) {
                return this.\u04b2\u0405(n2);
            }
            iOException.printStackTrace();
            return false;
        }
    }

    private byte[] tL() throws IOException, NoSuchAlgorithmException {
        if (!PacketEvents.getAPI().getServerManager().getVersion().isNewerThan(ServerVersion.V_1_20_4)) {
            return SecurityUtils.download(this.getClass().getClassLoader().getResource(this.getClass().getName().replace('.', '/') + ".class"));
        }
        Path path = this.\u03e0.getPlugin().getDataFolder().getParentFile().toPath();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path, "LPX*.jar");){
            for (Path path2 : directoryStream) {
                try (JarInputStream jarInputStream = new JarInputStream(Files.newInputStream(path2, new OpenOption[0]));){
                    JarEntry jarEntry;
                    while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                        if (!jarEntry.getName().endsWith("/z.class")) continue;
                        byte[] byArray = SecurityUtils.Wy(jarInputStream);
                        return byArray;
                    }
                }
            }
        }
        return new byte[0];
    }

    public z(LPX lPX, String string, boolean bl) {
        this.\u03e0 = lPX;
        this.\u00cb = string;
        this.\u019e = bl;
    }
}

