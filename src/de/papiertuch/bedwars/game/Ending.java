package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.nickaddon.NickAddon;
import de.papiertuch.nickaddon.utils.NickAPI;
import net.haoshoku.nick.NickPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
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
    private float xp = 1.00f;

    public void startCountdown() {
        BedWars.getInstance().getScheduler().getGame().stopCountdown();
        BedWars.getInstance().getScheduler().getBoarder().stop();
        BedWars.getInstance().setGameState(GameState.ENDING);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {

            @Override
            public void run() {
                BedWars.getInstance().getBoard().updateBoard();
                for (Player a : Bukkit.getOnlinePlayers()) {
                    if (Bukkit.getPluginManager().getPlugin("NickAddon") != null) {
                        if (NickAddon.getInstance().getNickPlayers().contains(a.getUniqueId())) {
                            new NickAPI(a).setNick(false);
                        }
                    }
                    xp = (((float) 1 / 60) * seconds);
                    a.setExp(xp);
                    a.setLevel(seconds);
                    a.showPlayer(a);
                    a.setAllowFlight(false);
                    a.setFlying(false);
                    if (!BedWars.getInstance().getPlayers().contains(a.getUniqueId())) {
                        BedWars.getInstance().getGameHandler().setPlayer(a);
                    }
                    if (BedWars.getInstance().getSpectators().contains(a.getUniqueId())) {
                        BedWars.getInstance().getSpectators().remove(a.getUniqueId());
                    }
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
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.roundEnds"));
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.endingCountdown")), 1, 1);
                        }
                        break;
                    case 10:
                    case 5:
                    case 3:
                    case 2:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopIn")
                                .replace("%seconds%", String.valueOf(seconds)));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.playSound(a.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.endingCountdown")), 1, 1);
                        }
                        break;
                    case 1:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopInOneSecond"));
                        for (Player a : Bukkit.getOnlinePlayers())
                            a.playSound(a.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.endingCountdown")), 1, 1);
                        break;
                    case 0:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStop"));
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            BedWars.getInstance().getGameHandler().sendToFallback(a);
                        }
                        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.shutdown();
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
        Firework firework = BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby").getWorld().spawn(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby"), Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(1);
        fireworkMeta.addEffect(FireworkEffect.builder().withFade(Color.BLUE).withColor(Color.YELLOW).flicker(true).withColor(Color.LIME).trail(true).build());
        firework.setFireworkMeta(fireworkMeta);
    }
}
