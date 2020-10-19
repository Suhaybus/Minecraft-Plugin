package me.steelhayb.sudo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.steelhayb.sudo.utils.Utils;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		//startup
		//reloads
		//plugin reloads
	}
	
	@Override
	public void onDisable() {
		//shutdown
		//reloads
		//plugin reloads
	}
	
	// /sudo Steelhayb "haha you bad" <-- "<Steelhayb> haha you bad!"
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("shout" /*+ p2*/)) {
			Player p1 = (Player) sender;				
			if (p1.hasPermission("shout.use")) {
				//i need to be able to know how to set a guideline, like a model to structure the command as /sudo <player_name> <message> and how to track that player name
//				p1.sendMessage("Hey welcome to the server!");
				if (args.length != 2) {
					p1.sendMessage(Utils.chat("&cYou need to enter 2 arguments! /sudo <player> <message>"));
				} else {
					Bukkit.broadcastMessage("<" + args[0] + "> " + args.clone());
				}
				return true;
			} else {
				p1.sendMessage(Utils.chat("&cYou do not have permission to execute this command!"));
				return true;
			}
		}
		return false;
	}

}
