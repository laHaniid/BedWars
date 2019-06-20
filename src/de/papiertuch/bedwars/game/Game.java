package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Game {

    private int minutes;
    private int taskID;

    public void startCountdown() {
        setGameStuff();
        if (BedWars.getInstance().getBedWarsTeams().size() == 8) {
            minutes = 3600;
        } else if (BedWars.getInstance().getBedWarsTeams().size() == 2) {
            minutes = 900;
        } else if (BedWars.getInstance().getBedWarsTeams().size() == 4) {
            minutes = 1800; //1800
        } else {
            minutes = 900;
        }
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {

            @Override
            public void run() {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    if (BedWars.getInstance().getPlayers().contains(a.getUniqueId())) {
                        BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(a);
                        if (team != null) {
                            String state = BedWars.getInstance().isGold() ? "§2✔" : "§4✖";
                            BedWars.getInstance().getGameHandler().sendActionBar(a, "§f§lGold §8» " + state + " §8┃ §f§lTeam §8» " + team.getColor() + "§l" + team.getName());
                        }
                    } else {
                        BedWars.getInstance().getGameHandler().sendActionBar(a, "§7§lSpectator");
                    }
                    for (UUID uuid : BedWars.getInstance().getSpectators()) {
                        Player spec = Bukkit.getPlayer(uuid);
                        if (spec != null) {
                            a.hidePlayer(spec);
                            spec.showPlayer(a);
                        }
                    }
                }
                BedWars.getInstance().getBoard().updateBoard();
                switch (minutes) {
                    case 1200:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.boarderIn")
                                .replace("%minutes%", String.valueOf(20)));
                        break;
                    case 900:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.boarderIn")
                                .replace("%minutes%", String.valueOf(15)));
                        break;
                    case 600:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.boarderIn")
                                .replace("%minutes%", String.valueOf(10)));
                        break;
                    case 300:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.boarderIn")
                                .replace("%minutes%", String.valueOf(5)));
                        break;
                    case 60:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.boarderInOneMinute"));
                        break;
                    case 0:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.boarder"));
                        BedWars.getInstance().getScheduler().getBoarder().startCountdown();
                        stopCountdown();
                        break;
                }
                minutes--;
            }
        }, 0, 20);

    }

    private void setGameStuff() {
        BedWars.getInstance().getScheduler().getLobby().stopWaiting();
        BedWars.getInstance().getScheduler().getLobby().stopCountdown();
        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.roundStarting"));
        BedWars.getInstance().getGameHandler().startSpawner();
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().size() == 0) {
                team.setBed(false);
                BedWars.getInstance().getAliveTeams().remove(team);
            }
        }
        for (Player a : Bukkit.getOnlinePlayers()) {
            BedWars.getInstance().getBoard().addPlayerToBoard(a);
            a.getInventory().clear();
            a.playSound(a.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 10F);
            a.setGameMode(GameMode.SURVIVAL);
            a.showPlayer(a);
            a.sendTitle("§a", "§7");
            BedWars.getInstance().getLastHit().clear();
            BedWars.getInstance().getStatsHandler().addPlayedGame(a);
        }
    }


    public int getMinutes() {
        return minutes;
    }

    public void stopCountdown() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
