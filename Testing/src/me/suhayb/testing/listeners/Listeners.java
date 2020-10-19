package me.suhayb.testing.listeners;

import org.bukkit.Bukkit;
//import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import me.suhayb.testing.Main;

public class Listeners implements Listener{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Listeners(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public static void noclip(PlayerGameModeChangeEvent event) {
		Player p = event.getPlayer();
		p.sendMessage("hey");
//		if (p.getGameMode() == GameMode.SPECTATOR) {
//			long start = System.nanoTime();
//			long end = System.nanoTime();
//			
//			while ((end - start) < 2000000000) {
//				end = System.nanoTime();
//				continue;
//			}
//			p.setGameMode(GameMode.SURVIVAL);
//		}
	}
}
