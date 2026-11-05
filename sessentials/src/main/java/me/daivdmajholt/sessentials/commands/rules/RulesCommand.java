package me.daivdmajholt.sessentials.commands.rules;

import static me.daivdmajholt.sessentials.Utils.cc;
import static me.daivdmajholt.sessentials.Utils.returnClickLink;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.common.returnsreceiver.qual.This;

import me.daivdmajholt.sessentials.Main;

public class RulesCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.rules") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "rules.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		String rulesUrl = plugin.getConfig().getString("messages.rules-link");
		
		if (plugin.getConfig().getBoolean("settings.linked-rules") || args.length >= 1) {

			if (plugin.getConfig().getString("messages.rules-link").equalsIgnoreCase("https://google.com/")
				|| args[0].toString().toLowerCase().equalsIgnoreCase("link")
			) {
				sender.sendMessage(cc(" &cThis server currently does not have any website with the server rules."));
				return true;
			}
			
			sender.sendMessage(cc(" &6&lLINK: &f" + returnClickLink(rulesUrl)));
			return true;
		}

		sender.sendMessage("");
		sender.sendMessage(cc(" &f&m                                  &f"));
		sender.sendMessage("");

		for (String value : cfg.getStringList("list")) {
			sender.sendMessage(cc(" &7- &6" + value));
			sender.sendMessage("");
		}

		if (cfg.getStringList("list").size() == 0) {
			sender.sendMessage(cc(" &cThis server currently does not have any server rules yet."));
			sender.sendMessage("");
		}

		sender.sendMessage(cc(" &f&m                                  &f"));
		sender.sendMessage("");

		return true;
	}
}
