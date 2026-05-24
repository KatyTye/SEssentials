package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.commands.NothingTab;
import me.daivdmajholt.sessentials.commands.buy.BuyCommand;
import me.daivdmajholt.sessentials.commands.discord.DiscordCommand;

public class Community {

	private final Main plugin = Main.plugin;

	public void registerCommunity() {
		if (plugin.getConfig().getBoolean("features.community")) {
			// DISCORD
			plugin.getCommand("discord").setExecutor(new DiscordCommand());
			plugin.getCommand("discord").setTabCompleter(new NothingTab());

			// BUY
			plugin.getCommand("buy").setExecutor(new BuyCommand());
			plugin.getCommand("buy").setTabCompleter(new NothingTab());
		} else {
            plugin.getLogger().info("Community are disabled!");
        }
	}
	
}
