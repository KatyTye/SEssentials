package me.daivdmajholt.sessentials.commands.spawn;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.io.IOException;

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

		if (!sender.hasPermission("sessentials.spawn.set")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player player = (Player) sender;

		try {
			Location playerLocation = player.getLocation();

			File dataFolder = plugin.getDataFolder();
			File file = new File(dataFolder, "spawn.yml");

			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			cfg.set("default.x", playerLocation.getX());
			cfg.set("default.y", playerLocation.getY());
			cfg.set("default.z", playerLocation.getZ());
			cfg.set("default.yaw", playerLocation.getYaw());
			cfg.set("default.pitch", playerLocation.getPitch());
			cfg.set("default.world", playerLocation.getWorld().getName());

			cfg.save(file);
		} catch (IOException e) {
			plugin.getLogger().severe("Failed to save spawn.yml: " + e.getMessage());
			return true;
		}

		player.sendMessage(cc(" &aYou have now set the location of spawn to your location."));
		return true;
	}
}
