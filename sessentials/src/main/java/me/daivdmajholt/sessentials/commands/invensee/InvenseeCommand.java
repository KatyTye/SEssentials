package me.daivdmajholt.sessentials.commands.invensee;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class InvenseeCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.invensee") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cOnly players can use this command."));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter the player's name."));
			return true;
		}

		Player player = (Player) sender;
		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
			return true;
		}

		Inventory inv = target.getInventory();

		player.openInventory(inv);

		sender.sendMessage(cc(" &aYou are now viewing the inventory of &f" + args[0] + "&a."));

		return true;
	}

}
