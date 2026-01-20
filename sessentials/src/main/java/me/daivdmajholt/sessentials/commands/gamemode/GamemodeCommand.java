package me.daivdmajholt.sessentials.commands.gamemode;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.daivdmajholt.sessentials.Utils.cc;


public class GamemodeCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player player)) {

			if (args.length == 0) {
				sender.sendMessage(cc(" &cUsage: /gamemode (survival/creative/adventure/spectator) (player)"));
				return true;
			}

			if (!(args.length == 2)) {
				sender.sendMessage(cc(" &cPlayer is also required on console."));
				return true;
			}

			Player target = Bukkit.getPlayer(args[1]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player was not found."));
				return true;
			}

			GameMode gm;
			switch (args[0].toLowerCase()) {
				case "s", "survival" -> gm = GameMode.SURVIVAL;
				case "c", "creative" -> gm = GameMode.CREATIVE;
				case "a", "adventure" -> gm = GameMode.ADVENTURE;
				case "sp", "spectator" -> gm = GameMode.SPECTATOR;
				default -> {
					sender.sendMessage(cc(" &cUnknown gamemode."));
					return true;
				}
			}

			target.setGameMode(gm);
			sender.sendMessage(cc(" &a" + target.getName() + "'s gamemode is now " + gm.name().toLowerCase()));
			target.sendMessage(cc(" &aYour gamemode has changed to &f" + gm.name().toLowerCase()));

			return true;
		}

		if (!sender.hasPermission("sessentials.gamemode")) {
			sender.sendMessage(cc(" &cYou don't have the required permission to use this command."));
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(cc(" &cUsage: /gamemode (survival/creative/adventure/spectator) (player)"));
			return true;
		}

		GameMode gm;
		switch (args[0].toLowerCase()) {
			case "s", "survival" -> gm = GameMode.SURVIVAL;
			case "c", "creative" -> gm = GameMode.CREATIVE;
			case "a", "adventure" -> gm = GameMode.ADVENTURE;
			case "sp", "spectator" -> gm = GameMode.SPECTATOR;
			default -> {
				player.sendMessage(cc(" &cUnknown gamemode."));
				return true;
			}
		}

		if (args.length >= 2) {
			Player target = Bukkit.getPlayer(args[1]);

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
