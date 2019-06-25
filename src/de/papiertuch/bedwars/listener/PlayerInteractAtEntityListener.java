package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (EntityType.valueOf(BedWars.getInstance().getBedWarsConfig().getString("shopType")) != EntityType.VILLAGER) {
            if (event.getRightClicked().getType() == EntityType.valueOf(BedWars.getInstance().getBedWarsConfig().getString("shopType")) && BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.openInventory(BedWars.getInstance().getShopHandler().getMainInventory());
                player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType() == EntityType.valueOf(BedWars.getInstance().getBedWarsConfig().getString("shopType")) && BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.openInventory(BedWars.getInstance().getShopHandler().getMainInventory());
            player.playSound(player.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
        }
    }
}
