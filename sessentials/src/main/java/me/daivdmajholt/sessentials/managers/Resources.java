package me.daivdmajholt.sessentials.managers;

import java.io.File;

import me.daivdmajholt.sessentials.Main;

public class Resources {

	private final Main plugin = Main.plugin;
	
	public void registerResources() {

		// Create Data Folder
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs(); 

		// Spawn Data File
		File spawnDataFile = new File(plugin.getDataFolder(), "data/spawn.json");
		if (!spawnDataFile.exists()) plugin.saveResource("data/spawn.json", false);
	}

}
