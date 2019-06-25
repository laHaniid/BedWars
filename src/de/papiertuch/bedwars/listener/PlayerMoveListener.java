package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        try {
            Player player = event.getPlayer();
            if (BedWars.getInstance().getGameState() == GameState.INGAME) {
                String team = BedWars.getInstance().getGameHandler().getTeam(player).getName().toLowerCase();
                if (player.getLocation().getY() <= (BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spawn." + team).getY() - 50)) {
                    if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                        if (!player.isDead()) {
                            player.setHealth(0);
                        }
                    }
                }
            }
            if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(a);
                    player.canSee(a);
                }
                if (player.getWorld().getName().equalsIgnoreCase("world")) {
                    if (player.getLocation().getY() <= (BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby").getY() - 50)) {
                        player.teleport(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby"));
                    }
                }
            }
        } catch (Exception ex) {
        }
    }
}
