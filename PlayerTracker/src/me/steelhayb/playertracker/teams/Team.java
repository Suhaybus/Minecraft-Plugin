package me.steelhayb.playertracker.teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

//import me.steelhayb.playertracker.utils.Utils;

public class Team {
	private static List<Player> redTeam = new ArrayList<Player>();
	private static List<Player> greenTeam = new ArrayList<Player>();
	
	public static void addToTeam(TeamType type, Player player) {
		switch (type) {
			case HUNTERS:
				redTeam.add(player);
				break;
			case SPEEDRUNNERS:
				greenTeam.add(player);
				break;
		}
//		player.sendMessage(Utils.chat("&aAdded to " + type.name() + " team!"));
		return;
	}
	
	public static boolean isInTeam(Player player) {
		return redTeam.contains(player) || greenTeam.contains(player);
	}
	
	public static boolean isInGreenTeam (Player player) {
		return greenTeam.contains(player);
	}
	
	public static boolean isInRedTeam (Player player) {
		return redTeam.contains(player);
	}
	
	public static void removeFromTeam(Player player) {
		if (redTeam.contains(player)) {
			redTeam.remove(player);
		}
		else if (greenTeam.contains(player)) {
			greenTeam.remove(player);
		}
	}
	
	public static void clearTeams() {
		redTeam.clear();
		greenTeam.clear();
	}
	
	public static TeamType getTeamType(Player player) {
		if (!isInTeam(player)) {
			return null;
		}
		return (redTeam.contains(player) ? TeamType.HUNTERS : TeamType.SPEEDRUNNERS);
	}
	
	public static List<Player> getRedTeam() {
		return redTeam;
	}
	
	public static List<Player> getGreenTeam() {
		return greenTeam;
	}
}
