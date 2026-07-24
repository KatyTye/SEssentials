package me.daivdmajholt.sessentials.commands.give;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.inventory.ItemStack;

import me.daivdmajholt.sessentials.Main;

import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import static me.daivdmajholt.sessentials.Utils.cc;

public class GiveCommand implements CommandExecutor {

    private final Main plugin = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("sessentials.give") && !sender.hasPermission("sessentials.*")) {
            sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(cc(" &cPlease use the command correctly /give (player) (material) [amount]"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(cc(" &cThe player named " + args[0] + " was not found. "));
            return true;
        }

        Material material = Material.matchMaterial(args[1].toUpperCase());
        if (material == null) {
            sender.sendMessage(" &cCould not find the material/item named " + args[1]);
            return true;
        }

        int amount = 1;
        if (args.length >= 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(cc(" &cThe entered number is not valid."));
                return true;
            }
        }

        ItemStack item = new ItemStack(material, amount);
        target.getInventory().addItem(item);

        if (!sender.equals(target)) {
            target.sendMessage(cc(" &aYou have received " + (amount >= 2 ? amount + " of " : "a ")
                    + material.name().toLowerCase() + " from " + sender.getName() + "."));
            sender.sendMessage(cc(" &aYou just gave " + (amount >= 2 ? amount + " of " : "a ")
                    + material.name().toLowerCase() + " to " + target.getName() + "."));
        } else {
            target.sendMessage(cc(" &aYou have received " + (amount >= 2 ? amount + " of " : "a ")
                    + material.name().toLowerCase() + "."));
        }

        return true;
    }
}
