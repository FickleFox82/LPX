/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryDragEvent
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.server.PluginDisableEvent
 */
package it.ytnoos.lpx;

import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx._c;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.we;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

class wq
implements Listener {
    final /* synthetic */ InventoryManager this$0;
    private static final long a = _c.a(-5070142650187420524L, 6326694588622944445L, MethodHandles.lookup().lookupClass()).a(190049456072190L);
    private static final long[] b;
    private static final Integer[] c;
    private static final Map d;

    wq(InventoryManager inventoryManager) {
        this.this$0 = inventoryManager;
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        long l = a ^ 0x5B307DFC91D6L;
        long l2 = l ^ 0xE4081FBFE8FL;
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.this$0.X.containsKey(player)) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.COLLECT_TO_CURSOR || inventoryClickEvent.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || inventoryClickEvent.getAction() == InventoryAction.NOTHING) {
            inventoryClickEvent.setCancelled(true);
            return;
        }
        if (inventoryClickEvent.getClickedInventory() == this.this$0.lpxBukkit.getInventoryViewAdapter(player).getTopInventory()) {
            inventoryClickEvent.setCancelled(true);
            int n = inventoryClickEvent.getSlot() / 9;
            int n2 = inventoryClickEvent.getSlot() % 9;
            if (n < 0 || n2 < 0) {
                return;
            }
            SmartInventory smartInventory = this.this$0.X.get(player);
            if (n >= smartInventory.Pr() || n2 >= smartInventory.PH()) {
                return;
            }
            smartInventory.getListeners().stream().filter(inventoryListener -> inventoryListener.getType() == InventoryClickEvent.class).forEach(inventoryListener -> inventoryListener.accept(inventoryClickEvent));
            this.this$0.S.get(player).A(n, l2, n2).ifPresent(clickableItem -> clickableItem.O5(inventoryClickEvent));
            player.updateInventory();
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onInventoryDrag(InventoryDragEvent inventoryDragEvent) {
        Player player = (Player)inventoryDragEvent.getWhoClicked();
        if (!this.this$0.X.containsKey(player)) {
            return;
        }
        SmartInventory smartInventory = this.this$0.X.get(player);
        Iterator iterator = inventoryDragEvent.getRawSlots().iterator();
        while (iterator.hasNext()) {
            int n = (Integer)iterator.next();
            if (n >= this.this$0.lpxBukkit.getInventoryViewAdapter(player).getTopInventory().getSize()) continue;
            inventoryDragEvent.setCancelled(true);
            break;
        }
        smartInventory.getListeners().stream().filter(inventoryListener -> inventoryListener.getType() == InventoryDragEvent.class).forEach(inventoryListener -> inventoryListener.accept(inventoryDragEvent));
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent inventoryOpenEvent) {
        Player player = (Player)inventoryOpenEvent.getPlayer();
        if (!this.this$0.X.containsKey(player)) {
            return;
        }
        SmartInventory smartInventory = this.this$0.X.get(player);
        smartInventory.getListeners().stream().filter(inventoryListener -> inventoryListener.getType() == InventoryOpenEvent.class).forEach(inventoryListener -> inventoryListener.accept(inventoryOpenEvent));
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player)inventoryCloseEvent.getPlayer();
        if (!this.this$0.X.containsKey(player)) {
            return;
        }
        SmartInventory smartInventory = this.this$0.X.get(player);
        smartInventory.getListeners().stream().filter(inventoryListener -> inventoryListener.getType() == InventoryCloseEvent.class).forEach(inventoryListener -> inventoryListener.accept(inventoryCloseEvent));
        if (smartInventory.isCloseable()) {
            inventoryCloseEvent.getInventory().clear();
            this.this$0.X.remove(player);
            this.this$0.S.remove(player);
        } else {
            FoliaScheduler.getEntityScheduler().run((Entity)player, this.this$0.plugin, object -> player.openInventory(inventoryCloseEvent.getInventory()), null);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if (!this.this$0.X.containsKey(player)) {
            return;
        }
        SmartInventory smartInventory = this.this$0.X.get(player);
        smartInventory.getListeners().stream().filter(inventoryListener -> inventoryListener.getType() == PlayerQuitEvent.class).forEach(inventoryListener -> inventoryListener.accept(playerQuitEvent));
        this.this$0.X.remove(player);
        this.this$0.S.remove(player);
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPluginDisable(PluginDisableEvent pluginDisableEvent) {
        new HashMap<Player, SmartInventory>(this.this$0.X).forEach((player, smartInventory) -> {
            smartInventory.getListeners().stream().filter(inventoryListener -> inventoryListener.getType() == PluginDisableEvent.class).forEach(inventoryListener -> inventoryListener.accept(pluginDisableEvent));
            smartInventory.close((Player)player);
        });
        this.this$0.X.clear();
        this.this$0.S.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    static {
        d = new HashMap(13);
        long l = a ^ 0x5EA2428CEC92L;
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        byte[] byArray = new byte[8];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)(l >>> 56);
        for (int i = 1; i < 8; ++i) {
            byArray2 = byArray2;
            byArray2[i] = (byte)(l << i * 8 >>> 56);
        }
        cipher.init(2, (Key)secretKeyFactory.generateSecret(new DESKeySpec(byArray2)), new IvParameterSpec(new byte[8]));
        long[] lArray = new long[2];
        int n = 0;
        String string = "\u00b3\u00fd\u0098\u008a\u0095\u0082M\tq\\\u009f\u00c9\u00b3\u00d3D\u00a2";
        int n2 = "\u00b3\u00fd\u0098\u008a\u0095\u0082M\tq\\\u009f\u00c9\u00b3\u00d3D\u00a2".length();
        int n3 = 0;
        do {
            byte[] byArray3 = string.substring(n3, n3 += 8).getBytes("ISO-8859-1");
            int n4 = n++;
            long l2 = ((long)byArray3[0] & 0xFFL) << 56 | ((long)byArray3[1] & 0xFFL) << 48 | ((long)byArray3[2] & 0xFFL) << 40 | ((long)byArray3[3] & 0xFFL) << 32 | ((long)byArray3[4] & 0xFFL) << 24 | ((long)byArray3[5] & 0xFFL) << 16 | ((long)byArray3[6] & 0xFFL) << 8 | (long)byArray3[7] & 0xFFL;
            byte[] byArray4 = cipher.doFinal(new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2});
            lArray[n4] = ((long)byArray4[0] & 0xFFL) << 56 | ((long)byArray4[1] & 0xFFL) << 48 | ((long)byArray4[2] & 0xFFL) << 40 | ((long)byArray4[3] & 0xFFL) << 32 | ((long)byArray4[4] & 0xFFL) << 24 | ((long)byArray4[5] & 0xFFL) << 16 | ((long)byArray4[6] & 0xFFL) << 8 | (long)byArray4[7] & 0xFFL;
        } while (n3 < n2);
        b = lArray;
        c = new Integer[2];
    }

    private static we a(we we2) {
        return we2;
    }

    private static int a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x6D3E;
        if (c[n2] == null) {
            byte[] byArray;
            byte[] byArray2 = new byte[]{(byte)(l >>> 56), (byte)(l >>> 48), (byte)(l >>> 40), (byte)(l >>> 32), (byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l};
            long l2 = b[n2];
            byte[] byArray3 = new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2};
            Long l3 = Thread.currentThread().getId();
            Object[] objectArray = (Object[])d.get(l3);
            try {
                if (objectArray == null) {
                    objectArray = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    d.put(l3, objectArray);
                }
                DESKeySpec dESKeySpec = new DESKeySpec(byArray2);
                SecretKey secretKey = ((SecretKeyFactory)objectArray[1]).generateSecret(dESKeySpec);
                Cipher cipher = (Cipher)objectArray[0];
                cipher.init(2, (Key)secretKey, (IvParameterSpec)objectArray[2]);
                byArray = cipher.doFinal(byArray3);
            }
            catch (Exception exception) {
                throw new RuntimeException("it/ytnoos/lpx/wq", exception);
            }
            int n3 = (byArray[4] & 0xFF) << 24 | (byArray[5] & 0xFF) << 16 | (byArray[6] & 0xFF) << 8 | byArray[7] & 0xFF;
            wq.c[n2] = n3;
        }
        return c[n2];
    }

    private static int a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        int n2 = wq.a(n, l);
        MethodHandle methodHandle = MethodHandles.constant(Integer.TYPE, n2);
        mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, Integer.TYPE, Long.TYPE));
        return n2;
    }

    private static CallSite a(MethodHandles.Lookup lookup, String string, MethodType methodType) {
        MutableCallSite mutableCallSite = new MutableCallSite(methodType);
        try {
            mutableCallSite.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(cfr_ldc_0().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string), methodType));
        }
        catch (Exception exception) {
            throw new RuntimeException("it/ytnoos/lpx/wq" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_0() {
        try {
            return MethodHandles.lookup().findStatic(wq.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }
}

