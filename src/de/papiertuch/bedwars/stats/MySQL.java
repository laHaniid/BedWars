package de.papiertuch.bedwars.stats;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class MySQL {

    private Connection connection;

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + BedWars.getInstance().getBedWarsConfig().getString("mysql.host") + ":3306/" + BedWars.getInstance().getBedWarsConfig().getString("mysql.dataBase") + "?autoReconnect=true", BedWars.getInstance().getBedWarsConfig().getString("mysql.user"), BedWars.getInstance().getBedWarsConfig().getConfiguration().getString("mysql.password"));
            Bukkit.getServer().getConsoleSender().sendMessage("§8[§6§lBedWars§8] §aEine Verbindung zum MySQl-Server war erfolgreich");
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("§8[§6§lBedWars§8] §cDie Verbindung zum MySQL-Server ist fehlgeschlagen");
        }
    }

    public void createTable() {
        BedWars.getInstance().getMySQL().update("CREATE TABLE IF NOT EXISTS bedwars (UUID VARCHAR(100), NAME VARCHAR(100), KILLS INT, DEATHS INT, WINS INT, PLAYED INT, BED INT, POINTS INT)");
    }


    public void disconnect() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void update(String qry) {
        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
