package me.steelhayb.listeners;

import org.bukkit.plugin.java.JavaPlugin;

import me.steelhayb.listeners.join.JoinListener;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		new JoinListener(this);
	}
}
