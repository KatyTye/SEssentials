package me.daivdmajholt.sessentials.commands.kill;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class SuicideCommand implements CommandExecutor {

    private final Main plugin = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("sessentials.suicide") && !sender.hasPermission("sessentials.*")) {
            sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
            return true;
        }

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be executed by a player."));
			return true;
		}

        Player player = (Player) sender;

        player.setHealth(0);

        player.sendMessage(cc(" &aYou are now dead, killed by yourself."));

        return true;
    }
}
