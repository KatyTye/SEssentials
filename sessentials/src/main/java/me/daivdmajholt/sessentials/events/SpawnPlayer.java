package me.daivdmajholt.sessentials.events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.database.DatabaseManager.ValueType;

public class SpawnPlayer implements Listener {
	
	private final Main plugin = Main.plugin;

	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent event) {

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "locations.yml");
		File ranksfile = new File(dataFolder, "ranks.yml");
		File tempfile = new File(dataFolder, "temp.yml");
		
		Player player = event.getPlayer();

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		FileConfiguration rankcfg = YamlConfiguration.loadConfiguration(ranksfile);
		FileConfiguration tempcfg = YamlConfiguration.loadConfiguration(tempfile);

		try {
			tempcfg.set(player.getUniqueId() + ".last-location.placed", true);
			tempcfg.set(player.getUniqueId() + ".last-location.x", (double) player.getLocation().getX());
			tempcfg.set(player.getUniqueId() + ".last-location.y", (double) player.getLocation().getY());
			tempcfg.set(player.getUniqueId() + ".last-location.z", (double) player.getLocation().getZ());
			tempcfg.set(player.getUniqueId() + ".last-location.yaw", (double) player.getLocation().getYaw());
			tempcfg.set(player.getUniqueId() + ".last-location.pitch", (double) player.getLocation().getPitch());
			tempcfg.set(player.getUniqueId() + ".last-location.world", (String) player.getLocation().getWorld().getName());
			tempcfg.save(tempfile);
		} catch (IOException e) {
			if (plugin.getConfig().getBoolean("settings.debug-mode")) {
				plugin.getLogger().severe("Could not set last player location.");
			}
		}

		String spawnName = "default";

		String rank = (String) Main.databaseManager.getValueFromDB("players", "rank",
		"uuid", event.getPlayer().getUniqueId().toString(), ValueType.STRING, ValueType.STRING);
		
		if (cfg.getStringList("spawns").contains(rankcfg.getString(rank + ".name").toString().toLowerCase())) {
			spawnName = rankcfg.getString(rank + ".name").toString().toLowerCase();
		}

		String worldName = cfg.getString(spawnName + ".world", "world");
		World world = Bukkit.getWorld(worldName);

		if (world == null) {
			plugin.getLogger().warning("World not found: " + worldName);
		} else {
			double x = cfg.getDouble(spawnName + ".x", 0.0);
			double y = cfg.getDouble(spawnName + ".y", 0.0);
			double z = cfg.getDouble(spawnName + ".z", 0.0);
			float yaw = (float) cfg.getDouble(spawnName + ".yaw", 0.0);
			float pitch = (float) cfg.getDouble(spawnName + ".pitch", 0.0);
	
			Location loc = new Location(world, x, y, z, yaw, pitch);

			event.setRespawnLocation(loc);
		}
	}
}
