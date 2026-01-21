package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.commands.gamemode.*;
import me.daivdmajholt.sessentials.commands.give.GiveCommand;
import me.daivdmajholt.sessentials.commands.give.GiveTab;
import org.bukkit.plugin.java.JavaPlugin;

import me.daivdmajholt.sessentials.commands.gamemode.GamemodeACommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeCCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeMiniTab;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeSCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeSPCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeTab;
import static me.daivdmajholt.sessentials.Utils.reqNN;


public class Commands {

    private final JavaPlugin plugin;

    public Commands(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {

        if (plugin.getConfig().getBoolean("features.commands")) {
            // GAMEMODES
            reqNN(plugin.getCommand("gmc")).setExecutor(new GamemodeCCommand());
            reqNN(plugin.getCommand("gma")).setExecutor(new GamemodeACommand());
            reqNN(plugin.getCommand("gms")).setExecutor(new GamemodeSCommand());
            reqNN(plugin.getCommand("gmsp")).setExecutor(new GamemodeSPCommand());
            reqNN(plugin.getCommand("gmc")).setTabCompleter(new GamemodeMiniTab());
            reqNN(plugin.getCommand("gma")).setTabCompleter(new GamemodeMiniTab());
            reqNN(plugin.getCommand("gms")).setTabCompleter(new GamemodeMiniTab());
            reqNN(plugin.getCommand("gmsp")).setTabCompleter(new GamemodeMiniTab());
            reqNN(plugin.getCommand("gm")).setExecutor(new GamemodeCommand());
            reqNN(plugin.getCommand("gm")).setTabCompleter(new GamemodeTab());
            reqNN(plugin.getCommand("gamemode")).setExecutor(new GamemodeCommand());
            reqNN(plugin.getCommand("gamemode")).setTabCompleter(new GamemodeTab());
        } else {
            plugin.getLogger().info("Commands are disabled!");
        }
    }
}
