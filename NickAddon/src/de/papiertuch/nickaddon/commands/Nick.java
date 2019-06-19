package de.papiertuch.nickaddon.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.nickaddon.NickAddon;
import de.papiertuch.nickaddon.utils.NickAPI;
import net.haoshoku.nick.NickPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Leon on 17.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Nick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission(NickAddon.getInstance().getNickConfig().getString("command.nick.permission"))) {
            if (NickAddon.getInstance().getNickConfig().getBoolean("lobbyMode")) {
                NickAPI nickAPI = new NickAPI(player);
                boolean bool = nickAPI.getAutoNickState();
                if (bool) {
                    nickAPI.setAutoNick(false);
                } else {
                    nickAPI.setAutoNick(true);
                }
            } else {
                if (!BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                    NickAPI nickAPI = new NickAPI(player);
                    if (args.length == 0) {
                        if (NickAddon.getInstance().getNickPlayers().contains(player.getUniqueId())) {
                            nickAPI.setRandomNick(false);
                            BedWars.getInstance().getBoard().addPlayerToBoard(player);
                        } else {
                            nickAPI.setRandomNick(true);
                            BedWars.getInstance().getBoard().addPlayerToBoard(player);
                        }
                    }
                } else {
                    player.sendMessage(NickAddon.getInstance().getNickConfig().getString("preifx") + " §cDu kannst dich nicht als Spectator nicken!");
                }
            }
        } else {
            player.sendMessage(NickAddon.getInstance().getNickConfig().getString("message.noPerms"));
        }
        return false;
    }
}
