package me.suhayb.testing;

import org.bukkit.plugin.java.JavaPlugin;

import me.suhayb.testing.commands.Commands;
import me.suhayb.testing.listeners.Listeners;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {		
		new Listeners(this);
		new Commands(this);
	}

}
