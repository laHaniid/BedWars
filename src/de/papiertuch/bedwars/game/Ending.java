package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Ending {

    private int seconds = 16;
    private int taskID;

    public void startCountdown() {
        BedWars.getInstance().setGameState(GameState.ENDING);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {

            @Override
            public void run() {
                BedWars.getInstance().getBoard().updateBoard();
                for (Player a : Bukkit.getOnlinePlayers()) {
                    //TODO NICK API
                    /*
                    if (NickAPI.nickplayers.contains(a.getUniqueId())) {
                        NickAPI.nickplayers.remove(a.getUniqueId());
                        new NickAPI(a).setNick(false);
                    }
                     */
                    a.showPlayer(a);
                    a.setAllowFlight(false);
                    a.setFlying(false);
                }
                switch (seconds) {
                    case 16:
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            BedWars.getInstance().getGameHandler().setPlayer(a);
                            a.playSound(a.getLocation(), Sound.FIREWORK_TWINKLE, 10F, 10F);
                            BedWars.getInstance().getBoard().setScoreBoard(a);
                            BedWars.getInstance().getBoard().updateNameTags(a);
                        }
                        break;
                    case 15:
                        startFirework();
                        Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.roundEnds"));
                        Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.LAVA_POP, 1, 1);
                        }
                        break;
                    case 10:
                    case 5:
                    case 3:
                    case 2:
                        Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.LAVA_POP, 1, 1);
                        }
                        break;
                    case 1:
                        Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopInOneSecond"));
                        for (Player a : Bukkit.getOnlinePlayers())
                            a.playSound(a.getLocation(), Sound.LAVA_POP, 1, 1);
                        break;
                    case 0:
                        Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.serverStop"));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            BedWars.getInstance().getGameHandler().sendToFallback(a);
                        }
                        for (Location location : BedWars.getInstance().getBlocks()) {
                            Bukkit.getWorld(BedWars.getInstance().getBedWarsConfig().getString("mapName")).getBlockAt(location).setType(Material.AIR);
                        }
                        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.reload();
                            }
                        }, 10);
                        break;
                }
                seconds--;

            }
        }, 0, 20);

    }

    public void startFirework() {
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                setFirework();
            }
        }, 20);
    }

    public int getSeconds() {
        return seconds;
    }

    private void setFirework() {
        Firework firework = BedWars.getInstance().getLocationAPI().getLocation("lobby").getWorld().spawn(BedWars.getInstance().getLocationAPI().getLocation("lobby"), Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(1);
        fireworkMeta.addEffect(FireworkEffect.builder().withFade(Color.BLUE).withColor(Color.YELLOW).flicker(true).withColor(Color.LIME).trail(true).build());
        firework.setFireworkMeta(fireworkMeta);
    }
}