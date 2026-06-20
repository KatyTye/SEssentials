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

	List<String> commands = List.of("info", "relocate", "delete", "create", "list");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.warp")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also needs to enter the warps name."));
			return true;
		}

		String lowercaseArg = args[0].toLowerCase();

		Player player;
		Location location;

		if (args.length == 0) {
			sender.sendMessage(cc(" &cPlease use the full command with /warp (warp name) (player) to teleport."));
			return true;
		}

		if (commands.contains(lowercaseArg)) {
			if (!sender.hasPermission("sessentials.warp.edit")) {
				sender.sendMessage(cc(" &cYou don't have the required permission to edit warps."));
				return true;
			}

			switch (lowercaseArg) {
				case "delete":
					if (args.length == 1) {
						sender.sendMessage(cc(" &cYou also need to enter the name of the warp."));
						break;
					}

					if (Main.databaseManager.checkValueFromDB("warps", "name", "name", args[1].toLowerCase(),
							ValueType.STRING) == false	) {
						sender.sendMessage(cc(" &cThe warp name you have entered does not exist."));
						break;
					}

					if (commands.contains(args[1].toLowerCase())) {
						sender.sendMessage(cc(" &cThere does not exist a warp with that name."));
						break;
					}

					Boolean deleted = Main.databaseManager.deleteSpeficItemFromDB("warps", "name", args[1].toLowerCase(), ValueType.STRING);

					if (deleted) {
						sender.sendMessage(cc(" &aThe warp named " + args[1].toLowerCase() + " has now been deleted."));
					}

					break;
				case "relocate":
					if (!(sender instanceof Player)) {
						sender.sendMessage(cc(" &cYou are required to be a player to execute this sub-command."));
						break;
					}

					if (args.length == 1) {
						sender.sendMessage(cc(" &cYou also need to enter the name of the warp."));
						break;
					}

					if (Main.databaseManager.checkValueFromDB("warps", "name", "name", args[1].toLowerCase(),
							ValueType.STRING) == false	) {
						sender.sendMessage(cc(" &cThe warp name you have entered does not exist."));
						break;
					}

					if (commands.contains(args[1].toLowerCase())) {
						sender.sendMessage(cc(" &cThere does not exist a warp with that name."));
						break;
					}

					player = (Player) sender;
					location = player.getLocation();

					Boolean updated = Main.databaseManager.updateValuesInDB("warps", "name", "name, x, y, z, yaw, pitch, world", List.of(
									args[1].toLowerCase(), String.valueOf(location.getX()),
									String.valueOf(location.getY()), String.valueOf(location.getZ()),
									String.valueOf(location.getYaw()), String.valueOf(location.getPitch()),
									player.getWorld().getName()),
							List.of(
									ValueType.STRING, ValueType.DOUBLE, ValueType.DOUBLE, ValueType.DOUBLE,
									ValueType.DOUBLE, ValueType.DOUBLE, ValueType.STRING));

					if (updated) {
						sender.sendMessage(cc(" &aThe warp named " + args[1].toLowerCase() + " has now been relocated."));
					}

					break;
				case "list":
					List<Object> warps = Main.databaseManager.getAllValuesFromDB("warps", "name",
							ValueType.STRING);

					if (warps.size() == 0) {
						sender.sendMessage(cc(" &cThere are currently no warps available on this server."));
						return true;
					}

					String listedWarps = warps.toString().replace("[", "").replace("]", "");

					sender.sendMessage("");
					sender.sendMessage(cc(" &6&lWARPS:"));
					sender.sendMessage(cc(" &f" + listedWarps));
					sender.sendMessage("");
					break;
				case "create":
					if (!(sender instanceof Player)) {
						sender.sendMessage(cc(" &cYou are required to be a player to execute this sub-command."));
						break;
					}

					if (args.length == 1) {
						sender.sendMessage(cc(" &cYou also need to enter the name of the warp."));
						break;
					}

					if (Main.databaseManager.checkValueFromDB("warps", "name", "name", args[1].toLowerCase(),
							ValueType.STRING) == true) {
						sender.sendMessage(cc(" &cThe name you have entered already exists."));
						break;
					}

					if (commands.contains(args[1].toLowerCase())) {
						sender.sendMessage(cc(" &cYou cant create a warp with that name."));
						break;
					}

					player = (Player) sender;
					location = player.getLocation();

					Boolean created = Main.databaseManager.createNewDataInDB("warps",
							"name, x, y, z, yaw, pitch, world", List.of(
									args[1].toLowerCase(), String.valueOf(location.getX()),
									String.valueOf(location.getY()), String.valueOf(location.getZ()),
									String.valueOf(location.getYaw()), String.valueOf(location.getPitch()),
									player.getWorld().getName()),
							List.of(
									ValueType.STRING, ValueType.DOUBLE, ValueType.DOUBLE, ValueType.DOUBLE,
									ValueType.DOUBLE, ValueType.DOUBLE, ValueType.STRING));

					if (created) {
						sender.sendMessage(cc(" &aThe warp named " + args[1].toLowerCase() + " has now been created."));
					}

					break;
				case "info":
					if (args.length == 1) {
						sender.sendMessage(cc(" &cYou also need to enter the name of the warp."));
						break;
					}

					List<Object> warpData = Main.databaseManager.getSpeficValuesFromDB("warps", "name",
							args[1].toLowerCase(),
							ValueType.STRING, ValueType.STRING);

					if (warpData.isEmpty()) {
						sender.sendMessage(cc(" &cThe warp named " + args[1].toLowerCase() + " does not exist!"));
						break;
					}

					sender.sendMessage("");
					sender.sendMessage(cc(" &6&lWARP INFORMATION:"));
					sender.sendMessage(cc(" &7Name: &f" + warpData.get(0).toString()));
					sender.sendMessage(cc(" &7World: &f" + warpData.get(6).toString()));
					sender.sendMessage(cc(" &7Vertical: &f" + warpData.get(5).toString()));
					sender.sendMessage(cc(" &7Horizontal: &f" + warpData.get(4).toString()));
					sender.sendMessage(cc(" &7Location: &fx: " + warpData.get(1).toString() + " y: "
							+ warpData.get(2).toString() + " z: " + warpData.get(3).toString()));
					sender.sendMessage("");
					break;
				default:
					sender.sendMessage(
							cc(" &cThere is no sub-command named " + lowercaseArg + " for the warp command."));
					break;
			}
		} else {
			if (args.length == 2 && !sender.hasPermission("sessentials.warp.others")) {
				sender.sendMessage(cc(" &cYou don't have the required permission to warp others."));
				return true;
			}

			if (args.length == 1 && !(sender instanceof Player)) {
				sender.sendMessage(cc(" &cYou also need to enter the players name to warp them."));
				return true;
			}

			if (!Main.databaseManager.checkValueFromDB("warps", "name", "name", lowercaseArg, ValueType.STRING)) {
				sender.sendMessage(cc(" &cCould not find any warps with that name."));
				return true;
			}

			if (!sender.hasPermission("sessentials.warp.*")
					|| !sender.hasPermission("sessentials.warp." + lowercaseArg)) {
				sender.sendMessage(cc(" &cYou don't have the required permission to use this warp."));
				return true;
			}

			if (args.length == 2) {
				player = Bukkit.getPlayer(args[1]);
			} else {
				player = (Player) sender;
			}

			if (player == null) {
				sender.sendMessage(cc(" &cThe player named " + lowercaseArg + " is currently online!"));
				return true;
			}

			List<Object> warpData = Main.databaseManager.getSpeficValuesFromDB("warps", "name", lowercaseArg,
					ValueType.STRING,
					ValueType.STRING);

			World world = Bukkit.getWorld(warpData.get(6).toString());

			if (world == null) {
				plugin.getLogger().warning("World not found: " + warpData.get(6));
				sender.sendMessage(cc(" &cTeleport failed, reason for failure is logged."));
				return true;
			}

			double x = Double.parseDouble(warpData.get(1).toString());
			double y = Double.parseDouble(warpData.get(2).toString());
			double z = Double.parseDouble(warpData.get(3).toString());
			float yaw = Float.parseFloat(warpData.get(4).toString());
			float pitch = Float.parseFloat(warpData.get(5).toString());

			Location loc = new Location(world, x, y, z, yaw, pitch);

			player.teleport(loc);
			player.sendMessage(cc(" &aYou have been teleported to " + lowercaseArg + "."));

			if (args.length >= 2) {
				sender.sendMessage(
						cc(" &aYou have teleported " + args[1] + " to the location of " + lowercaseArg + "."));
			}
		}

		return true;
	}
}
