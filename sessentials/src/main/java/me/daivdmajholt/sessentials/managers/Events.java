package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.events.ChatManager;
import me.daivdmajholt.sessentials.events.JoinHandler;
import me.daivdmajholt.sessentials.events.QuitHandler;
import me.daivdmajholt.sessentials.events.SpawnPlayer;
import me.daivdmajholt.sessentials.events.ForceRespawn;
import me.daivdmajholt.sessentials.events.GodModeDamage;
import me.daivdmajholt.sessentials.events.JoiningBanned;
import me.daivdmajholt.sessentials.events.MovementListener;

public class Events {

    private final Main plugin = Main.plugin;

    public void reqiesterEvents() {

        plugin.getServer().getPluginManager().registerEvents(new JoiningBanned(), plugin);

        if (plugin.getConfig().getBoolean("features.events")) {
            plugin.getServer().getPluginManager().registerEvents(new JoinHandler(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new QuitHandler(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new ChatManager(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new SpawnPlayer(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new ForceRespawn(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new GodModeDamage(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new MovementListener(), plugin);
        } else {
            plugin.getLogger().info("Events are disabled!");
        }
    }
}