package de.papiertuch.nickaddon;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.nickaddon.commands.Nick;
import de.papiertuch.nickaddon.listener.AsyncPlayerChatListener;
import de.papiertuch.nickaddon.listener.PlayerInteractListener;
import de.papiertuch.nickaddon.listener.PlayerJoinListener;
import de.papiertuch.nickaddon.utils.MySQL;
import de.papiertuch.nickaddon.utils.NickConfig;
import de.papiertuch.nickaddon.utils.TabListGroup;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
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
    private List<TabListGroup> tabListGroups;
    private ArrayList<UUID> nickPlayers;

    @Override
    public void onEnable() {
        instance = this;
        nickConfig = new NickConfig();
        mySQL = new MySQL();

        nickPlayers = new ArrayList<>();
        tabListGroups = new ArrayList<>();

        nickConfig.loadConfig();
        mySQL.connect();
        if (mySQL.isConnected()) {
            mySQL.createTable();
        }
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        if (nickConfig.getBoolean("lobbyMode")) {
            getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        }

        getCommand("nick").setExecutor(new Nick());

        for (String tabList : getNickConfig().getConfiguration().getStringList("tabList")) {
            tabListGroups.add(
                    new TabListGroup(tabList,
                            nickConfig.getString(tabList + ".prefix"),
                            nickConfig.getString(tabList + ".suffix"),
                            nickConfig.getString(tabList + ".display"),
                            nickConfig.getInt(tabList + ".tagId"),
                            nickConfig.getString(tabList + ".permission")));
        }
    }

    public void updateNameTags(Player player) {
        TabListGroup playerPermissionGroup = getTabListGroup(player);

        initScoreboard(player);
        for (Player all : player.getServer().getOnlinePlayers()) {
            initScoreboard(all);
            if (playerPermissionGroup != null)
                if (NickAddon.getInstance().getNickPlayers().contains(player.getUniqueId())) {
                    addTeamEntry(player, all, getTabListGroups().get(getTabListGroups().size() - 1));
                } else {

                    addTeamEntry(player, all, playerPermissionGroup);
                }

            TabListGroup targetPermissionGroup = getTabListGroup(all);

            if (targetPermissionGroup != null)
                addTeamEntry(all, player, targetPermissionGroup);
        }
    }

    private TabListGroup getTabListGroup(Player player) {
        for (TabListGroup tabListGroup : getTabListGroups()) {
            if (player.hasPermission(tabListGroup.getPermission())) {
                return tabListGroup;
            }
        }
        return getTabListGroups().get(getTabListGroups().size() - 1);
    }

    private void addTeamEntry(Player target, Player all, TabListGroup permissionGroup) {
        Team team;
        if (NickAddon.getInstance().getNickPlayers().contains(target.getUniqueId())) {
            TabListGroup tabListGroup = getTabListGroups().get(getTabListGroups().size() - 1);
            team = all.getScoreboard().getTeam(tabListGroup.getTagId() + tabListGroup.getName());
        } else {
            team = all.getScoreboard().getTeam(permissionGroup.getTagId() + permissionGroup.getName());
        }
        if (team == null)
            team = all.getScoreboard().registerNewTeam(permissionGroup.getTagId() + permissionGroup.getName());
        if (NickAddon.getInstance().getNickPlayers().contains(target.getUniqueId())) {
            TabListGroup tabListGroup = getTabListGroups().get(getTabListGroups().size() - 1);
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', tabListGroup.getPrefix()));
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', tabListGroup.getSuffix()));
            team.addEntry(target.getName());
            target.setDisplayName(ChatColor.translateAlternateColorCodes('&', tabListGroup.getDisplay()) + target.getName());
        } else {
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', permissionGroup.getPrefix()));
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', permissionGroup.getSuffix()));
            team.addEntry(target.getName());
            target.setDisplayName(ChatColor.translateAlternateColorCodes('&', permissionGroup.getDisplay()) + target.getName());
        }
    }

    private void initScoreboard(Player all) {
        if (all.getScoreboard() == null)
            all.setScoreboard(all.getServer().getScoreboardManager().getNewScoreboard());
    }

    @Override
    public void onDisable() {
        if (mySQL.isConnected()) {
            mySQL.disconnect();
        }
    }

    public List<TabListGroup> getTabListGroups() {
        return tabListGroups;
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
