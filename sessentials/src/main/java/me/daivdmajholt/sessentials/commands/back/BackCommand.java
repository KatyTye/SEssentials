package me.daivdmajholt.sessentials.commands.back;

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
import java.io.IOException;

public class BackCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.back") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be run by a player."));
			return true;
		}

		Player player = (Player) sender;

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "temp.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (cfg.getBoolean(player.getUniqueId() + ".last-location.placed") == true) {
			double targetX = cfg.getDouble(player.getUniqueId() + ".last-location.x", 0.0);
			double targetY = cfg.getDouble(player.getUniqueId() + ".last-location.y", 0.0);
			double targetZ = cfg.getDouble(player.getUniqueId() + ".last-location.z", 0.0);
			float targetYaw = (float) cfg.getDouble(player.getUniqueId() + ".last-location.yaw", 0.0);
			float targetPitch = (float) cfg.getDouble(player.getUniqueId() + ".last-location.pitch", 0.0);
	
			World targetWorld = (World) Bukkit.getWorld(cfg.getString(player.getUniqueId() + ".last-location.world"));
	
			double currentX = (double) player.getLocation().getX();
			double currentY = (double) player.getLocation().getY();
			double currentZ = (double) player.getLocation().getZ();
			float currentYaw = (float) player.getLocation().getYaw();
			float currentPitch = (float) player.getLocation().getPitch();
		
			Location targetLocation = new Location(targetWorld, targetX, targetY, targetZ, targetYaw, targetPitch);
	
			try {
				cfg.set(player.getUniqueId() + ".last-location.x", (double) currentX);
				cfg.set(player.getUniqueId() + ".last-location.y", (double) currentY);
				cfg.set(player.getUniqueId() + ".last-location.z", (double) currentZ);
				cfg.set(player.getUniqueId() + ".last-location.yaw", (double) currentYaw);
				cfg.set(player.getUniqueId() + ".last-location.pitch", (double) currentPitch);
				cfg.set(player.getUniqueId() + ".last-location.world", (String) player.getLocation().getWorld().getName());
				cfg.save(file);
			} catch (IOException e) {
				if (plugin.getConfig().getBoolean("settings.debug-mode")) {
					plugin.getLogger().severe("Could not set last player location.");
				}
			}
	
			player.teleport(targetLocation);
			sender.sendMessage(cc(" &aYou have now been teleported to your last location."));
		} else {
			sender.sendMessage(cc(" &cYou currently don't have a last location."));
		}

		return true;
	}
}
