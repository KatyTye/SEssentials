package me.daivdmajholt.sessentials.commands.worldedit;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;

public class CountCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.repair") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be executed by a player."));
			return true;
		}

		Player player = (Player) sender;

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "temp.yml");
		
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (cfg.getString(player.getUniqueId() + ".place-location-first.world").equals("") ||
			cfg.getString(player.getUniqueId() + ".place-location-second.world").equals("")
		) {
			sender.sendMessage(cc("&8 [&4WorldEdit&8] &cYou have not placed any locations yet."));
			return true;
		}

		if (!cfg.getString(player.getUniqueId() + ".place-location-first.world").equals(cfg.getString(player.getUniqueId() + ".place-location-second.world"))) {
			sender.sendMessage(cc("&8 [&4WorldEdit&8] &cThe worlds of the locations are diffrent and cant be counted."));
			return true;
		}

		double firstX = cfg.getDouble(player.getUniqueId() + ".place-location-first.x", 0.0);
		double firstY = cfg.getDouble(player.getUniqueId() + ".place-location-first.y", 0.0);
		double firstZ = cfg.getDouble(player.getUniqueId() + ".place-location-first.z", 0.0);

		double secondX = cfg.getDouble(player.getUniqueId() + ".place-location-second.x", 0.0);
		double secondY = cfg.getDouble(player.getUniqueId() + ".place-location-second.y", 0.0);
		double secondZ = cfg.getDouble(player.getUniqueId() + ".place-location-second.z", 0.0);
	
		World world = (World) Bukkit.getWorld(cfg.getString(player.getUniqueId() + ".place-location-first.world"));

		Location firstLocation = new Location(world, firstX, firstY, firstZ);
		Location secondLocation = new Location(world, secondX, secondY, secondZ);

		int minX = Math.min(firstLocation.getBlockX(), secondLocation.getBlockX());
		int maxX = Math.max(firstLocation.getBlockX(), secondLocation.getBlockX());
		int minY = Math.min(firstLocation.getBlockY(), secondLocation.getBlockY());
		int maxY = Math.max(firstLocation.getBlockY(), secondLocation.getBlockY());
		int minZ = Math.min(firstLocation.getBlockZ(), secondLocation.getBlockZ());
		int maxZ = Math.max(firstLocation.getBlockZ(), secondLocation.getBlockZ());

		long totalBlocks = (long) (maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1);

		sender.sendMessage(cc("&8 [&4WorldEdit&8] &aThere is a total of " + totalBlocks + " block(s) between first and second position."));
		return true;
	}
}
