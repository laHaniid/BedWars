package de.papiertuch.bedwars;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerState;
import de.papiertuch.bedwars.commands.ForceMap;
import de.papiertuch.bedwars.commands.Setup;
import de.papiertuch.bedwars.commands.Start;
import de.papiertuch.bedwars.commands.Stats;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.game.Scheduler;
import de.papiertuch.bedwars.listener.*;
import de.papiertuch.bedwars.stats.MySQL;
import de.papiertuch.bedwars.stats.StatsAPI;
import de.papiertuch.bedwars.stats.StatsHandler;
import de.papiertuch.bedwars.utils.*;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class BedWars extends JavaPlugin {

    private static BedWars instance;
    private Scheduler scheduler;
    private GameHandler gameHandler;
    private GameState gameState;
    private Board board;
    private BedWarsConfig bedWarsConfig;
    private StatsHandler statsHandler;
    private MySQL mySQL;
    private ShopHandler shopHandler;

    private ArrayList<TabListGroup> tabListGroups;
    private ArrayList<BedWarsTeam> bedWarsTeams;
    private ArrayList<BedWarsTeam> aliveTeams;
    private ArrayList<UUID> spectators;
    private ArrayList<UUID> players;
    private ArrayList<UUID> withGold;
    private ArrayList<UUID> noGold;
    private ArrayList<Player> death;
    private ArrayList<Location> statsWall;
    private ArrayList<Location> blocks;

    private HashMap<UUID, String> setupBed;
    private HashMap<UUID, String> setupBedTop;
    private HashMap<UUID, Integer> setupStatsWall;
    private HashMap<UUID, String> setupStatsWallMap;
    private HashMap<String, Color> colors;
    private HashMap<Color, Integer> colorIds;
    private HashMap<Player, Player> lastHit;
    private HashMap<BedWarsTeam, Inventory> teamChest;
    private HashMap<String, ArrayList<UUID>> mapVotes;

    private boolean boarder;
    private boolean gold;
    private boolean nickEnable;
    private boolean forceMap;

    private String map;


    @Override
    public void onEnable() {
        instance = this;
        statsHandler = new StatsHandler();
        mySQL = new MySQL();
        shopHandler = new ShopHandler();
        gameHandler = new GameHandler();
        bedWarsConfig = new BedWarsConfig();
        scheduler = new Scheduler();
        board = new Board();

        players = new ArrayList<>();
        death = new ArrayList<>();
        aliveTeams = new ArrayList<>();
        tabListGroups = new ArrayList<>();
        blocks = new ArrayList<>();
        statsWall = new ArrayList<>();
        bedWarsTeams = new ArrayList<>();
        spectators = new ArrayList<>();
        withGold = new ArrayList<>();
        noGold = new ArrayList<>();

        setupStatsWall = new HashMap<>();
        setupStatsWallMap = new HashMap<>();
        teamChest = new HashMap<>();
        lastHit = new HashMap<>();
        colorIds = new HashMap<>();
        colors = new HashMap<>();
        setupBedTop = new HashMap<>();
        mapVotes = new HashMap<>();
        setupBed = new HashMap<>();

        boarder = false;
        gold = true;
        nickEnable = false;
        forceMap = false;
        map = "Unbekannt";

        File file = new File("plugins/BedWars/mapBackup");
        if (file.exists()) {
            int random = new Random().nextInt(file.listFiles().length);
            map = file.listFiles()[random].getName();
        }
        try {
            for (File map : file.listFiles()) {
                getMapVotes().put(map.getName(), new ArrayList<>());
            }
        } catch (NullPointerException e) {

        }
        bedWarsConfig.loadConfig();
        setGameState(GameState.LOBBY);
        register();
        loadGame();
        checkLocations();
        mySQL.connect();
        if (mySQL.isConnected()) {
            mySQL.createTable();
            new StatsAPI().setStatsWall(statsWall);
        }

        if (getServer().getPluginManager().getPlugin("NickAddon") != null) {
            nickEnable = true;
        }
        if (getServer().getPluginManager().getPlugin("Multiverse-Core") != null) {
            getServer().getPluginManager().disablePlugin(getServer().getPluginManager().getPlugin("Multiverse-Core"));
        }
    }

    @Override
    public void onDisable() {
        if (getMySQL().isConnected()) {
            getMySQL().disconnect();
        }
        for (Player a : Bukkit.getOnlinePlayers()) {
            a.kickPlayer(getBedWarsConfig().getString("prefix") + " §cDer Server startet jetzt neu...");
        }
    }

    private void checkLocations() {
        getServer().getConsoleSender().sendMessage("§8[§6§lBedWars§8] §7Setup Infos:");
        if (!new LocationAPI(getMap()).getFile().exists()) {
            getServer().getConsoleSender().sendMessage("§cEs wurden keine Locations gefunden...");
            return;
        }

        getServer().getConsoleSender().sendMessage("§8- §7MapBackup§8: " + (new File("plugins/BedWars/mapBackup/" + getMap()).exists() ? "§aGesetzt" : "§cFehlt"));
        getServer().getConsoleSender().sendMessage("§8- §7LobbySpawn§8: " + (getLocationAPI(getMap()).isExists("lobby") ? "§aGesetzt" : "§cFehlt"));
        getServer().getConsoleSender().sendMessage("§8- §7SpectatorSpawn§8: " + (getLocationAPI(getMap()).isExists("spectator") ? "§aGesetzt" : "§cFehlt"));
        getServer().getConsoleSender().sendMessage("§8- §7StatsWand§8: " + (BedWars.getInstance().getLocationAPI(getMap()).getCfg().get("statsWall") != null ? "§aGesetzt" : "§cFehlt"));
        for (BedWarsTeam team : getBedWarsTeams()) {
            getServer().getConsoleSender().sendMessage("§8- §7Spawn von " + team.getColor() + team.getName() + "§8: " + (getLocationAPI(getMap()).isExists("spawn." + team.getName().toLowerCase()) ? "§aGesetzt" : "§cFehlt"));
            getServer().getConsoleSender().sendMessage("§8- §7Bed von " + team.getColor() + team.getName() + "§8: " + (getLocationAPI(getMap()).isExists("bed." + team.getName().toLowerCase()) ? "§aGesetzt" : "§cFehlt"));
            getServer().getConsoleSender().sendMessage("§8- §7BedTop von " + team.getColor() + team.getName() + "§8: " + (getLocationAPI(getMap()).isExists("bedtop." + team.getName().toLowerCase()) ? "§aGesetzt" : "§cFehlt"));
        }

        for (int i = 1; i < BedWars.getInstance().getLocationAPI(getMap()).getCfg().getInt("statsWall") + 1; i++) {
            statsWall.add(getLocationAPI(getMap()).getLocation("statsSkull." + i));
            }
    }

    private void loadGame() {
        colors.put("AQUA", Color.AQUA);
        colors.put("BLACK", Color.BLACK);
        colors.put("BLUE", Color.BLUE);
        colors.put("FUCHSIA", Color.FUCHSIA);
        colors.put("GRAY", Color.GRAY);
        colors.put("GREEN", Color.GREEN);
        colors.put("LIME", Color.LIME);
        colors.put("MAROON", Color.MAROON);
        colors.put("NAVY", Color.NAVY);
        colors.put("OLIVE", Color.OLIVE);
        colors.put("ORANGE", Color.ORANGE);
        colors.put("PURPLE", Color.PURPLE);
        colors.put("RED", Color.RED);
        colors.put("SILVER", Color.SILVER);
        colors.put("TEAL", Color.TEAL);
        colors.put("WHITE", Color.WHITE);
        colors.put("YELLOW", Color.YELLOW);

        colorIds.put(Color.AQUA, 9);
        colorIds.put(Color.BLACK, 15);
        colorIds.put(Color.BLUE, 11);
        colorIds.put(Color.FUCHSIA, 6);
        colorIds.put(Color.GRAY, 7);
        colorIds.put(Color.GREEN, 13);
        colorIds.put(Color.LIME, 5);
        colorIds.put(Color.ORANGE, 1);
        colorIds.put(Color.PURPLE, 10);
        colorIds.put(Color.RED, 14);
        colorIds.put(Color.SILVER, 8);
        colorIds.put(Color.WHITE, 0);
        colorIds.put(Color.YELLOW, 4);


        for (String team : getBedWarsConfig().getConfiguration().getStringList("teams")) {
            bedWarsTeams.add(
                    new BedWarsTeam(team,
                            bedWarsConfig.getString(team + ".colorCode"),
                            getGameHandler().getColorFromString(bedWarsConfig.getString(team + ".color")),
                            Integer.valueOf(bedWarsConfig.getString("mode").split("x")[1]),
                            new ArrayList<>()));
        }

        for (String tabList : getBedWarsConfig().getConfiguration().getStringList("tabList")) {
            tabListGroups.add(
                    new TabListGroup(tabList,
                            bedWarsConfig.getString(tabList + ".prefix"),
                            bedWarsConfig.getString(tabList + ".suffix"),
                            bedWarsConfig.getString(tabList + ".display"),
                            bedWarsConfig.getInt(tabList + ".tagId"),
                            bedWarsConfig.getString(tabList + ".permission")));
        }
        getServer().getConsoleSender().sendMessage("§8[§6§lBedWars§8] §7Geladene Teams:");
        for (BedWarsTeam team : getBedWarsTeams()) {
            getServer().getConsoleSender().sendMessage(team.getColor() + team.getName());
            getAliveTeams().add(team);
        }
        getServer().getConsoleSender().sendMessage("§8[§6§lBedWars§8] §7Geladene Prefixe:");
        for (TabListGroup tabListGroup : getTabListGroups()) {
            getServer().getConsoleSender().sendMessage(tabListGroup.getDisplay() + tabListGroup.getName());
        }

    }

    private void register() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerRespawnListener(), this);
        pluginManager.registerEvents(new ProtectionListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityExplodeListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new ShopClickListener(), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerInteractAtEntityListener(), this);

        getCommand("start").setExecutor(new Start());
        getCommand("setup").setExecutor(new Setup());
        getCommand("stats").setExecutor(new Stats());
        getCommand("forcemap").setExecutor(new ForceMap());
    }

    public HashMap<String, ArrayList<UUID>> getMapVotes() {
        return mapVotes;
    }

    public boolean isForceMap() {
        return forceMap;
    }

    public void setForceMap(boolean forceMap) {
        this.forceMap = forceMap;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }

    public boolean isNickEnable() {
        return nickEnable;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public HashMap<UUID, String> getSetupStatsWallMap() {
        return setupStatsWallMap;
    }

    public boolean isGold() {
        return gold;
    }

    public ArrayList<UUID> getNoGold() {
        return noGold;
    }

    public ArrayList<UUID> getWithGold() {
        return withGold;
    }

    public HashMap<Color, Integer> getColorIds() {
        return colorIds;
    }

    public ShopHandler getShopHandler() {
        return shopHandler;
    }

    public HashMap<BedWarsTeam, Inventory> getTeamChest() {
        return teamChest;
    }

    public HashMap<UUID, Integer> getSetupStatsWall() {
        return setupStatsWall;
    }

    public ArrayList<Location> getStatsWall() {
        return statsWall;
    }

    public StatsHandler getStatsHandler() {
        return statsHandler;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public ArrayList<Player> getDeath() {
        return death;
    }

    public ArrayList<TabListGroup> getTabListGroups() {
        return tabListGroups;
    }

    public ArrayList<Location> getBlocks() {
        return blocks;
    }

    public HashMap<UUID, String> getSetupBedTop() {
        return setupBedTop;
    }

    public LocationAPI getLocationAPI(String map) {
        return new LocationAPI(map);
    }

    public GameState getGameState() {
        return gameState;
    }

    public HashMap<String, Color> getColors() {
        return colors;
    }

    public HashMap<Player, Player> getLastHit() {
        return lastHit;
    }

    public HashMap<UUID, String> getSetupBed() {
        return setupBed;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<BedWarsTeam> getAliveTeams() {
        return aliveTeams;
    }

    public ArrayList<UUID> getSpectators() {
        return spectators;
    }

    public boolean isBoarder() {
        return boarder;
    }

    public void setBoarder(boolean boarder) {
        this.boarder = boarder;
    }

    public BedWarsConfig getBedWarsConfig() {
        return bedWarsConfig;
    }

    public void setGameState(GameState gameState) {
        if (getBedWarsConfig().getBoolean("cloudnet")) {
            if (gameState == GameState.LOBBY) {
                CloudServer cloudServer = CloudServer.getInstance();
                cloudServer.setMaxPlayers(getGameHandler().getMaxPlayers());
                cloudServer.setMotd(getMap());
                cloudServer.setServerState(ServerState.LOBBY);
                cloudServer.update();
            }
            if (gameState == GameState.INGAME) {
                CloudServer cloudServer = CloudServer.getInstance();
                cloudServer.setMaxPlayers(getGameHandler().getMaxPlayers() + 10);
                cloudServer.setMotd(getMap());
                cloudServer.setServerState(ServerState.INGAME);
                cloudServer.update();
            }
            if (gameState == GameState.ENDING) {
                CloudServer cloudServer = CloudServer.getInstance();
                cloudServer.setMaxPlayers(getGameHandler().getMaxPlayers());
                cloudServer.setMotd(getMap());
                cloudServer.setServerState(ServerState.OFFLINE);
                cloudServer.update();
            }
        } else {
            MinecraftServer.getServer().setMotd(getBedWarsConfig().getString("motd." + gameState.toString().toLowerCase()));
        }
        this.gameState = gameState;
    }

    public ArrayList<BedWarsTeam> getBedWarsTeams() {
        return bedWarsTeams;
    }

    public static BedWars getInstance() {
        return instance;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
