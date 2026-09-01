package me.daivdmajholt.sessentials.commands.online;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class OnlineStaffCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.online.staff") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		int onlineStaff = 0;

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("sessentials.staff") || player.hasPermission("sessentials.*")) {
				onlineStaff++;
			}
		}

		sender.sendMessage(cc(" &6Online Staff: &f" + onlineStaff));

		return true;
	}
}
