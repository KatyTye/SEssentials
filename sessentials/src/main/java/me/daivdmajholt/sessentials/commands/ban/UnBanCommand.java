package me.daivdmajholt.sessentials.commands.ban;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import me.daivdmajholt.sessentials.Main;
import me.daivdmajholt.database.DatabaseManager.ValueType;

public class UnBanCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.ban.remove")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter the player's name."));
			return true;
		}

		if (!Bukkit.getBanList(Type.NAME).isBanned(args[0])) {
			sender.sendMessage(cc(" &cThis player is not currently banned."));
			return true;
		}

		Bukkit.getBanList(Type.NAME).pardon(args[0]);

		try {
			Object uuid = Main.databaseManager.getValueFromDB("players", "uuid", "name", args[0], ValueType.STRING,
					ValueType.STRING);

			plugin.getLogger().info(uuid.toString());

			if (Bukkit.getBanList(Type.IP).isBanned(uuid.toString()))
				Bukkit.getBanList(Type.IP).pardon(uuid.toString());
		} catch (Error e) {
			if (plugin.getConfig().getBoolean("settings.debug-mode"))
				e.printStackTrace();
		}

		return true;
	}
}
