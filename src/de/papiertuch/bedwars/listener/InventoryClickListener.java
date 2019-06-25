package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
                event.setCancelled(true);
                if (player.getGameMode() == GameMode.CREATIVE) {
                    event.setCancelled(false);
                }
                if (event.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                    event.setCancelled(true);
                    BedWars.getInstance().getGameHandler().addPlayertoTeam(event);
                }
            }
            if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.vote.name"))) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("item.voting.mapVote"))) {
                    BedWars.getInstance().getGameHandler().getMapVoteInventory(player, event.getCurrentItem().getItemMeta().getDisplayName());
                    player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("item.voting.goldVote"))) {
                    BedWars.getInstance().getGameHandler().getGoldVoteInventory(player, event.getCurrentItem().getItemMeta().getDisplayName());
                    player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                }
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.voting.mapVote"))) {
                if (event.getCurrentItem().getType() == Material.EMPTY_MAP) {
                    if (!BedWars.getInstance().isForceMap()) {
                        String map = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» ", ""));
                        for (ArrayList<UUID> list : BedWars.getInstance().getMapVotes().values()) {
                            if (list.contains(player.getUniqueId())) {
                                list.remove(player.getUniqueId());
                            }
                        }
                        ArrayList<UUID> votes = BedWars.getInstance().getMapVotes().get(map);
                        votes.add(player.getUniqueId());
                        BedWars.getInstance().getMapVotes().put(map, votes);
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du hast für die Map §e§l" + map + " §7gestimmt");
                        player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                        player.closeInventory();
                    } else {
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cEs wurde durch einen ForceMap bereits eine Map ausgesucht...");
                    }
                }
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.voting.goldVote"))) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aMit Gold")) {
                    BedWars.getInstance().getNoGold().remove(player.getUniqueId());
                    BedWars.getInstance().getWithGold().remove(player.getUniqueId());
                    BedWars.getInstance().getWithGold().add(player.getUniqueId());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du hast für §aJa §7gestimmt");
                    player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cohne Gold")) {
                    BedWars.getInstance().getWithGold().remove(player.getUniqueId());
                    BedWars.getInstance().getNoGold().remove(player.getUniqueId());
                    BedWars.getInstance().getNoGold().add(player.getUniqueId());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du hast für §cNein §7gestimmt");
                    player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.closeInventory();
                }
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.compass.name"))) {
                event.setCancelled(true);
                String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                Player target = Bukkit.getPlayer(name);
                if (target != null) {
                    player.teleport(target);
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teleportToPlayer")
                            .replace("%player%", target.getDisplayName()));
                    player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                }
            }
        } catch (NullPointerException ex) {

        }
    }
}
