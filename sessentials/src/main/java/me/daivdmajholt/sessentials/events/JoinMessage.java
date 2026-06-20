package me.daivdmajholt.sessentials.events;

import me.daivdmajholt.sessentials.Main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;

public class JoinMessage implements Listener {

    private final Main plugin = Main.plugin;

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(cc(plugin.getConfig().getString("messages.join") + player.getName()));

        Main.databaseManager.registerPlayer(player, 0, 0);

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
            File dataFolder = plugin.getDataFolder();
            File file = new File(dataFolder, "spawn.yml");

            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

            String worldName = cfg.getString("world", "world");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                plugin.getLogger().warning("World not found: " + worldName);
            }

            double x = cfg.getDouble("x", 0.0);
            double y = cfg.getDouble("y", 0.0);
            double z = cfg.getDouble("z", 0.0);
            float yaw = (float) cfg.getDouble("yaw", 0.0);
            float pitch = (float) cfg.getDouble("pitch", 0.0);

            Location loc = new Location(world, x, y, z, yaw, pitch);

            player.teleport(loc);
        }
    }

}