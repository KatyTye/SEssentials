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
        String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";

		if (args.length == 1) {
            List<String> options = List.of("survival","creative","adventure","spectator");
            for (String s : options) if (s.startsWith(prefix)) suggestions.add(s);
		}

		if (args.length == 2) {
			return null;
		}

		return suggestions;
	}
}
