package me.daivdmajholt.sessentials.commands.buy;

import static me.daivdmajholt.sessentials.Utils.cc;
import static me.daivdmajholt.sessentials.Utils.returnClickLink;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;

public class BuyCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String storeUrl = plugin.getConfig().getString("messages.online-shop");

		if (storeUrl.equalsIgnoreCase("https://shop.website.com/")) {
			sender.sendMessage(cc(" &cThis server currently does not have a online shop yet."));
		} else {
			sender.sendMessage("");
			sender.sendMessage(cc(" &6&lSTORE: &f" + returnClickLink(storeUrl)));
			sender.sendMessage("");
		}

		return true;
	}
}
