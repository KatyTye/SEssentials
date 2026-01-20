package me.daivdmajholt.sessentials.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.daivdmajholt.sessentials.Utils.cc;

public class JoinMessage implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(cc(" &8[&a&l+&8] &f" + player.getName()));
    }

}