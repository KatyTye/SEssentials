package me.daivdmajholt.sessentials.commands.ban;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class UnBanIpTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> suggestions = new ArrayList<>();

		if (args.length == 1) {
			Bukkit.getBanList(Type.IP).getBanEntries().stream()
					.filter(Objects::nonNull)
					.map(entry -> entry.getTarget())
					.forEach(suggestions::add);
		}

		return suggestions;
	}
}