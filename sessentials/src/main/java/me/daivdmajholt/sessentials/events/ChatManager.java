package me.daivdmajholt.sessentials.events;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.daivdmajholt.database.DatabaseManager.ValueType;
import me.daivdmajholt.sessentials.Main;

public class ChatManager implements Listener {

	private final Main plugin = Main.plugin;

	@EventHandler
	public void onPlayerChatting(AsyncPlayerChatEvent event) {

		event.setCancelled(true);

		Player player = event.getPlayer();

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "ranks.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		String rank = Main.databaseManager.getSpefic("players", "rank",
		"uuid", player.getUniqueId().toString(), ValueType.STRING);
		String message = plugin.getConfig().getString("messages.sender-message");
				
		message = message.replace("%name%", player.getName());
		message = message.replace("%display%", player.getDisplayName());
		message = message.replace("%group%", cfg.getString(rank + ".name"));
		message = message.replace("%color%", cfg.getString(rank + ".color"));
		message = message.replace("%prefix%", cfg.getString(rank + ".prefix"));

		if (cfg.getString(rank + ".prefix").equals("")) {
			message = message.replace("[", "");
			message = message.replace("] ", "");
		}

		if (player.hasPermission("sessentials.chat.colored")) {
			Bukkit.broadcastMessage(cc(message + " &f" + event.getMessage()));
		} else {
			Bukkit.broadcastMessage(cc(message + " &f") + event.getMessage());
		}
	}
}
