package me.daivdmajholt.sessentials.commands.smite;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class SmiteCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!sender.hasPermission("sessentials.smite") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter the name of the player to be smited."));
			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
			return true;
		}

		sender.sendMessage(cc(" &aYou have now smited the player named &f" + args[0] + "&a."));
		target.getWorld().strikeLightning(target.getLocation());

		return true;
	}
}
