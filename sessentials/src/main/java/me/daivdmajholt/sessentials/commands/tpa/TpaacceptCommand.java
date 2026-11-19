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

public class TpaacceptCommand implements CommandExecutor {

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
			sender.sendMessage(cc(" &cYou can't accept requests from yourself."));
			return true;
		}

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "temp.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (!cfg.getStringList(player.getUniqueId() + ".tpa-recived").contains(target.getName().toString())) {
			sender.sendMessage(cc(" &cYou have not recived a teleport request from this player."));
			return true;
		}

		List<String> requests = cfg.getStringList(player.getUniqueId() + ".tpa-recived");

		requests.remove(target.getName());
		cfg.set(player.getUniqueId() + ".tpa-recived", requests);

		try {
			cfg.save(file);
			sender.sendMessage(cc(" &aYou have accepted " + target.getName() + "'s teleport request."));
			target.sendMessage(cc(" &aYour teleport request to " + player.getName() + " has been accepted."));
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				target.sendMessage(cc(" &aTeleporting you to " + player.getName() + " in 5 seconds."));
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					target.sendMessage(cc(" &aTeleporting you to " + player.getName() + " in 4 seconds."));
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						target.sendMessage(cc(" &aTeleporting you to " + player.getName() + " in 3 seconds."));
						Bukkit.getScheduler().runTaskLater(plugin, () -> {
							target.sendMessage(cc(" &aTeleporting you to " + player.getName() + " in 2 seconds."));
							Bukkit.getScheduler().runTaskLater(plugin, () -> {
								target.sendMessage(cc(" &aTeleporting you to " + player.getName() + " in 1 second."));
								Bukkit.getScheduler().runTaskLater(plugin, () -> {
									target.sendMessage(cc(" &aTeleporting..."));
									target.teleport(player.getLocation());
								}, 25L);
							}, 25L);
						}, 25L);
					}, 25L);
				}, 25L);
			}, 25L);
		} catch (IOException e) {
			if (plugin.getConfig().getBoolean("settings.debug-mode")) plugin.getLogger().severe("Could not save tpa accept requests.");
		}

		return true;
	}
}
