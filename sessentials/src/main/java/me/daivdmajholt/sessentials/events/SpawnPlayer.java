package me.daivdmajholt.sessentials.events;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.daivdmajholt.sessentials.Main;

public class SpawnPlayer implements Listener {
	
	private final Main plugin = Main.plugin;

	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent event) {

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "spawn.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		String worldName = cfg.getString("default.world", "world");
		World world = Bukkit.getWorld(worldName);
		if (world == null) {
			plugin.getLogger().warning("World not found: " + worldName);
		} else {
			double x = cfg.getDouble("default.x", 0.0);
			double y = cfg.getDouble("default.y", 0.0);
			double z = cfg.getDouble("default.z", 0.0);
			float yaw = (float) cfg.getDouble("default.yaw", 0.0);
			float pitch = (float) cfg.getDouble("default.pitch", 0.0);
	
			Location loc = new Location(world, x, y, z, yaw, pitch);

			event.setRespawnLocation(loc);
		}
	}
}
