package me.daivdmajholt.sessentials.commands.afk;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class AfkCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.afk") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player target = (Player) sender;

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.afk.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don't have the required permission to afk other players."));
				return true;
			}

			target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			if (plugin.afkPlayers.contains(target.getUniqueId())) {
				plugin.afkPlayers.remove(target.getUniqueId());
				target.sendMessage(cc(" &aYou are no longer afk."));
				sender.sendMessage(cc(" &aYou have now removed &f" + args[0] + "&a from being afk."));
			} else {
				plugin.afkPlayers.add(target.getUniqueId());
				target.sendMessage(cc(" &aYou are now afk."));
				sender.sendMessage(cc(" &aYou have now set &f" + args[0] + "&a to being afk."));
			}

			return true;
		}

		if (plugin.afkPlayers.contains(target.getUniqueId())) {
			plugin.afkPlayers.remove(target.getUniqueId());
			sender.sendMessage(cc(" &aYou are no longer afk."));
		} else {
			plugin.afkPlayers.add(target.getUniqueId());
			sender.sendMessage(cc(" &aYou are now afk."));
		}

		return true;
	}
}
