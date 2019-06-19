package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.nickaddon.NickAddon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            BedWars.getInstance().getGameHandler().clearFromTeams(player);
            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.leaveGame")
                    .replace("%player%", player.getDisplayName()));
            BedWars.getInstance().getPlayers().remove(player.getUniqueId());
            if (NickAddon.getInstance().getNickPlayers().contains(player.getUniqueId())) {
                NickAddon.getInstance().getNickPlayers().remove(player.getUniqueId());
            }
            if ((BedWars.getInstance().getPlayers().size() < BedWars.getInstance().getGameHandler().getMaxPlayers()) && (BedWars.getInstance().getScheduler().getLobby().isRunning())) {
                BedWars.getInstance().getScheduler().getLobby().stopCountdown();
                BedWars.getInstance().getScheduler().getLobby().startWaiting();
            }
            BedWars.getInstance().getBoard().updateBoard();
        }
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                BedWars.getInstance().getGameHandler().checkTeams(player);
                BedWars.getInstance().getGameHandler().checkWinner();
                BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.leaveGame")
                        .replace("%player%", player.getDisplayName()));
            } else {
                BedWars.getInstance().getSpectators().remove(player.getUniqueId());
            }
        }
    }
}
