package me.steelhayb.supermanhunt;

import org.bukkit.plugin.java.JavaPlugin;

import me.steelhayb.supermanhunt.commands.InitCommands;
import me.steelhayb.supermanhunt.listeners.Listeners;
import me.steelhayb.supermanhunt.teams.Team;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();

		new Listeners(this);
		new InitCommands(this);
		Team.clearTeams();
//		HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	}
	
	@Override
	public void onDisable() {
		Team.clearTeams();
	}
}
