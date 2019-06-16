package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn( PlayerRespawnEvent event ) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setRespawnLocation(BedWars.getInstance().getLocationAPI().getLocation("lobby"));
            player.getInventory().clear();
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 10F);
        } else if (BedWars.getInstance().getGameState() == GameState.INGAME && ! BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            event.setRespawnLocation(BedWars.getInstance().getLocationAPI().getLocation("spectator"));
            BedWars.getInstance().getGameHandler().setSpectator(player);
        } else {
            String team = BedWars.getInstance().getGameHandler().getTeam(player).getName().toLowerCase();
            event.setRespawnLocation(BedWars.getInstance().getLocationAPI().getLocation("spawn." + team));
        }
    }
}
