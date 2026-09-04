package me.daivdmajholt.sessentials.commands.hat;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class HatCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.hat") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be run by a player."));
			return true;
		}

		Player player = (Player) sender;

		PlayerInventory inventory = player.getInventory();

		ItemStack heldItem = inventory.getItemInMainHand();
		ItemStack oldHelmet = inventory.getHelmet();

		if (heldItem.getType() != Material.AIR) {
			inventory.setHelmet(heldItem.clone());
			inventory.setItemInMainHand(oldHelmet);
		}

		sender.sendMessage(cc(" &aYou are now wearing the item you were holding on your head."));

		return true;
	}
}
