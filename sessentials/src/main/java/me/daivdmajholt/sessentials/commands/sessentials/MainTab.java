package me.daivdmajholt.sessentials.commands.sessentials;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandSender;

public class MainTab implements TabCompleter {
	
	@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> suggestions = new ArrayList<>();
		String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";

		if (args.length == 1) {
			List<String> options = List.of("help","reload", "build","status"); //,"edit","toggle"
			for (String s : options) if (s.startsWith(prefix)) suggestions.add(s);
		}

		// if (args.length == 2) {
		// 	List<String> options = List.of("join","leave","events","commands");
		// 	for (String s : options) if (s.startsWith(prefix)) suggestions.add(s);
		// }

		return suggestions;
	}
}
