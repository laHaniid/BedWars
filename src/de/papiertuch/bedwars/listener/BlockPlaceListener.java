package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        event.setCancelled(true);
        if (p.getGameMode() == GameMode.CREATIVE) {
            BedWars.getInstance().getBlocks().add(event.getBlock().getLocation());
            event.setCancelled(false);
            return;
        }
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            BedWars.getInstance().getBlocks().add(event.getBlock().getLocation());
            event.setCancelled(false);
        }
        if (event.getBlock().getType() == Material.TNT) {
            event.setCancelled(false);
            event.getBlock().setType(Material.AIR);
            p.getWorld().spawn(event.getBlock().getLocation(), TNTPrimed.class);
        }
    }
}
