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

        if (args.length == 3) {
            suggestions.add("01");
            suggestions.add("02");
            suggestions.add("04");
            suggestions.add("08");
            suggestions.add("16");
            suggestions.add("32");
            suggestions.add("64");
            return suggestions;
        }

        if (args.length == 2) {
            for (Material material : Material.values()) {
                suggestions.add(material.name());
            }
            return suggestions;
        }

        if (args.length == 1) {
            return null;
        }

        return suggestions;
    }
}
