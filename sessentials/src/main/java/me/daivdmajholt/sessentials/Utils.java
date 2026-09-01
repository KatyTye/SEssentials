package me.daivdmajholt.sessentials;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.google.gson.JsonObject;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
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

		String url = null;
		if (matcher.find()) url = matcher.group(1);
		if (url == null) return message;

		String clickTarget = stripColorCodes(url).trim();
		if (!(clickTarget.startsWith("http://") || clickTarget.startsWith("https://")))
			clickTarget = "https://" + clickTarget;

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

		try {
			TextComponent link = new TextComponent(url);
			link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, clickTarget));
			return TextComponent.toLegacyText(new BaseComponent[] { link });
		} catch (Throwable t) {
			return message;
		}
	}

	public static void sendCopyableMessage(Player player, String message) {
		Pattern pattern = Pattern.compile("\\{([^{}]+)}");
		Matcher matcher = pattern.matcher(message);

		try {
			Class<?> componentClass = Class.forName("net.kyori.adventure.text.Component");

			Class<?> clickEventClass = Class.forName("net.kyori.adventure.text.event.ClickEvent");

			Class<?> serializerClass = Class.forName("net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer");

			Method emptyMethod = componentClass.getMethod("empty");

			Method appendMethod = componentClass.getMethod("append", componentClass);

			Method clickEventMethod = componentClass.getMethod("clickEvent", clickEventClass);

			Method copyToClipboardMethod = clickEventClass.getMethod("copyToClipboard", String.class);

			Method legacySectionMethod = serializerClass.getMethod("legacySection");

			Object serializer = legacySectionMethod.invoke(null);

			Method deserializeMethod = serializerClass.getMethod("deserialize", String.class);

			Object finalComponent = emptyMethod.invoke(null);

			int lastEnd = 0;

			while (matcher.find()) {
				String before = message.substring(lastEnd, matcher.start());

				String copyText = matcher.group(1);

				if (!before.isEmpty()) {
					Object beforeComponent = deserializeMethod.invoke(serializer, cc(before));

					finalComponent = appendMethod.invoke(finalComponent, beforeComponent);
				}

				Object clickableComponent = deserializeMethod.invoke(serializer, cc(copyText));

				Object clickEvent = copyToClipboardMethod.invoke(null, copyText);

				clickableComponent = clickEventMethod.invoke(clickableComponent, clickEvent);

				finalComponent = appendMethod.invoke(finalComponent, clickableComponent);

				lastEnd = matcher.end();
			}

			String remaining = message.substring(lastEnd);

			if (!remaining.isEmpty()) {
				Object remainingComponent = deserializeMethod.invoke(serializer, cc(remaining));

				finalComponent = appendMethod.invoke(finalComponent, remainingComponent);
			}

			Method sendMessageMethod = player.getClass().getMethod("sendMessage", componentClass);

			sendMessageMethod.invoke(player, finalComponent);

			return;

		} catch (ReflectiveOperationException ignored) {}

		try {
			List<BaseComponent> components = new ArrayList<>();

			matcher.reset();

			int lastEnd = 0;

			while (matcher.find()) {
				String before = message.substring(lastEnd, matcher.start());

				String copyText = matcher.group(1);

				if (!before.isEmpty()) {
					BaseComponent[] beforeComponents = TextComponent.fromLegacyText(cc(before));

					Collections.addAll(components, beforeComponents);
				}

				BaseComponent[] clickableComponents = TextComponent.fromLegacyText(cc(copyText));

				for (BaseComponent component : clickableComponents) {
					component.setClickEvent(
						new ClickEvent(
							ClickEvent.Action.COPY_TO_CLIPBOARD,
							copyText
						)
					);
				}

				Collections.addAll(components, clickableComponents);

				lastEnd = matcher.end();
			}

			String remaining = message.substring(lastEnd);

			if (!remaining.isEmpty()) {
				BaseComponent[] remainingComponents = TextComponent.fromLegacyText(cc(remaining));

				Collections.addAll(components, remainingComponents);
			}

			player.spigot().sendMessage(
				components.toArray(BaseComponent[]::new)
			);

		} catch (Throwable ignored) {
			player.sendMessage(cc(message));
		}
	}
}
