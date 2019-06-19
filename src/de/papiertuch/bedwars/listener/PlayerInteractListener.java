package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.ItemBuilder;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.*;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerInteractListener implements Listener {

    private static HashMap<String, ItemStack> skulls = new HashMap<>();
    private static HashMap<Player, Integer> scheduler = new HashMap<>();
    private static ArrayList<Player> noMove = new ArrayList<>();


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getCompass().getItemMeta().getDisplayName())) {
                Inventory inv = Bukkit.createInventory(null, 9 * 3, player.getItemInHand().getItemMeta().getDisplayName());
                for (UUID s : BedWars.getInstance().getPlayers()) {
                    Player target = Bukkit.getPlayer(s);
                    if (target != null) {
                        if (skulls.containsKey(target.getName())) {
                            inv.addItem(new ItemBuilder(skulls.get(target.getName())).setName(target.getDisplayName()).build());
                        } else {
                            skulls.put(target.getName(), new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(target.getName()).build());
                            inv.addItem(new ItemBuilder(skulls.get(target.getName())).setName(target.getDisplayName()).build());
                        }
                    }
                }
                player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                player.openInventory(inv);
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getTeams().getItemMeta().getDisplayName())) {
                player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                BedWars.getInstance().getGameHandler().getTeamInventory(player, player.getItemInHand().getItemMeta().getDisplayName());
                event.setCancelled(true);
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getLeave().getItemMeta().getDisplayName())) {
                player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                player.kickPlayer("§cLobby");
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getStartItem().getItemMeta().getDisplayName())) {
                player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                player.performCommand("start");
            }
            if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
                event.setCancelled(true);
            }
            if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.CARROT) {
                event.setCancelled(true);
            }
            if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                BedWars.getInstance().getShopHandler().removeItems(player.getInventory(), Material.BLAZE_ROD, 1);
                Location loc = player.getLocation().subtract(0.0, 3.0, 0.0);
                Location loc2 = player.getLocation().subtract(0.0, 3.0, 1.0);
                Location loc3 = player.getLocation().subtract(1.0, 3.0, 0.0);
                Location loc4 = player.getLocation().subtract(0.0, 3.0, 0.0).add(0.0, 0.0, 1.0);
                Location loc5 = player.getLocation().subtract(0.0, 3.0, 0.0).add(1.0, 0.0, 0.0);
                if (player.getLocation().subtract(0.0, 3.0, 1.0).getBlock().getType() == Material.AIR) {
                    player.getLocation().subtract(0.0, 3.0, 1.0).getBlock().setType(Material.SLIME_BLOCK);
                }
                if (player.getLocation().subtract(1.0, 3.0, 0.0).getBlock().getType() == Material.AIR) {
                    player.getLocation().subtract(1.0, 3.0, 0.0).getBlock().setType(Material.SLIME_BLOCK);
                }
                if (player.getLocation().subtract(0.0, 3.0, 0.0).add(1.0, 0.0, 0.0).getBlock().getType() == Material.AIR) {
                    player.getLocation().subtract(0.0, 3.0, 0.0).add(1.0, 0.0, 0.0).getBlock().setType(Material.SLIME_BLOCK);
                }
                if (player.getLocation().subtract(0.0, 3.0, 0.0).add(0.0, 0.0, 1.0).getBlock().getType() == Material.AIR) {
                    player.getLocation().subtract(0.0, 3.0, 0.0).add(0.0, 0.0, 1.0).getBlock().setType(Material.SLIME_BLOCK);
                }
                if (player.getLocation().subtract(0.0, 3.0, 0.0).getBlock().getType() == Material.AIR) {
                    player.getLocation().subtract(0.0, 3.0, 0.0).getBlock().setType(Material.SLIME_BLOCK);
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
            if (player.getItemInHand().getType() == Material.FIREWORK) {
                if (noMove.contains(player)) {
                    return;
                }
                noMove.add(player);
                Location loc = player.getLocation();
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + "§7Du wirst in §a5 §7Sekunden teleportiert!");
                for (int i = 0; i < 10; i++) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
                    }
                }
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                    public void run() {
                        if (player.getLocation().getBlockX() != loc.getBlockX() || player.getLocation().getBlockY() != loc.getBlockY() || player.getLocation().getBlockZ() != loc.getBlockZ()) {
                            noMove.remove(player);
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu darfst dich nicht bewegen!");
                        }
                        if (noMove.contains(player)) {
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du wirst in §a§l4 §7Sekunden teleportiert");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
                            }
                            if (player.getLocation().getBlockX() != loc.getBlockX() || player.getLocation().getBlockY() != loc.getBlockY() || player.getLocation().getBlockZ() != loc.getBlockZ()) {
                                noMove.remove(player);
                                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu darfst dich nicht bewegen!");
                            }
                            Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                public void run() {
                                    if (player.getLocation().getBlockX() != loc.getBlockX() || player.getLocation().getBlockY() != loc.getBlockY() || player.getLocation().getBlockZ() != loc.getBlockZ()) {
                                        noMove.remove(player);
                                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu darfst dich nicht bewegen!");
                                    }
                                    if (noMove.contains(player)) {
                                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du wirst in §a§l3 §7Sekunden teleportiert");
                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            all.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
                                        }
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                            public void run() {
                                                if (player.getLocation().getBlockX() != loc.getBlockX() || player.getLocation().getBlockY() != loc.getBlockY() || player.getLocation().getBlockZ() != loc.getBlockZ()) {
                                                    noMove.remove(player);
                                                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu darfst dich nicht bewegen!");
                                                }
                                                if (noMove.contains(player)) {
                                                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                                                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du wirst in §a§l2 §7Sekunden teleportiert");
                                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                                        all.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
                                                    }
                                                    Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                                        public void run() {
                                                            if (player.getLocation().getBlockX() != loc.getBlockX() || player.getLocation().getBlockY() != loc.getBlockY() || player.getLocation().getBlockZ() != loc.getBlockZ()) {
                                                                noMove.remove(player);
                                                                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu darfst dich nicht bewegen!");
                                                            }
                                                            if (noMove.contains(player)) {
                                                                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                                                                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du wirst in §a§leiner §7Sekunde teleportiert");
                                                                for (Player all : Bukkit.getOnlinePlayers()) {
                                                                    all.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
                                                                }
                                                                Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
                                                                    public void run() {
                                                                        BedWars.getInstance().getGameHandler().teleportToMap(player);
                                                                        noMove.remove(player);
                                                                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                                                                        BedWars.getInstance().getShopHandler().removeItems(player.getInventory(), Material.FIREWORK, 1);
                                                                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §aDu wurdest nach Hause teleportiert");
                                                                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 10F);
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
            if (player.getItemInHand().getType() == Material.EGG) {
                event.setCancelled(true);
                BedWars.getInstance().getShopHandler().removeItems(player.getInventory(), Material.EGG, 1);
                Chicken ch = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
                player.setPassenger(ch);
                scheduler.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {

                    @Override
                    public void run() {
                        if (player.getPassenger() != null && player.getPassenger().getType() == EntityType.CHICKEN) {
                            player.setVelocity(new Vector(player.getLocation().getDirection().getX() * 0.5, player.getVelocity().getY() * 0.3, player.getLocation().getDirection().getZ() * 0.5));
                            player.setFallDistance(0.0f);
                            if (player.isOnGround()) {
                                player.getPassenger().remove();
                                Bukkit.getScheduler().cancelTask(scheduler.get(player));
                            }
                        }
                    }
                }, 5, 5));
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.JUKEBOX) {
                if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                    event.setCancelled(true);
            }
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
                if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                    event.setCancelled(true);
            }
            }
        } catch (NullPointerException ex) {
        }
    }
}
