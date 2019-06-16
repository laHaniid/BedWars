package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND && BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            player.openInventory(BedWars.getInstance().getShopHandler().getMainInventory());
            player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 1);
            event.setCancelled(true);
        }
    }
}
