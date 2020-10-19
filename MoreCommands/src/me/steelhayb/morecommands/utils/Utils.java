package me.steelhayb.morecommands.utils;

import org.bukkit.ChatColor;

public class Utils {

	public static String chat (String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String chatf (String s1, String s2) {
		return ChatColor.translateAlternateColorCodes('&', s1);
	}
}