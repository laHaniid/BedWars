package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ItemStorage {

    private ItemStack teams = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.team.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.team.name")
            ).build();

    private ItemStack leave = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.leave.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.leave.name"))
            .build();

    private ItemStack startItem = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.start.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.start.name"))
            .build();

    private ItemStack vote = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.vote.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.vote.name"))
            .build();

    private ItemStack compass = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.compass.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.compass.name"))
            .build();

    public ItemStack getVote() {
        return vote;
    }

    public ItemStack getStartItem() {
        return startItem;
    }

    public ItemStack getCompass() {
        return compass;
    }

    public ItemStack getLeave() {
        return leave;
    }

    public ItemStack getTeams() {
        return teams;
    }

}
