package me.steelhayb.supermanhunt.teams;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Angel {
	
	public static void givePerks(Player player) {
		if (Team.isInAngel(player)) {
			player.setGameMode(GameMode.CREATIVE);
		}
		
//		if (Team.getDemons().size() > 0) {
//			;
//		}
	}
	
}
