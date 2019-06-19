package de.papiertuch.nickaddon.listener;

import de.papiertuch.nickaddon.NickAddon;
import de.papiertuch.nickaddon.utils.NickAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Leon on 17.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (NickAddon.getInstance().getNickConfig().getBoolean("lobbyMode")) {
            NickAddon.getInstance().getMySQL().createPlayer(player);
            NickAddon.getInstance().getMySQL().updateName(player);
            if (player.hasPermission(NickAddon.getInstance().getNickConfig().getString("command.nick.permission"))) {
                ItemStack itemStack = new ItemStack(Material.valueOf(NickAddon.getInstance().getNickConfig().getString("item.nick.material")));
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(NickAddon.getInstance().getNickConfig().getString("item.nick.name"));
                itemStack.setItemMeta(itemMeta);
                player.getInventory().setItem(NickAddon.getInstance().getNickConfig().getInt("item.nick.slot"), itemStack);
            } else {
                new NickAPI(player).disableNick();
            }
        }
    }
}
