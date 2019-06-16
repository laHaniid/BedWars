package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntity(EntityDamageByEntityEvent event) {
        try {
            Player damager = (Player) event.getDamager();
            Player player = (Player) event.getEntity();
            if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
                event.setCancelled(true);
            }
            if (BedWars.getInstance().getSpectators().contains(damager.getUniqueId())) {
                event.setCancelled(true);
            }
            if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
            BedWarsTeam TeamD = BedWars.getInstance().getGameHandler().getTeam(damager);
            BedWarsTeam TeamP = BedWars.getInstance().getGameHandler().getTeam(player);
            if (TeamD == TeamP || BedWars.getInstance().getSpectators().contains(player.getUniqueId()) || BedWars.getInstance().getSpectators().contains(damager.getUniqueId())) {
                event.setCancelled(true);
            } else {
                BedWars.getInstance().getLastHit().put(player, damager);
            }
            if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                if (TeamD == TeamP) {
                    event.setCancelled(true);
                } else {
                    BedWars.getInstance().getLastHit().put(player, damager);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
