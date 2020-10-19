package me.steelhayb.playertracker;

import org.bukkit.plugin.java.JavaPlugin;

import me.steelhayb.playertracker.commands.InitCommands;
import me.steelhayb.playertracker.listener.TrackingListener;
import me.steelhayb.playertracker.teams.Team;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		saveDefaultConfig();

		new TrackingListener(this);
		new InitCommands(this);
		Team.clearTeams();
	}
	
	@Override
	public void onDisable() {
		Team.clearTeams();
	}
}
