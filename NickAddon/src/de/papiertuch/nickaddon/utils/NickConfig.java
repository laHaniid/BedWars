package de.papiertuch.nickaddon.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 17.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class NickConfig {

    private static File file = new File("plugins/NickAddon", "config.yml");
    private static FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    private static HashMap<String, String> cacheString = new HashMap<>();
    private static HashMap<String, Boolean> cacheBoolean = new HashMap<>();
    private static HashMap<String, Integer> cacheInt = new HashMap<>();

    public void loadConfig() {
        configuration.options().copyDefaults(true);

        configuration.addDefault("lobbyMode", true);
        configuration.addDefault("prefix", "&8[&5&lNick&8]&7");

        configuration.addDefault("mysql.host", "localhost");
        configuration.addDefault("mysql.dataBase", "nick");
        configuration.addDefault("mysql.user", "root");
        configuration.addDefault("mysql.password", "1234");

        configuration.addDefault("item.nick.name", "&5Nick &8\u00BB &7Rechtsklick");
        configuration.addDefault("item.nick.material", "NAME_TAG");
        configuration.addDefault("item.nick.slot", 4);

        configuration.addDefault("command.nick.permission", "bedwars.nick");

        configuration.addDefault("message.autoNickEnable", "%prefix% &7Dein &e&lAutoNick &7ist nun &a&laktiviert");
        configuration.addDefault("message.autoNickDisable", "%prefix% &7Dein &e&lAutoNick &7ist nun &c&ldeaktiviert");
        configuration.addDefault("message.nick", "%prefix% &7Du spielst nun als &e&l%nick%");
        configuration.addDefault("message.unNick", "%prefix% &cDein Nickname wurde entfernt");
        configuration.addDefault("message.noPerms", "%prefix% &cDazu hast du keine Rechte...");

        configuration.addDefault("chat.format", "%player% &8\u00BB &7%message%");

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



        List<String> nicks = new ArrayList<>();
        nicks.add("Test");
        nicks.add("Server");
        configuration.addDefault("nicks", nicks);
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
