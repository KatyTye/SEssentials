package me.daivdmajholt.sessentials.commands.economy;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.daivdmajholt.sessentials.Main;

public class BalanceCommand implements CommandExecutor  {

    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(cc(" &cThe console can only check players balance."));
				sender.sendMessage(cc(" &cPlease use the command correctly: /bal (player)"));
				return true;
			}

			sender.sendMessage(cc(" &aYou currently have " + Main.databaseManager.getBalance((Player) sender, null) + "$ in your balance."));
		} else {
			Player player = Bukkit.getPlayer(args[0]);

			if (player == null) {

				var found = false;

				for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					if (p.getName() != null && p.getName().equalsIgnoreCase(args[0])) {
						found = true;
						sender.sendMessage(cc(" &aThe balance of " + p.getName() + " is currently " + Main.databaseManager.getBalance(null, p.getUniqueId().toString()) + "$."));
						break;
					}
				}

				if (!found) sender.sendMessage(cc(" &cThe player named " + args[0] + " does not exist!"));
				return true;
			}

			sender.sendMessage(cc(" &aThe balance of " + player.getName() + " is currently " + Main.databaseManager.getBalance(player, null) + "$."));
		}

		return true;
	}
}
