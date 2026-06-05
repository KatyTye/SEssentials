package me.daivdmajholt.sessentials.commands.economy;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.database.DatabaseManager.ValueType;
import me.daivdmajholt.sessentials.Main;

public class EconomyCommand implements CommandExecutor  {

	private final Main plugin = Main.plugin;

    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.economy")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage("");
			sender.sendMessage(cc(" &f&m                                  &f"));
			sender.sendMessage("");
			sender.sendMessage(cc(" &6/economy set (player) (amount) &7- Sets players balance to specific amount."));
			sender.sendMessage(cc(" &6/economy give (player) (amount) &7- Adds specific amount to player's balance."));
			sender.sendMessage(cc(" &6/economy remove (player) (amount) &7- Removes a specific amount from the players balance."));
			sender.sendMessage("");
			sender.sendMessage(cc(" &f&m                                  &f"));
			sender.sendMessage("");
			return true;
		}

		if (args.length <= 2) {
			sender.sendMessage(cc(" &cPlease use the full command."));
			return true;
		}

		if (!args[0].equalsIgnoreCase("give") && !args[0].equalsIgnoreCase("set") && !args[0].equalsIgnoreCase("remove")) {
			sender.sendMessage(cc(" &cUnknown subcommand, please use the command correctly."));
			return true;
		}

		double amount;

		try {
			amount = Double.parseDouble(args[2]);
		} catch (NumberFormatException e) {
			sender.sendMessage(cc(" &cYou have entered a invalid number."));
			return true;
		}

		Player player = Bukkit.getPlayer(args[1]);

		if (player == null) {
			var found = false;

			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
				if (p.getName() != null && p.getName().equalsIgnoreCase(args[1])) {
					found = true;

					if (args[0].equalsIgnoreCase("set")) {
						if (amount < 0) {
							sender.sendMessage(cc(" &cYou can't set a player's balance below 0$."));
							return true;
						}

						Main.databaseManager.setBalance(null, p.getUniqueId().toString(), amount);
					} else if (args[0].equalsIgnoreCase("give")) {
						amount += (double) Main.databaseManager.getValueFromDB("players", "balance",
						"uuid", p.getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE);
						Main.databaseManager.setBalance(null, p.getUniqueId().toString(), amount);
					} else {
						amount = (Double) Main.databaseManager.getValueFromDB("players", "balance",
						"uuid", p.getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE) - amount;

						if (amount < 0) {
							sender.sendMessage(cc(" &cYou can't set a player's balance below 0$."));
							return true;
						}

						Main.databaseManager.setBalance(null, p.getUniqueId().toString(), amount);
					}

					sender.sendMessage(cc(" &aYou have changed " + p.getName() + "'s balance to " + String.format("%.2f", Main.databaseManager.getValueFromDB("players", "balance",
						"uuid", p.getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE)) + "$."));
					break;
				}
			}

			if (!found) sender.sendMessage(cc(" &cThe player named " + args[0] + " does not exist!"));
		} else {
			if (args[0].equalsIgnoreCase("set")) {
				if (amount < 0) {
					sender.sendMessage(cc(" &cYou can't set a player's balance below 0$."));
					return true;
				}

				Main.databaseManager.setBalance(null, player.getUniqueId().toString(), amount);
			} else if (args[0].equalsIgnoreCase("give")) {
				amount += (double) Main.databaseManager.getValueFromDB("players", "balance",
						"uuid", player.getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE);
				Main.databaseManager.setBalance(null, player.getUniqueId().toString(), amount);
			} else {
				amount = (double) Main.databaseManager.getValueFromDB("players", "balance",
						"uuid", player.getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE) - amount;

				if (amount < 0) {
					sender.sendMessage(cc(" &cYou can't set a player's balance below 0$."));
					return true;
				}

				Main.databaseManager.setBalance(null, player.getUniqueId().toString(), amount);
			}
			sender.sendMessage(cc(" &aYou have changed " + player.getName() + "'s balance to " + String.format("%.2f", Main.databaseManager.getValueFromDB("players", "balance",
						"uuid", player.getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE)) + "$."));
			player.sendMessage(cc(" &aYour balance has changed to " + Main.databaseManager.getValueFromDB("players", "balance",
						"uuid", player.getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE) + "$."));
		}

		return true;
	}
}
