package me.daivdmajholt.sessentials.commands.fly;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;

public class FlyCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.fly")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(cc(" &cThis command can only be run by a player."));
				return true;
			}

			Player player = (Player) sender;

			player.setAllowFlight(!player.getAllowFlight());
			player.sendMessage(cc((player.getAllowFlight()) ? " &aYour flight has now been enabled." : " &cYour flight has now been disabled."));
		} else {
			Player player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " does not exist!"));
				return true;
			}

			player.setAllowFlight(!player.getAllowFlight());
			sender.sendMessage(cc(" &aYou have now " + (player.getAllowFlight() ? "enabled " : "disabled ") + args[0] + "'s flight."));
			player.sendMessage(cc((player.getAllowFlight()) ? " &aYour flight has now been enabled." : " &cYour flight has now been disabled."));
		}

		return true;
	}
}
