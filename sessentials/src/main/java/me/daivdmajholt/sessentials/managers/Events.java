package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.events.ChatManager;
import me.daivdmajholt.sessentials.events.ForceRespawn;
import me.daivdmajholt.sessentials.events.GodModeDamage;
import me.daivdmajholt.sessentials.events.JoinMessage;
import me.daivdmajholt.sessentials.events.JoiningBanned;
import me.daivdmajholt.sessentials.events.LeaveMessage;
import me.daivdmajholt.sessentials.events.SpawnPlayer;

public class Events {

    private final Main plugin = Main.plugin;

    public void reqiesterEvents() {

        plugin.getServer().getPluginManager().registerEvents(new JoiningBanned(), plugin);

        if (plugin.getConfig().getBoolean("features.events")) {
            plugin.getServer().getPluginManager().registerEvents(new JoinMessage(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new LeaveMessage(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new ChatManager(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new ForceRespawn(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new SpawnPlayer(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new GodModeDamage(), plugin);
        } else {
            plugin.getLogger().info("Events are disabled!");
        }
    }
}