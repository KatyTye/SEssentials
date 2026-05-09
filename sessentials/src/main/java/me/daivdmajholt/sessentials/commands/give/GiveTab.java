package me.daivdmajholt.sessentials.commands.give;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class GiveTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> suggestions = new ArrayList<>();
        String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";

        if (args.length == 3) {
            List<String> options = List.of("01","02","04","08","16","32","64");
            for (String s : options) if (s.startsWith(prefix)) suggestions.add(s);
            return suggestions;
        }

        if (args.length == 2) {
            for (Material material : Material.values()) {
                String name = material.name().toLowerCase();
                if (name.startsWith(prefix)) suggestions.add(name);
            }
            return suggestions;
        }

        if (args.length == 1) {
            return null;
        }

        return suggestions;
    }
}
