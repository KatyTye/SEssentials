package me.daivdmajholt.sessentials.events;

import me.daivdmajholt.sessentials.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.IOException;
import java.io.File;

public class QuitHandler implements Listener {

	private final Main plugin = Main.plugin;

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event){
		Player player = event.getPlayer();
		event.setQuitMessage(cc(plugin.getConfig().getString("messages.quit") + player.getName()));

		if (plugin.afkPlayers.contains(player.getUniqueId())) {
			plugin.afkPlayers.remove(player.getUniqueId());
		}

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "temp.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		try {
			cfg.set(player.getUniqueId().toString(), null);
			cfg.save(file);
		} catch (IOException e) {
			if (plugin.getConfig().getBoolean("settings.debug-mode")) {
				plugin.getLogger().severe("Could not remove player.");
			}
		}
	}
}
