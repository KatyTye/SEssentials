package me.daivdmajholt.sessentials.events;

import me.daivdmajholt.sessentials.Main;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.daivdmajholt.sessentials.Utils.cc;

public class JoinMessage implements Listener {

    private final Main plugin = Main.plugin;

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(cc(plugin.getConfig().getString("messages.join") + player.getName()));

        if (plugin.getConfig().getBoolean("settings.force-gamemode")) {
            try {
                GameMode gm;
                switch (plugin.getConfig().getString("settings.default-gamemode").toLowerCase()) {
                    case "s", "survival" -> gm = GameMode.SURVIVAL;
                    case "c", "creative" -> gm = GameMode.CREATIVE;
                    case "a", "adventure" -> gm = GameMode.ADVENTURE;
                    case "sp", "spectator" -> gm = GameMode.SPECTATOR;
                    default -> {
                        gm = GameMode.SURVIVAL;
                    }
                }
                player.setGameMode(gm);
            } catch (NumberFormatException e) {
                plugin.getLogger().warning("Could not force gamemode on " + player.getName() + ", is it a valid gamemode?");
            }
        }
    }

}