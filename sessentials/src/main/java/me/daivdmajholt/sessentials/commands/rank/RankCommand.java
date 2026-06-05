package me.daivdmajholt.sessentials.commands.rank;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daivdmajholt.database.DatabaseManager.ValueType;
import me.daivdmajholt.sessentials.Main;

public class RankCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.manage.ranks")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cPlease use the command correctly /rankmanager help"));
			return true;
		}

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "ranks.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<Integer> ranks = cfg.getIntegerList("ranks");

		switch (args[0].toLowerCase()) {
			case "help" -> {
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
				sender.sendMessage(cc(" &6/rankmanager help &7- Show all subcommands for commands."));
				sender.sendMessage(cc(" &6/rankmanager list &7- Shows a list of all the ranks on the server."));
				sender.sendMessage(cc(" &6/rankmanager change (rank id) (player) &7- Changes the rank of a player."));
				sender.sendMessage(
						cc(" &6/rankmanager permissions (rank id) &7- Lists all the permissions of the rank."));
				sender.sendMessage(
						cc(" &6/rankmanager rename (rank id) (name) &7- Renames the name of the rank chosen."));
				sender.sendMessage(
						cc(" &6/rankmanager (info/details) (rank id) &7- Shows all the details about a rank."));
				sender.sendMessage(
						cc(" &6/rankmanager prefix (rank id) (prefix) &7- Changes the prefix of the rank chosen."));
				sender.sendMessage(
						cc(" &6/rankmanager suffix (rank id) (suffix) &7- Changes the suffix of the rank chosen."));
				sender.sendMessage(cc(
						" &6/rankmanager color (rank id) (color code) &7- Changes the color of a ranks prefix and suffix."));
				sender.sendMessage(cc(
						" &6/rankmanager priority (rank id) (priority) &7- Changes the priority of the rank chosen."));
				sender.sendMessage(cc(
						" &6/rankmanager permission (rank id) (add/remove) (permission) &7- Adds or removes a permission from a rank."));
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
			}

			case "change" -> {

				if (args.length < 3) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				Player target = Bukkit.getPlayerExact(args[2]);

				if (target == null) {
					UUID found = null;

					for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
						if (p.getName() != null && p.getName().equalsIgnoreCase(args[2])) {
							found = p.getUniqueId();

							String oldRank = (String) Main.databaseManager.getValueFromDB("players", "rank",
							"uuid", found.toString(), ValueType.STRING, ValueType.STRING);

							cfg.set(oldRank + ".members", cfg.getInt(oldRank + ".members") - 1);
							cfg.set(args[1] + ".members", cfg.getInt(args[1] + ".members") + 1);

							try {
								cfg.save(file);
							} catch (IOException e) {
								e.printStackTrace();
							}

							if (Main.databaseManager.setPlayerRank(found.toString(), Integer.parseInt(args[1]))) {
								sender.sendMessage(cc(" &aYou have changed the rank of &f" + found.toString() + "&a to &f" + cfg.getString(args[1] + ".name")));
							} else {
								sender.sendMessage(cc(" &cCould not change the rank of the player named " + args[2] + "."));
							}

							break;
						}
					}

					if (found == null) sender.sendMessage(cc(" &cThe player named " + args[0] + " does not exist!"));
					return true;
				}

				String oldRank = (String) Main.databaseManager.getValueFromDB("players", "rank",
				"uuid", target.getUniqueId().toString(), ValueType.STRING, ValueType.STRING);

				cfg.set(oldRank + ".members", cfg.getInt(oldRank + ".members") - 1);
				cfg.set(args[1] + ".members", cfg.getInt(args[1] + ".members") + 1);

				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (Main.databaseManager.setPlayerRank(target.getUniqueId().toString(), Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &aYou have changed the rank of &f" + target.getName() + "&a to &f" + cfg.getString(args[1] + ".name")));
				} else {
					sender.sendMessage(cc(" &cCould not change the rank of the player named " + target.getName() + "."));
				}
			}

			case "list" -> {
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
				for (int value : ranks) {
					sender.sendMessage(cc(" &7- &f&lID: &6" + value + " &f&lNAME: &6" + cfg.getString(value + ".name")
							+ " &f&lPRIORITY: &6" + cfg.getString(value + ".priority")));
					sender.sendMessage("");
				}

				if (ranks.size() == 0) {
					sender.sendMessage(cc(" &cCould not find any ranks on the server."));
					sender.sendMessage("");
				}
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
			}

			case "permissions" -> {

				if (args.length < 2) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");

				for (String value : cfg.getStringList(args[1] + ".permissions")) {
					sender.sendMessage(cc(" &7- &6" + value));
				}

				if (cfg.getStringList(args[1] + ".permissions").size() == 0) {
					sender.sendMessage(cc(" &cThe rank named " + cfg.getString(args[1] + ".name")
							+ " does not have any permissions."));
				}

				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
			}

			case "rename" -> {

				if (args.length < 3) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				cfg.set(args[1] + ".name", args[2]);
				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(cc(" &aYou have now changed the ranks name to: &f" + args[2]));
			}

			case "prefix" -> {

				if (args.length < 3) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				cfg.set(args[1] + ".prefix", args[2]);
				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(cc(" &aYou have now changed the ranks prefix to: &f" + args[2]));
			}

			case "suffix" -> {

				if (args.length < 3) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				cfg.set(args[1] + ".suffix", args[2]);
				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(cc(" &aYou have now changed the ranks suffix to: &f" + args[2]));
			}

			case "color" -> {

				if (args.length < 3) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				cfg.set(args[1] + ".color", args[2]);
				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(cc(" &aYou have now changed the ranks color to: " + args[2]) + args[2]);
			}

			case "priority" -> {

				if (args.length < 3) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				cfg.set(args[1] + ".priority", Integer.parseInt(args[2]));
				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(cc(" &aYou have now changed the ranks priority to: &f" + args[2]));
			}

			case "permission" -> {

				if (args.length < 4) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				List<String> currentPermissions = cfg.getStringList(args[1] + ".permissions");

				if (args[2].equalsIgnoreCase("add")) {

					if (cfg.getStringList(args[1] + ".permissions").contains(args[3])) {
						sender.sendMessage(cc(" &cThe rank already has this permission."));
						return true;
					}

					currentPermissions.add(args[3]);
					cfg.set(args[1] + ".permissions", currentPermissions);
					try {
						cfg.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					sender.sendMessage(cc(" &aYou have now added the permission &f" + args[3] + "&a to the rank."));
				} else {
					if (!cfg.getStringList(args[1] + ".permissions").contains(args[3])) {
						sender.sendMessage(cc(" &cThe rank does not have the permission named: &f" + args[3]));
						return true;
					}

					currentPermissions.remove(args[3]);
					cfg.set(args[1] + ".permissions", currentPermissions);
					try {
						cfg.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					sender.sendMessage(cc(" &aYou have now removed a permission from the rank."));
				}
			}

			case "details", "info" -> {

				if (args.length < 2) {
					sender.sendMessage(cc(" &cPlease use the full command to execute."));
					return true;
				}

				if (!ranks.contains(Integer.parseInt(args[1]))) {
					sender.sendMessage(cc(" &cThere does not exist a rank with that id."));
					return true;
				}

				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
				sender.sendMessage(cc(" &7- &fNAME: &6"
						+ (cfg.getString(args[1] + ".name") != null && !cfg.getString(args[1] + ".name").isEmpty()
								? cfg.getString(args[1] + ".name")
								: "<none>")));
				sender.sendMessage(cc(" &7- &fCOLOR: &6"
						+ (cfg.getString(args[1] + ".color") != null && !cfg.getString(args[1] + ".color").isEmpty()
								? cfg.getString(args[1] + ".color")
								: "&6"))
						+ (cfg.getString(args[1] + ".color") != null && !cfg.getString(args[1] + ".color").isEmpty()
								? cfg.getString(args[1] + ".color")
								: "<none>"));
				sender.sendMessage(cc(" &7- &fPREFIX: &6"
						+ (cfg.getString(args[1] + ".prefix") != null && !cfg.getString(args[1] + ".prefix").isEmpty()
								? cfg.getString(args[1] + ".prefix")
								: "<none>")));
				sender.sendMessage(cc(" &7- &fSUFFIX: &6"
						+ (cfg.getString(args[1] + ".suffix") != null && !cfg.getString(args[1] + ".suffix").isEmpty()
								? cfg.getString(args[1] + ".suffix")
								: "<none>")));
				sender.sendMessage(cc(" &7- &fMEMBERS: &6"
						+ (cfg.getString(args[1] + ".members") != null ? cfg.getString(args[1] + ".members")
								: "<none>")));
				sender.sendMessage(cc(" &7- &fPRIORITY: &6"
						+ (cfg.getString(args[1] + ".priority") != null ? cfg.getString(args[1] + ".priority")
								: "<none>")));
				sender.sendMessage(cc(" &7- &fPERMISSIONS: &6/rankmanager permissions " + args[1]));
				sender.sendMessage("");
				sender.sendMessage(cc(" &f&m                                  &f"));
				sender.sendMessage("");
			}

			default -> sender.sendMessage(cc(plugin.getConfig().getString("messages.subcommand-unknown")
					+ "using the help command /rankmanager help"));
		}

		return true;
	}
}