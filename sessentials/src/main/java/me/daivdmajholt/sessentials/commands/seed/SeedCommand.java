package me.daivdmajholt.sessentials.commands.seed;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;
import static me.daivdmajholt.sessentials.Utils.sendCopyableMessage;

public class SeedCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.seed") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be run by a player."));
			return true;
		}

		Player player = (Player) sender;

		World world = player.getWorld();

		String seed = String.valueOf(world.getSeed());

		sendCopyableMessage(player, " &6World Seed: &f{" + seed + "}");

		return true;
	}
}
