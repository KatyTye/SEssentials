package me.daivdmajholt.database;

import me.daivdmajholt.sessentials.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DatabaseManager {
	
	public static enum ValueType { STRING, INT, LONG, DOUBLE, BOOLEAN, UUID, OBJECT }

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

		String sqlgodmode = """
					CREATE TABLE IF NOT EXISTS godmode (
						uuid TEXT PRIMARY KEY,
						enabled BOOLEAN DEFAULT false
					);
				""";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlgodmode)) {

			statement.execute();

			if (plugin.getConfig().getBoolean("settings.debug-mode")) {
				plugin.getLogger().info("God mode database table created/loaded.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sqlwarp = """
					CREATE TABLE IF NOT EXISTS warps (
						name TEXT PRIMARY KEY,
						x REAL DEFAULT 0.0,
						y REAL DEFAULT 0.0,
						z REAL DEFAULT 0.0,
						yaw REAL DEFAULT 0.0,
						pitch REAL DEFAULT 0.0,
						world TEXT DEFAULT 'world'
					);
				""";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlwarp)) {

			statement.execute();

			if (plugin.getConfig().getBoolean("settings.debug-mode")) {
				plugin.getLogger().info("Warps database table created/loaded.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Object getValueFromDB(String table, String field, String whereColumn, String whereValue, ValueType type, ValueType returnType) {
		if (type == null) type = ValueType.STRING;
		if (returnType == null) returnType = ValueType.STRING;

		String sql = "SELECT " + field + " FROM " + table + " WHERE " + whereColumn + " = ?";
		try (Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) {

			switch (type) {
				case STRING -> statement.setString(1, whereValue);
				case INT -> statement.setInt(1, Integer.parseInt(whereValue));
				case LONG -> statement.setLong(1, Long.parseLong(whereValue));
				case DOUBLE -> statement.setDouble(1, Double.parseDouble(whereValue));
				case BOOLEAN -> statement.setBoolean(1, Boolean.parseBoolean(whereValue));
				case UUID -> statement.setObject(1, whereValue, Types.VARCHAR);
				default -> statement.setString(1, whereValue);
			}

			try (ResultSet result = statement.executeQuery()) {
				if (!result.next()) return null;

				Object raw = result.getObject(field);
				if (raw == null) return null;

				switch (returnType) {
					case STRING:  return raw.toString();
					case INT:     return raw instanceof Number ? ((Number) raw).intValue() : Integer.parseInt(raw.toString());
					case LONG:    return raw instanceof Number ? ((Number) raw).longValue() : Long.parseLong(raw.toString());
					case DOUBLE:  return raw instanceof Number ? ((Number) raw).doubleValue() : Double.parseDouble(raw.toString());
					case BOOLEAN: return raw instanceof Boolean ? raw : Boolean.parseBoolean(raw.toString());
					case UUID:    return UUID.fromString(raw.toString());
					case OBJECT:  return raw;
					default:      return raw;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Boolean checkValueFromDB(String table, String field, String whereColumn, String whereValue, ValueType type) {

		String sql = "SELECT " + field + " FROM " + table + " WHERE " + whereColumn + " = ?";
		
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			switch (type) {
				case STRING -> statement.setString(1, whereValue);
				case INT -> statement.setInt(1, Integer.parseInt(whereValue));
				case LONG -> statement.setLong(1, Long.parseLong(whereValue));
				case DOUBLE -> statement.setDouble(1, Double.parseDouble(whereValue));
				case BOOLEAN -> statement.setBoolean(1, Boolean.parseBoolean(whereValue));
				case UUID -> statement.setObject(1, whereValue, Types.VARCHAR);
				default -> statement.setString(1, whereValue);
			}

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public Boolean setPlayerRank(String uuid, Integer rank) {

		String sql = """
					UPDATE players SET rank = ? WHERE uuid = ?
				""";

		if (rank == null)
			rank = 0;

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, rank);
			statement.setString(2, uuid);

			int rows = statement.executeUpdate();

			if (rows > 0) {
				if (plugin.getConfig().getBoolean("settings.debug-mode")) {
					plugin.getLogger().info("Changed the rank of the player to rank id " + rank + ".");
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Object> getAllValuesFromDB(String table, String field, ValueType type) {
		List<Object> list = new ArrayList<>();

		String sql = "SELECT " + field + " FROM " + table;

		try (Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery()) {

			while (result.next()) {
				Object raw = result.getObject(field);

				if (raw == null) {
					continue;
				}

				switch (type) {
					case STRING -> list.add(raw.toString());
					case INT -> list.add(raw instanceof Number ? ((Number) raw).intValue() : Integer.parseInt(raw.toString()));
					case LONG -> list.add(raw instanceof Number ? ((Number) raw).longValue() : Long.parseLong(raw.toString()));
					case DOUBLE -> list.add(raw instanceof Number ? ((Number) raw).doubleValue() : Double.parseDouble(raw.toString()));
					case BOOLEAN -> list.add(raw instanceof Boolean ? raw : Boolean.parseBoolean(raw.toString()));
					case UUID -> list.add(UUID.fromString(raw.toString()));
					case OBJECT -> list.add(raw);
					default -> list.add(raw);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<String> getAllWarps() {
		List<String> warps = new ArrayList<>();

		String sql = """
				SELECT name FROM warps
				""";
		
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				warps.add(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return warps;
	}
	
	public Boolean findGodMode(Player player) {

		String sql = """
					SELECT enabled FROM godmode WHERE uuid = ?
				""";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, player.getUniqueId().toString());
			
			ResultSet result = statement.executeQuery();

			if (result.next()) {

				if (plugin.getConfig().getBoolean("settings.debug-mode")) {
					plugin.getLogger().info("Found a player named " + player.getName() + " from the database.");
				}

				Boolean returnValue = result.getBoolean("enabled");

				return returnValue;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void registerPlayer(Player player, int rank, double balance) {

		String checkSQL = """
					SELECT uuid FROM players WHERE uuid = ?
				""";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(checkSQL)) {

			statement.setString(1, player.getUniqueId().toString());

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				if (plugin.getConfig().getBoolean("settings.debug-mode")) {
					plugin.getLogger()
							.info("Could not register " + player.getName() + " into the database: Already exists.");
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

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(insertSQL)) {

			statement.setString(1, player.getUniqueId().toString());
			statement.setInt(2, rank);
			statement.setDouble(3, balance);

			statement.executeUpdate();

			if (plugin.getConfig().getBoolean("settings.debug-mode")) {
				plugin.getLogger().info("Registered player in database: " + player.getName() + " ("
						+ player.getUniqueId().toString() + ")");
			}

			File dataFolder = plugin.getDataFolder();
			File file = new File(dataFolder, "ranks.yml");

			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			Integer Amount = cfg.getInt("0.members");
			cfg.set("0.members", Amount + 1);

			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setGodMode(Player player, boolean mode) {

		String sql = """
					INSERT INTO godmode(uuid, enabled)
					VALUES(?, ?)
					ON CONFLICT(uuid)
					DO UPDATE SET enabled = excluded.enabled;
				""";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, player.getUniqueId().toString());

			statement.setBoolean(2, mode);

			statement.executeUpdate();

			if (plugin.getConfig().getBoolean("settings.debug-mode")) {
				plugin.getLogger().info("Changed god mode for a player to: " + mode);
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
}