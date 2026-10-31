package me.daivdmajholt.sessentials.commands.mute;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class MuteCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.mute") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player target = (Player) sender;

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.mute.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don't have the required permission to mute other players."));
				return true;
			}

			target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			if (plugin.mutedPlayers.contains(target.getUniqueId())) {
				plugin.mutedPlayers.remove(target.getUniqueId());
				target.sendMessage(cc(" &aYou are no longer muted."));
				sender.sendMessage(cc(" &aYou have now removed &f" + args[0] + "&a from being mute."));
			} else {
				plugin.mutedPlayers.add(target.getUniqueId());
				target.sendMessage(cc(" &aYou are now muted."));
				sender.sendMessage(cc(" &aYou have now set &f" + args[0] + "&a to being mute."));
			}

			return true;
		}

		if (plugin.mutedPlayers.contains(target.getUniqueId())) {
			plugin.mutedPlayers.remove(target.getUniqueId());
			sender.sendMessage(cc(" &aYou are no longer muted."));
		} else {
			plugin.mutedPlayers.add(target.getUniqueId());
			sender.sendMessage(cc(" &aYou are now muted."));
		}

		return true;
	}
}
