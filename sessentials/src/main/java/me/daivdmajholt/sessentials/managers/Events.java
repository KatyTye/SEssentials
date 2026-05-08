package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.events.JoinMessage;
import me.daivdmajholt.sessentials.events.LeaveMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class Events {

    private final Main plugin = Main.plugin;

    public void reqiesterEvents() {
        plugin.getServer().getPluginManager().registerEvents(new JoinMessage(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new LeaveMessage(), plugin);
    }
}