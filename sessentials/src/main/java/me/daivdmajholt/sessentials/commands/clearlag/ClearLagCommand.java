package me.daivdmajholt.sessentials.commands.clearlag;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import static me.daivdmajholt.sessentials.Utils.cc;

public class ClearLagCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		int removedItems = 0;

		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntitiesByClass(Item.class)) {
				Item item = (Item) entity;

				if (item.getCustomName() != null) {
					continue;
				}

				item.remove();
				removedItems++;
			}
		}

		sender.sendMessage(cc("&a Removed all " + removedItems + " dropped items from the ground!"));

		return true;
	}
}