package me.daivdmajholt.sessentials.commands.kick;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;

public class KickCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.kick")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter the player's name."));
			return true;
		}

		Player player = Bukkit.getPlayer(args[0]);

		if (player == null) {
			sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
			return true;
		}

		String message = "";

		for (int i = 1; i < args.length; i++) {
			if (i > 1) message += " " + args[i];
		}

		String kickMessage = (args.length == 1) ? "KICKED BY " + sender.getName().toUpperCase() + ": No reason was provided."
		: "KICKED BY " + sender.getName().toUpperCase() + ":" + message;
		
		player.kickPlayer(kickMessage);
		sender.sendMessage(cc(" &aYou have now kicked the player named &f" + args[0] + "&a."));

		return true;
	}
}
