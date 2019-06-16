package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.ItemBuilder;
import de.papiertuch.bedwars.utils.ItemStorage;
import de.papiertuch.bedwars.utils.ShopHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import org.bukkit.*;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerInteractListener implements Listener {

    private static ArrayList<Player> nomove = new ArrayList<>();
    public static HashMap<Player, Integer> scheduler = new HashMap();
    public static HashMap<String, ItemStack> skulls = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            Player p = e.getPlayer();
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
                    ShopHandler.removeInventoryItems(p.getInventory(), Material.BLAZE_ROD, 1);
                    final Location loc = p.getLocation().subtract(0.0, 3.0, 0.0);
                    final Location loc2 = p.getLocation().subtract(0.0, 3.0, 1.0);
                    final Location loc3 = p.getLocation().subtract(1.0, 3.0, 0.0);
                    final Location loc4 = p.getLocation().subtract(0.0, 3.0, 0.0).add(0.0, 0.0, 1.0);
                    final Location loc5 = p.getLocation().subtract(0.0, 3.0, 0.0).add(1.0, 0.0, 0.0);
                    if (p.getLocation().subtract(0.0, 3.0, 1.0).getBlock().getType() == Material.AIR) {
                        p.getLocation().subtract(0.0, 3.0, 1.0).getBlock().setType(Material.SLIME_BLOCK);
                    }
                    if (p.getLocation().subtract(1.0, 3.0, 0.0).getBlock().getType() == Material.AIR) {
                        p.getLocation().subtract(1.0, 3.0, 0.0).getBlock().setType(Material.SLIME_BLOCK);
                    }
                    if (p.getLocation().subtract(0.0, 3.0, 0.0).add(1.0, 0.0, 0.0).getBlock().getType() == Material.AIR) {
                        p.getLocation().subtract(0.0, 3.0, 0.0).add(1.0, 0.0, 0.0).getBlock().setType(Material.SLIME_BLOCK);
                    }
                    if (p.getLocation().subtract(0.0, 3.0, 0.0).add(0.0, 0.0, 1.0).getBlock().getType() == Material.AIR) {
                        p.getLocation().subtract(0.0, 3.0, 0.0).add(0.0, 0.0, 1.0).getBlock().setType(Material.SLIME_BLOCK);
                    }
                    if (p.getLocation().subtract(0.0, 3.0, 0.0).getBlock().getType() == Material.AIR) {
                        p.getLocation().subtract(0.0, 3.0, 0.0).getBlock().setType(Material.SLIME_BLOCK);
                    }
                    Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), new Runnable() {

                        @Override
                        public void run() {
                            if (loc.getBlock().getType() == Material.SLIME_BLOCK) {
                                loc.getBlock().setType(Material.AIR);
                            }
                            if (loc2.getBlock().getType() == Material.SLIME_BLOCK) {
                                loc2.getBlock().setType(Material.AIR);
                            }
                            if (loc3.getBlock().getType() == Material.SLIME_BLOCK) {
                                loc3.getBlock().setType(Material.AIR);
                            }
                            if (loc4.getBlock().getType() == Material.SLIME_BLOCK) {
                                loc4.getBlock().setType(Material.AIR);
                            }
                            if (loc5.getBlock().getType() == Material.SLIME_BLOCK) {
                                loc5.getBlock().setType(Material.AIR);
                            }
                        }
                    }, 20 * 5);
                }
                if (p.getItemInHand().getType() == Material.EGG) {
                    ShopHandler.removeInventoryItems(p.getInventory(), Material.EGG, 1);
                    Chicken ch = (Chicken) p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
                    p.setPassenger(ch);
                    scheduler.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {

                        @Override
                        public void run() {
                            if (p.getPassenger() != null && p.getPassenger().getType() == EntityType.CHICKEN) {
                                p.setVelocity(new Vector(p.getLocation().getDirection().getX() * 0.5, p.getVelocity().getY() * 0.3, p.getLocation().getDirection().getZ() * 0.5));
                                p.setFallDistance(0.0f);
                                if (p.isOnGround()) {
                                    p.getPassenger().remove();
                                    Bukkit.getScheduler().cancelTask(scheduler.get(p).intValue());
                                }
                            }
                        }
                    }, 5, 5));
                }
                if (p.getItemInHand().getType() == Material.ARMOR_STAND && BedWars.getInstance().getGameState() == GameState.INGAME) {
                    ShopHandler.openHauptInv(p);
                    ShopHandler.removeInventoryItems(p.getInventory(), Material.ARMOR_STAND, 1);
                    p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                }
                if (p.getItemInHand().getType() == Material.FIREWORK) {
                    if (nomove.contains(p)) {
                        return;
                    }
                    nomove.add(p);
                    Location loc = p.getLocation();
                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§7Du wirst in §a5 §7Sekunden teleportiert!");
                    for (int i = 0; i < 10; i++) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
                        }
                    }
                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                        public void run() {
                            if (p.getLocation().getBlockX() != loc.getBlockX() || p.getLocation().getBlockY() != loc.getBlockY() || p.getLocation().getBlockZ() != loc.getBlockZ()) {
                                nomove.remove(p);
                                p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDu darfst dich nicht bewegen!");
                            }
                            if (nomove.contains(p)) {
                                p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                                p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§7Du wirst in §a4 §7Sekunden teleportiert!");
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
                                }
                                if (p.getLocation().getBlockX() != loc.getBlockX() || p.getLocation().getBlockY() != loc.getBlockY() || p.getLocation().getBlockZ() != loc.getBlockZ()) {
                                    nomove.remove(p);
                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDu darfst dich nicht bewegen!");
                                }
                                Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                    public void run() {
                                        if (p.getLocation().getBlockX() != loc.getBlockX() || p.getLocation().getBlockY() != loc.getBlockY() || p.getLocation().getBlockZ() != loc.getBlockZ()) {
                                            nomove.remove(p);
                                            p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDu darfst dich nicht bewegen!");
                                        }
                                        if (nomove.contains(p)) {
                                            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                                            p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§7Du wirst in §a3 §7Sekunden teleportiert!");
                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                all.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
                                            }
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                                public void run() {
                                                    if (p.getLocation().getBlockX() != loc.getBlockX() || p.getLocation().getBlockY() != loc.getBlockY() || p.getLocation().getBlockZ() != loc.getBlockZ()) {
                                                        nomove.remove(p);
                                                        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDu darfst dich nicht bewegen!");
                                                    }
                                                    if (nomove.contains(p)) {
                                                        p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                                                        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§7Du wirst in §a2 §7Sekunden teleportiert!");
                                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                                            all.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
                                                        }
                                                        Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                                            public void run() {
                                                                if (p.getLocation().getBlockX() != loc.getBlockX() || p.getLocation().getBlockY() != loc.getBlockY() || p.getLocation().getBlockZ() != loc.getBlockZ()) {
                                                                    nomove.remove(p);
                                                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§cDu darfst dich nicht bewegen!");
                                                                }
                                                                if (nomove.contains(p)) {
                                                                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                                                                    p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§7Du wirst in §aeiner §7Sekunde teleportiert!");
                                                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                                                        all.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
                                                                    }
                                                                    Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                                                        public void run() {
                                                                            BedWars.getInstance().getGameHandler().teleportToMap(p);
                                                                            nomove.remove(p);
                                                                            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                                                                            ShopHandler.removeInventoryItems(p.getInventory(), Material.FIREWORK, 1);
                                                                            p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§aDu wurdest nach Hause teleportiert!");
                                                                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 10F);
                                                                        }
                                                                    }, 20L);
                                                                }
                                                            }
                                                        }, 20L);
                                                    }
                                                }
                                            }, 20L);
                                        }
                                    }
                                }, 20L);
                            }
                        }
                    }, 20L);
                }
                if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getCompass().getItemMeta().getDisplayName())) {
                    Inventory inv = Bukkit.createInventory(null, 9 * 3, p.getItemInHand().getItemMeta().getDisplayName());
                    for (UUID s : BedWars.getInstance().getPlayers()) {
                        Player player = Bukkit.getPlayer(s);
                        if (skulls.containsKey(player.getName())) {
                            inv.addItem(new ItemBuilder(skulls.get(player.getName())).setName(player.getDisplayName()).build());
                        } else {
                            skulls.put(player.getName(), new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(player.getName()).build());
                            inv.addItem(new ItemBuilder(skulls.get(player.getName())).setName(player.getDisplayName()).build());
                        }
                    }
                    p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                    p.openInventory(inv);
                }
                if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getTeams().getItemMeta().getDisplayName())) {
                    p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                    BedWars.getInstance().getGameHandler().getTeamInventory(p, p.getItemInHand().getItemMeta().getDisplayName());
                    e.setCancelled(true);
                }
                if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getLeave().getItemMeta().getDisplayName())) {
                    p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                    p.kickPlayer("§cLobby");
                }
                if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getStartItem().getItemMeta().getDisplayName())) {
                    p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                    p.performCommand("start");
                }
            }
            if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL) {
                e.setCancelled(true);
            }
            if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.CARROT) {
                e.setCancelled(true);
            }
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.JUKEBOX) {
                if (BedWars.getInstance().getSpectators().contains(p.getUniqueId())) {
                    e.setCancelled(true);
                }
            }
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CHEST) {
                if (BedWars.getInstance().getSpectators().contains(p.getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        } catch (Exception ex) {
        }
    }
}
