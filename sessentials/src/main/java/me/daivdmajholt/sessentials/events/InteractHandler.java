package me.daivdmajholt.sessentials.events;

import me.daivdmajholt.sessentials.Main;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.event.player.PlayerInteractEvent;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.io.File;
import java.io.IOException;

import org.bukkit.World;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class InteractHandler implements Listener {

	private final Main plugin = Main.plugin;

	private void applyEffects(Location block) {
		World world = block.getWorld();
		if (world == null) return;

		double x = block.getBlockX();
		double y = block.getBlockY();
		double z = block.getBlockZ();

		new BukkitRunnable() {
			private int runs = 0;

			@Override
			public void run() {
				for (int i = 0; i <= 10; i++) {
					double t = i / 10.0;

					for (int a = 0; a <= 1; a++) {
						for (int b = 0; b <= 1; b++) {
							world.spawnParticle(Particle.SMOKE_NORMAL, x + t, y + a, z + b, 1, 0, 0, 0, 0);
							world.spawnParticle(Particle.SMOKE_NORMAL, x + a, y + t, z + b, 1, 0, 0, 0, 0);
							world.spawnParticle(Particle.SMOKE_NORMAL, x + a, y + b, z + t, 1, 0, 0, 0, 0);
						}
					}
				}

				if (++runs >= 10) {
					cancel();
				}
			}
		}.runTaskTimer(plugin, 0L, 10L);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();

		if (event.getClickedBlock() == null) return;

		Location blockLocation = event.getClickedBlock().getLocation();

		File dataFolder = plugin.getDataFolder();
		File file = new File(dataFolder, "temp.yml");
		
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (inventory.getItemInMainHand().getType() == Material.WOODEN_AXE && player.getGameMode() == GameMode.CREATIVE) {
				if (player.hasPermission("sessentials.worldedit") || player.hasPermission("sessentials.*")) {
					event.setCancelled(true);

					if (cfg.getDouble(player.getUniqueId() + ".place-location-first.x") == blockLocation.getBlockX() && blockLocation.getBlockY()
					== cfg.getDouble(player.getUniqueId() + ".place-location-first.y") && cfg.getDouble(player.getUniqueId() + ".place-location-first.z")
					== blockLocation.getBlockZ()) return;

					cfg.set(player.getUniqueId() + ".place-location-first.x", blockLocation.getBlockX());
					cfg.set(player.getUniqueId() + ".place-location-first.y", blockLocation.getBlockY());
					cfg.set(player.getUniqueId() + ".place-location-first.z", blockLocation.getBlockZ());
					cfg.set(player.getUniqueId() + ".place-location-first.yaw", blockLocation.getYaw());
					cfg.set(player.getUniqueId() + ".place-location-first.pitch", blockLocation.getPitch());
					cfg.set(player.getUniqueId() + ".place-location-first.world", blockLocation.getWorld().getName().toString());

					try {
						cfg.save(file);
						applyEffects(blockLocation);
						player.sendMessage(cc("&8 [&4WorldEdit&8] &aSelected primary location at " + blockLocation.getBlockX() + ", " + blockLocation.getBlockY()
						+ ", " + blockLocation.getBlockZ() + " in world named " + blockLocation.getWorld().getName() + "."));
					} catch (IOException e) {
						if (plugin.getConfig().getBoolean("settings.debug-mode")) plugin.getLogger().severe("Could not set first place location.");
					}
				}
			}
		} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getHand() == org.bukkit.inventory.EquipmentSlot.OFF_HAND) return;

			if (inventory.getItemInMainHand().getType() == Material.WOODEN_AXE && player.getGameMode() == GameMode.CREATIVE) {
				if (player.hasPermission("sessentials.worldedit") || player.hasPermission("sessentials.*")) {
					event.setCancelled(true);

					if (cfg.getDouble(player.getUniqueId() + ".place-location-second.x") == blockLocation.getBlockX() && blockLocation.getBlockY()
					== cfg.getDouble(player.getUniqueId() + ".place-location-second.y") && cfg.getDouble(player.getUniqueId() + ".place-location-second.z")
					== blockLocation.getBlockZ()) return;

					cfg.set(player.getUniqueId() + ".place-location-second.x", blockLocation.getBlockX());
					cfg.set(player.getUniqueId() + ".place-location-second.y", blockLocation.getBlockY());
					cfg.set(player.getUniqueId() + ".place-location-second.z", blockLocation.getBlockZ());
					cfg.set(player.getUniqueId() + ".place-location-second.yaw", blockLocation.getYaw());
					cfg.set(player.getUniqueId() + ".place-location-second.pitch", blockLocation.getPitch());
					cfg.set(player.getUniqueId() + ".place-location-second.world", blockLocation.getWorld().getName().toString());

					try {
						cfg.save(file);
						applyEffects(blockLocation);
						player.sendMessage(cc("&8 [&4WorldEdit&8] &aSelected secondary location at " + blockLocation.getBlockX() + ", " + blockLocation.getBlockY()
						+ ", " + blockLocation.getBlockZ() + " in world named " + blockLocation.getWorld().getName() + "."));
					} catch (IOException e) {
						if (plugin.getConfig().getBoolean("settings.debug-mode")) plugin.getLogger().severe("Could not set second place location.");
					}
				}
			}
		}
	}
}