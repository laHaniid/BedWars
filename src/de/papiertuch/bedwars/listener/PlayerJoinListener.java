package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            BedWars.getInstance().getStatsHandler().createPlayer(player);
            BedWars.getInstance().getGameHandler().setPlayer(player);
            if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("command.start.permission"))) {
                player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.start.slot"), new ItemStorage().getStartItem());
            }
            //TODO NICK API
            /*
            if (new NickAPI(player).getAutoNickState()) {
                new NickAPI(player).setNick(true);
            }
             */

            BedWars.getInstance().getBoard().addPlayerToBoard(player);
            event.setJoinMessage(BedWars.getInstance().getBedWarsConfig().getString("message.joinGame")
                    .replace("%player%", player.getDisplayName())
                    .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("%maxPlayers%", String.valueOf(BedWars.getInstance().getGameHandler().getMaxPlayers())));
            if ((BedWars.getInstance().getPlayers().size() >= 2) && (!BedWars.getInstance().getScheduler().getLobby().isRunning())) {
                BedWars.getInstance().getScheduler().getLobby().stopWaiting();
                BedWars.getInstance().getScheduler().getLobby().startCountdown();
            }
            if ((BedWars.getInstance().getPlayers().size() < 2) && (!BedWars.getInstance().getScheduler().getLobby().isWaiting())) {
                BedWars.getInstance().getScheduler().getLobby().startWaiting();
            }
            if (BedWars.getInstance().getPlayers().size() == BedWars.getInstance().getGameHandler().getMaxPlayers()) {
                BedWars.getInstance().getScheduler().getLobby().setSeconds(30);
                Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.gameStarting"));
            }
        }
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            event.setJoinMessage(null);
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.spectator"));
            player.teleport(BedWars.getInstance().getLocationAPI().getLocation("spectator"));
            BedWars.getInstance().getGameHandler().setSpectator(player);
        }
    }


}