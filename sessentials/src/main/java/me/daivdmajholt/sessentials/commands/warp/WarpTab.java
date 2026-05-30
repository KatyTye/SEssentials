package me.daivdmajholt.sessentials.commands.warp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.daivdmajholt.sessentials.Main;

public class WarpTab implements TabCompleter {

	List<String> warps = Main.databaseManager.getAllWarps();

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";
		List<String> suggestions = new ArrayList<>();

		if (args.length == 2) {
			return null;
		}

		if (args.length == 1) {
			for (String s : warps)
				if (s.startsWith(prefix))
					suggestions.add(s);
			return suggestions;
		}

		return new ArrayList<>();
	}
	
}
