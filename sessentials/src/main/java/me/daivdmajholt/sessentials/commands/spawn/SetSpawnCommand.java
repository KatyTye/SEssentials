package me.daivdmajholt.sessentials.commands.spawn;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;

public class SetSpawnCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be run by a player."));
			return true;
		}

		if (!sender.hasPermission("sessentials.spawn.set") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player player = (Player) sender;

		String name = args.length > 0 ? args[0].toString().toLowerCase() : "default";

		try {
			Location playerLocation = player.getLocation();

			File dataFolder = plugin.getDataFolder();
			File file = new File(dataFolder, "locations.yml");

			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			if (args.length >= 1) {
				int price = args.length > 1 ? Integer.parseInt(args[1].toString()) : 0;
				int cooldown = args.length > 2 ? Integer.parseInt(args[2].toString()) : 0;

				cfg.set(name + ".x", playerLocation.getX());
				cfg.set(name + ".y", playerLocation.getY());
				cfg.set(name + ".z", playerLocation.getZ());
				cfg.set(name + ".yaw", playerLocation.getYaw());
				cfg.set(name + ".pitch", playerLocation.getPitch());
				cfg.set(name + ".price", price);
				cfg.set(name + ".cooldown", cooldown);
				cfg.set(name + ".world", playerLocation.getWorld().getName());

				if (!cfg.getStringList("spawns").contains(name)) {
					List<String> spawns = cfg.getStringList("spawns");
					spawns.add(name);

					cfg.set("spawns", spawns);
				}
			} else {
				String def = cfg.getStringList("spawns").get(0);

				cfg.set(def + ".x", playerLocation.getX());
				cfg.set(def + ".y", playerLocation.getY());
				cfg.set(def + ".z", playerLocation.getZ());
				cfg.set(def + ".yaw", playerLocation.getYaw());
				cfg.set(def + ".pitch", playerLocation.getPitch());
				cfg.set(def + ".world", playerLocation.getWorld().getName());
			}

			cfg.save(file);
		} catch (IOException e) {
			plugin.getLogger().severe("Failed to save locations.yml: " + e.getMessage());
			return true;
		}

		player.sendMessage(cc(" &aYou have now set the location of " + (args.length > 0 ? args[0] : "") + " to your location."));
		return true;
	}
}
