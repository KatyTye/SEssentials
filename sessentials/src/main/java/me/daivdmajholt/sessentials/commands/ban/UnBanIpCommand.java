package me.daivdmajholt.sessentials.commands.ban;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;

public class UnBanIpCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.ban.remove") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter the player's uuid."));
			return true;
		}

		if (!Bukkit.getBanList(Type.IP).isBanned(args[0])) {
			sender.sendMessage(cc(" &cThis player is not currently banned."));
			return true;
		}

		Bukkit.getBanList(Type.IP).pardon(args[0]);

		sender.sendMessage(cc(" &aYou have now unbanned the player with the uuid &f" + args[0] + "&a."));

		return true;
	}
}
