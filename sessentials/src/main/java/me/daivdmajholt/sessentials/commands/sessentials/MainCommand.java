package me.daivdmajholt.sessentials.commands.sessentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import me.daivdmajholt.BuildInfo;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.events.UpdateChecker;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.io.IOException;

public class MainCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.settings") && !sender.hasPermission("sessentials.*")) {
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
			sender.sendMessage(cc(" &6/sessentials toggle &7- Toggle plugin masterswitch."));
			sender.sendMessage(cc(" &6/sessentials status &7- View status of plugin and config."));
			sender.sendMessage("");
			sender.sendMessage(cc(" &f&m                                  &f"));
			sender.sendMessage("");
			return true;
		}

		if (args[0].equalsIgnoreCase("toggle")) {
			File dataFolder = plugin.getDataFolder();
			File file = new File(dataFolder, "config.yml");

			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			if (plugin.getConfig().getBoolean("enabled") == true) {
				cfg.set("enabled", false);
				try {
					cfg.save(file);
					plugin.reloadConfig();
					sender.sendMessage(cc(" &aDisabling plugin."));
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						plugin.unloadPlugin();
						sender.sendMessage(cc(" &aPlugin has been disabled."));
					}, 25L);
				} catch (IOException exception) {
					sender.sendMessage(cc(" &cCould not disable the plugin."));
				}
			} else {
				cfg.set("enabled", true);
				try {
					cfg.save(file);
					plugin.reloadConfig();
					sender.sendMessage(cc(" &aEnabling plugin."));
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						plugin.loadPlugin();
						sender.sendMessage(cc(" &aPlugin has been enabled."));
					}, 25L);
				} catch (IOException exception) {
					sender.sendMessage(cc(" &cCould not enable the plugin."));
				}
			}

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
				if (plugin.getConfig().getBoolean("settings.debug-mode") && plugin.getConfig().getBoolean("enabled")) {
					sender.sendMessage(cc(" &fDebug Mode: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fDebug Mode: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("check-updates") && plugin.getConfig().getBoolean("enabled")) {
					sender.sendMessage(cc(" &fCheck for updates: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fCheck for updates: &c&lDISABLED"));
				}
				sender.sendMessage("");
				if (plugin.getConfig().getBoolean("features.enabled") && plugin.getConfig().getBoolean("enabled")) {
					sender.sendMessage(cc(" &fFeatures: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fFeatures: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("features.events") && plugin.getConfig().getBoolean("enabled")) {
					sender.sendMessage(cc(" &fEvents: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fEvents: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("features.commands") && plugin.getConfig().getBoolean("enabled")) {
					sender.sendMessage(cc(" &fCommands: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fCommands: &c&lDISABLED"));
				}
				if (plugin.getConfig().getBoolean("features.community") && plugin.getConfig().getBoolean("enabled")) {
					sender.sendMessage(cc(" &fCommunity: &a&lENABLED"));
				} else {
					sender.sendMessage(cc(" &fCommunity: &c&lDISABLED"));
				}
				sender.sendMessage("");

				if (plugin.getConfig().getBoolean("settings.allow-interfering")) {
					sender.sendMessage(
							cc(" &e&lWARNING:&e Allow-Interfering is enabled, please disable it in the config."));
					sender.sendMessage("");
				}
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
