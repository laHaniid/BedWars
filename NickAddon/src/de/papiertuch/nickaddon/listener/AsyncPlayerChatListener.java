package de.papiertuch.nickaddon.listener;

import de.papiertuch.nickaddon.NickAddon;
import de.papiertuch.nickaddon.utils.TabListGroup;
import org.bukkit.Bukkit;
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
        if (Bukkit.getPluginManager().getPlugin("BedWars") == null) {
            TabListGroup tabListGroup = NickAddon.getInstance().getTabListGroup(player);
            String message = NickAddon.getInstance().getNickConfig().getString("chat.format")
                    .replace("%player%", player.getName())
                    .replace("%", "%%")
                    .replace("%display%", tabListGroup.getDisplay())
                    .replace("%prefix%", tabListGroup.getPrefix())
                    .replace("%suffix%", tabListGroup.getSuffix())
                    .replace("%group%", tabListGroup.getName())
                    .replace("%message%", event.getMessage());
            if (player.hasPermission("chat.color")) message.replace("&", "§");

            event.setFormat(message);
        }
    }
}
