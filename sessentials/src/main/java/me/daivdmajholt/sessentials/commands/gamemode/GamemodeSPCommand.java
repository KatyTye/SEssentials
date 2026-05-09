package me.daivdmajholt.sessentials.commands.gamemode;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;

import static me.daivdmajholt.sessentials.Utils.cc;


public class GamemodeSPCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.gamemode")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player player)) {

			if (args.length == 0) {
				sender.sendMessage(cc(" &cUsage: /gamemode (survival/creative/adventure/spectator) (player)"));
				return true;
			}

			if (!(args.length == 1)) {
				sender.sendMessage(cc(" &cPlayer is also required on console."));
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player was not found."));
				return true;
			}

			GameMode gm;
			gm = GameMode.SPECTATOR;

			target.setGameMode(gm);
			sender.sendMessage(cc(" &a" + target.getName() + "'s gamemode is now " + gm.name().toLowerCase()));
			target.sendMessage(cc(" &aYour gamemode has changed to " + gm.name().toLowerCase()));

			return true;
		}

		GameMode gm;
		gm = GameMode.SPECTATOR;

		if (args.length >= 1) {
			Player target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				player.sendMessage(cc(" &cThe player was not found."));
				return true;
			}

			target.setGameMode(gm);
			player.sendMessage(cc(" &a" + target.getName() + "'s gamemode is now " + gm.name().toLowerCase()));
			target.sendMessage(cc(" &aYour gamemode has changed to " + gm.name().toLowerCase()));

			return true;
		}

		player.setGameMode(gm);
		player.sendMessage(cc(" &aYour gamemode has changed to " + gm.name().toLowerCase()));

		return true;
	}
}
