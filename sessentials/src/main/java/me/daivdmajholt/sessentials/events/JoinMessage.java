package me.daivdmajholt.sessentials.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static me.daivdmajholt.sessentials.Utils.cc;

public class JoinMessage implements Listener {

    private final JavaPlugin plugin;

    public JoinMessage(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(cc(plugin.getConfig().getString("messages.join") + player.getName()));
    }

}