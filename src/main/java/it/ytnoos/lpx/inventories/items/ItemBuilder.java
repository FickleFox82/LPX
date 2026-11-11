/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 */
package it.ytnoos.lpx.inventories.items;

import com.google.common.collect.Lists;
import it.ytnoos.lpx._c;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import java.lang.invoke.MethodHandles;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
    private final ItemStack itemStack;
    private static String[] Q;
    private static final long a;
    private static final long b;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int n) {
        this.itemStack = new ItemStack(material, n);
    }

    public static ItemBuilder DZ(Material material) {
        return new ItemBuilder(material);
    }

    public ItemBuilder Qu() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder uy(int n) {
        this.itemStack.setAmount(n);
        return this;
    }

    public ItemBuilder Ad(String string) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(MessageFormatter.k3(string));
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder yT(Enchantment enchantment, int n) {
        this.itemStack.addUnsafeEnchantment(enchantment, n);
        return this;
    }

    public ItemBuilder Pi(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder Ag(String string, long l) {
        long l2 = l;
        l2 = a ^ l2;
        SkullMeta skullMeta = (SkullMeta)this.itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwner(string);
        }
        this.itemStack.setItemMeta((ItemMeta)skullMeta);
        if (1L <= l2) {
            // empty if block
        }
        return this;
    }

    public ItemBuilder yK(long l, Enchantment enchantment, int n) {
        long l2 = l;
        l2 = a ^ l2;
        if (n == 0) {
            return this;
        }
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (1455275723581818655L >= l2) {
            // empty if block
        }
        if (itemMeta != null) {
            itemMeta.addEnchant(enchantment, n, true);
            this.itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder Q8() {
        this.itemStack.setDurability((short)Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder _I(String[] stringArray) {
        return this.Is(Lists.newArrayList((Object[])stringArray));
    }

    public ItemBuilder Is(List list) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        list.add(0, "");
        if (itemMeta != null) {
            itemMeta.setLore(list.stream().map(MessageFormatter::k3).collect(Collectors.toList()));
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack toItemStack() {
        return this.itemStack;
    }

    public static void _n(String[] stringArray) {
    }

    public static String[] Qb() {
        return Q;
    }

    /*
     * Enabled aggressive block sorting
     */
    static {
        a = _c.a(-5250232644462480517L, 4648352961733344520L, MethodHandles.lookup().lookupClass()).a(211145204153448L);
        String[] cfr_ignored_0 = new String[1];
        long l = a ^ 0x64B05259D0DEL;
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        byte[] byArray = new byte[8];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)(l >>> 56);
        int n = 1;
        while (true) {
            if (n >= 8) {
                cipher.init(2, (Key)secretKeyFactory.generateSecret(new DESKeySpec(byArray2)), new IvParameterSpec(new byte[8]));
                long l2 = -8909549390167671140L;
                byte[] byArray3 = cipher.doFinal(new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2});
                b = ((long)byArray3[0] & 0xFFL) << 56 | ((long)byArray3[1] & 0xFFL) << 48 | ((long)byArray3[2] & 0xFFL) << 40 | ((long)byArray3[3] & 0xFFL) << 32 | ((long)byArray3[4] & 0xFFL) << 24 | ((long)byArray3[5] & 0xFFL) << 16 | ((long)byArray3[6] & 0xFFL) << 8 | (long)byArray3[7] & 0xFFL;
                return;
            }
            byArray2 = byArray2;
            byArray2[n] = (byte)(l << n * 8 >>> 56);
            ++n;
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

