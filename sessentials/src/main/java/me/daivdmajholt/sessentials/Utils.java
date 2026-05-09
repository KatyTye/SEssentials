package me.daivdmajholt.sessentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.JsonObject;

public class Utils {
	
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

}