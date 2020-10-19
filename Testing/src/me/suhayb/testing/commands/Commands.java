package me.suhayb.testing.commands;

//import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

//import com.google.common.base.Stopwatch;

import me.suhayb.testing.Main;

public class Commands implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Commands(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("gmsp").setExecutor(this);
		plugin.getCommand("give").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		
		if (cmd.getName().equalsIgnoreCase("gmsp")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players may execute this command!");
				return true;
			} else {
				Player p = (Player) sender;
				p.hasPermission("gmsp.use");
				if (args.length == 0) {
					p.setGameMode(GameMode.SPECTATOR);
					p.sendMessage(ChatColor.GREEN + "Gamemode has successfully been changed.");
					
					long startTime = System.nanoTime();
					long endTime = System.nanoTime();
					
					while ((endTime - startTime) < 2000000000) {
						endTime = System.nanoTime();
						if (((startTime - endTime) % 1000000000) == 0) {
							p.sendMessage(ChatColor.RED + "You have " + ((2000000000 / 1000000000) - ((endTime - startTime) / 1000000000)) + " seconds left!");
						}
						continue;
					}
					
					Location playerLocation = p.getLocation();
					p.setGameMode(GameMode.SURVIVAL);
					p.teleport(playerLocation);
					p.sendMessage(ChatColor.GREEN + "Gamemode has successfully been changed.");
					
//					long startCooldown = System.nanoTime();
//					long endCooldown = System.nanoTime();
//					
//					while ((endCooldown - startCooldown) < (long) 2147483647) {
//						;
//					}
					
//					Stopwatch stopwatch = Stopwatch.createStarted();
////					@SuppressWarnings("unused")
////					boolean perms = p.hasPermission("gmsp.use");
//					
//					while (stopwatch.elapsed(TimeUnit.SECONDS) < 30) {
////						p.unsetPermission("permission.here");
////						if (perms == true) {
////							perms = false;
////						}
//						
//						continue;
//					}
//					
//					stopwatch.stop();
					
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Incorrect use of command. Enter '/gmsp'.");
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("give")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players may execute this command!");
			} else {
				Player p = (Player) sender;
				
				p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
				long start = System.nanoTime();
				long end = System.nanoTime();
				
				while ((end - start) < 2000000000) {
					end = System.nanoTime();
					continue;
				}
				
				p.sendMessage(ChatColor.GREEN + "Done.");
			}
		}		
		
		return false;
	}
}
