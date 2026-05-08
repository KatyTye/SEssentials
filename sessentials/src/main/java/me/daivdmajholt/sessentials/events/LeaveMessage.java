package me.daivdmajholt.sessentials.events;

import me.daivdmajholt.sessentials.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.daivdmajholt.sessentials.Utils.cc;

public class LeaveMessage implements Listener {

    private final Main plugin = Main.plugin;

    @EventHandler
    public void onPlayerJoinEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(cc(plugin.getConfig().getString("messages.quit") + player.getName()));
    }
}
