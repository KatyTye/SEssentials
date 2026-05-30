package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.commands.give.GiveCommand;
import me.daivdmajholt.sessentials.commands.give.GiveTab;
import me.daivdmajholt.sessentials.commands.god.GodCommand;
import me.daivdmajholt.sessentials.commands.god.GodTab;
import me.daivdmajholt.sessentials.commands.rank.RankCommand;
import me.daivdmajholt.sessentials.commands.rank.RankTab;
import me.daivdmajholt.sessentials.commands.sessentials.MainCommand;
import me.daivdmajholt.sessentials.commands.sessentials.MainTab;
import me.daivdmajholt.sessentials.commands.spawn.SetSpawnCommand;
import me.daivdmajholt.sessentials.commands.spawn.SpawnCommand;
import me.daivdmajholt.sessentials.commands.spawn.SpawnTab;
import me.daivdmajholt.sessentials.commands.warp.WarpCommand;
import me.daivdmajholt.sessentials.commands.warp.WarpTab;
import me.daivdmajholt.sessentials.commands.NothingTab;
import me.daivdmajholt.sessentials.commands.economy.BalanceCommand;
import me.daivdmajholt.sessentials.commands.economy.BalanceTab;
import me.daivdmajholt.sessentials.commands.economy.EconomyCommand;
import me.daivdmajholt.sessentials.commands.economy.EconomyTab;
import me.daivdmajholt.sessentials.commands.fly.FlyCommand;
import me.daivdmajholt.sessentials.commands.fly.FlyTab;
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

            // ECONOMY
            plugin.getCommand("bal").setExecutor(new BalanceCommand());
            plugin.getCommand("bal").setTabCompleter(new BalanceTab());
            plugin.getCommand("balance").setExecutor(new BalanceCommand());
            plugin.getCommand("balance").setTabCompleter(new BalanceTab());

            plugin.getCommand("eco").setExecutor(new EconomyCommand());
            plugin.getCommand("eco").setTabCompleter(new EconomyTab());
            plugin.getCommand("economy").setExecutor(new EconomyCommand());
            plugin.getCommand("economy").setTabCompleter(new EconomyTab());

            // RANK
            plugin.getCommand("rm").setExecutor(new RankCommand());
            plugin.getCommand("rm").setTabCompleter(new RankTab());
            plugin.getCommand("rankmanager").setExecutor(new RankCommand());
            plugin.getCommand("rankmanager").setTabCompleter(new RankTab());

            // FLY
            plugin.getCommand("fly").setExecutor(new FlyCommand());
            plugin.getCommand("fly").setTabCompleter(new FlyTab());

            // GOD
            plugin.getCommand("god").setExecutor(new GodCommand());
            plugin.getCommand("god").setTabCompleter(new GodTab());

            // WARP
            plugin.getCommand("warp").setExecutor(new WarpCommand());
            plugin.getCommand("warp").setTabCompleter(new WarpTab());

            // SPAWN
            plugin.getCommand("spawn").setExecutor(new SpawnCommand());
            plugin.getCommand("spawn").setTabCompleter(new SpawnTab());
            plugin.getCommand("setspawn").setExecutor(new SetSpawnCommand());
            plugin.getCommand("setspawn").setTabCompleter(new NothingTab());
        } else {
            plugin.getLogger().info("Commands are disabled!");
        }
    }
}
