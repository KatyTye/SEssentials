package me.daivdmajholt.sessentials.commands.clearInventory;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class ClearInventoryCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.clearinventory") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player) && args.length == 0) {
			sender.sendMessage(cc(" &cYou need to use the full command as the console."));
			return true;
		}

		if (args.length != 0) {
			if (!sender.hasPermission("sessentials.clearinventory.other") && !sender.hasPermission("sessentials.*")) {
				sender.sendMessage(cc(" &cYou don't have the required permission to clear other players inventory."));
				return true;
			}

			Player player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
				return true;
			}

			player.getInventory().clear();

			sender.sendMessage(cc(" &aThe player named &f" + args[0] + "&a inventory has been cleared."));
			return true;
		}

		Player player = (Player) sender;

		player.getInventory().clear();

		sender.sendMessage(cc(" &aYour inventory has been cleared."));

		return true;
	}
}
