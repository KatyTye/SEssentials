package me.daivdmajholt.sessentials.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.daivdmajholt.sessentials.Main;

public class ForceRespawn implements Listener {
	
	private final Main plugin = Main.plugin;

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		Player player = event.getEntity();

		if (plugin.getConfig().getBoolean("settings.force-respawn")) {
			Bukkit.getScheduler().runTask(plugin, () -> {
				player.spigot().respawn();
			});
		}
	}
}
