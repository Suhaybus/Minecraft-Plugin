package me.steelhayb.helloworld2;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
	
	// /hello <-- Hey, welcome!
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("hello")) {
			if (sender instanceof Player) {
				//player
				Player player = (Player) sender;
				if (player.hasPermission("hello.use")) {
					player.sendMessage(ChatColor.GREEN + "Hey welcome to the server!");
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission to execute this command!");
					return true;
				}
				
			}
			else {
				//console
				sender.sendMessage("Hey console!");
				return true;
			}
		}
		return false;
	}

}
