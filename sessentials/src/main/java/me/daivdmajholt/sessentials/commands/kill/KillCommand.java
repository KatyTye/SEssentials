package me.daivdmajholt.sessentials.commands.kill;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class KillCommand implements CommandExecutor {

    private final Main plugin = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("sessentials.kill") && !sender.hasPermission("sessentials.*")) {
            sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 0) {
            if (!sender.hasPermission("sessentials.kill.other") && !sender.hasPermission("sessentials.*")) {
                sender.sendMessage(cc(" &cYou don't have the required permission to kill other players."));
                return true;
            }

            player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage(cc(" &cThe player named " + args[0] + " is currently not online!"));
                return true;
            }

            player.setHealth(0);
            sender.sendMessage(cc(" &aYou have now killed the player named &f" + player.getName() + "&a."));
            player.sendMessage(cc(" &fYou have been killed by " + sender.getName() + "."));
        }

        player.setHealth(0);

        player.sendMessage(cc(" &aYou are now dead, killed by yourself."));

        return true;
    }
}
