package me.daivdmajholt.sessentials.commands.warp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.daivdmajholt.database.DatabaseManager.ValueType;
import me.daivdmajholt.sessentials.Main;

public class WarpTab implements TabCompleter {

	List<String> commands = List.of("info", "relocate", "delete", "create", "list");

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<Object> warps = Main.databaseManager.getAllValuesFromDB("warps", "name", ValueType.STRING);
		String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";
		List<String> suggestions = new ArrayList<>();

		if (args.length == 1) {
			if (sender.hasPermission("sessentials.warp.edit") || sender.hasPermission("sessentials.*")) {
				warps.addAll(commands);
			}

			for (Object s : warps)
				if (s.toString().startsWith(prefix))
					suggestions.add(s.toString());
			return suggestions;
		}

		if (args.length == 2 && (args[0].equals("delete") || args[0].equals("info") || args[0].equals("relocate"))) {
			for (Object s : warps)
				if (s.toString().startsWith(prefix))
					suggestions.add(s.toString());
			return suggestions;
		}

		return new ArrayList<>();
	}
}
