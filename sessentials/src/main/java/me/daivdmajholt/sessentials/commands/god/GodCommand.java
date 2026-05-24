package me.daivdmajholt.sessentials.commands.god;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;

public class GodCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.god")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(cc(" &cThis command can only be run by a player."));
				return true;
			}

			Player player = (Player) sender;

			Main.databaseManager.setGodMode(player, !Main.databaseManager.findGodMode(player));
			player.sendMessage(cc((Main.databaseManager.findGodMode(player)) ? " &aYour god mode has now been enabled." : " &cYour god mode has now been disabled."));
		} else {
			Player player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " does not exist!"));
				return true;
			}

			Main.databaseManager.setGodMode(player, !Main.databaseManager.findGodMode(player));
			sender.sendMessage(cc(" &aYou have now " + (Main.databaseManager.findGodMode(player) ? "enabled " : "disabled ") + args[0] + "'s god mode."));
			player.sendMessage(cc((Main.databaseManager.findGodMode(player)) ? " &aYour god mode has now been enabled." : " &cYour god mode has now been disabled."));
		}

		return true;
	}
}
