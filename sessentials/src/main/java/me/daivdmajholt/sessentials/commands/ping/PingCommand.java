package me.daivdmajholt.sessentials.commands.ping;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class PingCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.ping") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player player = (Player) sender;

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.ping.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don’t have the required permission to feed other players."));
				return true;
			}

			player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			sender.sendMessage(cc(" &aThe ping of " + player.getName() + "&f is &f" + player.getPing() + "&a."));
			return true;
		}

		sender.sendMessage(cc(" &aYour ping is &f" + player.getPing() + "&a."));

		return true;
	}
}
