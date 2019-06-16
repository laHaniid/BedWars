package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class GameHandler {

    public Color getColorFromString(String color) {
        if (BedWars.getInstance().getColors().containsKey(color)) {
            return BedWars.getInstance().getColors().get(color);
        }
        return null;
    }

    public void addPlayertoTeam(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("item.team.name"))) {
            Player player = (Player) event.getWhoClicked();
            BedWarsTeam team = BedWars.getInstance().getBedWarsTeams().get(event.getSlot());
            if (team.getPlayers().size() == team.getSize()) {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teamFull"));
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
                return;
            }
            clearFromTeams(player);
            team.addPlayer(player.getUniqueId());
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.choseTeam")
                    .replace("%team%",
                            team.getColor() + team.getName()));
            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1, 1);
            player.closeInventory();
        }
    }


    public int getMaxPlayers() {
        String mode = BedWars.getInstance().getBedWarsConfig().getString("mode");
        return Integer.valueOf(mode.split("x")[0]) * Integer.valueOf(mode.split("x")[1]);
    }

    public void setSpectator(Player player) {
        if (!BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            BedWars.getInstance().getSpectators().add(player.getUniqueId());
        }
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemStorage().getCompass());
        player.getInventory().setItem(8, new ItemStorage().getLeave());
        BedWars.getInstance().getBoard().addPlayerToBoard(player);
        for (Player a : Bukkit.getOnlinePlayers()) {
            for (UUID uuid : BedWars.getInstance().getSpectators()) {
                Player spec = Bukkit.getPlayer(uuid);
                a.hidePlayer(spec);
            }
        }
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                BedWars.getInstance().getBoard().addPlayerToBoard(player);
            }
        }, 20);
    }

    public void getTeamInventory(Player player, String name) {
        Inventory inv = Bukkit.createInventory(null, 9, name);
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            ArrayList<String> list = new ArrayList<>();
            for (UUID uuid : team.getPlayers()) {
                list.add("§8» §f" + Bukkit.getPlayer(uuid).getName());
            }
            inv.addItem(new ItemBuilder(Material.LEATHER_BOOTS, 1)
                    .setName(team.getColor() + team.getName())
                    .setLeatherColor(team.getColorasColor())
                    .addFlags(ItemFlag.values())
                    .setLore(list)
                    .build());

            player.openInventory(inv);
        }
    }

    public TabListGroup getTabListGroup(Player player) {
        for (TabListGroup tabListGroup : BedWars.getInstance().getTabListGroups()) {
            if (player.hasPermission(tabListGroup.getPermission())) {
                return tabListGroup;
            }
        }
        return BedWars.getInstance().getTabListGroups().get(BedWars.getInstance().getTabListGroups().size() - 1);
    }

    public void checkTeams(Player player) {
        BedWarsTeam team = getTeam(player);
        if (!BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            team.removePlayer(player.getUniqueId());
            if (team.getPlayers().size() == 0) {
                Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teamDeath")
                        .replace("%team%", team.getColor() + team.getName()));
                BedWars.getInstance().getAliveTeams().remove(team);
                team.setBed(false);
                for (Player a : Bukkit.getOnlinePlayers()) {
                    a.playSound(a.getLocation(), Sound.WITHER_DEATH, 10F, 10F);
                    BedWars.getInstance().getBoard().addPlayerToBoard(a);
                }
            } else {
                Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teamReamingPlayers")
                        .replace("%team%", team.getColor() + team.getName())
                        .replace("%players%", String.valueOf(team.getPlayers().size())));
                for (Player a : Bukkit.getOnlinePlayers()) {
                    a.playSound(a.getLocation(), Sound.NOTE_PLING, 1, 1);
                    BedWars.getInstance().getBoard().addPlayerToBoard(a);
                }
            }
        }
    }

    public void clearFromTeams(Player player) {
        BedWarsTeam team = getTeam(player);
        if (team != null) {
            team.removePlayer(player.getUniqueId());
        }
    }


    public BedWarsTeam getTeam(Player player) {
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().contains(player.getUniqueId())) {
                return team;
            }
        }
        return null;
    }

    public void teleportToMap(Player p) {
        String team = getTeam(p).getName().toLowerCase();
        p.teleport(BedWars.getInstance().getLocationAPI().getLocation("spawn." + team));
    }


    public void checkWinner() {
        BedWarsTeam winner = null;
        if (BedWars.getInstance().getAliveTeams().size() == 1) {
            winner = BedWars.getInstance().getAliveTeams().get(0);
            for (UUID uuid : winner.getPlayers()) {
                Player a = Bukkit.getPlayer(uuid);
                BedWars.getInstance().getStatsHandler().addWin(a);
            }
            BedWars.getInstance().setGameState(GameState.ENDING);
            BedWars.getInstance().getScheduler().getEnding().startCountdown();
            for (Player a : Bukkit.getOnlinePlayers()) {
                a.setHealth(20);
                a.setAllowFlight(false);
                a.setFlying(false);
                a.getInventory().clear();
                a.getInventory().setArmorContents(null);
                a.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.leave.slot"), new ItemStorage().getLeave());
                a.sendTitle("§7Team " + winner.getColor() + winner.getName(), "§7hat gewonnen");
            }
            Bukkit.broadcastMessage("§7");
            Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teamWin")
                    .replace("%team%", winner.getColor() + winner.getName()));
            Bukkit.broadcastMessage("§7");
            return;
        }
    }

    public void startSpawner() {
        Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                String type = "bronze";
                int spawner = BedWars.getInstance().getLocationAPI().getCfg().getInt(type + "spawnercount");
                for (int i = 1; i <= spawner; i++) {
                    Location loc = BedWars.getInstance().getLocationAPI().getLocation("spawner." + type + "." + i);
                    Bukkit.getWorld(loc.getWorld().getName()).dropItem(loc, new ItemBuilder(Material.CLAY_BRICK, 1).setName("§cBronze").build());
                }
            }
        }, 1, 10);
        Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                String type = "iron";
                int spawner = BedWars.getInstance().getLocationAPI().getCfg().getInt(type.toLowerCase() + "spawnercount");
                for (int i = 1; i <= spawner; i++) {
                    Location loc = BedWars.getInstance().getLocationAPI().getLocation("spawner." + type.toLowerCase() + "." + i);
                    Bukkit.getWorld(loc.getWorld().getName()).dropItem(loc, new ItemBuilder(Material.IRON_INGOT, 1).setName("§fEisen").build());
                }
            }
        }, 0, 10 * 20);
        Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), new Runnable() {
            @Override
            public void run() {
                String type = "gold";
                int spawner = BedWars.getInstance().getLocationAPI().getCfg().getInt(type.toLowerCase() + "spawnercount");
                for (int i = 1; i <= spawner; i++) {
                    Location loc = BedWars.getInstance().getLocationAPI().getLocation("spawner." + type.toLowerCase() + "." + i);
                    Bukkit.getWorld(loc.getWorld().getName()).dropItem(loc, new ItemBuilder(Material.GOLD_INGOT, 1).setName("§6Gold").build());
                }

            }
        }, 0, 35 * 20);
    }

    public void sendToFallback(Player player) {
        player.kickPlayer("§cRestart");
    }

    public void setPlayer(Player player) {
        player.teleport(BedWars.getInstance().getLocationAPI().getLocation("lobby"));
        if (!BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            BedWars.getInstance().getPlayers().add(player.getUniqueId());
        }
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.team.slot"), new ItemStorage().getTeams());
        }
        player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.leave.slot"), new ItemStorage().getLeave());
    }

    public void getFreeTeamForPlayer(Player player) {
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().isEmpty()) {
                team.addPlayer(player.getUniqueId());
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.yourInTeam")
                        .replace("%team%", team.getColor() + team.getName()));
                return;
            }
        }
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().size() < team.getSize()) {
                team.addPlayer(player.getUniqueId());
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.yourInTeam")
                        .replace("%team%", team.getColor() + team.getName()));
                return;
            }
        }
    }

    public boolean hasTeam(Player player) {
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public void sendActionBar(Player player, String message) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer
                .a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }

}