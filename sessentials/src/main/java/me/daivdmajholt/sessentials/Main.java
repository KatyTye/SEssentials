package me.daivdmajholt.sessentials;
import org.bukkit.plugin.java.JavaPlugin;

import me.daivdmajholt.sessentials.commands.gamemode.GamemodeACommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeCCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeMiniTab;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeSCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeSPCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeTab;

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
