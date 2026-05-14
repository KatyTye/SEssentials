package me.daivdmajholt.database;

import me.daivdmajholt.sessentials.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class DatabaseManager {

    private final Main plugin;
    private String url;

    public DatabaseManager(Main plugin) {
        this.plugin = plugin;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void connect() {

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        File databaseFile = new File(plugin.getDataFolder(), "database.db");

        url = "jdbc:sqlite:" + databaseFile.getAbsolutePath();

        if (plugin.getConfig().getBoolean("settings.debug-mode")) {
            plugin.getLogger().info("Connected to SQLite database.");
        }

        createTables();
    }

    private void createTables() {

        String sql = """
            CREATE TABLE IF NOT EXISTS players (
                uuid TEXT PRIMARY KEY,
                balance DOUBLE,
                rank INT
            );
        """;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.execute();

            if (plugin.getConfig().getBoolean("settings.debug-mode")) {
                plugin.getLogger().info("Players database table created/loaded.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String findPlayerRank(Player player) {

        String sql = """
            SELECT rank FROM players WHERE uuid = ?
        """;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getUniqueId().toString());

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                
                if (plugin.getConfig().getBoolean("settings.debug-mode")) {
                    plugin.getLogger().info("Could not find rank from " + player.getName() + " in the database.");
                }

                String returnValue = String.valueOf(result.getInt("rank"));

                return returnValue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "0";
    }

    public void registerPlayer(Player player, int rank, double balance) {

        String checkSQL = """
            SELECT uuid FROM players WHERE uuid = ?
        """;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(checkSQL)) {

            statement.setString(1, player.getUniqueId().toString());

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                if (plugin.getConfig().getBoolean("settings.debug-mode")) {
                    plugin.getLogger().info("Could not register " + player.getName() + " into the database: Already exists.");
                }
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        String insertSQL = """
            INSERT INTO players(uuid, rank, balance)
            VALUES (?, ?, ?)
        """;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(insertSQL)) {

            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, rank);
            statement.setDouble(3, balance);

            statement.executeUpdate();

            if (plugin.getConfig().getBoolean("settings.debug-mode")) {
                plugin.getLogger().info("Registered player in database: "+ player.getName() + " (" + player.getUniqueId().toString() + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setBalance(Player player, String uuid, double balance) {

        String sql = """
            INSERT INTO players(uuid, balance)
            VALUES(?, ?)
            ON CONFLICT(uuid)
            DO UPDATE SET balance = excluded.balance;
        """;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            if (player == null) {
                statement.setString(1, uuid);
            } else {
                statement.setString(1, player.getUniqueId().toString());
            }

            statement.setDouble(2, balance);

            statement.executeUpdate();

            if (plugin.getConfig().getBoolean("settings.debug-mode")) {
                plugin.getLogger().info("Changed balance for a player to: " + balance + "$");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public double getBalance(Player player, String uuid) {

        String sql = """
            SELECT balance FROM players WHERE uuid = ?
        """;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            if (player == null) {
                statement.setString(1, uuid);
            } else {
                statement.setString(1, player.getUniqueId().toString());
            }

            ResultSet result = statement.executeQuery();

            if (plugin.getConfig().getBoolean("settings.debug-mode")) {
                plugin.getLogger().info("Player checked balance from database.");
            }

            if (result.next()) {
                return result.getDouble("balance");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;

    }
}