package me.daivdmajholt.sessentials.commands.ban;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class BanTab implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)  {
		List<String> suggestions = new ArrayList<>();

		if (args.length == 1) {
			return null;
		} else if (args.length == 2) {
			return List.of("(time in minutes)", "0");
		} else if (args.length >= 3) {
			List<String> options = List.of("Breaking server rules", "Inappropriate behavior", "Toxic behavior", "Harassment", "Excessive swearing",
			"Disrespecting staff", "Advertising", "Spamming", "Chat abuse", "Impersonation", "Using cheats (multiple)", "Using cheats (X-ray)",
			"Using cheats (Flying)", "Using cheats (Speed)", "Using cheats (Combat)", "Using cheats (Auto-clicker)", "Using cheats (Exploiting/Abusing glitches)",
			"Griefing", "Ban evasion", "Lag machines/Crashing the server");

			String search = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

			for (String s : options) if (s.toLowerCase().startsWith(search.toLowerCase())) suggestions.add(s);
			return suggestions;
		}

		return new ArrayList<>();
	}
}