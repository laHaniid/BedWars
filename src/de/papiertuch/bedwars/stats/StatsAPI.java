package de.papiertuch.bedwars.stats;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Location;
import org.bukkit.SkullType;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class StatsAPI {

    private Player player;
    private UUID uuid;
    private String name;

    public StatsAPI(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public StatsAPI() {

    }

    public void createTable(String table) {
        BedWars.getInstance().getMySQL().update("CREATE TABLE IF NOT EXISTS " + table.toLowerCase() + " (UUID VARCHAR(100), NAME VARCHAR(100), TYPE TEXT, VALUE INT)");

    }

    public Integer getRankingFromUUID(String table, String type) {
        boolean done = false;
        int rank = 0;
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT UUID FROM " + table.toLowerCase() + " WHERE TYPE = ? ORDER BY VALUE DESC");
            preparedStatement.setString(1, type.toUpperCase());
            ResultSet rs = preparedStatement.executeQuery();
            while ((rs.next()) && (!done)) {
                rank++;
                if (rs.getString("UUID").equalsIgnoreCase(uuid.toString())) {
                    done = true;
                }
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rank;
    }

    public void createPlayer(String table, String type) {
        if (!isExistPlayer(table, type)) {
            try {
                PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("INSERT INTO " + table.toLowerCase() + " (UUID, NAME, TYPE, VALUE) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, type);
                preparedStatement.setInt(4, 0);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                new StatsAPI(player).updateName(table, name);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addInt(String table, String type, int value) {
        if (isExistPlayer(table, type)) {
            setInt(table, type, getInt(table, type) + value);
        } else {
            createPlayer(table, type);
        }
    }

    public void updateName(String table, String name) {
        BedWars.getInstance().getMySQL().update("UPDATE " + table.toLowerCase() + " SET NAME= '" + name + "' WHERE UUID= '" + uuid + "';");
    }

    public void removeInt(String table, String type, int value) {
        if (isExistPlayer(table, type)) {
            setInt(table, type, getInt(table, type) - value);
        } else {
            createPlayer(table, type);
        }
    }

    public void setInt(String table, String type, int value) {
        if (isExistPlayer(table, type)) {
            try {
                PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("UPDATE " + table.toLowerCase() + " SET VALUE = ? WHERE UUID = ? AND TYPE = ?");
                preparedStatement.setInt(1, value);
                preparedStatement.setString(2, uuid.toString());
                preparedStatement.setString(3, type.toUpperCase());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Integer getInt(String table, String type) {
        try {
            PreparedStatement var6 = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT VALUE FROM " + table.toLowerCase() + " WHERE UUID = ? AND TYPE = ?");
            var6.setString(1, uuid.toString());
            var6.setString(2, type.toUpperCase());
            ResultSet rs = var6.executeQuery();
            if (rs.next()) {
                return rs.getInt("VALUE");
            }
            rs.close();
            var6.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    private boolean isExistPlayer(String table, String type) {
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM " + table.toLowerCase() + " WHERE UUID = ? AND TYPE = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, type.toUpperCase());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getString("TYPE") != null) {
                    return true;
                }
                return false;
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private String getNameRankByID(String table, String type, int place) {
        try {
            int rank = 0;
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM " + table.toLowerCase() + " WHERE TYPE = ? ORDER BY VALUE DESC");
            preparedStatement.setString(1, type.toUpperCase());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rank++;
                if (rank == place) {
                    return rs.getString("NAME");
                }
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String getUUIDRankByID(String table, String type, int place) {
        try {
            int rank = 0;
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM " + table.toLowerCase() + " WHERE TYPE = ? ORDER BY VALUE DESC");
            preparedStatement.setString(1, type.toUpperCase());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rank++;
                if (rank == place) {
                    return rs.getString("UUID");
                }
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setStatsWall(String table, ArrayList<Location> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                int id = i + 1;
                String name = getNameRankByID(table, "points", id);
                Skull skull = (Skull) list.get(i).getBlock().getState();
                skull.setSkullType(SkullType.PLAYER);
                skull.setOwner(name);
                skull.update();
                Location loc = new Location((list.get(i)).getWorld(), (list.get(i)).getX(), (list.get(i)).getBlockY() - 1, (list.get(i)).getZ());
                if (loc.getBlock().getState() instanceof Sign) {
                    BlockState blockState = loc.getBlock().getState();
                    Sign sign = (Sign) blockState;
                    sign.setLine(0, "Platz §8#" + id);
                    sign.setLine(1, "§8" + name);
                    sign.setLine(2, "§l" + getInteger(UUID.fromString(getUUIDRankByID(table, "points", id)), table, "points") + " §rPunkte");
                    sign.setLine(3, "§l" + getInteger(UUID.fromString(getUUIDRankByID(table, "wins", id)), table, "wins") + " §rWins");
                    sign.update();
                }
            }
        } catch (Exception ex) {
        }
    }

    private Integer getInteger(UUID id, String table, String type) {
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT VALUE FROM " + table.toLowerCase() + " WHERE UUID = ? AND TYPE = ?");
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, type.toUpperCase());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("VALUE");
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
