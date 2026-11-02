package me.daivdmajholt.sessentials.commands.pay;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;
import me.daivdmajholt.database.DatabaseManager.ValueType;

public class PayCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.pay") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be run by a player."));
			return true;
		}

		Player target = (Player) sender;

		if (args.length >= 2) {
			target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			} else if (target.getName().equals(sender.getName())) {
				sender.sendMessage(cc(" &cYou can't send money to yourself."));
				return true;
			}

			double amount;

			try {
				amount = Double.parseDouble(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(cc(" &cYou have entered a invalid number."));
				return true;
			}

			double balance = (Double) Main.databaseManager.getValueFromDB("players", "balance",
				"uuid", ((Player) sender).getUniqueId().toString(), ValueType.STRING, ValueType.DOUBLE);

			if (amount>balance) {
				sender.sendMessage(cc(" &cYou don't have the money your trying to send."));
				return true;
			}

			Main.databaseManager.setBalance(null, ((Player) sender).getUniqueId().toString(), balance-amount);
			Main.databaseManager.setBalance(null, target.getUniqueId().toString(), balance+amount);

			sender.sendMessage(cc(" &aYou have sendt " + amount + "$ to " + target.getName() + "."));
			target.sendMessage(cc(" &aYou have recived " + amount + "$ from " + sender.getName() + "."));
			return true;
		}

		sender.sendMessage(cc(" &cPlease use the full command /pay (player) (amount)"));

		return true;
	}
}
