package me.daivdmajholt.sessentials.commands.clearchat;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;

public class ClearChatCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.chat.clear")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			for (int i = 0; i < 100; i++) {
				player.sendMessage("");
			}
		}

		return true;
	}
}
