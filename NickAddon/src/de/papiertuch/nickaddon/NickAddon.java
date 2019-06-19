package de.papiertuch.nickaddon;

import de.papiertuch.nickaddon.commands.Nick;
import de.papiertuch.nickaddon.listener.PlayerInteractListener;
import de.papiertuch.nickaddon.listener.PlayerJoinListener;
import de.papiertuch.nickaddon.utils.MySQL;
import de.papiertuch.nickaddon.utils.NickConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leon on 17.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class NickAddon extends JavaPlugin {

    private static NickAddon instance;
    private NickConfig nickConfig;
    private MySQL mySQL;
    private ArrayList<UUID> nickPlayers;

    @Override
    public void onEnable() {
        instance = this;
        nickConfig = new NickConfig();
        mySQL = new MySQL();

        nickPlayers = new ArrayList<>();

        nickConfig.loadConfig();
        mySQL.connect();
        if (mySQL.isConnected()) {
            mySQL.createTable();
        }
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);

        getCommand("nick").setExecutor(new Nick());
    }

    @Override
    public void onDisable() {
        if (mySQL.isConnected()) {
            mySQL.disconnect();
        }
    }

    public ArrayList<UUID> getNickPlayers() {
        return nickPlayers;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public NickConfig getNickConfig() {
        return nickConfig;
    }

    public static NickAddon getInstance() {
        return instance;
    }
}
