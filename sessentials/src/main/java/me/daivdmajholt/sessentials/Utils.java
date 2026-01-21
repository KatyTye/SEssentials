package me.daivdmajholt.sessentials;

import org.bukkit.ChatColor;

public class Utils {
	
	public static String cc(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static <T> T reqNN(T obj) {
		return java.util.Objects.requireNonNull(obj);
	}
}
