package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Start implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("commands.start.permission"))) {
            if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
                if (BedWars.getInstance().getPlayers().size() >= BedWars.getInstance().getBedWarsConfig().getInt("minPlayers")) {
                    if (BedWars.getInstance().getScheduler().getLobby().getSeconds() > BedWars.getInstance().getBedWarsConfig().getInt("commands.start.seconds")) {
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.startRound"));
                        player.playSound(player.getLocation(), Sound.ANVIL_USE, 3, 2);
                        BedWars.getInstance().getScheduler().getLobby().setSeconds(BedWars.getInstance().getBedWarsConfig().getInt("commands.start.seconds"));
                    } else {
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.countDownUnderSeconds")
                                .replace("%seconds%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("commands.start.seconds"))));
                    }
                } else {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.notEnoughPlayers"));
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
