package me.steelhayb.morecommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.steelhayb.morecommands.utils.Utils;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shout")) {
			if (args.length >=1) {
				Bukkit.broadcastMessage(args.toString());
				return true;
			}
		}
		else if (command.getName().equalsIgnoreCase("heal")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			} else {
				Player p = (Player) sender;
				if (args.length == 1) {
					Player player = (Player) Bukkit.getOnlinePlayers();
					player.setHealth(20);
					player.setSaturation(20);
					p.sendMessage(Utils.chatf(String.format("&a%s has been healed."), player.getName()));
					//you may need to just use config and .replace
					p.sendMessage(Utils.chat("&a<player> has been healed.").replace("<player>", player.getName()));
					return true;
				} else {
					p.sendMessage(Utils.chat("&cInvalid arguments! Enter '/heal <player>'"));
				}
			}
		}
		
//		return super.onCommand(sender, command, label, args);
		return false;
	}
}
