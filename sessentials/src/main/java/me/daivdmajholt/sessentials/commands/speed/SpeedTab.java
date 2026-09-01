package me.daivdmajholt.sessentials.commands.speed;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class SpeedTab implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)  {
		List<String> suggestions = new ArrayList<>();

		if (args.length == 1) {
			List<String> options = List.of("1","2","3","4","5","6","7","8","9");
			for (String s : options)
				if (s.startsWith(args[args.length - 1]))
					suggestions.add(s);
			return suggestions;
		}

		return (args.length == 2) ? null : new ArrayList<>();
	}
}
