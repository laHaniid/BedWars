package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
                        String state = BedWars.getInstance().isGold() ? "§2✔" : "§4✖";
                        BedWars.getInstance().getGameHandler().sendActionBar(a, "§f§lGold §8» " + state + " §8┃ §f§lTeam §8» " + team.getColor() + "§l" + team.getName());
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
                        WorldBorder wb = Bukkit.getWorld(BedWars.getInstance().getMap()).getWorldBorder();
                        wb.setCenter(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
                        wb.setSize(50, minutes);
                        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                            team.setBed(false);
                        }
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.destroyAllBeds"));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.destroyBed")), 10F, 10F);
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
