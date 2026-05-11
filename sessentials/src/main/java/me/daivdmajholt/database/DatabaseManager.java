package me.daivdmajholt.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;

public class DatabaseManager {

    private final JavaPlugin plugin;
    private Connection connection;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void connect() {

        try {

            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }

            File databaseFile = new File(plugin.getDataFolder(), "database.db");

            String url = "jdbc:sqlite:" + databaseFile.getAbsolutePath();

            connection = DriverManager.getConnection(url);

            plugin.getLogger().info("Connected to SQLite database.");

            createTables();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createTables() {

        String sql = """
                CREATE TABLE IF NOT EXISTS players (
                    uuid TEXT PRIMARY KEY,
                    username TEXT,
                    balance DOUBLE,
                    rank INT
                );
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setBalance(String uuid, double balance) {

        String sql = """
                INSERT INTO players(uuid, balance)
                VALUES(?, ?)
                ON CONFLICT(uuid)
                DO UPDATE SET balance = excluded.balance;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid);
            statement.setDouble(2, balance);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public double getBalance(String uuid) {

        String sql = "SELECT balance FROM players WHERE uuid = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getDouble("balance");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;

    }

    public void close() {

        try {

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}