package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Created by Leon on 21.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ForceMap implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("commands.forceMap.permission"))) {
            if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
                if (args.length == 1) {
                    String map = args[0];
                    if (new File("plugins/BedWars/mapBackup/" + map).exists()) {
                        if (BedWars.getInstance().getScheduler().getLobby().getSeconds() > BedWars.getInstance().getBedWarsConfig().getInt("commands.start.seconds")) {
                            BedWars.getInstance().setMap(map);
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.changeMap")
                                    .replace("%map%", map));
                            BedWars.getInstance().getBoard().updateBoard();
                            BedWars.getInstance().setForceMap(true);
                        } else {
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.countDownUnderSeconds")
                                    .replace("%seconds%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("commands.start.seconds"))));
                        }
                    } else {
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.mapNotExists"));
                        File file = new File("plugins/BedWars/mapBackup");
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Map §8» §e§l" + file.listFiles().length);
                        for (File sting : file.listFiles()) {
                            player.sendMessage("§8» §f" + sting.getName());
                        }
                    }
                } else {
                    File file = new File("plugins/BedWars/mapBackup");
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Maps §8» §e§l" + file.listFiles().length);
                    for (File sting : file.listFiles()) {
                        player.sendMessage("§8» §f" + sting.getName());
                    }
                }
            } else {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.roundAlreadyStarting"));
            }
        } else {
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.noPerms"));
        }
        return false;
    }
}
