package me.daivdmajholt.sessentials.commands.spawn;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;

public class SpawnCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (plugin.getConfig().getBoolean("settings.spawn-permission") && !sender.hasPermission("sessentials.spawn")
				&& !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player player;

		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				sender.sendMessage(cc(" &cThe console can only teleport players to spawn!"));
				sender.sendMessage(cc(" &cPlease use the command correctly: /spawn (player)"));
				return true;
			}

			player = (Player) Bukkit.getPlayer(args[0]);
		} else {
			player = (Player) sender;
		}

		if (player == null) {
			sender.sendMessage(cc(" &cThe player your trying to teleport is currently not online."));
			return true;
		}

		try {
			File dataFolder = plugin.getDataFolder();
			File file = new File(dataFolder, "locations.yml");

			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			if (args.length > 1 && !cfg.getStringList("spawns").contains(args[1].toString().toLowerCase())) {
				sender.sendMessage(cc(" &cThis spawn location does not exist."));
				return true;
			}

			String spawnName = args.length > 1 ? args[1].toString().toLowerCase() : "default";

			String worldName = cfg.getString(spawnName + ".world", "world");
			World world = Bukkit.getWorld(worldName);

			if (world == null) {
				plugin.getLogger().warning("World not found: " + worldName);
				return false;
			}

			double x = cfg.getDouble(spawnName + ".x", 0.0);
			double y = cfg.getDouble(spawnName + ".y", 0.0);
			double z = cfg.getDouble(spawnName + ".z", 0.0);
			float yaw = (float) cfg.getDouble(spawnName + ".yaw", 0.0);
			float pitch = (float) cfg.getDouble(spawnName + ".pitch", 0.0);

			Location loc = new Location(world, x, y, z, yaw, pitch);

			if (args.length > 0) {
				if (!player.hasPermission("sessentials.spawn.others") && !player.hasPermission("sessentials.*")) {
					sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
					return true;
				}

				Player target = Bukkit.getPlayerExact(args[0]);

				target.teleport(loc);
				target.sendMessage(cc(" &aYou have been teleported to spawn."));
				player.sendMessage(cc(" &aYou have teleported " + target.getName() + " to spawn."));

				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(cc(" &cThis command can only be run by a player."));
				return true;
			}

			player.teleport(loc);
			player.sendMessage(cc(" &aYou have been teleported to spawn."));
		} catch (NumberFormatException e) {
			plugin.getLogger().severe("Failed to teleport player to spawn.");
			sender.sendMessage(cc(" &cFailed to teleport to spawn."));
		}

		return true;
	}
}
