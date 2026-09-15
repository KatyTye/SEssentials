package me.daivdmajholt.sessentials.commands.kill;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.cc;

public class KillAllCommand implements CommandExecutor {

	private final Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("sessentials.killall") && !sender.hasPermission("sessentials.*")) {
			sender.sendMessage(cc(plugin.getConfig().getString("messages.permission-denied")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(cc(" &cThis command can only be executed by a player."));
			return true;
		}

		Integer killed = 0;

		if (args.length == 0) {
			for (Player target : Bukkit.getOnlinePlayers()) {
				target.setHealth(0);
				killed++;
			}

			sender.sendMessage(cc(" &aYou have now killed all &f" + killed + "&a players in the world."));
		} else {
			Player player = (Player) sender;
			World world = player.getWorld();

			switch (args[0]) {
				case "entitys":
					for (Entity target : world.getEntities()) {
						killed++;
						if (target instanceof LivingEntity) {
							LivingEntity targetedEntity = (LivingEntity) target;
							targetedEntity.setHealth(0);
						} else {
							target.remove();
						}
					}

					sender.sendMessage(
							cc(" &aYou have now killed/removed all &f" + killed + "&a entitys in the world."));
					break;
				case "mobs":
					for (Entity target : world.getEntities()) {
						if (target instanceof Animals) {
							Animals targetMob = (Animals) target;
							targetMob.setHealth(0);
							killed++;
						}
					}

					sender.sendMessage(cc(" &aYou have now killed all &f" + killed + "&a mobs in the world."));
					break;
				case "monsters":
					for (Entity target : world.getEntities()) {
						if (target instanceof Mob) {
							Mob targetMob = (Mob) target;
							targetMob.setHealth(0);
							killed++;
						}
					}

					sender.sendMessage(cc(" &aYou have now killed all &f" + killed + "&a monsters in the world."));
					break;
				case "staff":
					for (Player target : world.getPlayers()) {
						if (target.hasPermission("sessentials.staff") && !target.hasPermission("sessentials.*")) {
							target.setHealth(0);
							killed++;
						}
					}

					sender.sendMessage(cc(" &aYou have now killed all &f" + killed + "&a staff members in the world."));
					break;
				default:
					for (Player target : world.getPlayers()) {
						target.setHealth(0);
						killed++;
					}

					sender.sendMessage(cc(" &aYou have now killed all &f" + killed + "&a players in the world."));
					break;
			}
		}

		return true;
	}
}
