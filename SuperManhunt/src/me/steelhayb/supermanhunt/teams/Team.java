package me.steelhayb.supermanhunt.teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Team {
	private static List<Player> Angel = new ArrayList<Player>();
	private static List<Player> Speedrunner = new ArrayList<Player>();
	private static List<Player> Demons = new ArrayList<Player>();
	
	public static void addToTeam(TeamType type, Player player) {
		switch (type) {
		case ANGEL:
			Angel.add(player);
			break;
		case SPEEDRUNNER:
			Speedrunner.add(player);
			break;
		case DEMONS:
			Demons.add(player);
		}
		return;
	}
	
	public static boolean isInTeam (Player player) {
		return Angel.contains(player) || Speedrunner.contains(player) || Demons.contains(player);
	}
	
	public static boolean isInAngel (Player player) {
		return Angel.contains(player);
	}
	
	public static boolean isInSpeedrunner (Player player) {
		return Speedrunner.contains(player);
	}
	
	public static boolean isInDemons (Player player) {
		return Demons.contains(player);
	}	
	
	public static void removeFromTeam(Player player) {
		if (Angel.contains(player)) {
			Angel.remove(player);
		}
		else if (Speedrunner.contains(player)) {
			Speedrunner.remove(player);
		}
		else if (Demons.contains(player)) {
			Demons.remove(player);
		}
	}
	
	public static void clearTeams() {
		Angel.clear();
		Speedrunner.clear();
		Demons.clear();
	}
	
	public static List<Player> getAngel() {
		return Angel;
	}
	
	public static List<Player> getSpeedrunner() {
		return Speedrunner;
	}
	
	public static List<Player> getDemons() {
		return Demons;
	}

}
