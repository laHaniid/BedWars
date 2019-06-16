package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.stats.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Stats implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.stats")
                        .replace("%player%", player.getDisplayName()));
                player.sendMessage("§7");
                player.sendMessage("§8» §f§lPlatz §8» §e" + new StatsAPI(player).getRankingFromUUID("bedwars", "points"));
                player.sendMessage("§8» §f§lPunkte §8» §e" + new StatsAPI(player).getInt("bedwars", "points"));
                player.sendMessage("§8» §f§lKills §8» §e" + new StatsAPI(player).getInt("bedwars", "kills"));
                player.sendMessage("§8» §f§lTode §8» §e" + new StatsAPI(player).getInt("bedwars", "deaths"));
                player.sendMessage("§8» §f§lGewonnen §8» §e" + new StatsAPI(player).getInt("bedwars", "wins"));
                player.sendMessage("§8» §f§lGespielt §8» §e" + new StatsAPI(player).getInt("bedwars", "played"));
                player.sendMessage("§8» §f§lBetten §8» §e" + new StatsAPI(player).getInt("bedwars", "bed"));
                player.sendMessage("§7");
            }
        });
        return false;
    }
}
