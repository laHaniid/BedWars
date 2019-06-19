package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                            BedWars.getInstance().getLocationAPI().setLocation("spawn." + args[1].toLowerCase(), player.getLocation());
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
                String path = BedWars.getInstance().getBedWarsConfig().getString("mapName");
                String target = "plugins/BedWars/mapBackup/" + path;
                BedWars.getInstance().getGameHandler().copyFilesInDirectory(new File(path), new File(target));
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Die aktuelle §e§lMap §7wurde als Backup gespeichert");
            } else if (args[0].equalsIgnoreCase("status")) {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Setup Infos:");
                if (!BedWars.getInstance().getLocationAPI().getFile().exists()) {
                    player.sendMessage("§cEs wurden keine Locations gefunden...");
                    return true;
                }
                player.sendMessage("§8» §f§lMapBackup §8» " + (new File(BedWars.getInstance().getBedWarsConfig().getString("mapName")).exists() ? "§a✔" : "§c✖"));
                player.sendMessage("§8» §f§lLobbySpawn §8» " + (BedWars.getInstance().getLocationAPI().isExists("lobby") ? "§a✔" : "§c✖"));
                player.sendMessage("§8» §f§lSpectatorSpawn §8» " + (BedWars.getInstance().getLocationAPI().isExists("spectator") ? "§a✔" : "§c✖"));
                player.sendMessage("§8» §f§lStatsWand §8» " + (BedWars.getInstance().getLocationAPI().getCfg().getInt("statsWall") == 10 ? "§a✔" : "§c✖"));
                for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                    player.sendMessage("§8» §f§lSpawn von " + team.getColor() + team.getName() + " §8» " + (BedWars.getInstance().getLocationAPI().isExists("spawn." + team.getName().toLowerCase()) ? "§a✔" : "§c✖"));
                    player.sendMessage("§8» §f§lBed von " + team.getColor() + team.getName() + " §8» " + (BedWars.getInstance().getLocationAPI().isExists("bed." + team.getName().toLowerCase()) ? "§a✔" : "§c✖"));
                    player.sendMessage("§8» §f§lBedTop von " + team.getColor() + team.getName() + " §8» " + (BedWars.getInstance().getLocationAPI().isExists("bedtop." + team.getName().toLowerCase()) ? "§a✔" : "§c✖"));
                }
            } else if (args[0].equalsIgnoreCase("setSpawner")) {
                String type = args[1];
                if (type.equalsIgnoreCase("Bronze") || type.equalsIgnoreCase("Iron") || type.equalsIgnoreCase("Gold")) {
                    int spawnerCount;
                    try {
                        spawnerCount = BedWars.getInstance().getLocationAPI().getCfg().getInt(type.toLowerCase() + "spawnercount");
                    } catch (Exception e) {
                        BedWars.getInstance().getLocationAPI().getCfg().addDefault(type.toLowerCase() + "spawnercount", 0);
                        spawnerCount = 0;
                        try {
                            BedWars.getInstance().getLocationAPI().getCfg().save(BedWars.getInstance().getLocationAPI().getFile());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    int newCount = spawnerCount + 1;
                    BedWars.getInstance().getLocationAPI().getCfg().set(type.toLowerCase() + "spawnercount", newCount);
                    try {
                        BedWars.getInstance().getLocationAPI().getCfg().save(BedWars.getInstance().getLocationAPI().getFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    BedWars.getInstance().getLocationAPI().setLocation("spawner." + type.toLowerCase() + "." + newCount, player.getLocation());
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
                BedWars.getInstance().getLocationAPI().setLocation("lobby", player.getLocation());
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Die Location §e§llobby §7wurde gesetzt!");
            } else if (args[0].equalsIgnoreCase("setStatsWall")) {
                int statsWall;
                try {
                    statsWall = BedWars.getInstance().getLocationAPI().getCfg().getInt("statsWall");
                } catch (Exception e) {
                    BedWars.getInstance().getLocationAPI().getCfg().addDefault("statsWall", 0);
                    statsWall = 0;
                    try {
                        BedWars.getInstance().getLocationAPI().getCfg().save(BedWars.getInstance().getLocationAPI().getFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (statsWall == 10) {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §cDu kannst maximal nur 10 Kopfe setzen!");
                    return true;
                }
                int newStatsWall = statsWall + 1;
                BedWars.getInstance().getLocationAPI().getCfg().set("statsWall", newStatsWall);
                try {
                    BedWars.getInstance().getLocationAPI().getCfg().save(BedWars.getInstance().getLocationAPI().getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Klicke auf den §e§l" + newStatsWall + " §7Kopf (Rechtsklick)");
                BedWars.getInstance().getSetupStatsWall().put(player.getUniqueId(), newStatsWall);
            } else if (args[0].equalsIgnoreCase("setSpectator")) {
                BedWars.getInstance().getLocationAPI().setLocation("spectator", player.getLocation());
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7Die Location §e§lspectator §7wurde gesetzt!");
            }
        } else {
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.noPerms"));
        }
        return true;
    }

    private void sendHelp(Player p) {
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup status");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup saveMap");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup list");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setSpawn <Team>");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setBed <Team>");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setSpawner <§cBronze§7/§fIron§7/§6Gold§7>");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setStatsWall");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setSpectator");
        p.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("prefix") + " §7/setup setLobby");
    }
}
