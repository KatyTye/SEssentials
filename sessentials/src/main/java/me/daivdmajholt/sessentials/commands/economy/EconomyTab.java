package me.daivdmajholt.sessentials.commands.economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class EconomyTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> suggestions = new ArrayList<>();
        String prefix = args.length > 0 ? args[args.length - 1].toLowerCase() : "";

        if (args.length == 1) {
            List<String> options = List.of("give","remove","set");
            for (String s : options) if (s.startsWith(prefix)) suggestions.add(s);
        }

        if (args.length == 2) {
            return null;
        }

        if (args.length == 3) {
            return new ArrayList<>();
        }

        return suggestions;
    }
}
