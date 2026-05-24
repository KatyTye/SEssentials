package me.daivdmajholt.sessentials.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.daivdmajholt.sessentials.Main;

public class GodModeDamage implements Listener {
	
	@EventHandler
	public void onDamageOnPlayerGodMode(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();

		if (Main.databaseManager.findGodMode(player)) {
			event.setCancelled(true);
		}
	}

}
