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

		// GAMEMODES
		getCommand("gmc").setExecutor(new GamemodeCCommand());
		getCommand("gma").setExecutor(new GamemodeACommand());
		getCommand("gms").setExecutor(new GamemodeSCommand());
		getCommand("gmsp").setExecutor(new GamemodeSPCommand());
		getCommand("gmc").setTabCompleter(new GamemodeMiniTab());
		getCommand("gma").setTabCompleter(new GamemodeMiniTab());
		getCommand("gms").setTabCompleter(new GamemodeMiniTab());
		getCommand("gmsp").setTabCompleter(new GamemodeMiniTab());
		getCommand("gm").setExecutor(new GamemodeCommand());
		getCommand("gm").setTabCompleter(new GamemodeTab());
		getCommand("gamemode").setExecutor(new GamemodeCommand());
		getCommand("gamemode").setTabCompleter(new GamemodeTab());
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
