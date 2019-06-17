package de.papiertuch.bedwars.utils;

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
        configuration.addDefault("builder", "Unbekannt");
        configuration.addDefault("mode", "4x2");
        configuration.addDefault("minPlayers", 2);
        configuration.addDefault("mapName", "Unbekannt");
        configuration.addDefault("bronzeSpawnRate", 1);
        configuration.addDefault("ironSpawnRate", 10);
        configuration.addDefault("goldSpawnRate", 35);

        configuration.addDefault("mysql.host", "localhost");
        configuration.addDefault("mysql.dataBase", "bedWars");
        configuration.addDefault("mysql.user", "root");
        configuration.addDefault("mysql.password", "1234");

        List<String> teams = new ArrayList<>();
        teams.add("Blau");
        teams.add("Rot");
        teams.add("Gr\u00FCn");
        teams.add("Gelb");
        configuration.addDefault("teams", teams);

        configuration.addDefault("Blau.colorCode", "&9");
        configuration.addDefault("Blau.color", "BLUE");

        configuration.addDefault("Rot.colorCode", "&c");
        configuration.addDefault("Rot.color", "RED");

        configuration.addDefault("Gr\u00FCn.colorCode", "&a");
        configuration.addDefault("Gr\u00FCn.color", "LIME");

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

        configuration.addDefault("command.setup.permission", "bedwars.setup");
        configuration.addDefault("command.start.permission", "bedwars.start");
        configuration.addDefault("command.start.seconds", 5);

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

        configuration.addDefault("inventory.shop.title", "&8\u00BB &6Haupt");

        configuration.addDefault("inventory.shop.bricks.name", "&8\u00BB &6Steine");
        configuration.addDefault("inventory.shop.bricks.item", "STAINED_CLAY");
        configuration.addDefault("inventory.shop.armor.name", "&8\u00BB &6R\u00FCstung");
        configuration.addDefault("inventory.shop.armor.item", "CHAINMAIL_CHESTPLATE");
        configuration.addDefault("inventory.shop.tools.name", "&8\u00BB &6Spitzhacken");
        configuration.addDefault("inventory.shop.tools.item", "STONE_PICKAXE");
        configuration.addDefault("inventory.shop.swords.name", "&8\u00BB &6Schwerter");
        configuration.addDefault("inventory.shop.swords.item", "WOOD_SWORD");
        configuration.addDefault("inventory.shop.bows.name", "&8\u00BB &6B\u00F6gen");
        configuration.addDefault("inventory.shop.bows.item", "BOW");
        configuration.addDefault("inventory.shop.food.name", "&8\u00BB &6Essen");
        configuration.addDefault("inventory.shop.food.item", "COOKED_BEEF");
        configuration.addDefault("inventory.shop.chests.name", "&8\u00BB &6Kisten");
        configuration.addDefault("inventory.shop.chests.item", "CHEST");
        configuration.addDefault("inventory.shop.potions.name", "&8\u00BB &6Tr\u00E4nke");
        configuration.addDefault("inventory.shop.potions.item", "GLASS_BOTTLE");
        configuration.addDefault("inventory.shop.specials.name", "&8\u00BB &6Extras");
        configuration.addDefault("inventory.shop.specials.item", "EMERALD");

        configuration.addDefault("inventory.shop.item.bricks.name", "&8\u00BB &aBl\u00F6cke");
        configuration.addDefault("inventory.shop.item.bricks.amount", 2);
        configuration.addDefault("inventory.shop.item.bricks.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.bricks.price.price", 1);
        configuration.addDefault("inventory.shop.item.endStone.name", "&8\u00BB &aEndsteine");
        configuration.addDefault("inventory.shop.item.endStone.amount", 1);
        configuration.addDefault("inventory.shop.item.endStone.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.endStone.price.price", 8);
        configuration.addDefault("inventory.shop.item.ironBlock.name", "&8\u00BB &aEisenblock");
        configuration.addDefault("inventory.shop.item.ironBlock.amount", 1);
        configuration.addDefault("inventory.shop.item.ironBlock.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.ironBlock.price.price", 3);
        configuration.addDefault("inventory.shop.item.glass.name", "&8\u00BB &aGlass");
        configuration.addDefault("inventory.shop.item.glass.amount", 1);
        configuration.addDefault("inventory.shop.item.glass.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.glass.price.price", 4);
        configuration.addDefault("inventory.shop.item.glowStone.name", "&8\u00BB &aLicht");
        configuration.addDefault("inventory.shop.item.glowStone.amount", 1);
        configuration.addDefault("inventory.shop.item.glowStone.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.glowStone.price.price", 4);

        configuration.addDefault("inventory.shop.item.helmet.name", "&8\u00BB &aHelm");
        configuration.addDefault("inventory.shop.item.helmet.amount", 1);
        configuration.addDefault("inventory.shop.item.helmet.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.helmet.price.price", 1);
        configuration.addDefault("inventory.shop.item.leggings.name", "&8\u00BB &aHose");
        configuration.addDefault("inventory.shop.item.leggings.amount", 1);
        configuration.addDefault("inventory.shop.item.leggings.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.leggings.price.price", 1);
        configuration.addDefault("inventory.shop.item.boots.name", "&8\u00BB &aSchuhe");
        configuration.addDefault("inventory.shop.item.boots.amount", 1);
        configuration.addDefault("inventory.shop.item.boots.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.boots.price.price", 1);
        configuration.addDefault("inventory.shop.item.chestPlate1.name", "&8\u00BB &aBrustpanzer I");
        configuration.addDefault("inventory.shop.item.chestPlate1.amount", 1);
        configuration.addDefault("inventory.shop.item.chestPlate1.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.chestPlate1.price.price", 1);
        configuration.addDefault("inventory.shop.item.chestPlate2.name", "&8\u00BB &aBrustpanzer II");
        configuration.addDefault("inventory.shop.item.chestPlate2.amount", 1);
        configuration.addDefault("inventory.shop.item.chestPlate2.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.chestPlate2.price.price", 3);
        configuration.addDefault("inventory.shop.item.chestPlate3.name", "&8\u00BB &aBrustpanzer III");
        configuration.addDefault("inventory.shop.item.chestPlate3.amount", 1);
        configuration.addDefault("inventory.shop.item.chestPlate3.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.chestPlate3.price.price", 5);
        configuration.addDefault("inventory.shop.item.chestPlate4.name", "&8\u00BB &aBrustpanzer IV");
        configuration.addDefault("inventory.shop.item.chestPlate4.amount", 1);
        configuration.addDefault("inventory.shop.item.chestPlate4.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.chestPlate4.price.price", 7);

        configuration.addDefault("inventory.shop.item.woodPickAxe.name", "&8\u00BB &aHolzspitzhacke");
        configuration.addDefault("inventory.shop.item.woodPickAxe.amount", 1);
        configuration.addDefault("inventory.shop.item.woodPickAxe.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.woodPickAxe.price.price", 4);
        configuration.addDefault("inventory.shop.item.stonePickAxe.name", "&8\u00BB &aSteinspitzhacke");
        configuration.addDefault("inventory.shop.item.stonePickAxe.amount", 1);
        configuration.addDefault("inventory.shop.item.stonePickAxe.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.stonePickAxe.price.price", 2);
        configuration.addDefault("inventory.shop.item.ironPickAxe.name", "&8\u00BB &aEisenspitzhacke");
        configuration.addDefault("inventory.shop.item.ironPickAxe.amount", 1);
        configuration.addDefault("inventory.shop.item.ironPickAxe.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.ironPickAxe.price.price", 1);

        configuration.addDefault("inventory.shop.item.stick.name", "&8\u00BB &aStock");
        configuration.addDefault("inventory.shop.item.stick.amount", 1);
        configuration.addDefault("inventory.shop.item.stick.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.stick.price.price", 8);
        configuration.addDefault("inventory.shop.item.sword1.name", "&8\u00BB &aHolzschwert I");
        configuration.addDefault("inventory.shop.item.sword1.amount", 1);
        configuration.addDefault("inventory.shop.item.sword1.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.sword1.price.price", 1);
        configuration.addDefault("inventory.shop.item.sword2.name", "&8\u00BB &aHolzschwert II");
        configuration.addDefault("inventory.shop.item.sword2.amount", 1);
        configuration.addDefault("inventory.shop.item.sword2.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.sword2.price.price", 3);
        configuration.addDefault("inventory.shop.item.sword3.name", "&8\u00BB &aHolzschwert III");
        configuration.addDefault("inventory.shop.item.sword3.amount", 1);
        configuration.addDefault("inventory.shop.item.sword3.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.sword3.price.price", 5);
        configuration.addDefault("inventory.shop.item.sword4.name", "&8\u00BB &aEisenschwert");
        configuration.addDefault("inventory.shop.item.sword4.amount", 1);
        configuration.addDefault("inventory.shop.item.sword4.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.sword4.price.price", 5);

        configuration.addDefault("inventory.shop.item.bow1.name", "&8\u00BB &aBogen I");
        configuration.addDefault("inventory.shop.item.bow1.amount", 1);
        configuration.addDefault("inventory.shop.item.bow1.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.bow1.price.price", 3);
        configuration.addDefault("inventory.shop.item.bow2.name", "&8\u00BB &aBogen II");
        configuration.addDefault("inventory.shop.item.bow2.amount", 1);
        configuration.addDefault("inventory.shop.item.bow2.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.bow2.price.price", 6);
        configuration.addDefault("inventory.shop.item.bow3.name", "&8\u00BB &aBogen III");
        configuration.addDefault("inventory.shop.item.bow3.amount", 1);
        configuration.addDefault("inventory.shop.item.bow3.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.bow3.price.price", 9);
        configuration.addDefault("inventory.shop.item.arrow.name", "&8\u00BB &aPfeil");
        configuration.addDefault("inventory.shop.item.arrow.amount", 8);
        configuration.addDefault("inventory.shop.item.arrow.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.arrow.price.price", 4);

        configuration.addDefault("inventory.shop.item.apple.name", "&8\u00BB &aApfel");
        configuration.addDefault("inventory.shop.item.apple.amount", 1);
        configuration.addDefault("inventory.shop.item.apple.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.apple.price.price", 1);
        configuration.addDefault("inventory.shop.item.beef.name", "&8\u00BB &aFleisch");
        configuration.addDefault("inventory.shop.item.beef.amount", 1);
        configuration.addDefault("inventory.shop.item.beef.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.beef.price.price", 2);
        configuration.addDefault("inventory.shop.item.cake.name", "&8\u00BB &aKuchen");
        configuration.addDefault("inventory.shop.item.cake.amount", 1);
        configuration.addDefault("inventory.shop.item.cake.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.cake.price.price", 1);
        configuration.addDefault("inventory.shop.item.goldenApple.name", "&8\u00BB &aGold Apfel");
        configuration.addDefault("inventory.shop.item.goldenApple.amount", 1);
        configuration.addDefault("inventory.shop.item.goldenApple.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.goldenApple.price.price", 2);

        configuration.addDefault("inventory.shop.item.chest.name", "&8\u00BB &aKiste");
        configuration.addDefault("inventory.shop.item.chest.amount", 1);
        configuration.addDefault("inventory.shop.item.chest.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.chest.price.price", 1);
        configuration.addDefault("inventory.shop.item.endChest.name", "&8\u00BB &aEnderkiste");
        configuration.addDefault("inventory.shop.item.endChest.amount", 1);
        configuration.addDefault("inventory.shop.item.endChest.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.endChest.price.price", 1);

        configuration.addDefault("inventory.shop.item.healing1.name", "&8\u00BB &aHeltrank I");
        configuration.addDefault("inventory.shop.item.healing1.amount", 1);
        configuration.addDefault("inventory.shop.item.healing1.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.healing1.price.price", 3);
        configuration.addDefault("inventory.shop.item.healing2.name", "&8\u00BB &aHeiltrank II");
        configuration.addDefault("inventory.shop.item.healing2.amount", 1);
        configuration.addDefault("inventory.shop.item.healing2.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.healing2.price.price", 6);
        configuration.addDefault("inventory.shop.item.strength.name", "&8\u00BB &aSt\u00E4rketrank");
        configuration.addDefault("inventory.shop.item.strength.amount", 1);
        configuration.addDefault("inventory.shop.item.strength.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.strength.price.price", 3);
        configuration.addDefault("inventory.shop.item.regeneration.name", "&8\u00BB &aRegenerationstrank");
        configuration.addDefault("inventory.shop.item.regeneration.amount", 1);
        configuration.addDefault("inventory.shop.item.regeneration.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.regeneration.price.price", 3);
        configuration.addDefault("inventory.shop.item.speed.name", "&8\u00BB &aSchnelligkeitstrank");
        configuration.addDefault("inventory.shop.item.speed.amount", 1);
        configuration.addDefault("inventory.shop.item.speed.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.speed.price.price", 4);

        configuration.addDefault("inventory.shop.item.ladder.name", "&8\u00BB &aLeiter");
        configuration.addDefault("inventory.shop.item.ladder.amount", 1);
        configuration.addDefault("inventory.shop.item.ladder.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.ladder.price.price", 4);
        configuration.addDefault("inventory.shop.item.web.name", "&8\u00BB &aSpinnennetz");
        configuration.addDefault("inventory.shop.item.web.amount", 1);
        configuration.addDefault("inventory.shop.item.web.price.material", "CLAY_BRICK");
        configuration.addDefault("inventory.shop.item.web.price.price", 16);
        configuration.addDefault("inventory.shop.item.warp.name", "&8\u00BB &aTeleporter");
        configuration.addDefault("inventory.shop.item.warp.amount", 1);
        configuration.addDefault("inventory.shop.item.warp.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.warp.price.price", 5);
        configuration.addDefault("inventory.shop.item.shop.name", "&8\u00BB &aMobiler Shop");
        configuration.addDefault("inventory.shop.item.shop.amount", 1);
        configuration.addDefault("inventory.shop.item.shop.price.material", "IRON_INGOT");
        configuration.addDefault("inventory.shop.item.shop.price.price", 7);
        configuration.addDefault("inventory.shop.item.tnt.name", "&8\u00BB &aTNT");
        configuration.addDefault("inventory.shop.item.tnt.amount", 1);
        configuration.addDefault("inventory.shop.item.tnt.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.tnt.price.price", 3);
        configuration.addDefault("inventory.shop.item.egg.name", "&8\u00BB &aFallschirm");
        configuration.addDefault("inventory.shop.item.egg.amount", 1);
        configuration.addDefault("inventory.shop.item.egg.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.egg.price.price", 3);
        configuration.addDefault("inventory.shop.item.rescue.name", "&8\u00BB &aRettungsplatform");
        configuration.addDefault("inventory.shop.item.rescue.amount", 1);
        configuration.addDefault("inventory.shop.item.rescue.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.rescue.price.price", 4);
        configuration.addDefault("inventory.shop.item.pearl.name", "&8\u00BB &aEnderperle");
        configuration.addDefault("inventory.shop.item.pearl.amount", 1);
        configuration.addDefault("inventory.shop.item.pearl.price.material", "GOLD_INGOT");
        configuration.addDefault("inventory.shop.item.pearl.price.price", 13);

        configuration.addDefault("motd.lobby", "§a§lLOBBY");
        configuration.addDefault("motd.ingame", "§6§lINGAME");
        configuration.addDefault("motd.ending", "§c§lENDING");

        configuration.addDefault("chat.format.spectators", "&8[&4\u2716&8] %player% &8\u00BB &7%message%");
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

        configuration.addDefault("scoreboard.teams.prefix", "&8\u00BB %team%");
        configuration.addDefault("scoreboard.spectator.prefix", "&8\u00BB &7");
        configuration.addDefault("scoreboard.spectator.display", "&7");

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
