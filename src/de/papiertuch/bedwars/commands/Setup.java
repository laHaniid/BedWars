package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.LocationAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Setup implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("commands.setup.permission"))) {
            if (args.length == 0) {
                sendHelp(player);
            } else if (args[0].equalsIgnoreCase("setSpawn")) {
                if (args.length == 1) {
                    sendHelp(player);
                } else {
                    for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                        if (team.getName().equalsIgnoreCase(args[1])) {
                            BedWars.getInstance().getLocationAPI(player.getWorld().getName()).setLocation("spawn." + args[1].toLowerCase(), player.getLocation());
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Die Location §e§lspawn." + args[1] + " §7wurde gesetzt!");
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Alle Teams:");
                for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                    player.sendMessage("§8» " + team.getColor() + team.getName());
                }
            } else if (args[0].equalsIgnoreCase("saveMap")) {
                String path = player.getWorld().getName();
                String target = "plugins/BedWars/mapBackup/" + path;
                BedWars.getInstance().getGameHandler().copyFilesInDirectory(new File(path), new File(target));
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Die aktuelle §e§lMap §7wurde als Backup gespeichert");
            } else if (args[0].equalsIgnoreCase("status")) {
                if (args.length == 2) {
                    String map = args[1];
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Setup Infos:");
                    if (!new LocationAPI(map).getFile().exists()) {
                        player.sendMessage("§cEs wurden keine Locations gefunden...");
                        return true;
                    }
                    player.sendMessage("§8» §f§lMapBackup §8» " + (new File("plugins/BedWars/mapBackup/" + map).exists() ? "§a✔" : "§c✖"));
                    player.sendMessage("§8» §f§lLobbySpawn §8» " + (new LocationAPI(map).isExists("lobby") ? "§a✔" : "§c✖"));
                    player.sendMessage("§8» §f§lSpectatorSpawn §8» " + (new LocationAPI(map).isExists("spectator") ? "§a✔" : "§c✖"));
                    player.sendMessage("§8» §f§lStatsWand §8» " + (new LocationAPI(map).getCfg().get("statsWall") != null ? "§a✔" : "§c✖"));
                    for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                        player.sendMessage("§8» §f§lSpawn von " + team.getColor() + team.getName() + " §8» " + (new LocationAPI(map).isExists("spawn." + team.getName().toLowerCase()) ? "§a✔" : "§c✖"));
                        player.sendMessage("§8» §f§lBed von " + team.getColor() + team.getName() + " §8» " + (new LocationAPI(map).isExists("bed." + team.getName().toLowerCase()) ? "§a✔" : "§c✖"));
                        player.sendMessage("§8» §f§lBedTop von " + team.getColor() + team.getName() + " §8» " + (new LocationAPI(map).isExists("bedtop." + team.getName().toLowerCase()) ? "§a✔" : "§c✖"));
                    }
                } else {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup status <Map>");
                }
            } else if (args[0].equalsIgnoreCase("setSpawner")) {
                String type = args[1];
                LocationAPI locationAPI = new LocationAPI(player.getWorld().getName());
                if (type.equalsIgnoreCase("Bronze") || type.equalsIgnoreCase("Iron") || type.equalsIgnoreCase("Gold")) {
                    int spawnerCount;
                    try {
                        spawnerCount = locationAPI.getCfg().getInt(type.toLowerCase() + "spawnercount");
                    } catch (Exception e) {
                        locationAPI.getCfg().addDefault(type.toLowerCase() + "spawnercount", 0);
                        spawnerCount = 0;
                        locationAPI.save();
                    }
                    int newCount = spawnerCount + 1;
                    locationAPI.getCfg().set(type.toLowerCase() + "spawnercount", newCount);
                    locationAPI.save();
                    locationAPI.setLocation("spawner." + type.toLowerCase() + "." + newCount, player.getLocation());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Du hast Spawner §e§l" + type.toLowerCase() + " §7mit ID §e§l" + newCount + " §7gesetzt!");
                } else {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDieses Material ist nicht vorhanden, Vorlage §8» §cBronze, §fIron, §6Gold");
                }
            } else if (args[0].equalsIgnoreCase("setBed")) {
                if (args.length == 1) {
                    sendHelp(player);
                } else {
                    for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                        if (team.getName().equalsIgnoreCase(args[1])) {
                            BedWars.getInstance().getSetupBed().put(player.getUniqueId(), args[1]);
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Klicke auf das §e§luntere §7Bett (Rechtsklick)");
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("setLobby")) {
                if (args.length == 2) {
                    new LocationAPI(args[1]).setLocation("lobby", player.getLocation());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Die Location §e§llobby §7wurde gesetzt!");
                } else {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setLobby <Map>");
                }
            } else if (args[0].equalsIgnoreCase("setStatsWall")) {
                if (args.length == 2) {
                    String map = args[1];
                    int statsWall;
                    LocationAPI locationAPI = new LocationAPI(map);
                    try {
                        statsWall = locationAPI.getCfg().getInt("statsWall");
                    } catch (Exception e) {
                        new LocationAPI(map).getCfg().addDefault("statsWall", 0);
                        statsWall = 0;
                        locationAPI.save();
                    }
                    int newStatsWall = statsWall + 1;
                    locationAPI.getCfg().set("statsWall", newStatsWall);
                    locationAPI.save();
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Klicke auf den §e§l" + newStatsWall + " §7Kopf (Rechtsklick)");
                    BedWars.getInstance().getSetupStatsWall().put(player.getUniqueId(), newStatsWall);
                    BedWars.getInstance().getSetupStatsWallMap().put(player.getUniqueId(), map);
                } else {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setStatsWall <Map>");
                }
            } else if (args[0].equalsIgnoreCase("setSpectator")) {
                BedWars.getInstance().getLocationAPI(player.getWorld().getName()).setLocation("spectator", player.getLocation());
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Die Location §e§lspectator §7wurde gesetzt!");
            }
        } else {
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.noPerms"));
        }
        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup status <Map>");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup saveMap");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup list");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setSpawn <Team>");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setBed <Team>");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setSpawner <§cBronze§7/§fIron§7/§6Gold§7>");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setStatsWall <Map>");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setSpectator");
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setLobby <Map>");
    }
}
