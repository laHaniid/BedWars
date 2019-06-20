package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ProtectionListener implements Listener {

    @EventHandler
    public void onPlayerArmorStand(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.ARMOR_STAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                BedWarsTeam bedWarsTeam = BedWars.getInstance().getGameHandler().getTeam(player);
                if (event.getInventory().getName().equalsIgnoreCase(bedWarsTeam.getColor() + bedWarsTeam.getName())) {
                    BedWars.getInstance().getTeamChest().put(bedWarsTeam, event.getInventory());
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 1, 1);
                }
            }
        }
    }

    @EventHandler
    public void onInt(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                if (BedWars.getInstance().getTeamChest().containsKey(team)) {
                    player.openInventory(BedWars.getInstance().getTeamChest().get(team));
                    player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    event.setCancelled(true);
                    return;
                }
                Inventory inventory = Bukkit.createInventory(null, 3 * 9, team.getColor() + team.getName());
                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.SKULL_ITEM || event.getClickedBlock().getType() == Material.SKULL) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    if (BedWars.getInstance().getSetupStatsWall().containsKey(player.getUniqueId())) {
                        int count = BedWars.getInstance().getSetupStatsWall().get(player.getUniqueId());
                        BedWars.getInstance().getLocationAPI().setLocation("statsSkull." + count, event.getClickedBlock().getLocation());
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du hast den §e§l" + count + " §7Kopf gesetzt");
                        BedWars.getInstance().getSetupStatsWall().remove(player.getUniqueId());
                        return;
                    }
                }
            }
            if (event.getClickedBlock().getType() == Material.BED_BLOCK || event.getClickedBlock().getType() == Material.BED) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (!player.isSneaking()) {
                        event.setCancelled(true);
                        if (BedWars.getInstance().getSetupBed().containsKey(player.getUniqueId())) {
                            BedWars.getInstance().getLocationAPI().setLocation("bed." + BedWars.getInstance().getSetupBed().get(player.getUniqueId()).toLowerCase(), event.getClickedBlock().getLocation());
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Klicke auf das §e§lobere §7Bett");
                            BedWars.getInstance().getSetupBedTop().put(player.getUniqueId(), BedWars.getInstance().getSetupBed().get(player.getUniqueId()));
                            BedWars.getInstance().getSetupBed().remove(player.getUniqueId());
                            return;
                        }
                        if (BedWars.getInstance().getSetupBedTop().containsKey(player.getUniqueId())) {
                            BedWars.getInstance().getLocationAPI().setLocation("bedtop." + BedWars.getInstance().getSetupBedTop().get(player.getUniqueId()).toLowerCase(), event.getClickedBlock().getLocation());
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Das Bett für Team §e§l" + BedWars.getInstance().getSetupBedTop().get(player.getUniqueId()) + " §7wurde gesetzt");
                            BedWars.getInstance().getSetupBedTop().remove(player.getUniqueId());
                            return;
                        }
                    }
                }
            }
        } catch (NullPointerException ex) {
        }

    }


    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
        if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
        if (BedWars.getInstance().getSpectators().contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }
}
