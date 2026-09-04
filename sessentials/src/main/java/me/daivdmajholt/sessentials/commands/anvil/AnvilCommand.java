package me.daivdmajholt.sessentials.commands.anvil;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class AnvilCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.anvil") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(cc(" &cYou need to use the full command as the console."));
			return true;
		}

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.anvil.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don’t have the required permission to open a anvil menu for others."));
				return true;
			}

			Player player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			Inventory anvil = Bukkit.createInventory(null, InventoryType.ANVIL);
			player.openInventory(anvil);

			sender.sendMessage(cc(" &aYou have opened a anvil for the player named &f" + player.getName() + "&a."));
			return true;
		}

		Player player = (Player) sender;

		Inventory anvil = Bukkit.createInventory(null, InventoryType.ANVIL);
		player.openInventory(anvil);

		sender.sendMessage(cc(" &aOpened a anvil menu for you."));

		return true;
	}
}
