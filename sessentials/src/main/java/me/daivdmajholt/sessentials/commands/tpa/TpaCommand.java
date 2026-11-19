package me.daivdmajholt.sessentials.commands.tpa;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.util.List;
import java.io.IOException;

public class TpaCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.tpa") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be executed by a player."));
			return true;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter the name of an online player."));
			return true;
		}

		Player target = Bukkit.getPlayerExact(args[0]);

		if (target == null) {
			sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
			return true;
		}

		if (player.getUniqueId() == target.getUniqueId()) {
			sender.sendMessage(cc(" &cYou can't request to teleport to yourself."));
			return true;
		}

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "temp.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (cfg.getStringList(target.getUniqueId() + ".tpa-recived").contains(player.getName().toString())) {
			sender.sendMessage(cc(" &cYou have already requested a teleport to this player."));
			return true;
		}

		List<String> requests = cfg.getStringList(target.getUniqueId() + ".tpa-recived");

		requests.add(player.getName().toString());
		cfg.set(target.getUniqueId() + ".tpa-recived", requests);

		try {
			cfg.save(file);
			sender.sendMessage(cc(" &aYou have requested to teleport to " + target.getName() + "."));
			target.sendMessage(cc(" &aA player named " + player.getName() + " has requested to teleport to you."));

			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				if (cfg.getStringList(target.getUniqueId() + ".tpa-recived").contains(player.getName().toString())) {
					List<String> targetRequests = cfg.getStringList(target.getUniqueId() + ".tpa-recived");

					targetRequests.remove(player.getName().toString());
					cfg.set(target.getUniqueId() + ".tpa-recived", targetRequests);

					try {
						cfg.save(file);
						sender.sendMessage(cc(" &cYour teleport request to " + target.getName() + " was not accepted."));
					} catch (IOException e) {
						if (plugin.getConfig().getBoolean("settings.debug-mode")) plugin.getLogger().severe("Could not cancel tpa request.");
					}
				}
			}, 3000L);
		} catch (IOException e) {
			if (plugin.getConfig().getBoolean("settings.debug-mode")) plugin.getLogger().severe("Could not save tpa requests.");
		}

		return true;
	}
}
