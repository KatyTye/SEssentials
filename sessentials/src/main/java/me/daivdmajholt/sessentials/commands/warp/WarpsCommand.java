package me.daivdmajholt.sessentials.commands.warp;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.database.DatabaseManager.ValueType;

public class WarpsCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.warp.list") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		List<Object> warps = Main.databaseManager.getAllValuesFromDB("warps", "name",
				ValueType.STRING);
		String listedWarps = warps.toString().replace("[", "").replace("]", "");

		if (warps.size() == 0) {
			sender.sendMessage(cc(" &cThere are currently no warps available on this server."));
			return true;
		}

		sender.sendMessage("");
		sender.sendMessage(cc(" &6&lWARPS:"));
		sender.sendMessage(cc(" &f" + listedWarps));
		sender.sendMessage("");

		return true;
	}
}
