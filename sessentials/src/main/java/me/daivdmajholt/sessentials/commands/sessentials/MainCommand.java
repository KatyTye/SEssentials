package me.daivdmajholt.sessentials.commands.sessentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.daivdmajholt.BuildInfo;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.events.UpdateChecker;

import static me.daivdmajholt.sessentials.Utils.cc;

public class MainCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.help")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cPlease use the command correctly /sessentials help"));
			return true;
		}

		if (args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("");
			sender.sendMessage(cc(" &f&m                                  &f"));
			sender.sendMessage("");
			sender.sendMessage(cc(" &6/sessentials build &7- View plugin build time."));
			sender.sendMessage(cc(" &6/sessentials help &7- View plugin commands."));
			// sender.sendMessage(cc(" &6/sessentials edit &7- Edit plugin features."));
			sender.sendMessage(cc(" &6/sessentials reload &7- Reload plugin features."));
			// sender.sendMessage(cc(" &6/sessentials toggle &7- Toggle plugin masterswitch."));
			sender.sendMessage(cc(" &6/sessentials status &7- View status of plugin and config."));
			sender.sendMessage("");
			sender.sendMessage(cc(" &f&m                                  &f"));
			sender.sendMessage("");
			return true;
		}

		if (args[0].equalsIgnoreCase("status")) {
			sender.sendMessage("");
			sender.sendMessage(cc(" &f&m                                  &f"));
			sender.sendMessage("");
			new UpdateChecker().messageCheck(sender, () -> {
				sender.sendMessage("");
				if (plugin.getConfig().getBoolean("enabled")) {
					sender.sendMessage(cc(" &fPlugin: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fPlugin: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("settings.debug-mode")) {
					sender.sendMessage(cc(" &fDebug Mode: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fDebug Mode: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("check-updates")) {
					sender.sendMessage(cc(" &fCheck for updates: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fCheck for updates: &c&lDISABLED"));
				}
				sender.sendMessage("");
				if (plugin.getConfig().getBoolean("features.enabled")) {
					sender.sendMessage(cc(" &fFeatures: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fFeatures: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("features.events")) {
					sender.sendMessage(cc(" &fEvents: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fEvents: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("features.commands")) {
					sender.sendMessage(cc(" &fCommands: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fCommands: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("features.community")) {
					sender.sendMessage(cc(" &fCommunity: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fCommunity: &c&lDISABLED"));
				}
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
			});
			return true;
		}

		if (args[0].equalsIgnoreCase("build")) {
			sender.sendMessage(cc(" &aYour build was made " + BuildInfo.BUILD_TIMESTAMP + "."));
			return true;
		}

		if (args[0].equalsIgnoreCase("reload")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(cc(" &cThis command can only be run by a player."));
				return true;
			}

			plugin.reloadPlugin((Player) sender);
			return true;
		}

		sender.sendMessage(cc(plugin.getConfig().getString("messages.subcommand-unknown") + "/sessentials help"));
		return true;
	}

}
