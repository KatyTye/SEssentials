package me.daivdmajholt.sessentials.commands.ban;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;

public class ListBansCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.ban.list")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		String bannedPlayers = Bukkit.getBanList(Type.NAME).getBanEntries().stream()
				.map(entry -> entry.getTarget())
				.collect(Collectors.joining(", "));

		String safePlayerList = bannedPlayers.isEmpty() ? "No players have been banned yet." : bannedPlayers;

		sender.sendMessage(cc(" &c&lBANNED: &f" + safePlayerList));

		return true;
	}
}
