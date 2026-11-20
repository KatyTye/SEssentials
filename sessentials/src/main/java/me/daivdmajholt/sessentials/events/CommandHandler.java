package me.daivdmajholt.sessentials.events;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandHandler implements Listener {

	public static final List<String> doubleSlashCommands = List.of("count");

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String message = event.getMessage();

		if (!message.startsWith("//")) {
			return;
		}

		String commandAndArgs = message.substring(2).trim();
		String[] parts = commandAndArgs.split("\\s+", 2);

		if (parts.length == 0 || !doubleSlashCommands.contains(parts[0].toLowerCase(Locale.ROOT))) {
			event.setCancelled(true);
			return;
		}

		event.setMessage("/" + commandAndArgs);
	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent event) {
		if (!event.getBuffer().startsWith("//")) {
			return;
		}

		String partial = event.getBuffer().substring(2).toLowerCase(Locale.ROOT);
		event.setCompletions(doubleSlashCommands.stream()
				.filter(command -> command.toLowerCase(Locale.ROOT).startsWith(partial))
				.map(command -> "//" + command)
				.collect(Collectors.toList()));
	}
}
