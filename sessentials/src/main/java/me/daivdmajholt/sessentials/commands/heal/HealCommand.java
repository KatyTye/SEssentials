package me.daivdmajholt.sessentials.commands.heal;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;

public class HealCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.heal") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player player = (Player) sender;

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.heal.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don’t have the required permission to heal other players."));
				return true;
			}

			player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}
		}

		double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

		player.setHealth(maxHealth);

		if (sender.getName() != player.getName()) {
			sender.sendMessage(cc(" &aYou have now restored " + player.getName() + " to full health."));
		}

		player.sendMessage(cc(" &aYour health has been fully restored."));

		return true;
	}
}
