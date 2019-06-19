package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        String message = event.getMessage().replace("%", "%%");
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("chat.format.normal")
                    .replace("%player%", player.getDisplayName())
                    .replace("%message%", message));
        } else if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            for (UUID spec : BedWars.getInstance().getSpectators()) {
                Player a = Bukkit.getPlayer(spec);
                if (a != null) {
                    a.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("chat.format.spectators")
                            .replace("%player%", player.getDisplayName())
                            .replace("%message%", message));
                }
            }
        } else if (message.startsWith("@all")) {
            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("chat.format.all")
                    .replace("%player%", player.getDisplayName())
                    .replace("%message%", message.replace("@all", "")));
        } else if (message.startsWith("@a")) {
            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("chat.format.all")
                    .replace("%player%", player.getDisplayName())
                    .replace("%message%", message.replace("@a", "")));
        } else if (message.startsWith("@")) {
            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("chat.format.all")
                    .replace("%player%", player.getDisplayName())
                    .replace("%message%", message.replace("@", "")));
        } else {
            BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
            for (UUID uuid : team.getPlayers()) {
                Player a = Bukkit.getPlayer(uuid);
                a.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("chat.format.team")
                        .replace("%player%", player.getDisplayName())
                        .replace("%message%", message));
            }
        }
    }
}
