package me.daivdmajholt.sessentials.commands.spawn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daivdmajholt.sessentials.Main;

public class SetSpawnTab implements TabCompleter {

	private final Main plugin = Main.plugin;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)  {
		
		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "locations.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (args.length == 1) {
			List<String> returnString = cfg.getStringList("spawns");
			returnString.add("(rank name)");

            return returnString;
        } else if (args.length == 2) {
			return  List.of("(price)");
		} else if (args.length == 3) return List.of("(cooldown in seconds)");

		return new ArrayList<>();
	}
}
