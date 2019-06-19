package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Boarder {

    private int minutes = 600; //600
    private int taskID;

    public void startCountdown() {
        BedWars.getInstance().setBoarder(true);
        for (Player a : Bukkit.getOnlinePlayers()) {
            BedWars.getInstance().getBoard().setScoreBoard(a);
        }
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {

            @Override
            public void run() {
                BedWars.getInstance().getBoard().updateBoard();
                for (Player a : Bukkit.getOnlinePlayers()) {
                    if (BedWars.getInstance().getPlayers().contains(a.getUniqueId())) {
                        BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(a);
                        BedWars.getInstance().getGameHandler().sendActionBar(a, team.getColor() + "§l" + team.getName());
                    }
                    for (UUID uuid : BedWars.getInstance().getSpectators()) {
                        Player spec = Bukkit.getPlayer(uuid);
                        if (spec != null) {
                            a.hidePlayer(spec);
                            spec.showPlayer(a);
                        }
                    }
                }
                switch (minutes) {
                    case 600:
                        WorldBorder wb = Bukkit.getWorld(BedWars.getInstance().getBedWarsConfig().getString("mapName")).getWorldBorder();
                        wb.setCenter(BedWars.getInstance().getLocationAPI().getLocation("spectator"));
                        wb.setSize(50, minutes);
                        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                            team.setBed(false);
                        }
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.destroyAllBeds"));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.WITHER_DEATH, 10F, 10F);
                            BedWars.getInstance().getBoard().setScoreBoard(a);
                        }
                        break;
                    case 0:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.smallBoarder"));
                        BedWars.getInstance().setGameState(GameState.ENDING);
                        stop();
                        BedWars.getInstance().getScheduler().getEnding().startCountdown();
                        break;
                }
                minutes--;

            }
        }, 0, 20);
    }


    public int getMinutes() {
        return minutes;
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
