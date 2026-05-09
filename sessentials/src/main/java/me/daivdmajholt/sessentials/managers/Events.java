package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.events.JoinMessage;
import me.daivdmajholt.sessentials.events.LeaveMessage;

public class Events {

    private final Main plugin = Main.plugin;

    public void reqiesterEvents() {

        if (plugin.getConfig().getBoolean("features.events")) {
            plugin.getServer().getPluginManager().registerEvents(new JoinMessage(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new LeaveMessage(), plugin);
        } else {
            plugin.getLogger().info("Events are disabled!");
        }
    }
}