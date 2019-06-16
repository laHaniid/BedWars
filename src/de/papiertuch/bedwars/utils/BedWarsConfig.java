package de.papiertuch.bedwars.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class BedWarsConfig {

    private static File file = new File("plugins/BedWars", "config.yml");
    private static FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    private static HashMap<String, String> cacheString = new HashMap<>();
    private static HashMap<String, Boolean> cacheBoolean = new HashMap<>();
    private static HashMap<String, Integer> cacheInt = new HashMap<>();

    public void loadConfig() {
        configuration.options().copyDefaults(true);

        configuration.addDefault("prefix", "&8[&6&lBedWars&8]&7");
        configuration.addDefault("builder", "Internet");
        configuration.addDefault("mode", "4x2");
        configuration.addDefault("minPlayers", 2);
        configuration.addDefault("mapName", "Palast");

        configuration.addDefault("mysql.host", "localhost");
        configuration.addDefault("mysql.dataBase", "bedWars");
        configuration.addDefault("mysql.user", "root");
        configuration.addDefault("mysql.password", "1234");

        configuration.addDefault("item.team.material", "ARMOR_STAND");
        configuration.addDefault("item.team.name", "&6Teams &8\u00BB &7Rechtsklick");
        configuration.addDefault("item.team.slot", 0);
        configuration.addDefault("item.leave.material", "BARRIER");
        configuration.addDefault("item.leave.name", "&cZur Lobby &8\u00BB &7Rechtsklick");
        configuration.addDefault("item.leave.slot", 8);
        configuration.addDefault("item.compass.material", "COMPASS");
        configuration.addDefault("item.compass.name", "&aSpieler &8\u00BB &7Rechtsklick");
        configuration.addDefault("item.compass.slot", 4);
        configuration.addDefault("item.start.material", "PAPER");
        configuration.addDefault("item.start.name", "&bSpiel starten &8\u00BB &7Rechtsklick");
        configuration.addDefault("item.start.slot", 4);

        configuration.addDefault("message.gameStarting", "%prefix% &aDas Spiel wird gestartet...");
        configuration.addDefault("message.gameStartingIn", "%prefix% &7Das &a&lSpiel &7startet in &a&l%seconds% &7Sekunden");
        configuration.addDefault("message.gameStartingInOneSecond", "%prefix% &7Das &a&lSpiel &7startet in &a&leiner &7Sekunde");
        configuration.addDefault("message.builder", "%prefix% &7Erbaut von&8: &e&l%builder%");
        configuration.addDefault("message.yourInTeam", "%prefix% &7Du bist nun im Team %team%");
        configuration.addDefault("message.waiting", "%prefix% &7Warte auf &c&l%players%&7...");
        configuration.addDefault("message.teamWin", " %prefix% &7Das Team %team% &7hat gewonnen!");
        configuration.addDefault("message.boarderIn", "%prefix% &7Die &cBoarder &7verkleinert sich in &c&l%minutes% &7Minuten");
        configuration.addDefault("message.boarderInOneMinute", "%prefix% &7Die &cBoarder &7verkleinert sich in &c&leiner Minute");
        configuration.addDefault("message.boarder", "%prefix% &7Die &cBoarder &7verkleinert sich nun!");
        configuration.addDefault("message.roundStarting", "%prefix% &7Die Runde wurde gestartet");
        configuration.addDefault("message.destroyAllBeds", "%prefix% &cEs wurden &4&lAlle &cBetten entfernt");
        configuration.addDefault("message.smallBoarder", "%prefix% &cDie Boarder ist nun ganz klein!");
        configuration.addDefault("message.roundEnds", "%prefix% &cDie Runde ist zuende");
        configuration.addDefault("message.serverStopIn", "%prefix% &cDer Server stoppt in &a&l%seconds% &cSekunden");
        configuration.addDefault("message.serverStopInOneSecond", "%prefix% &cDer Server stoppt in &a&leiner &cSekunde");
        configuration.addDefault("message.serverStop", "%prefix% &cDer Server stoppt jetzt!");
        configuration.addDefault("message.startRound", "%prefix% &aDu hast die Runde gestartet");
        configuration.addDefault("message.countDownUnderSeconds", "%prefix% &cDer Countdown ist bereits unter &e&l%seconds% &cSekunden!");
        configuration.addDefault("message.notEnoughPlayers", "%prefix% &cEs sind ncht genug Spieler online!");
        configuration.addDefault("message.roundAlreadyStarting", "%prefix% &cDie Runde startet bereits...");
        configuration.addDefault("message.noPerms", "%prefix% &cDazu hast du keine Rechte...");
        configuration.addDefault("message.destroyOwnBed", "%prefix% &cDu kannst dein eigenes Bett nicht abbauen!");
        configuration.addDefault("message.destroyBed", "%prefix% &7Das Bett vom Team %team% &7wurde von %player% &7abgebaut!");
        configuration.addDefault("message.teamFull", "%prefix% &cDieses Team ist bereits voll!");
        configuration.addDefault("message.otherTeamsEmpty", "%prefix% &cNehme ein anderes Team da diese leer sind");
        configuration.addDefault("message.choseTeam", "%prefix% &7Du wirst nun im Team %team% &7sein");
        configuration.addDefault("message.teleportToPlayer", "%prefix% &7Du bist nun bei %player%");
        configuration.addDefault("message.killMessage", "%prefix% %player% &7wurde von %killer% &7get\u00F6tet");
        configuration.addDefault("message.killerLife", "%prefix% &7Leben von %killer% &8\u00BB &e&l%live%");
        configuration.addDefault("message.death", "%prefix% %player% &cist gestorben");
        configuration.addDefault("message.teamDeath", "%prefix% &7Das Team %team% &7ist ausgeschieden");
        configuration.addDefault("message.teamReamingPlayers", "%prefix% &7Das Team %team% &7hat noch &e&l%players% Spieler");
        configuration.addDefault("message.joinGame", "%prefix% %player% &7hat das Spiel betreten &8\u00BB &e&l%players%&8/&e&l%maxPlayers%");
        configuration.addDefault("message.spectator", "%prefix% &7Du bist ist ein Spectator");
        configuration.addDefault("message.leaveGame", "%prefix% %player% &7hat das Spiel verlassen");
        configuration.addDefault("message.stats", "%prefix% &7Stats von %player%");

        configuration.addDefault("motd.lobby", "§a§lLOBBY");
        configuration.addDefault("motd.ingame", "§6§lINGAME");
        configuration.addDefault("motd.ending", "§c§lENDING");

        configuration.addDefault("chat.format.spectators", "&7[Spec] %player% &8\u00BB &7%message%");
        configuration.addDefault("chat.format.normal", "%player% &8\u00BB &7%message%");
        configuration.addDefault("chat.format.team", "%player% &8\u00BB &7%message%");
        configuration.addDefault("chat.format.all", "&8[&f&lGlobal&8] %player% &8\u00BB&7%message%");

        configuration.addDefault("scoreboard.title", "%prefix% &f%time%");

        configuration.addDefault("scoreboard.line1.title", "&f&lSpieler:");
        configuration.addDefault("scoreboard.line1.input", " &8\u00BB &e&l%players%");

        configuration.addDefault("scoreboard.line2.title", "&f&lModus:");
        configuration.addDefault("scoreboard.line2.input", " &8\u00BB &e&l%mode%");

        configuration.addDefault("scoreboard.line3.title", "&f&lKarte:");
        configuration.addDefault("scoreboard.line3.input", " &8\u00BB &e&l%map%");

        configuration.addDefault("scoreboard.line4.title", "&f&lTeam:");
        configuration.addDefault("scoreboard.line4.input", " &8\u00BB %team%");

        configuration.addDefault("scoreboard.line5.title", "&f&lTeams:");

        configuration.addDefault("scoreboard.line.teamHasBed.one", " &8\u00BB &2\u2714 %team%");
        configuration.addDefault("scoreboard.line.teamHasBed.two", " &8\u2503 &f%players%");
        configuration.addDefault("scoreboard.line.teamHasNoBed.one", " &8\u00BB &4\u2716 %team%");
        configuration.addDefault("scoreboard.line.teamHasNoBed.two", " &8\u2503 &f%players%");
        configuration.addDefault("scoreboard.line.teamDeath.one", " &r&8\u00BB &4\u2716 %team%");
        configuration.addDefault("scoreboard.line.teamDeath.two", "&m%team%&r");

        configuration.addDefault("scoreboard.teams.prefix", "&8\u2716 %team%");
        configuration.addDefault("scoreboard.spectator.prefix", "&4\u2716 &f");
        configuration.addDefault("scoreboard.spectator.display", "&f");


        configuration.addDefault("command.setup.permission", "bedwars.setup");
        configuration.addDefault("command.start.permission", "bedwars.start");
        configuration.addDefault("command.start.seconds", 5);

        List<String> teams = new ArrayList<>();
        teams.add("Blau");
        teams.add("Rot");
        teams.add("Lila");
        teams.add("Gelb");
        configuration.addDefault("teams", teams);

        configuration.addDefault("Blau.colorCode", "&9");
        configuration.addDefault("Blau.color", "BLUE");

        configuration.addDefault("Rot.colorCode", "&c");
        configuration.addDefault("Rot.color", "RED");

        configuration.addDefault("Lila.colorCode", "&5");
        configuration.addDefault("Lila.color", "PURPLE");

        configuration.addDefault("Gelb.colorCode", "&e");
        configuration.addDefault("Gelb.color", "YELLOW");

        List<String> tabList = new ArrayList<>();
        tabList.add("Admin");
        tabList.add("Spieler");
        configuration.addDefault("tabList", tabList);

        configuration.addDefault("Admin.prefix", "&c&lAdmin &8\u258E &f");
        configuration.addDefault("Admin.suffix", "&c");
        configuration.addDefault("Admin.display", "&c");
        configuration.addDefault("Admin.tagId", 9998);
        configuration.addDefault("Admin.permission", "bedwars.admin");

        configuration.addDefault("Spieler.prefix", "&f");
        configuration.addDefault("Spieler.suffix", "&f");
        configuration.addDefault("Spieler.display", "&f");
        configuration.addDefault("Spieler.tagId", 9999);
        configuration.addDefault("Spieler.permission", "bedwars.Spieler");

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public Integer getInt(String string) {
        if (!cacheInt.containsKey(string)) {
            cacheInt.put(string, configuration.getInt(string));
        }
        return cacheInt.get(string);
    }

    public Boolean getBoolean(String string) {
        if (!cacheBoolean.containsKey(string)) {
            cacheBoolean.put(string, configuration.getBoolean(string));
        }
        return cacheBoolean.get(string);
    }

    public String getString(String string) {
        if (!cacheString.containsKey(string)) {
            cacheString.put(string, configuration.getString(string));
        }
        return cacheString.get(string)
                .replace("&", "§")
                .replace("%prefix%", configuration.getString("prefix").replace("&", "§"));
    }
}
