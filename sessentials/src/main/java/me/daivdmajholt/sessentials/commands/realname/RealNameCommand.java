package me.daivdmajholt.sessentials.commands.realname;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class RealNameCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.realname") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(cc(" &cYou need to use the full command as the console."));
			return true;
		}

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.realname.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don’t have the required permission to feed other players."));
				return true;
			}

			Player player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			sender.sendMessage(cc(" &aThe players' real username is &f" + player.getName() + "&a."));
			return true;
		}

		Player player = (Player) sender;

		sender.sendMessage(cc(" &aThe players' real username is &f" + player.getName() + "&a."));

		return true;
	}
}
