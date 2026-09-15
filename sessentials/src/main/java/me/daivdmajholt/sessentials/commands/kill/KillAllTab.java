package me.daivdmajholt.sessentials.commands.kill;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class KillAllTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> suggestions = new ArrayList<>();
		String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";

		List<String> options = List.of("players", "mobs", "monsters", "entitys", "staff");

		for (String s : options)
			if (s.startsWith(prefix))
				suggestions.add(s);

		return suggestions;
	}
}
