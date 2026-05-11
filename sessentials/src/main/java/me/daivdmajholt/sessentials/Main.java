package me.daivdmajholt.sessentials;
import me.daivdmajholt.database.DatabaseManager;
import me.daivdmajholt.sessentials.events.UpdateChecker;
import me.daivdmajholt.sessentials.managers.Commands;
import me.daivdmajholt.sessentials.managers.Events;
import me.daivdmajholt.sessentials.managers.Resources;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main plugin;

	private DatabaseManager databaseManager;

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();
		new Resources().registerResources();
		databaseManager = new DatabaseManager(plugin);

		if (getConfig().getBoolean("enabled")) {

			databaseManager.connect();

			getLogger().info("----------------------------------");
			getLogger().info("");
			getLogger().info("SEssentials has been enabled!");
			getLogger().info("");
			getLogger().info("----------------------------------");

			loadPlugin();

			// UPDATE CHECKER
			if (getConfig().getBoolean("check-updates")) {
				getLogger().info("Checking for updates...");
				new UpdateChecker().check();
			}
		} else {
			getLogger().info("----------------------------------");
			getLogger().info("");
			getLogger().info("SEssentials is currently disabled!");
			getLogger().info("");
			getLogger().info("----------------------------------");
		}
	}

	@Override
	public void onDisable() {

		if (databaseManager != null) {
			databaseManager.close();
		}

		getLogger().info("----------------------------------");
		getLogger().info("");
		getLogger().info("SEssentials has been disabled!");
		getLogger().info("");
		getLogger().info("----------------------------------");
	}

	private void unloadPlugin() {
		HandlerList.unregisterAll(plugin);
	}

	private void loadPlugin() {

		if (getConfig().getBoolean("enabled")) {
			// MANAGERS
			new Commands().registerCommands();
			new Events().reqiesterEvents();
		}
	}

	public void reloadPlugin(Player player) {
		player.sendMessage(cc(" &aReloading plugin, this can take some time."));

		try {
			unloadPlugin();
			reloadConfig();
			loadPlugin();
			player.sendMessage(cc(" &aReloaded plugin without any issues."));
			getLogger().info("Plugin reloaded.");
		} catch (Exception e) {
			player.sendMessage(cc(" &cFailed to reload plugin, please restart the server."));
			getLogger().log(java.util.logging.Level.SEVERE, "Failed to reload plugin. ", e.getMessage());
		}
	}
}
