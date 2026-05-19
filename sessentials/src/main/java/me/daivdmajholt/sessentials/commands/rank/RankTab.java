package me.daivdmajholt.sessentials.commands.rank;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daivdmajholt.sessentials.Main;

public class RankTab implements TabCompleter {

	private final Main plugin = Main.plugin;

	File dataFolder = plugin.getDataFolder();
	File file = new File(dataFolder, "ranks.yml");

	FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	List<Integer> ranks = cfg.getIntegerList("ranks");

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> suggestions = new ArrayList<>();
		String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";

		if (args.length == 1) {
			List<String> options = List.of("help", "list", "permissions", "rename", "prefix", "suffix", "color",
					"priority", "permission", "details", "info", "change");
			for (String s : options)
				if (s.startsWith(prefix))
					suggestions.add(s);
			return suggestions;
		}

		if (args.length == 2 && !args[0].equalsIgnoreCase("help")
				&& !args[0].equalsIgnoreCase("list") && !args[0].equalsIgnoreCase("")) {
			for (int s : ranks)
				suggestions.add(String.valueOf(s));
			return suggestions;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("permission")) {
			return List.of("add", "remove");
		}

		if (args.length == 4 && !args[0].equalsIgnoreCase("add") && args[0].equalsIgnoreCase("permission")) {
			List<String> permissions = cfg.getStringList(args[1] + ".permissions");
			return permissions;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("priority")) {
			suggestions.add(String.valueOf(cfg.getInt(args[1] + ".priority")));
			return suggestions;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("color")) {
			suggestions.add(cfg.getString(args[1] + ".color"));
			return suggestions;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("suffix")) {
			suggestions.add(cfg.getString(args[1] + ".suffix"));
			return suggestions;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("prefix")) {
			suggestions.add(cfg.getString(args[1] + ".prefix"));
			return suggestions;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("rename")) {
			suggestions.add(cfg.getString(args[1] + ".name"));
			return suggestions;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("change")) {
			return null;
		}

		return new ArrayList<>();
	}

}
