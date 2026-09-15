package me.daivdmajholt.sessentials.commands.repair;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class RepairCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.repair") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be executed by a player."));
			return true;
		}

		Player player = (Player) sender;

		ItemStack heldItem = (ItemStack) player.getInventory().getItemInMainHand();

		if (heldItem.getType().isAir()) {
			sender.sendMessage(cc(" &cYou need to hold a item to repair it."));
			return true;
		}

		ItemMeta meta = heldItem.getItemMeta();

		if (meta instanceof Damageable damageable) {
			damageable.setDamage(0);
			heldItem.setItemMeta(meta);
		} else {
			sender.sendMessage(cc(" &cYour held item can't be repaired."));
		}

		return true;
	}
}
