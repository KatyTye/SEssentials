package me.daivdmajholt.sessentials.commands.speed;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class SpeedCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.speed") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter your speed for flying/running."));
			return true;
		}

		float speed = Float.parseFloat(args[0]);

		if (!(sender instanceof Player) && args.length <= 1) {
			sender.sendMessage(cc(" &cYou need to use the full command as the console."));
			return true;
		}

		if (args.length >= 2) {

			if (!sender.hasPermission("sessentials.speed.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don’t have the required permission to feed other players."));
				return true;
			}

			Player player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			if (player.isFlying()) {
				player.setFlySpeed(speed/10);
				sender.sendMessage(cc(" &aChanged &f" + player.getName() + "'s&a flight speed to &f" + speed + "&a."));
				return true;
			}

			player.setWalkSpeed(speed/10);
			sender.sendMessage(cc(" &aChanged &f" + player.getName() + "'s&a walking speed to &f" + speed + "&a."));
			return true;
		}

		Player player = (Player) sender;

		if (player.isFlying()) {
			player.setFlySpeed(speed/10);
			sender.sendMessage(cc(" &aChanged your flight speed to &f" + speed + "&a."));
			return true;
		}

		player.setWalkSpeed(speed/10);
		sender.sendMessage(cc(" &aChanged your walking speed to &f" + speed + "&a."));

		return true;
	}
}
