package de.papiertuch.nickaddon.listener;

import de.papiertuch.nickaddon.NickAddon;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Leon on 17.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(NickAddon.getInstance().getNickConfig().getString("item.nick.name"))) {
                player.performCommand("nick");
                player.playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 10F, 10F);
            }
        } catch (NullPointerException ex) {

        }
    }
}
