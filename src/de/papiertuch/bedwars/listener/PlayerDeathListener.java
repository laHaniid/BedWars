package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getDrops().clear();
        event.setDeathMessage(null);
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (!BedWars.getInstance().getDeath().contains(player)) {
                if (BedWars.getInstance().getLastHit().containsKey(player)) {
                    Player killer = BedWars.getInstance().getLastHit().get(player);
                    respawnPlayer(player);
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.killMessage")
                            .replace("%player%", player.getDisplayName())
                            .replace("%killer%", killer.getDisplayName()));
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.killerLife")
                            .replace("%killer%", killer.getDisplayName())
                            .replace("%live%", getCurrentLive(killer) + " ❤"));
                    killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 1);
                    BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                    if (!team.hasBed()) {
                        BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                    }
                    BedWars.getInstance().getGameHandler().checkTeams(player);
                    BedWars.getInstance().getGameHandler().checkWinner();
                    BedWars.getInstance().getLastHit().remove(player);

                    BedWars.getInstance().getStatsHandler().addDeath(player);
                    BedWars.getInstance().getStatsHandler().addKill(killer);
                } else {
                    respawnPlayer(player);
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.death")
                    .replace("%player%", player.getDisplayName()));
                    BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                    if (!team.hasBed()) {
                        BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                    }
                    BedWars.getInstance().getGameHandler().checkTeams(player);
                    BedWars.getInstance().getGameHandler().checkWinner();
                    BedWars.getInstance().getStatsHandler().addDeath(player);
                }
            }
        }
    }

    private void respawnPlayer(Player player) {
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }, 2);
    }

    private int getCurrentLive(Player player) {
        double h = player.getHealth();
        int l = 2;
        double H = h / l;
        double rounded = Math.round(H * 100.0D) / 100.0D;
        if ((int) rounded == 0) {
            return (int) 0.5;
        }
        return (int) rounded;
    }
}
