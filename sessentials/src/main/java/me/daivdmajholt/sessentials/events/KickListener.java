package me.daivdmajholt.sessentials.events;

import java.util.Locale;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event) {
		String reason = event.getReason();

		if (reason != null && reason.toLowerCase(Locale.ROOT).contains("idle")) {
			event.setCancelled(true);
		}
	}
}
