package me.daivdmajholt.sessentials.events;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.daivdmajholt.database.DatabaseManager.ValueType;
import me.daivdmajholt.sessentials.Main;
import static me.daivdmajholt.sessentials.Utils.applyRankPerms;
import static me.daivdmajholt.sessentials.Utils.cc;

public class JoinMessage implements Listener {

    private final Main plugin = Main.plugin;

    File dataFolder = plugin.getDataFolder();
    File spawnFile = new File(dataFolder, "spawn.yml");
    File rankFile = new File(dataFolder, "ranks.yml");

    FileConfiguration spawnCFG = YamlConfiguration.loadConfiguration(spawnFile);
    FileConfiguration rankCFG = YamlConfiguration.loadConfiguration(rankFile);

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(cc(plugin.getConfig().getString("messages.join") + player.getName()));

        Main.databaseManager.registerPlayer(player, 0, 0);

        String rank = (String) Main.databaseManager.getValueFromDB("players", "rank",
		"uuid", player.getUniqueId().toString(), ValueType.STRING, ValueType.STRING);

        List<String> perms = rankCFG.getStringList(rank + ".permissions");

		applyRankPerms(player, perms);

        if (plugin.getConfig().getBoolean("do-plugin-advertisements")) {
            plugin.getLogger().info(
                    " This server is using the SEssentials plugin from: https://www.curseforge.com/minecraft/bukkit-plugins/sessentials");
            player.sendMessage("");
            player.sendMessage(cc(" &f&lWELCOME TO THE &6&lSERVER!"));
            player.sendMessage(cc(" &fThis server is using &6SEssentials&f for basically everything!"));
            player.sendMessage(cc(
                    " &fTry this plugin on your server at: &6https://www.curseforge.com/minecraft/bukkit-plugins/sessentials"));
            player.sendMessage("");
        }

        if (plugin.getConfig().getBoolean("check-updates") && player.hasPermission("sessentials.*")) {
            new UpdateChecker().versionCheck(player);
        }

        if (plugin.getConfig().getBoolean("settings.force-gamemode")) {
            try {
                GameMode gm;
                switch (plugin.getConfig().getString("settings.default-gamemode").toLowerCase()) {
                    case "s", "survival", "0" -> gm = GameMode.SURVIVAL;
                    case "c", "creative", "1" -> gm = GameMode.CREATIVE;
                    case "a", "adventure", "2" -> gm = GameMode.ADVENTURE;
                    case "sp", "spectator", "3" -> gm = GameMode.SPECTATOR;
                    default -> {
                        gm = GameMode.SURVIVAL;
                    }
                }
                player.setGameMode(gm);
            } catch (NumberFormatException e) {
                plugin.getLogger()
                        .warning("Could not force gamemode on " + player.getName() + ", is it a valid gamemode?");
            }
        }

        if (plugin.getConfig().getBoolean("settings.spawn-on-join")) {
            String worldName = spawnCFG.getString("world", "world");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                plugin.getLogger().warning("World not found: " + worldName);
            }

            double x = spawnCFG.getDouble("x", 0.0);
            double y = spawnCFG.getDouble("y", 0.0);
            double z = spawnCFG.getDouble("z", 0.0);
            float yaw = (float) spawnCFG.getDouble("yaw", 0.0);
            float pitch = (float) spawnCFG.getDouble("pitch", 0.0);

            Location loc = new Location(world, x, y, z, yaw, pitch);

            player.teleport(loc);
        }

        for (Player target : Bukkit.getOnlinePlayers()) {
			if (plugin.vanishedPlayers.contains(target.getUniqueId())) {
				player.hidePlayer(plugin, target);
			} else {
				player.showPlayer(plugin, target);
			}
		}
    }

}