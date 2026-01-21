package me.daivdmajholt.sessentials.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static me.daivdmajholt.sessentials.Utils.cc;

public class LeaveMessage implements Listener {

    private final JavaPlugin plugin;

    public LeaveMessage(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(cc(plugin.getConfig().getString("messages.quit") + player.getName()));
    }
}
