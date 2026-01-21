package me.daivdmajholt.sessentials.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final String apiUrl;

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
        this.apiUrl = "https://raw.githubusercontent.com/KatyTye/SEssentials/main/version.txt";
    }

     public void check() {
         Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
             try (Scanner scanner = new Scanner(new URL(apiUrl).openStream())) {
                 String latest = scanner.nextLine();
                 String current = plugin.getDescription().getVersion();

                 if (!current.equals(latest)) {
                     plugin.getLogger().info("New version available: " + latest);
                 } else {
                     plugin.getLogger().info("You are running the latest version.");
                 }
             } catch (Exception e) {
                 plugin.getLogger().warning("Could not check for updates.");
             }
         });
     }
}
