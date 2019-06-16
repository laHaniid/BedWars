package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.ItemBuilder;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerInteractListener implements Listener {

    private static HashMap<String, ItemStack> skulls = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (player.getItemInHand().getType() == Material.ARMOR_STAND && BedWars.getInstance().getGameState() == GameState.INGAME) {
                // ShopHandler.openHauptInv(player);
                // ShopHandler.removeInventoryItems(player.getInventory(), Material.ARMOR_STAND, 1);
                player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 1);
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getCompass().getItemMeta().getDisplayName())) {
                Inventory inv = Bukkit.createInventory(null, 9 * 3, player.getItemInHand().getItemMeta().getDisplayName());
                for (UUID s : BedWars.getInstance().getPlayers()) {
                    Player target = Bukkit.getPlayer(s);
                    if (skulls.containsKey(player.getName())) {
                        inv.addItem(new ItemBuilder(skulls.get(target.getName())).setName(target.getDisplayName()).build());
                    } else {
                        skulls.put(player.getName(), new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(target.getName()).build());
                        inv.addItem(new ItemBuilder(skulls.get(target.getName())).setName(target.getDisplayName()).build());
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
            if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                if (BedWars.getInstance().getTeamChest().containsKey(team)) {
                    player.openInventory(BedWars.getInstance().getTeamChest().get(team));
                    player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    return;
                }
                Inventory inventory = Bukkit.createInventory(null, 3 * 9, team.getColor() + team.getName());
                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
            }
        } catch (Exception ex) {
        }
    }
}
