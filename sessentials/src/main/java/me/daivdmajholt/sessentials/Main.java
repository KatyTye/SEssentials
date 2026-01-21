package me.daivdmajholt.sessentials;
import me.daivdmajholt.sessentials.events.UpdateChecker;
import me.daivdmajholt.sessentials.managers.Commands;
import me.daivdmajholt.sessentials.managers.Events;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();

		if (getConfig().getBoolean("enabled")) {
			getLogger().info("----------------------------------");
			getLogger().info("");
			getLogger().info("SEssentials has been enabled!");
			getLogger().info("");
			getLogger().info("----------------------------------");

			// MANAGERS
			new Commands(this).registerCommands();
			new Events(this).reqiesterEvents();

			// UPDATE CHECKER
			if (getConfig().getBoolean("check-updates")) {
				getLogger().info("Checking for updates...");
				new UpdateChecker(this).check();
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
		getLogger().info("----------------------------------");
		getLogger().info("");
		getLogger().info("SEssentials has been disabled!");
		getLogger().info("");
		getLogger().info("----------------------------------");
	}
}
