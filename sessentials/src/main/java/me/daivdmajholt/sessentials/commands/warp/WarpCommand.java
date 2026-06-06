package me.daivdmajholt.sessentials.commands.warp;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.database.DatabaseManager.ValueType;
import me.daivdmajholt.sessentials.Main;

public class WarpCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.warp")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cPlease use the full command with /warp (warp name) (player) to teleport."));
			return true;
		}

		if (args.length == 2 && !sender.hasPermission("sessentials.warp.others")) {
			sender.sendMessage(cc(" &cYou don't have the required permission to teleport others."));
			return true;
		}

		if (args.length == 1 && !(sender instanceof Player)) {
			sender.sendMessage(cc(" &cYou also need to enter the players name to teleport."));
			return true;
		}
		
		if (!Main.databaseManager.checkValueFromDB("warps", "name", "name", args[0], ValueType.STRING)) {
			sender.sendMessage(cc(" &cCould not find any warps with that name."));
			return true;
		}

		if (!sender.hasPermission("sessentials.warp.*") && sender.hasPermission("sessentials.warp." + args[0])) {
			sender.sendMessage(cc(" &cYou don't have the required permission to use this warp."));
			return true;
		}

		return true;
	}
	
}
