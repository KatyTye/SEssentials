package me.daivdmajholt.sessentials;
import me.daivdmajholt.sessentials.managers.Commands;
import me.daivdmajholt.sessentials.managers.Events;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();

		getLogger().info("----------------------------------");
		getLogger().info("");
		getLogger().info("SEssentials has been enabled!");
		getLogger().info("");
		getLogger().info("----------------------------------");

		// String prefix = plugin.getConfig().getString("prefix");

		// MANAGERS
		new Commands(this).registerCommands();
		new Events(this).reqiesterEvents();
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
