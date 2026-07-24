package me.daivdmajholt.sessentials.commands.ban;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.util.Date;
import java.time.Instant;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;

public class BanCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.ban") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter the player's name."));
			return true;
		}

		Player player = Bukkit.getPlayer(args[0]);

		String banMessage = (args.length <= 2)
				? "No reason was provided."
				: String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replace("\\", "");

		Date expires = null;

		if (args.length > 1 && (!args[1].equals("0") && !args[1].equalsIgnoreCase("(time in minutes)"))) {
			try {
				int time = Integer.parseInt(args[1]);
				expires = Date.from(Instant.now().plusSeconds(time * 60));
			} catch (NumberFormatException e) {
				sender.sendMessage(cc("&cYou have entered an invalid time, please use only numbers."));
				return true;
			}
		}

		if (Bukkit.getBanList(Type.NAME).isBanned(args[0].toLowerCase())) {
			sender.sendMessage(cc(" &cThis player is already banned."));
			return true;
		}

		if (player == null) {
			Bukkit.getBanList(Type.NAME)
					.addBan(args[0], banMessage, expires, sender.getName());
		} else {

			if (player.hasPermission("sessentials.ban.immunity") || player.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cThis player cannot be banned or kicked from this server."));
				return true;
			}

			Bukkit.getBanList(Type.IP)
					.addBan(player.getAddress().getAddress().getHostAddress().toString(), banMessage, expires,
							sender.getName());

			Bukkit.getBanList(Type.NAME)
					.addBan(args[0], banMessage, expires, sender.getName());

			player.kickPlayer(
					cc("&cYou have been banned!\n\n&7Reason: &f" + banMessage));
		}

		sender.sendMessage(cc(" &aYou have now banned the player named &f" + args[0] + "&a."));
		return true;
	}
}
