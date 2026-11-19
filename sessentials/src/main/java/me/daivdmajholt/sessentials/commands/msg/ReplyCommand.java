package me.daivdmajholt.sessentials.commands.msg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.util.Arrays;
import java.io.IOException;

public class ReplyCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.msg") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be executed by a player."));
			return true;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			sender.sendMessage(cc(" &cYou also need to enter a message."));
			return true;
		}

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "temp.yml");

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		Player target = Bukkit.getPlayerExact(cfg.getString(player.getUniqueId() + ".msg-last-recived"));

		if (target == null) {
			sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
			return true;
		}

		if (!cfg.getString(target.getUniqueId() + ".msg-last-recived").equals(player.getName().toString())) {
			cfg.set(target.getUniqueId() + ".msg-last-recived", player.getName().toString());

			try {
				cfg.save(file);
			} catch (IOException e) {
				if (plugin.getConfig().getBoolean("settings.debug-mode")) plugin.getLogger().severe("Could not set last private message from reply.");
			}
		}

		String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));

		if (sender.hasPermission("sessentials.msg.colored") || sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc("&8 [&7You&8->&7" + player.getName() + "&8] &f" + message));
			target.sendMessage(cc("&8 [&7" + player.getName() + "&8->&7You&8] &f" + message));
		} else {
			sender.sendMessage(cc("&8 [&7You&8->&7" + player.getName() + "&8] &f" + message.replaceAll("\u00A7[0-9A-FK-ORa-fk-or]", "")));
			target.sendMessage(cc("&8 [&7" + player.getName() + "&8->&7You&8] &f" + message.replaceAll("\u00A7[0-9A-FK-ORa-fk-or]", "")));
		}

		return true;
	}
}
