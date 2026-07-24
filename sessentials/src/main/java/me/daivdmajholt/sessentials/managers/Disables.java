package me.daivdmajholt.sessentials.managers;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.sessentials.Disables.BanIp;

public class Disables {

	private final Main plugin = Main.plugin;

	public void registerDisables() {
		if (!plugin.getConfig().getBoolean("settings.allow-interfering")) {
			// COMMANDS
			plugin.getServer().getPluginManager().registerEvents(new BanIp(), plugin);
		} else {
			plugin.getLogger().warning("Allow-Interfering is enabled, please disable it!");
			plugin.getLogger()
					.warning("NOTE: This can allow some commands and events can run outside the plugins features.");
			plugin.getLogger().warning(
					"NOTE: This does not affect the plugin in any way; it only allows built-in server features to, for example, hide IP-banned players from the ban list.");
		}
	}

}
