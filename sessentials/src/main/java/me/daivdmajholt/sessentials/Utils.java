package me.daivdmajholt.sessentials;

import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Method;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.google.gson.JsonObject;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class Utils {

	private static final Map<UUID, PermissionAttachment> attachments = new HashMap<>();

    public static void clearRankPerms(Player player) {
        PermissionAttachment old = attachments.remove(player.getUniqueId());
        if (old != null) {
			try {
				player.removeAttachment(old);
			} catch (IllegalArgumentException ignored) {}
			attachments.remove(player.getUniqueId());
        }
    }

    public static void applyRankPerms(Player player, List<String> perms) {
        clearRankPerms(player);

        PermissionAttachment attachment = player.addAttachment(Main.plugin);
        for (String perm : perms) {
            attachment.setPermission(perm, true);
        }

        attachments.put(player.getUniqueId(), attachment);
    }
	
	public static String cc(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static <T> T reqNN(T obj) {
		return java.util.Objects.requireNonNull(obj);
	}

	public static Location convertObjectToLocation(JsonObject obj) {
		if (obj == null || !obj.has("world")) return null;

		String worldName = obj.get("world").getAsString();
		World world = Bukkit.getWorld(worldName);
		if (world == null) return null;

		double x = obj.has("x-cordinate") ? obj.get("x-cordinate").getAsDouble() : 0.0;
		double y = obj.has("y-cordinate") ? obj.get("y-cordinate").getAsDouble() : 0.0;
		double z = obj.has("z-cordinate") ? obj.get("z-cordinate").getAsDouble() : 0.0;
		float yaw = obj.has("yaw") ? obj.get("yaw").getAsFloat() : 0f;
		float pitch = obj.has("pitch") ? obj.get("pitch").getAsFloat() : 0f;

		return new Location(world, x, y, z, yaw, pitch);
	}

	private static String stripColorCodes(String s) {
		return s == null ? null : s.replaceAll("\u00A7[0-9A-FK-ORa-fk-or]", "");
	}

	public static Object returnClickLink(String message) {
		Pattern regex = Pattern.compile("(https?:\\/\\/[^,\\s]+?)(?=[,\\s]|$)");
		Matcher matcher = regex.matcher(message);

		// If the whole string is a single URL (or contains one), extract the first match
		String url = null;
		if (matcher.find()) url = matcher.group(1);
		if (url == null) return message; // no URL -> return original string

		String clickTarget = stripColorCodes(url).trim();
		if (!(clickTarget.startsWith("http://") || clickTarget.startsWith("https://")))
			clickTarget = "https://" + clickTarget;

		// Adventure via reflection
		try {
			Class<?> Component = Class.forName("net.kyori.adventure.text.Component");
			Class<?> ClickEvent = Class.forName("net.kyori.adventure.text.event.ClickEvent");

			Method text = Component.getMethod("text", CharSequence.class);
			Method clickEventSetter = Component.getMethod("clickEvent", ClickEvent);
			Method openUrl = ClickEvent.getMethod("openUrl", String.class);

			Object comp = text.invoke(null, url);
			Object click = openUrl.invoke(null, clickTarget);
			return clickEventSetter.invoke(comp, click);
		} catch (ReflectiveOperationException ignored) {}

		// Fallback to Bungee TextComponent
		try {
			TextComponent link = new TextComponent(url);
			link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, clickTarget));
			return TextComponent.toLegacyText(new BaseComponent[] { link });
		} catch (Throwable t) {
			return message;
		}
	}
}
