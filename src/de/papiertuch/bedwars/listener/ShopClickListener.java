package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.ShopHandler;
import org.bukkit.event.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ShopClickListener implements Listener {

    public static HashMap<Integer, Material> bloecke_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> bloecke_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> ruestung_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> ruestung_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> spitzhacken_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> spitzhacken_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> schwerter_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> schwerter_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> boegen_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> boegen_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> essen_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> essen_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> kisten_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> kisten_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> traenke_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> traenke_price = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Material> spezial_material = new HashMap<Integer, Material>();
    public static HashMap<Integer, Integer> spezial_price = new HashMap<Integer, Integer>();

    @EventHandler
    public void onClick( InventoryClickEvent e ) {
        try {
            Player p = (Player) e.getWhoClicked();
            if (e.getInventory().getName().startsWith("§bSpiele")) {
                e.setCancelled(true);
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                Player target = Bukkit.getPlayer(name);
                if (target != null) {
                    p.teleport(target);
                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§7Du bist nun bei " + target.getDisplayName());
                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 10F, 10F);
                }
            }
            if (e.getInventory().getName().contains("§8» §eBedWars")) {
                if (e.getClickedInventory() != p.getInventory()) {
                    e.setCancelled(true);
                    if (e.getCurrentItem() == null) {
                        return;
                    }
                    if (e.getCurrentItem().getType() != Material.AIR) {
                        String s;
                        try {
                            s = e.getCurrentItem().getItemMeta().getDisplayName();
                            if (s.equalsIgnoreCase("§8» §aBlöcke")) {
                                ShopHandler.openBloeckeInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aSpitzhacken")) {
                                ShopHandler.openSpitzhackenInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aSchwerter")) {
                                ShopHandler.openSchwerterInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aBögen")) {
                                ShopHandler.openBoegenInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aEssen")) {
                                ShopHandler.openEssenInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aKisten")) {
                                ShopHandler.openKistenInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aTränke")) {
                                ShopHandler.openTraenkeInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aRüstung")) {
                                ShopHandler.openRuestungInv(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §aSpezial")) {
                                ShopHandler.openSpezialSHOP(p);
                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                            }
                            if (s.equalsIgnoreCase("§8» §c✖")) {
                                p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDieses Item ist gerade deaktiviert!");
                                p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                if (e.getInventory().getName().equalsIgnoreCase("§8» §eBedWars §8× §aBlöcke")) {
                    if (e.getClickedInventory() != p.getInventory()) {
                        e.setCancelled(true);
                        if (e.getCurrentItem().getType() != Material.AIR && e.getSlot() >= 11 && e.getSlot() <= 15) {
                            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                                int price = 0;
                                Material waehrung = null;
                                if (bloecke_price.containsKey(e.getSlot())) {
                                    price = bloecke_price.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                if (bloecke_material.containsKey(e.getSlot())) {
                                    waehrung = bloecke_material.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                int amount = e.getCurrentItem().getAmount();
                                Material m = e.getCurrentItem().getType();
                                short nebenID = e.getCurrentItem().getData().getData();
                                ItemStack add = new ItemStack(m, amount, nebenID);
                                if (e.getCurrentItem().getType() == Material.LEATHER_HELMET || e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
                                        || e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS || e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                                    LeatherArmorMeta itemm = (LeatherArmorMeta) add.getItemMeta();
                                    LeatherArmorMeta im2 = (LeatherArmorMeta) e.getCurrentItem().getItemMeta();
                                    itemm.setColor(im2.getColor());
                                    Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();
                                    for (Enchantment ench : enchanments.keySet()) {
                                        itemm.addEnchant(ench, enchanments.get(ench), true);
                                    }
                                    add.setItemMeta(itemm);
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                                    ShopHandler.removeInventoryItemsStack(p.getInventory(), waehrung, price, add);
                                } else {
                                    ItemMeta itemm = add.getItemMeta();
                                    Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();
                                    for (Enchantment ench : enchanments.keySet()) {
                                        itemm.addEnchant(ench, enchanments.get(ench), true);
                                    }
                                    add.setItemMeta(itemm);
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                                    ShopHandler.removeInventoryItemsStack(p.getInventory(), waehrung, price, add);
                                }
                            } else {
                                int price = 0;
                                Material waehrung = null;
                                if (bloecke_price.containsKey(e.getSlot())) {
                                    price = bloecke_price.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                if (bloecke_material.containsKey(e.getSlot())) {
                                    waehrung = bloecke_material.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                int amount = e.getCurrentItem().getAmount();
                                Material m = e.getCurrentItem().getType();
                                short nebenID = e.getCurrentItem().getData().getData();
                                if (p.getInventory().contains(waehrung, price)) {
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                                    ShopHandler.removeInventoryItems(p.getInventory(), waehrung, price);
                                    if (e.getCurrentItem().getType() == Material.LEATHER_HELMET || e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
                                            || e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS || e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                                        ItemStack item = new ItemStack(m, amount, nebenID);
                                        LeatherArmorMeta itemm = (LeatherArmorMeta) item.getItemMeta();
                                        LeatherArmorMeta im2 = (LeatherArmorMeta) e.getCurrentItem().getItemMeta();
                                        itemm.setColor(im2.getColor());
                                        Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();
                                        for (Enchantment ench : enchanments.keySet()) {
                                            itemm.addEnchant(ench, enchanments.get(ench), true);
                                        }
                                        item.setItemMeta(itemm);
                                        p.getInventory().addItem(item);
                                    } else {
                                        ItemStack item = new ItemStack(m, amount, nebenID);
                                        ItemMeta itemm = item.getItemMeta();
                                        Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();
                                        for (Enchantment ench : enchanments.keySet()) {
                                            itemm.addEnchant(ench, enchanments.get(ench), true);
                                        }
                                        item.setItemMeta(itemm);
                                        p.getInventory().addItem(item);
                                    }
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDu hast nicht genug Ressourcen!");
                                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 10.0F, 10.0F);
                                }
                            }
                        }
                    }
                } else if (e.getInventory().getName().contains("§8» §eBedWars §8× ")) {
                    if (e.getClickedInventory() != p.getInventory()) {
                        e.setCancelled(true);
                        String s = "";
                        try {
                            s = e.getCurrentItem().getItemMeta().getDisplayName();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        HashMap<Integer, Material> list1 = new HashMap<Integer, Material>();
                        HashMap<Integer, Integer> list2 = new HashMap<Integer, Integer>();
                        if (e.getInventory().getName().contains("Blöcke")) {
                            list1 = bloecke_material;
                            list2 = bloecke_price;
                        } else if (e.getInventory().getName().contains("Rüstung")) {
                            list1 = ruestung_material;
                            list2 = ruestung_price;
                        } else if (e.getInventory().getName().contains("Spitzhacken")) {
                            list1 = spitzhacken_material;
                            list2 = spitzhacken_price;
                        } else if (e.getInventory().getName().contains("Schwerter")) {
                            list1 = schwerter_material;
                            list2 = schwerter_price;
                        } else if (e.getInventory().getName().contains("Bögen")) {
                            list1 = boegen_material;
                            list2 = boegen_price;
                        } else if (e.getInventory().getName().contains("Essen")) {
                            list1 = essen_material;
                            list2 = essen_price;
                        } else if (e.getInventory().getName().contains("Kisten")) {
                            list1 = kisten_material;
                            list2 = kisten_price;
                        } else if (e.getInventory().getName().contains("Tränke")) {
                            list1 = traenke_material;
                            list2 = traenke_price;
                        } else if (e.getInventory().getName().contains("Spezial")) {
                            list1 = spezial_material;
                            list2 = spezial_price;
                        }
                        if (s.contains("§b")) {
                            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                                int price = 0;
                                Material waehrung = null;
                                if (list2.containsKey(e.getSlot())) {
                                    price = list2.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                if (list1.containsKey(e.getSlot())) {
                                    waehrung = list1.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                int amount = e.getCurrentItem().getAmount();
                                Material m = e.getCurrentItem().getType();
                                short nebenID = e.getCurrentItem().getData().getData();
                                p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                                ItemStack add = new ItemStack(m, amount, nebenID);
                                if (e.getCurrentItem().getType() == Material.LEATHER_HELMET || e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
                                        || e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS || e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                                    LeatherArmorMeta itemm = (LeatherArmorMeta) add.getItemMeta();
                                    LeatherArmorMeta im2 = (LeatherArmorMeta) e.getCurrentItem().getItemMeta();
                                    itemm.setColor(im2.getColor());
                                    Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();
                                    for (Enchantment ench : enchanments.keySet()) {
                                        itemm.addEnchant(ench, enchanments.get(ench), true);
                                    }
                                    add.setItemMeta(itemm);
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                                    ShopHandler.removeInventoryItemsStack(p.getInventory(), waehrung, price, add);
                                } else {
                                    ItemMeta itemm = add.getItemMeta();

                                    Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();

                                    for (Enchantment ench : enchanments.keySet()) {
                                        itemm.addEnchant(ench, enchanments.get(ench), true);
                                    }
                                    add.setItemMeta(itemm);
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                                    ShopHandler.removeInventoryItemsStack(p.getInventory(), waehrung, price, add);

                                }
                            } else {
                                int price = 0;
                                Material waehrung = null;
                                if (list2.containsKey(e.getSlot())) {
                                    price = list2.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                if (list1.containsKey(e.getSlot())) {
                                    waehrung = list1.get(e.getSlot());
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cEs ist ein Fehler aufgetreten!");
                                    e.getView().close();
                                    return;
                                }
                                int amount = e.getCurrentItem().getAmount();
                                Material m = e.getCurrentItem().getType();
                                short nebenID = e.getCurrentItem().getData().getData();
                                if (p.getInventory().contains(waehrung, price)) {
                                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                                    ShopHandler.removeInventoryItems(p.getInventory(), waehrung, price);
                                    if (e.getCurrentItem().getType() == Material.LEATHER_HELMET || e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
                                            || e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS || e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                                        ItemStack item = new ItemStack(m, amount, nebenID);
                                        LeatherArmorMeta itemm = (LeatherArmorMeta) item.getItemMeta();
                                        LeatherArmorMeta im2 = (LeatherArmorMeta) e.getCurrentItem().getItemMeta();
                                        itemm.setColor(im2.getColor());
                                        Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();
                                        for (Enchantment ench : enchanments.keySet()) {
                                            itemm.addEnchant(ench, enchanments.get(ench), true);
                                        }
                                        item.setItemMeta(itemm);
                                        p.getInventory().addItem(item);
                                    } else {
                                        ItemStack item = new ItemStack(m, amount, nebenID);
                                        ItemMeta itemm = item.getItemMeta();
                                        Map<Enchantment, Integer> enchanments = e.getCurrentItem().getItemMeta().getEnchants();
                                        for (Enchantment ench : enchanments.keySet()) {
                                            itemm.addEnchant(ench, enchanments.get(ench), true);
                                        }
                                        p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1, 1);
                                        item.setItemMeta(itemm);
                                        p.getInventory().addItem(item);
                                    }
                                } else {
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDu hast nicht genug Ressourcen!");
                                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 10.0F, 10.0F);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
    }
}
