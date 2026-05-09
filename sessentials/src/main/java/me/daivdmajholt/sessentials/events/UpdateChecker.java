package me.daivdmajholt.sessentials.events;

import static me.daivdmajholt.sessentials.Utils.cc;

import java.net.URI;
import java.util.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import me.daivdmajholt.sessentials.Main;

public class UpdateChecker {

    private final Main plugin = Main.plugin;
    private final String apiUrl;

    public UpdateChecker() {
        this.apiUrl = "https://raw.githubusercontent.com/KatyTye/SEssentials/main/version.txt";
    }

    public void check() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Scanner scanner = new Scanner(URI.create(apiUrl).toURL().openStream())) {
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

    public void messageCheck(CommandSender sender, Runnable callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Scanner scanner = new Scanner(URI.create(apiUrl).toURL().openStream())) {
                String latest = scanner.nextLine();
                String current = plugin.getDescription().getVersion();

                sender.sendMessage(cc(" &fCurrent Version: &a" + current));

                if (!current.equals(latest)) {
                    sender.sendMessage(cc(" &cYour plugin version is outdated."));
                } else {
                    sender.sendMessage(cc(" &aYour plugin is up to date."));
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Could not check for updates.");
            }

            callback.run();
        });
    }
}
