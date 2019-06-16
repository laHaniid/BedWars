package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;

import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                event.setCancelled(false);
                return;
            }
        } else if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                event.setCancelled(false);
                return;
            }
            if (BedWars.getInstance().getBlocks().contains(event.getBlock().getLocation())) {
                event.setCancelled(false);
                return;
            }
            for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                if (event.getBlock().getLocation().equals(BedWars.getInstance().getLocationAPI().getBedLocation("bed." + team.getName().toLowerCase())) || event.getBlock().getLocation().equals(BedWars.getInstance().getLocationAPI().getBedLocation("bedtop." + team.getName().toLowerCase()))) {
                    if (team.getPlayers().contains(player.getUniqueId())) {
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.destroyOwnBed"));
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
                        event.setCancelled(true);
                        return;
                    }
                    if (team.hasBed()) {
                        team.setBed(false);
                        Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.destroyBed")
                                .replace("%player%", player.getDisplayName())
                                .replace("%team%", team.getColor() + team.getName()));
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                        event.setCancelled(true);
                        BedWars.getInstance().getLocationAPI().getBedLocation("bed." + team.getName().toLowerCase()).getBlock().setType(Material.AIR);
                        BedWars.getInstance().getLocationAPI().getBedLocation("bedtop." + team.getName().toLowerCase()).getBlock().setType(Material.AIR);
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.WITHER_DEATH, 10F, 10F);
                            BedWars.getInstance().getBoard().updateBoard();
                        }
                        for (UUID uuid : team.getPlayers()) {
                            Player a = Bukkit.getPlayer(uuid);
                            a.sendTitle("§cDein Bett", "§cwurde zerstört");
                            a.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                        }
                        BedWars.getInstance().getStatsHandler().addDestroyBed(player);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent event) {
        try {
            Player player = (Player) event.getHandlers();
            if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                event.setBuildable(true);
            }
        } catch (Exception ex) {
        }
    }
}
