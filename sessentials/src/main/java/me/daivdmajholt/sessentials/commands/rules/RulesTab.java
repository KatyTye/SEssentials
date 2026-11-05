package me.daivdmajholt.sessentials.commands.rules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.daivdmajholt.sessentials.Main;

public class RulesTab implements TabCompleter {

	private final Main plugin = Main.plugin;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)  {

		if (args.length == 1 && !plugin.getConfig().getBoolean("settings.linked-rules")) {
			return List.of("link");
		}

		return new ArrayList<>();
	}
}
