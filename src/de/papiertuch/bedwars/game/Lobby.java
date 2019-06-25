package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Lobby {

    private static int seconds = 60;
    private static int taskID, waitingID;
    private float xp = 1.00f;
    private boolean isRunning = false;
    private static boolean waiting = false;


    public void startCountdown() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    BedWars.getInstance().getBoard().updateBoard();
                    xp = (((float) 1 / 60) * seconds);
                    a.setExp(xp);
                    a.showPlayer(a);
                    a.setLevel(seconds);
                    BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(a);
                    if (team != null) {
                        BedWars.getInstance().getGameHandler().sendActionBar(a, team.getColor() + "§l" + team.getName());
                    }
                }
                switch (seconds) {
                    case 60:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStarting"));
                        playSound();
                        break;
                    case 45:
                    case 30:
                    case 10:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        playSound();
                        break;
                    case 15:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.builder")
                                .replace("%builder%", BedWars.getInstance().getBedWarsConfig().getString("builder")));
                        playSound();
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle(BedWars.getInstance().getBedWarsConfig().getString("prefix"), BedWars.getInstance().getMap());
                        }
                        break;
                    case 5:
                        BedWars.getInstance().getGameHandler().checkGoldVoting();
                        BedWars.getInstance().getGameHandler().checkMapVoting();
                        BedWars.getInstance().getGameHandler().sendBroadCast("");
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.goldStatus")
                        .replace("%state%", BedWars.getInstance().isGold() ? "§2§l✔" : "§4§l✖"));
                        if (BedWars.getInstance().isForceMap()) {
                            BedWars.getInstance().getGameHandler().sendBroadCast("");
                        }
                        if (!BedWars.getInstance().isForceMap()) {
                            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Map Voting §8» §e§l" + BedWars.getInstance().getMap());
                            BedWars.getInstance().getGameHandler().sendBroadCast("");
                        }
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.ANVIL_LAND, 1, 1);
                            a.getInventory().remove(new ItemStorage().getVote());
                        }
                        BedWars.getInstance().getGameHandler().loadMap();
                        break;
                    case 3:
                    case 2:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        playSound();
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle("§e" + seconds, "");
                        }
                        break;
                    case 1:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingInOneSecond"));
                        playSound();
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle("§eStart", "");
                        }
                        WorldBorder wb = Bukkit.getWorld(BedWars.getInstance().getMap()).getWorldBorder();
                        wb.setCenter(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
                        wb.setSize(50, 2000000);
                        break;
                    case 0:
                            for (Player a : Bukkit.getOnlinePlayers()) {
                                if (!BedWars.getInstance().getGameHandler().hasTeam(a)) {
                                    BedWars.getInstance().getGameHandler().getFreeTeamForPlayer(a);
                                }
                                BedWars.getInstance().getGameHandler().teleportToMap(a);
                            }
                        BedWars.getInstance().setGameState(GameState.INGAME);
                        BedWars.getInstance().getScheduler().getGame().startCountdown();
                        stopCountdown();
                        stopWaiting();
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }

    private void playSound() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            a.playSound(a.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.countdown")), 10F, 10F);
        }
    }

    public void startWaiting() {
        waiting = true;
        waitingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                Integer missing = BedWars.getInstance().getBedWarsConfig().getInt("minPlayers") - BedWars.getInstance().getPlayers().size();
                for (Player a : Bukkit.getOnlinePlayers()) {
                    if (BedWars.getInstance().getPlayers().size() == 1) {
                        BedWars.getInstance().getGameHandler().sendActionBar(a, BedWars.getInstance().getBedWarsConfig().getString("message.waiting")
                                .replace("%players%", "einen"));
                    } else {
                        BedWars.getInstance().getGameHandler().sendActionBar(a, BedWars.getInstance().getBedWarsConfig().getString("message.waiting")
                                .replace("%players%", String.valueOf(missing)));
                    }
                    xp = (((float) 1 / 60) * seconds);
                    a.setExp(xp);
                    a.setLevel(seconds);
                }
            }
        }, 0, 20 * 1);
    }


    public void stopWaiting() {
        if (isWaiting()) {
            setWaiting(false);
            Bukkit.getScheduler().cancelTask(waitingID);
        }
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        Lobby.waiting = waiting;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopCountdown() {
        if (isRunning) {
            isRunning = false;
            Bukkit.getScheduler().cancelTask(taskID);
            seconds = 60;
        }
    }

    public void setSeconds(Integer seconds) {
        Lobby.seconds = seconds;
    }

    public Integer getSeconds() {
        return seconds;
    }
}
