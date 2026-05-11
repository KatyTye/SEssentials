package me.daivdmajholt.sessentials.managers;

import java.io.File;

import me.daivdmajholt.sessentials.Main;

public class Resources {

	private final Main plugin = Main.plugin;

	public void registerResources() {

		// Create Data Folder
		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdirs();

		// Ranks Data File
		File ranksDataFile = new File(plugin.getDataFolder(), "ranks.yml");
		if (!ranksDataFile.exists())
			plugin.saveResource("ranks.yml", false);

		// Spawn Data File
		File spawnDataFile = new File(plugin.getDataFolder(), "spawn.yml");
		if (!spawnDataFile.exists())
			plugin.saveResource("spawn.yml", false);
	}

}
