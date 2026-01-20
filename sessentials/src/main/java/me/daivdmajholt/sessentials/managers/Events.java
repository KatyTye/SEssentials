package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.events.JoinMessage;
import me.daivdmajholt.sessentials.events.LeaveMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class Events {

    private final JavaPlugin plugin;

    public Events(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void reqiesterEvents() {
        plugin.getServer().getPluginManager().registerEvents(new JoinMessage(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new LeaveMessage(), plugin);
    }
}