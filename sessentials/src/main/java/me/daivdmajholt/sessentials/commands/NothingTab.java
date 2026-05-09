package me.daivdmajholt.sessentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class NothingTab implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)  {
		return new ArrayList<>();
	}
}
