package me.daivdmajholt.sessentials.events;

import org.bukkit.event.Listener;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.daivdmajholt.sessentials.Main;

public class MovementListener implements Listener {

	private final Main plugin = Main.plugin;
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		if (plugin.afkPlayers.contains(event.getPlayer().getUniqueId())) {
            plugin.afkPlayers.remove(event.getPlayer().getUniqueId());
			event.getPlayer().sendMessage(cc(" &aYou are no longer afk."));
        }
	}
}
