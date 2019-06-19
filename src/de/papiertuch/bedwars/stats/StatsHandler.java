package de.papiertuch.bedwars.stats;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class StatsHandler {


    public void createPlayer(Player player) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                new StatsAPI(player).createPlayer();
            }
        });
    }

    public void addKill(Player player) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                new StatsAPI(player).addInt("KILLS", 1);
                addPoints(player, 1);
            }
        });
    }

    public void addDeath(Player player) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                new StatsAPI(player).addInt("DEATHS", 1);
            }
        });
    }

    public void addPlayedGame(Player player) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                new StatsAPI(player).addInt("PLAYED", 1);
                addPoints(player, 5);
            }
        });
    }


    public void addWin(Player player) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                new StatsAPI(player).addInt("WINS", 1);
                addPoints(player, 20);
            }
        });
    }

    public void addDestroyBed(Player player) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                new StatsAPI(player).addInt("BED", 1);
                addPoints(player, 10);
            }
        });
    }

    private void addPoints(Player player, int amount) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                new StatsAPI(player).addInt("POINTS", amount);
            }
        });
    }
}
