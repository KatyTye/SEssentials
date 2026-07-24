package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.commands.give.GiveCommand;
import me.daivdmajholt.sessentials.commands.give.GiveTab;
import me.daivdmajholt.sessentials.commands.god.GodCommand;
import me.daivdmajholt.sessentials.commands.god.GodTab;
import me.daivdmajholt.sessentials.commands.heal.HealCommand;
import me.daivdmajholt.sessentials.commands.kick.KickCommand;
import me.daivdmajholt.sessentials.commands.rank.RankCommand;
import me.daivdmajholt.sessentials.commands.rank.RankTab;
import me.daivdmajholt.sessentials.commands.sessentials.MainCommand;
import me.daivdmajholt.sessentials.commands.sessentials.MainTab;
import me.daivdmajholt.sessentials.commands.spawn.SetSpawnCommand;
import me.daivdmajholt.sessentials.commands.spawn.SpawnCommand;
import me.daivdmajholt.sessentials.commands.spawn.SpawnTab;
import me.daivdmajholt.sessentials.commands.stop.StopCommand;
import me.daivdmajholt.sessentials.commands.warp.WarpCommand;
import me.daivdmajholt.sessentials.commands.warp.WarpTab;
import me.daivdmajholt.sessentials.commands.warp.WarpsCommand;
import me.daivdmajholt.sessentials.commands.NothingTab;
import me.daivdmajholt.sessentials.commands.PlayerTab;
import me.daivdmajholt.sessentials.commands.ban.BanCommand;
import me.daivdmajholt.sessentials.commands.ban.BanTab;
import me.daivdmajholt.sessentials.commands.ban.ListBansCommand;
import me.daivdmajholt.sessentials.commands.ban.UnBanCommand;
import me.daivdmajholt.sessentials.commands.ban.UnBanIpCommand;
import me.daivdmajholt.sessentials.commands.ban.UnBanIpTab;
import me.daivdmajholt.sessentials.commands.ban.UnBanTab;
import me.daivdmajholt.sessentials.commands.clearchat.ClearChatCommand;
import me.daivdmajholt.sessentials.commands.economy.BalanceCommand;
import me.daivdmajholt.sessentials.commands.economy.BalanceTab;
import me.daivdmajholt.sessentials.commands.economy.EconomyCommand;
import me.daivdmajholt.sessentials.commands.economy.EconomyTab;
import me.daivdmajholt.sessentials.commands.feed.FeedCommand;
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

        // PRIMARY/MAIN
        plugin.getCommand("sessentials").setExecutor(new MainCommand());
        plugin.getCommand("sessentials").setTabCompleter(new MainTab());

        // STOP
        plugin.getCommand("stop").setExecutor(new StopCommand());
        plugin.getCommand("stop").setTabCompleter(new NothingTab());

        // BAN/IP BAN
        plugin.getCommand("ban").setExecutor(new BanCommand());
        plugin.getCommand("ban").setTabCompleter(new BanTab());

        plugin.getCommand("bans").setExecutor(new ListBansCommand());
        plugin.getCommand("bans").setTabCompleter(new NothingTab());

        plugin.getCommand("unban").setExecutor(new UnBanCommand());
        plugin.getCommand("unban").setTabCompleter(new UnBanTab());

        plugin.getCommand("unbanip").setExecutor(new UnBanIpCommand());
        plugin.getCommand("unbanip").setTabCompleter(new UnBanIpTab());

        // KICK
        plugin.getCommand("kick").setExecutor(new KickCommand());
        plugin.getCommand("kick").setTabCompleter(new PlayerTab());

        // COMMANDS AFTER CONFIG
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

            // HEAL
            plugin.getCommand("heal").setExecutor(new HealCommand());
            plugin.getCommand("heal").setTabCompleter(new PlayerTab());

            // FEED
            plugin.getCommand("feed").setExecutor(new FeedCommand());
            plugin.getCommand("feed").setTabCompleter(new PlayerTab());

            // FLY
            plugin.getCommand("fly").setExecutor(new FlyCommand());
            plugin.getCommand("fly").setTabCompleter(new FlyTab());

            // GOD
            plugin.getCommand("god").setExecutor(new GodCommand());
            plugin.getCommand("god").setTabCompleter(new GodTab());

            // WARP
            plugin.getCommand("warp").setExecutor(new WarpCommand());
            plugin.getCommand("warp").setTabCompleter(new WarpTab());
            plugin.getCommand("warps").setExecutor(new WarpsCommand());
            plugin.getCommand("warps").setTabCompleter(new NothingTab());

            // CLEARCHAT
            plugin.getCommand("clearchat").setExecutor(new ClearChatCommand());
            plugin.getCommand("clearchat").setTabCompleter(new NothingTab());
            plugin.getCommand("cc").setExecutor(new ClearChatCommand());
            plugin.getCommand("cc").setTabCompleter(new NothingTab());

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
