package me.daivdmajholt.sessentials.Disables;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BanIp implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/ban-ip")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(cc(" &cThis command is disabled by SEssentials."));
		}
	}
}
