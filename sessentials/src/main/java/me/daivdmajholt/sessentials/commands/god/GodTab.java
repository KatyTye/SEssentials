package me.daivdmajholt.sessentials.commands.god;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class GodTab implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)  {

		if (args.length == 1) {
			return null;
		}

		return new ArrayList<>();
	}
}
