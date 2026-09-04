package me.daivdmajholt.sessentials.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class TeleportAllCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.teleport.all") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(cc(" &cYou need to use the full command as the console."));
			return true;
		}

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.teleport.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don't have the required permission to teleport all players to another player."));
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!target.getName().equals(player.getName())) {
					player.teleport(target.getLocation());
				}
			}

			sender.sendMessage(cc(" &aYou have now teleported all players to &f" + args[0] + "'s&a location."));
			return true;
		}

		Player target = (Player) sender;

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!target.getName().equals(player.getName())) {
				player.teleport(target.getLocation());
			}
		}

		sender.sendMessage(cc(" &aYou have now teleported all players to your location."));

		return true;
	}
}
