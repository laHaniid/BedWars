package de.papiertuch.bedwars;

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
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<TabListGroup> tabListGroups;
    private Board board;
    private LocationAPI locationAPI;
    private ArrayList<BedWarsTeam> bedWarsTeams;
    private ArrayList<BedWarsTeam> aliveTeams;
    private HashMap<UUID, String> setupBed;
    private HashMap<UUID, String> setupBedTop;
    private ArrayList<UUID> spectators;
    private BedWarsConfig bedWarsConfig;
    private ArrayList<Location> blocks;
    private StatsHandler statsHandler;
    private HashMap<String, Color> colors;
    private HashMap<UUID, Integer> setupStatsWall;
    private ArrayList<UUID> players;
    private HashMap<Player, Player> lastHit;
    private ArrayList<Player> death;
    private ArrayList<Location> statsWall;
    private boolean boarder;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        instance = this;
        players = new ArrayList<>();
        death = new ArrayList<>();
        mySQL = new MySQL();
        setupStatsWall = new HashMap<>();
        blocks = new ArrayList<>();
        statsHandler = new StatsHandler();
        scheduler = new Scheduler();
        gameHandler = new GameHandler();
        aliveTeams = new ArrayList<>();
        tabListGroups = new ArrayList<>();
        lastHit = new HashMap<>();
        statsWall = new ArrayList<>();
        colors = new HashMap<>();
        spectators = new ArrayList<>();
        setupBedTop = new HashMap<>();
        setupBed = new HashMap<>();
        board = new Board();
        locationAPI = new LocationAPI();
        bedWarsTeams = new ArrayList<>();
        boarder = false;
        bedWarsConfig = new BedWarsConfig();
        bedWarsConfig.loadConfig();
        Bukkit.createWorld(WorldCreator.name(getBedWarsConfig().getString("mapName")).type(WorldType.FLAT).generatorSettings("3;minecraft:air;2").generateStructures(false));
        setGameState(GameState.LOBBY);
        register();
        loadGame();
        checkLocations();
        mySQL.connect();
        if (mySQL.isConnected()) {
            new StatsAPI().createTable("bedwars");
            new StatsAPI().setStatsWall("bedwars", statsWall);
        }
    }

    @Override
    public void onDisable() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            a.kickPlayer("§cRestart");
        }
        for (Location location : BedWars.getInstance().getBlocks()) {
            Bukkit.getWorld(BedWars.getInstance().getBedWarsConfig().getString("mapName")).getBlockAt(location).setType(Material.AIR);
        }
        if (getMySQL().isConnected()) {
            getMySQL().disconnect();
        }
    }

    private void checkLocations() {
        getServer().getConsoleSender().sendMessage("§8[§6§lBedWars§8] §7Setup Infos:");
        if (!getLocationAPI().getFile().exists()) {
            getServer().getConsoleSender().sendMessage("§cEs wurden keine Locations gefunden...");
            return;
        }
        getServer().getConsoleSender().sendMessage("§8- §7LobbySpawn§8: " + (getLocationAPI().isExists("lobby") ? "§aGesetzt" : "§cFehlt"));
        getServer().getConsoleSender().sendMessage("§8- §7SpectatorSpawn§8: " + (getLocationAPI().isExists("spectator") ? "§aGesetzt" : "§cFehlt"));
        getServer().getConsoleSender().sendMessage("§8- §7StatsWand§8: " + (BedWars.getInstance().getLocationAPI().getCfg().getInt("statsWall") == 10 ? "§aGesetzt" : "§cFehlt"));
        for (BedWarsTeam team : getBedWarsTeams()) {
            getServer().getConsoleSender().sendMessage("§8- §7Spawn von " + team.getColor() + team.getName() + "§8: " + (getLocationAPI().isExists("spawn." + team.getName().toLowerCase()) ? "§aGesetzt" : "§cFehlt"));
            getServer().getConsoleSender().sendMessage("§8- §7Bed von " + team.getColor() + team.getName() + "§8: " + (getLocationAPI().isExists("bed." + team.getName().toLowerCase()) ? "§aGesetzt" : "§cFehlt"));
            getServer().getConsoleSender().sendMessage("§8- §7BedTop von " + team.getColor() + team.getName() + "§8: " + (getLocationAPI().isExists("bedtop." + team.getName().toLowerCase()) ? "§aGesetzt" : "§cFehlt"));
        }

        if (BedWars.getInstance().getLocationAPI().getCfg().getInt("statsWall") == 10) {
            for (int i = 1; i < 11; i++) {
                statsWall.add(getLocationAPI().getLocation("statsSkull." + i));
            }
        }
    }

    private void loadGame() {
        ShopHandler.loadHashMaps();
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
        //TODO CloudNet Server Signs

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

    public LocationAPI getLocationAPI() {
        return locationAPI;
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
        MinecraftServer.getServer().setMotd(getBedWarsConfig().getString("motd." + gameState.toString().toLowerCase()));
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
