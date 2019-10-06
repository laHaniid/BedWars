package de.papiertuch.bedwars.listener;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.nickaddon.utils.NickAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

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
            if (BedWars.getInstance().isNickEnable()) {
                if (new NickAPI(player).getAutoNickState()) {
                    new NickAPI(player).setNick(true);
                }
            }
            BedWars.getInstance().getGameHandler().setPlayer(player);
            BedWars.getInstance().getBoard().addPlayerToBoard(player);
            event.setJoinMessage(BedWars.getInstance().getBedWarsConfig().getString("message.joinGame")
                    .replace("%player%", player.getDisplayName())
                    .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("%maxPlayers%", String.valueOf(BedWars.getInstance().getGameHandler().getMaxPlayers())));
            if ((BedWars.getInstance().getPlayers().size() >= BedWars.getInstance().getBedWarsConfig().getInt("minPlayers")) && (!BedWars.getInstance().getScheduler().getLobby().isRunning())) {
                BedWars.getInstance().getScheduler().getLobby().stopWaiting();
                BedWars.getInstance().getScheduler().getLobby().startCountdown();
            }
            if ((BedWars.getInstance().getPlayers().size() < BedWars.getInstance().getBedWarsConfig().getInt("minPlayers")) && (!BedWars.getInstance().getScheduler().getLobby().isWaiting())) {
                BedWars.getInstance().getScheduler().getLobby().startWaiting();
            }
            if (BedWars.getInstance().getPlayers().size() == BedWars.getInstance().getGameHandler().getMaxPlayers()) {
                BedWars.getInstance().getScheduler().getLobby().setSeconds(30);
                BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStarting"));
            }
        }
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            event.setJoinMessage(null);
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.spectator"));
            player.teleport(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
            BedWars.getInstance().getGameHandler().setSpectator(player);
        }
    }

    @EventHandler
    public void onLoginEvent(PlayerLoginEvent event) {
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            Player player = event.getPlayer();
            int i = BedWars.getInstance().getBedWarsConfig().getBoolean("cloudnet") ? CloudServer.getInstance().getMaxPlayers() : BedWars.getInstance().getGameHandler().getMaxPlayers();
            if (i < Bukkit.getOnlinePlayers().size()) {
                return;
            }
            if (i == Bukkit.getOnlinePlayers().size()) {
                if (!player.hasPermission("bedwars.premium")) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu benötigst mindestens den §6§lPremium §cRang, um diesen Server betreten zu können!");
                    return;
                }
                int q = 0;
                for (Player a : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("bedwars.premium")) {
                        q++;
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDieser Server ist komplett voll. Jeder hat mindestenes einen §6§lPremium §cRang!");
                        if (q == Bukkit.getOnlinePlayers().size()) {
                            return;
                        }
                    }
                }
                for (Player a : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPermission("bedwars.premium")) {
                        a.kickPlayer(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu wurdest von einem §e§lhöherrängigen §cSpieler gekickt!");
                        a.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu wurdest von einem §e§lhöherrängigen §cSpieler gekickt!");
                        event.allow();
                        return;
                    }
                }
            } else {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu benötigst mindestens den §6§lPremium §cRang, um diesen Server betreten zu können!");
            }
        }
        if (BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDie Runde ist bereits zuende...");
        }
    }


}
