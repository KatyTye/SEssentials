package me.daivdmajholt.sessentials.commands.warp;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.database.DatabaseManager.ValueType;

public class WarpCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.warp")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cPlease use the full command with /warp (warp name) (player) to teleport."));
			return true;
		}

		if (args.length == 2 && !sender.hasPermission("sessentials.warp.others")) {
			sender.sendMessage(cc(" &cYou don't have the required permission to teleport others."));
			return true;
		}

		if (args.length == 1 && !(sender instanceof Player)) {
			sender.sendMessage(cc(" &cYou also need to enter the players name to teleport."));
			return true;
		}

		if (!Main.databaseManager.checkValueFromDB("warps", "name", "name", args[0], ValueType.STRING)) {
			sender.sendMessage(cc(" &cCould not find any warps with that name."));
			return true;
		}

		if (!sender.hasPermission("sessentials.warp.*") && sender.hasPermission("sessentials.warp." + args[0])) {
			sender.sendMessage(cc(" &cYou don't have the required permission to use this warp."));
			return true;
		}

		Player player;

		if (args.length == 2) {
			player = Bukkit.getPlayer(args[1]);
		} else {
			player = (Player) sender;
		}

		if (player == null) {
			sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently online!"));
			return true;
		}

		List<Object> warpData = Main.databaseManager.getSpeficValuesFromDB("warps", "name", args[0], ValueType.STRING,
				ValueType.STRING);

		World world = Bukkit.getWorld(warpData.get(6).toString());

		if (world == null) {
			plugin.getLogger().warning("World not found: " + warpData.get(6));
			return true;
		}

		double x = Double.parseDouble(warpData.get(1).toString());
		double y = Double.parseDouble(warpData.get(2).toString());
		double z = Double.parseDouble(warpData.get(3).toString());
		float yaw = Float.parseFloat(warpData.get(4).toString());
		float pitch = Float.parseFloat(warpData.get(5).toString());

		Location loc = new Location(world, x, y, z, yaw, pitch);

		player.teleport(loc);
		player.sendMessage(cc(" &aYou have been teleported to " + args[0] + "."));

		if (args.length >= 2) {
			sender.sendMessage(cc(" &aYou have teleported " + args[1] + " to the location of " + args[0] + "."));
		}

		return true;
	}

}
