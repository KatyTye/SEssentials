package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.commands.give.GiveCommand;
import me.daivdmajholt.sessentials.commands.give.GiveTab;
import me.daivdmajholt.sessentials.commands.sessentials.MainCommand;
import me.daivdmajholt.sessentials.commands.sessentials.MainTab;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeACommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeCCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeMiniTab;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeSCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeSPCommand;
import me.daivdmajholt.sessentials.commands.gamemode.GamemodeTab;

public class Commands {

    private final Main plugin = Main.plugin;

    public void registerCommands() {

        plugin.getCommand("sessentials").setExecutor(new MainCommand());
        plugin.getCommand("sessentials").setTabCompleter(new MainTab());

        if (plugin.getConfig().getBoolean("features.commands")) {
            // GAMEMODES
            plugin.getCommand("gmc").setExecutor(new GamemodeCCommand());
            plugin.getCommand("gms").setExecutor(new GamemodeSCommand());
            plugin.getCommand("gma").setExecutor(new GamemodeACommand());
            plugin.getCommand("gmsp").setExecutor(new GamemodeSPCommand());
            plugin.getCommand("gmc").setTabCompleter(new GamemodeMiniTab());
            plugin.getCommand("gma").setTabCompleter(new GamemodeMiniTab());
            plugin.getCommand("gms").setTabCompleter(new GamemodeMiniTab());
            plugin.getCommand("gmsp").setTabCompleter(new GamemodeMiniTab());
            plugin.getCommand("gm").setExecutor(new GamemodeCommand());
            plugin.getCommand("gm").setTabCompleter(new GamemodeTab());
            plugin.getCommand("gamemode").setExecutor(new GamemodeCommand());
            plugin.getCommand("gamemode").setTabCompleter(new GamemodeTab());

            // GIVE
            plugin.getCommand("give").setExecutor(new GiveCommand());
            plugin.getCommand("give").setTabCompleter(new GiveTab());
        } else {
            plugin.getLogger().info("Commands are disabled!");
        }
    }
}
