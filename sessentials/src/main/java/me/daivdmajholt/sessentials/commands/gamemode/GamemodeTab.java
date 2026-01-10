package me.daivdmajholt.sessentials.commands.gamemode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class GamemodeTab implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> suggestions = new ArrayList<>();

		if (args.length == 1) {
			suggestions.add("survival");
			suggestions.add("creative");
			suggestions.add("adventure");
			suggestions.add("spectator");
			return suggestions;
		}

		if (args.length == 2) {
			return null;
		}

		return suggestions;
	}
}
