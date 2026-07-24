package me.daivdmajholt.sessentials.events;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import me.daivdmajholt.sessentials.Main;

public class JoiningBanned implements Listener {

	private final Main plugin = Main.plugin;

	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event) {

		String discordUrl = plugin.getConfig().getString("messages.discord-link");

		String discordMessage = (discordUrl.equalsIgnoreCase("https://discord.gg/xxxxxxxx"))
				? "We currently don't accept appeals."
				: discordUrl;

		BanEntry ban = Bukkit.getBanList(BanList.Type.IP)
				.getBanEntry(event.getAddress().getHostAddress().toString());

		BanEntry nameBan = Bukkit.getBanList(BanList.Type.NAME)
				.getBanEntry(event.getName());

		if (ban != null) {

			String reason = ban.getReason();
			String time;

			if (ban.getExpiration() == null) {
				time = "Never";
			} else {
				time = ban.getExpiration().toString();
			}

			event.disallow(
					AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
					"§cYou are banned from this server!\n\n" +
							"§eExpires: §f" + time + "\n" +
							"§eReason: §f" + reason + "\n" +
							"§eAppeal: §b" + discordMessage);

			Bukkit.getOnlinePlayers().stream()
					.filter(p -> p.hasPermission("sessentials.ban.notify") || p.hasPermission("sessentials.*"))
					.forEach(p -> p.sendMessage("§c" + event.getName() + " tried to join while banned!"));

			Bukkit.getLogger().info(event.getName() + " tried to join while banned.");
		} else if (nameBan != null) {

			String reason = nameBan.getReason();
			String time;

			if (nameBan.getExpiration() == null) {
				time = "Never";
			} else {
				time = nameBan.getExpiration().toString();
			}

			event.disallow(
					AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
					"§cYou are banned from this server!\n\n" +
							"§eExpires: §f" + time + "\n" +
							"§eReason: §f" + reason + "\n" +
							"§eAppeal: §b" + discordMessage);

			Bukkit.getOnlinePlayers().stream()
					.filter(p -> p.hasPermission("sessentials.ban.notify") || p.hasPermission("sessentials.*"))
					.forEach(p -> p.sendMessage("§c" + event.getName() + " tried to join while banned!"));

			Bukkit.getLogger().info(event.getName() + " tried to join while banned.");
		}
	}
}