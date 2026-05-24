package me.daivdmajholt.sessentials.commands.discord;

import static me.daivdmajholt.sessentials.Utils.cc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.daivdmajholt.sessentials.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class DiscordCommand implements CommandExecutor {
	
	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String discordUrl = plugin.getConfig().getString("messages.discord-link");

		if (discordUrl.equalsIgnoreCase("https://discord.gg/xxxxxxxx")) {
			sender.sendMessage(cc(" &cThis server currently does not have a discord link."));
		} else {
			TextComponent link = new TextComponent(discordUrl);
			link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, discordUrl));
			
			sender.sendMessage(cc(" &9&lLINK: &f" + link));
		}

		return true;
	}
}
