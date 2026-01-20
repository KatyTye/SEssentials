package me.daivdmajholt.sessentials.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.daivdmajholt.sessentials.Utils.cc;

public class LeaveMessage implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(cc(" &8[&c&l-&8] &f" + player.getName()));
    }
}
