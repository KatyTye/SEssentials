package me.daivdmajholt.sessentials.commands.vanish;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class VanishCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.vanish") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player target = (Player) sender;

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.vanish.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don’t have the required permission to vanish other players."));
				return true;
			}

			target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			if (plugin.vanishedPlayers.contains(target.getUniqueId())) {
				plugin.vanishedPlayers.remove(target.getUniqueId());
				sender.sendMessage(cc(" &aYou have now removed &f" + args[0] + "&a from vanish."));
			} else {
				plugin.vanishedPlayers.add(target.getUniqueId());
				sender.sendMessage(cc(" &aYou have now added &f" + args[0] + "&a to vanish."));
			}

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (plugin.vanishedPlayers.contains(target.getUniqueId())) {
					player.hidePlayer(plugin, target);
				} else {
					player.showPlayer(plugin, target);
				}
			}

			return true;
		}

		if (plugin.vanishedPlayers.contains(target.getUniqueId())) {
			plugin.vanishedPlayers.remove(target.getUniqueId());
			sender.sendMessage(cc(" &aYou have now exited vanish."));
		} else {
			plugin.vanishedPlayers.add(target.getUniqueId());
			sender.sendMessage(cc(" &aYou are now in vanish."));
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (plugin.vanishedPlayers.contains(target.getUniqueId())) {
				player.hidePlayer(plugin, target);
			} else {
				player.showPlayer(plugin, target);
			}
		}

		return true;
	}
}
