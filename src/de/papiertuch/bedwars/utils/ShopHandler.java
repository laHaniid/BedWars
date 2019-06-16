package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.listener.ShopClickListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ShopHandler {
    
    private static boolean isIron = true;
    private static boolean isGold = true;

    public static void openHauptInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, 1 * 9, "§8» §eBedWars §8× §aShop");

        inv.addItem(createItem(Material.SANDSTONE, "§8» §aBlöcke"));
        inv.addItem(createItem(Material.CHAINMAIL_CHESTPLATE, "§8» §aRüstung"));
        inv.addItem(createItem(Material.IRON_PICKAXE, "§8» §aSpitzhacken"));
        inv.addItem(createItem(Material.WOOD_SWORD, "§8» §aSchwerter"));
        inv.addItem(createItem(Material.BOW, "§8» §aBögen"));
        inv.addItem(createItem(Material.CAKE, "§8» §aEssen"));
        inv.addItem(createItem(Material.ENDER_CHEST, "§8» §aKisten"));
        inv.addItem(createItem(Material.GLASS_BOTTLE, "§8» §aTränke"));
        inv.addItem(createItem(Material.EMERALD, "§8» §aSpezial"));

        p.openInventory(inv);
    }

    public static void openBoegenInv(Player p) {
        Inventory inv = getHauptInv("Bögen");
        HashMap<Enchantment, Integer> list = new HashMap<Enchantment, Integer>();
        list.put(Enchantment.ARROW_INFINITE, 1);
        ItemStack i = createItemenchant(Material.BOW, "§8» §bBogen Level 1", "§8» §63 Gold", list);
        i.setDurability((short) 0.25);
        inv.setItem(11, i);
        list.put(Enchantment.ARROW_DAMAGE, 1);
        i = createItemenchant(Material.BOW, "§8» §bBogen Level 2", "§8» §66 Gold", list);
        i.setDurability((short) 0.25);
        inv.setItem(12, i);
        list.clear();
        list.put(Enchantment.ARROW_DAMAGE, 2);
        list.put(Enchantment.ARROW_INFINITE, 1);
        i = createItemenchant(Material.BOW, "§8» §bBogen Level 3", "§8» §69 Gold", list);
        i.setDurability((short) 0.25);
        inv.setItem(13, i);
        inv.setItem(15, createItemamount(Material.ARROW, "§8» §bPfeil", 1, "§8» §61 Gold"));


        p.openInventory(inv);
    }

    public static void openSpezialSHOP(Player p) {
        Inventory inv = getHauptInv("Spezial");
        inv.setItem(9, createItemamount(Material.LADDER, "§8» §bLeiter", 1, "§8» §c4 Bronze"));
        inv.setItem(10, createItemamount(Material.FIREWORK, "§8» §bTeleporter", 1, "§8» §f5 Eisen"));
        inv.setItem(11, createItemamount(Material.ARMOR_STAND, "§8» §bMobiler Shop", 1, "§8» §f7 Eisen"));
        inv.setItem(12, createItemamount(Material.TNT, "§8» §bTNT", 1, "§8» §63 Gold"));
        inv.setItem(14, createItemamount(Material.EGG, "§8» §bFallschirm", 1, "§8» §63 Gold"));
        inv.setItem(15, createItemamount(Material.BLAZE_ROD, "§8» §bRettungsplattform", 1, "§8» §63 Gold"));
        inv.setItem(16, createItemamount(Material.ENDER_PEARL, "§8» §bEnderperle", 1, "§8» §613 Gold"));
        inv.setItem(17, createItemamount(Material.WEB, "§8» §bSpinnweben", 1, "§8» §c16 Bronze"));
        p.openInventory(inv);
    }


    public static void openBloeckeInv(Player p) {
        Inventory inv = getHauptInv("Blöcke");
        inv.setItem(11, createItemamount(Material.SANDSTONE, "§8» §bSandstein", 2, "§8» §c1 Bronze"));
        inv.setItem(12, createItemamount(Material.ENDER_STONE, "§8» §bEndstein", 1, "§8» §c7 Bronze"));
        inv.setItem(13, createItemamount(Material.IRON_BLOCK, "§8» §bEisenblock", 1, "§8» §f3 Eisen"));
        ItemStack item = new ItemStack(Material.STAINED_GLASS, 1, (short) 0);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName("§8» §bTeamGlass");
        itemm.setLore(Arrays.asList("§8» §c4 Bronze"));
        item.setItemMeta(itemm);
        inv.setItem(14, item);
        inv.setItem(15, createItemamount(Material.GLOWSTONE, "§8» §bGlowstone", 1, "§8» §c4 Bronze"));
        p.openInventory(inv);
    }

    public static void openKistenInv(Player p) {
        Inventory inv = getHauptInv("Kisten");
        if (isIron) {
            inv.setItem(12, createItemamount(Material.CHEST, "§8» §bKiste", 1, "§8» §f1 Eisen"));
        } else {
            inv.setItem(12, createItem(Material.BARRIER, "§8» §c✖"));
        }
        if (isGold) {
            inv.setItem(14, createItemamount(Material.ENDER_CHEST, "§8» §bTeamKiste", 1, "§8» §61 Gold"));
        } else {
            inv.setItem(14, createItem(Material.BARRIER, "§8» §c✖"));
        }
        p.openInventory(inv);
    }

    public static void openTraenkeInv(Player p) {
        Inventory inv = getHauptInv("Tränke");
        if (isIron) {
            inv.setItem(9, createItemtraenke("§8» §bTrank der Heilung 1", "§8» §f3 Eisen", 8261));
        } else {
            inv.setItem(9, createItem(Material.BARRIER, "§8» §c✖"));
        }
        if (isIron) {
            inv.setItem(11, createItemtraenke("§8» §bTrank der Heilung 2", "§8» §f7 Eisen", 8229));
        } else {
            inv.setItem(11, createItem(Material.BARRIER, "§8» §c✖"));
        }
        if (isGold) {
            inv.setItem(13, createItemtraenke("§8» §bTrank der Stärke", "§8» §63 Gold", 8201));
        } else {
            inv.setItem(13, createItem(Material.BARRIER, "§8» §c✖"));
        }
        if (isGold) {
            inv.setItem(15, createItemtraenke("§8» §bTrank der Regeneration", "§8» §63 Gold", 8193));
        } else {
            inv.setItem(15, createItem(Material.BARRIER, "§8» §c✖"));
        }
        if (isIron) {
            inv.setItem(17, createItemtraenke("§8» §bTrank der Sprungkraft", "§8» §f Eisen", 8203));
        } else {
            inv.setItem(17, createItem(Material.BARRIER, "§8» §c✖"));
        }
        p.openInventory(inv);
    }

    public static void openEssenInv(Player p) {
        Inventory inv = getHauptInv("Essen");
        inv.setItem(11, createItemamount(Material.APPLE, "§8» §bApfel", 1, "§8» §c1 Bronze"));
        inv.setItem(12, createItemamount(Material.COOKED_BEEF, "§8» §bSteak", 1, "§8» §c2 Bronze"));
        if (isIron) {
            inv.setItem(13, createItemamount(Material.CAKE, "§8» §bKuchen", 1, "§8» §f1 Eisen"));
        } else {
            inv.setItem(13, createItem(Material.BARRIER, "§8» §c✖"));
        }
        if (isGold) {
            inv.setItem(15, createItemamount(Material.GOLDEN_APPLE, "§8» §bGoldener Apfel", 1, "§8» §62 Gold"));
        } else {
            inv.setItem(15, createItem(Material.BARRIER, "§8» §c✖"));
        }
        p.openInventory(inv);
    }

    public static void openSpitzhackenInv(Player p) {
        Inventory inv = getHauptInv("Spitzhacken");
        inv.setItem(12, createItemamount(Material.WOOD_PICKAXE, "§8» §bSpitzhacke Level 1", 1, "§8» §c4 Bronze"));
        if (isIron) {
            inv.setItem(13, createItemamount(Material.STONE_PICKAXE, "§8» §bSpitzhacke Level 2", 1, "§8» §f2 Eisen"));
        } else {
            inv.setItem(13, createItem(Material.BARRIER, "§8» §c✖"));
        }
        if (isGold) {
            inv.setItem(14, createItemamount(Material.IRON_PICKAXE, "§8» §bSpitzhacke Level 3", 1, "§8» §61 Gold"));
        } else {
            inv.setItem(14, createItem(Material.BARRIER, "§8» §c✖"));
        }
        p.openInventory(inv);
    }

    public static void openRuestungInv(Player p) {
        Inventory inv = getHauptInv("Rüstung");
        inv.setItem(9, createItemarmor("§8» §bLederhelm", "§8» §c1 Bronze", Material.LEATHER_HELMET, p));
        inv.setItem(10, createItemarmor("§8» §bLederhose", "§8» §c1 Bronze", Material.LEATHER_LEGGINGS, p));
        inv.setItem(11, createItemarmor("§8» §bLederschuhe", "§8» §c1 Bronze", Material.LEATHER_BOOTS, p));
        if (isIron) {
            inv.setItem(14, createItemamount(Material.CHAINMAIL_CHESTPLATE, "§8» §bBrustplatte Level 1", 1, "§8» §f1 Eisen"));
        } else {
            inv.setItem(14, createItem(Material.BARRIER, "§8» §c✖"));
        }
        HashMap<Enchantment, Integer> list = new HashMap<Enchantment, Integer>();
        list.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        if (isIron) {
            inv.setItem(15, createItemenchant(Material.CHAINMAIL_CHESTPLATE, "§8» §bBrustplatte Level 2", "§8» §f3 Eisen", list));
        } else {
            inv.setItem(15, createItem(Material.BARRIER, "§8» §c✖"));
        }
        list.clear();
        list.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        if (isIron) {
            inv.setItem(16, createItemenchant(Material.CHAINMAIL_CHESTPLATE, "§8» §bBrustplatte Level 3", "§8» §f5 Eisen", list));
        } else {
            inv.setItem(16, createItem(Material.BARRIER, "§8» §c✖"));
        }
        list.clear();
        list.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        list.put(Enchantment.THORNS, 1);
        if (isIron) {
            inv.setItem(17, createItemenchant(Material.CHAINMAIL_CHESTPLATE, "§8» §bBrustplatte Level 4", "§8» §f7 Eisen", list));
        } else {
            inv.setItem(17, createItem(Material.BARRIER, "§8» §c✖"));
        }
        p.openInventory(inv);
    }

    public static void openSchwerterInv(Player p) {
        Inventory inv = getHauptInv("Schwerter");
        HashMap<Enchantment, Integer> list = new HashMap<Enchantment, Integer>();
        list.put(Enchantment.KNOCKBACK, 1);
        inv.setItem(11, createItemenchant(Material.STICK, "§8» §bKnüppel", "§8» §c8 Bronze", list));
        list.clear();
        if (isIron) {
            inv.setItem(12, createItemamount(Material.WOOD_SWORD, "§8» §bHolzschwert", 1, "§f1 Eisen"));
        } else {
            inv.setItem(12, createItem(Material.BARRIER, "§8» §c✖"));
        }
        list.clear();
        list.put(Enchantment.DAMAGE_ALL, 1);
        list.put(Enchantment.DURABILITY, 1);
        if (isIron) {
            inv.setItem(13, createItemenchant(Material.WOOD_SWORD, "§8» §bHolzschwert Level 1", "§8» §f3 Eisen", list));
        } else {
            inv.setItem(13, createItem(Material.BARRIER, "§8» §c✖"));
        }
        list.clear();
        list.put(Enchantment.DAMAGE_ALL, 2);
        list.put(Enchantment.DURABILITY, 1);
        if (isIron) {
            inv.setItem(14, createItemenchant(Material.WOOD_SWORD, "§8» §bHolzschwert Level 2", "§8» §f5 Eisen", list));
        } else {
            inv.setItem(14, createItem(Material.BARRIER, "§8» §c✖"));
        }
        list.clear();
        list.put(Enchantment.DAMAGE_ALL, 2);
        list.put(Enchantment.KNOCKBACK, 1);
        if (isGold) {
            inv.setItem(15, createItemenchant(Material.IRON_SWORD, "§8» §bEisenschwert", "§8» §65 Gold", list));
        } else {
            inv.addItem(createItem(Material.BARRIER, "§8» §c✖"));
        }
        p.openInventory(inv);
    }

    public static Inventory getHauptInv(String name) {
        Inventory inv = Bukkit.createInventory(null, 2 * 9, "§8» §eBedWars §8× §a" + name);
        inv.addItem(createItem(Material.SANDSTONE, "§8» §aBlöcke"));
        inv.addItem(createItem(Material.CHAINMAIL_CHESTPLATE, "§8» §aRüstung"));
        inv.addItem(createItem(Material.IRON_PICKAXE, "§8» §aSpitzhacken"));
        inv.addItem(createItem(Material.WOOD_SWORD, "§8» §aSchwerter"));
        if (isGold) {
            inv.addItem(createItem(Material.BOW, "§8» §aBögen"));
        } else {
            inv.addItem(createItem(Material.BARRIER, "§8» §c✖"));
        }
        inv.addItem(createItem(Material.CAKE, "§8» §aEssen"));
        inv.addItem(createItem(Material.ENDER_CHEST, "§8» §aKisten"));
        inv.addItem(createItem(Material.GLASS_BOTTLE, "§8» §aTränke"));
        inv.addItem(createItem(Material.EMERALD, "§8» §aSpezial"));
        return inv;
    }

    private static ItemStack createItem(Material m, String name) {
        ItemStack item = new ItemStack(m);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        item.setItemMeta(itemm);
        return item;
    }

    private static ItemStack createItemamount(Material m, String name, int amount, String lore) {
        ItemStack item = new ItemStack(m, amount);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        itemm.setLore(Arrays.asList(lore));
        item.setItemMeta(itemm);
        return item;
    }

    private static ItemStack createItemtraenke(String name, String lore, int shortID) {
        ItemStack item = new ItemStack(Material.POTION, 1, (short) shortID);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        itemm.setLore(Arrays.asList(lore));
        item.setItemMeta(itemm);
        return item;
    }

    private static ItemStack createItemarmor(String name, String lore, Material m, Player p) {
        ItemStack item = new ItemStack(m);
        LeatherArmorMeta itemm = (LeatherArmorMeta) item.getItemMeta();
        itemm.setDisplayName(name);
        itemm.setLore(Arrays.asList(lore));
        itemm.setColor(BedWars.getInstance().getGameHandler().getTeam(p).getColorasColor());
        item.setItemMeta(itemm);
        return item;
    }

    private static ItemStack createItemenchant(Material m, String name, String lore, HashMap<Enchantment, Integer> list) {
        ItemStack item = new ItemStack(m);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        itemm.setLore(Arrays.asList(lore));
        for (Enchantment e : list.keySet()) {
            itemm.addEnchant(e, list.get(e), true);
        }
        item.setItemMeta(itemm);
        return item;
    }

    public static void removeInventoryItemsStack(PlayerInventory inv, Material type, int amount, ItemStack add) {
        int maxamount = 0;
        ItemStack[] arritemStack = inv.getContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack is = arritemStack[n2];
            if (is != null && is.getType() == type) {
                maxamount += is.getAmount();
            }
            ++n2;
        }
        int amountofitems = maxamount / amount;
        if (add.getAmount() == 2) {
            if (amountofitems >= 33) {
                amountofitems = 32;
            }
        } else if (amountofitems >= 65) {
            amountofitems = 64;
        }
        int remove = amountofitems * amount;
        removeInventoryItems(inv, type, remove);
        int i = 0;
        while (i < amountofitems) {
            inv.addItem(new ItemStack[]{add});
            ++i;
        }
    }

    public static boolean removeInventoryItems(PlayerInventory inv, Material type, int amount) {
        boolean b = false;
        ItemStack[] arritemStack = inv.getContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack is = arritemStack[n2];
            if (is != null && is.getType() == type) {
                b = true;
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                }
                inv.remove(is);
                amount = -newamount;
                if (amount == 0) break;
            }
            ++n2;
        }
        return b;
    }

    public static void loadHashMaps() {
        ShopClickListener.bloecke_material.put(11, Material.CLAY_BRICK);
        ShopClickListener.bloecke_material.put(12, Material.CLAY_BRICK);
        ShopClickListener.bloecke_material.put(13, Material.IRON_INGOT);
        ShopClickListener.bloecke_material.put(14, Material.CLAY_BRICK);
        ShopClickListener.bloecke_material.put(15, Material.CLAY_BRICK);
        ShopClickListener.bloecke_price.put(11, 1);
        ShopClickListener.bloecke_price.put(12, 7);
        ShopClickListener.bloecke_price.put(13, 3);
        ShopClickListener.bloecke_price.put(14, 4);
        ShopClickListener.bloecke_price.put(15, 4);
        ShopClickListener.ruestung_material.put(9, Material.CLAY_BRICK);
        ShopClickListener.ruestung_material.put(10, Material.CLAY_BRICK);
        ShopClickListener.ruestung_material.put(11, Material.CLAY_BRICK);
        ShopClickListener.ruestung_material.put(14, Material.IRON_INGOT);
        ShopClickListener.ruestung_material.put(15, Material.IRON_INGOT);
        ShopClickListener.ruestung_material.put(16, Material.IRON_INGOT);
        ShopClickListener.ruestung_material.put(17, Material.IRON_INGOT);
        ShopClickListener.ruestung_price.put(9, 1);
        ShopClickListener.ruestung_price.put(10, 1);
        ShopClickListener.ruestung_price.put(11, 1);
        ShopClickListener.ruestung_price.put(14, 1);
        ShopClickListener.ruestung_price.put(15, 3);
        ShopClickListener.ruestung_price.put(16, 5);
        ShopClickListener.ruestung_price.put(17, 7);
        ShopClickListener.spitzhacken_material.put(12, Material.CLAY_BRICK);
        ShopClickListener.spitzhacken_material.put(13, Material.IRON_INGOT);
        ShopClickListener.spitzhacken_material.put(14, Material.GOLD_INGOT);
        ShopClickListener.spitzhacken_price.put(12, 4);
        ShopClickListener.spitzhacken_price.put(13, 2);
        ShopClickListener.spitzhacken_price.put(14, 1);
        ShopClickListener.schwerter_material.put(11, Material.CLAY_BRICK);
        ShopClickListener.schwerter_material.put(12, Material.IRON_INGOT);
        ShopClickListener.schwerter_material.put(13, Material.IRON_INGOT);
        ShopClickListener.schwerter_material.put(14, Material.IRON_INGOT);
        ShopClickListener.schwerter_material.put(15, Material.GOLD_INGOT);
        ShopClickListener.schwerter_price.put(11, 8);
        ShopClickListener.schwerter_price.put(12, 1);
        ShopClickListener.schwerter_price.put(13, 3);
        ShopClickListener.schwerter_price.put(14, 5);
        ShopClickListener.schwerter_price.put(15, 5);
        ShopClickListener.boegen_material.put(11, Material.GOLD_INGOT);
        ShopClickListener.boegen_material.put(12, Material.GOLD_INGOT);
        ShopClickListener.boegen_material.put(13, Material.GOLD_INGOT);
        ShopClickListener.boegen_material.put(15, Material.GOLD_INGOT);
        ShopClickListener.boegen_price.put(11, 3);
        ShopClickListener.boegen_price.put(12, 6);
        ShopClickListener.boegen_price.put(13, 9);
        ShopClickListener.boegen_price.put(15, 1);
        ShopClickListener.essen_material.put(11, Material.CLAY_BRICK);
        ShopClickListener.essen_material.put(12, Material.CLAY_BRICK);
        ShopClickListener.essen_material.put(13, Material.IRON_INGOT);
        ShopClickListener.essen_material.put(15, Material.GOLD_INGOT);
        ShopClickListener.essen_price.put(11, 1);
        ShopClickListener.essen_price.put(12, 2);
        ShopClickListener.essen_price.put(13, 1);
        ShopClickListener.essen_price.put(15, 2);
        ShopClickListener.kisten_material.put(12, Material.IRON_INGOT);
        ShopClickListener.kisten_material.put(14, Material.GOLD_INGOT);
        ShopClickListener.kisten_price.put(12, 1);
        ShopClickListener.kisten_price.put(14, 1);
        ShopClickListener.traenke_material.put(9, Material.IRON_INGOT);
        ShopClickListener.traenke_material.put(11, Material.IRON_INGOT);
        ShopClickListener.traenke_material.put(13, Material.GOLD_INGOT);
        ShopClickListener.traenke_material.put(15, Material.GOLD_INGOT);
        ShopClickListener.traenke_material.put(17, Material.IRON_INGOT);
        ShopClickListener.traenke_price.put(9, 3);
        ShopClickListener.traenke_price.put(11, 7);
        ShopClickListener.traenke_price.put(13, 3);
        ShopClickListener.traenke_price.put(15, 3);
        ShopClickListener.traenke_price.put(17, 5);
        ShopClickListener.spezial_material.put(9, Material.CLAY_BRICK);
        ShopClickListener.spezial_material.put(10, Material.IRON_INGOT);
        ShopClickListener.spezial_material.put(11, Material.IRON_INGOT);
        ShopClickListener.spezial_material.put(12, Material.GOLD_INGOT);
        ShopClickListener.spezial_material.put(13, Material.IRON_INGOT);
        ShopClickListener.spezial_material.put(14, Material.GOLD_INGOT);
        ShopClickListener.spezial_material.put(15, Material.GOLD_INGOT);
        ShopClickListener.spezial_material.put(16, Material.GOLD_INGOT);
        ShopClickListener.spezial_material.put(17, Material.CLAY_BRICK);
        ShopClickListener.spezial_price.put(9, 4);
        ShopClickListener.spezial_price.put(10, 5);
        ShopClickListener.spezial_price.put(11, 7);
        ShopClickListener.spezial_price.put(12, 3);
        ShopClickListener.spezial_price.put(14, 3);
        ShopClickListener.spezial_price.put(15, 3);
        ShopClickListener.spezial_price.put(16, 13);
        ShopClickListener.spezial_price.put(17, 16);
        ShopClickListener.spezial_price.put(13, 3);
    }
}
