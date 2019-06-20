package de.papiertuch.nickaddon.listener;

import de.papiertuch.nickaddon.NickAddon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Leon on 20.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        String message = event.getMessage().replace("%", "%%");
        event.setFormat(NickAddon.getInstance().getNickConfig().getString("chat.format")
                .replace("%player%", player.getDisplayName())
                .replace("%message%", message));
    }
}
