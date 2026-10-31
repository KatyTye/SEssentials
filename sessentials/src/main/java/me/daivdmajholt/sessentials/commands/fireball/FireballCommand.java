package me.daivdmajholt.sessentials.commands.fireball;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class FireballCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.fireball") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		Player target = (Player) sender;

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.afk.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don't have the required permission to afk other players."));
				return true;
			}

			target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}
		}

		Location location = target.getEyeLocation();

		Fireball fireball = target.getWorld().spawn(location, Fireball.class);

		Vector direction = location.getDirection();

		fireball.setDirection(direction);
		fireball.setShooter(target);
		fireball.setYield(2.0F);
		fireball.setIsIncendiary(true);

		return true;
	}
}
