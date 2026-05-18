package me.daivdmajholt.sessentials.commands.rank;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daivdmajholt.sessentials.Main;

public class RankCommand implements CommandExecutor {
	
	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.manage.ranks")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cPlease use the command correctly /rankmanager help"));
			return true;
		}

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "ranks.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<Integer> ranks = cfg.getIntegerList("ranks");

		switch (args[0].toLowerCase()) {
			case "help" -> {
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
				sender.sendMessage(cc(" &6/rankmanager help &7- Show all subcommands for commands."));
				sender.sendMessage(cc(" &6/rankmanager list &7- Shows a list of all the ranks on the server."));
				sender.sendMessage(cc(" &6/rankmanager permissions (rank id) &7- Lists all the permissions of the rank."));
				sender.sendMessage(cc(" &6/rankmanager rename (rank id) (name) &7- Renames the name of the rank chosen."));
				sender.sendMessage(cc(" &6/rankmanager prefix (rank id) (prefix) &7- Changes the prefix of the rank chosen."));
				sender.sendMessage(cc(" &6/rankmanager suffix (rank id) (suffix) &7- Changes the suffix of the rank chosen."));
				sender.sendMessage(cc(" &6/rankmanager color (rank id) (color code) &7- Changes the color of a ranks prefix and suffix."));
				sender.sendMessage(cc(" &6/rankmanager priority (rank id) (priority) &7- Changes the priority of the rank chosen."));
				sender.sendMessage(cc(" &6/rankmanager permission (rank id) (add/remove) (permission) &7- Adds or removes a permission from a rank."));
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
			}

			case "list" -> {
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
				for (int value : ranks) {
					sender.sendMessage(cc(" &7- &f&lID: &6" + value + " &f&lNAME: &6" + cfg.getString(value + ".name") + " &f&lPRIORITY: &6" + cfg.getString(value + ".priority")));
					sender.sendMessage("");
				}

				if (ranks.size() == 0) {
					sender.sendMessage(cc(" &cCould not find any ranks on the server."));
					sender.sendMessage("");
				}
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
			}
		
			default -> sender.sendMessage(cc(plugin.getConfig().getString("messages.subcommand-unknown") + "using the help command /rankmanager help"));
		}
		
		return true;
	}
}