package me.steelhayb.playertracker.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.steelhayb.playertracker.Main;
import me.steelhayb.playertracker.teams.Team;
import me.steelhayb.playertracker.teams.TeamType;
import me.steelhayb.playertracker.utils.Utils;

public class InitCommands implements CommandExecutor {
	
	private Main plugin;
	
	public InitCommands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("join").setExecutor(this);
		plugin.getCommand("leave").setExecutor(this);
		plugin.getCommand("team").setExecutor(this);
		plugin.getCommand("player").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("join")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("join.use")) {
					if (Team.isInTeam(p)) {
						p.sendMessage(Utils.chat("&cYou are already in a team!"));
						return true;
					}				
					else if (args.length == 1 && args[0].equalsIgnoreCase("Hunters")) {
						Team.addToTeam(TeamType.HUNTERS, p);
						if (!(p.getInventory().contains(new ItemStack(Material.COMPASS)))) {
							p.getInventory().addItem(new ItemStack(Material.COMPASS));
						}
						p.sendMessage(Utils.chat(plugin.getConfig().getString("joinhunters_message")));
						return true;
					} 
					else if (args.length == 1 && args[0].equalsIgnoreCase("Speedrunners")) {
						Team.addToTeam(TeamType.SPEEDRUNNERS, p);
						p.sendMessage(Utils.chat(plugin.getConfig().getString("joinspeedrunners_message")));
						return true;
					} else {
						p.sendMessage(Utils.chat("&cError! Invalid arguments given!"));
						p.sendMessage(Utils.chat("&cTry '/join Hunters' or '/join Speedrunners'"));
					}
					return true;
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("leave")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("leave.use") && Team.isInTeam(p)) {
					if (args.length != 1) {
						p.sendMessage(Utils.chat("&cError! Invalid arguments! Try '/leave <team>'."));
						return true;
					}				
					else if (args[0].equalsIgnoreCase("Hunters")) {
						if (Team.isInRedTeam(p)) {
							Team.removeFromTeam(p);
							p.sendMessage(Utils.chat(plugin.getConfig().getString("leaveteam_message")));
						} else {
							p.sendMessage(Utils.chat("&cYou aren't in that team! Enter '/team' or '/player list' to check."));
						}
						return true;
					}
					else if (args[0].equalsIgnoreCase("Speedrunners")) {
						if (Team.isInGreenTeam(p)) {
							Team.removeFromTeam(p);
							p.sendMessage(Utils.chat(plugin.getConfig().getString("leaveteam_message")));
						} else {
							p.sendMessage(Utils.chat("&cYou aren't in that team! Enter '/team' or '/player list' to check."));
						}
						return true;
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("team")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("team.use")) {
					if (args.length == 0) {
						if (Team.isInRedTeam(p)) {
							p.sendMessage(Utils.chat(plugin.getConfig().getString("inhunters_message")));
							return true;
						} 
						else if (Team.isInGreenTeam(p)){
							p.sendMessage(Utils.chat(plugin.getConfig().getString("inspeedrunners_message")));
							return true;
						} 
						else if (!(Team.isInTeam(p))) {
							p.sendMessage(Utils.chat(plugin.getConfig().getString("notinteam_message")));
							return true;
						}
					} else {
						p.sendMessage(Utils.chat("&cCommand entered incorrectly. Enter '/team'."));
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("player")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Only players may execute this command!");
				return true;
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("player.use")) {
					List<String> hunters = new ArrayList<String>();
					List<String> speedrunners = new ArrayList<String>();
					List<String> rest = new ArrayList<String>();

					if (args[0].equalsIgnoreCase("list") && args.length == 1) {
						for (Player player : plugin.getServer().getOnlinePlayers()) {							
							if (Team.isInRedTeam(player)) {
								hunters.add(player.getName());
							}
							else if (Team.isInGreenTeam(player)) {
								speedrunners.add(player.getName());
							}
							else {
								rest.add(player.getName());
							}
						}
						
						p.sendMessage(Utils.chat("&cHunters: " +  hunters));
						p.sendMessage(Utils.chat("&aSpeedrunners: " +  speedrunners));
						p.sendMessage(Utils.chat("Unassigned players: " +  rest));
						hunters.clear();
						speedrunners.clear();
						rest.clear();
						return true;
					}
					
					else {
						p.sendMessage(Utils.chat("&cInvalid arguments! Enter '/player list'."));
					}
				}
			}
		}
		return false;
	}
}
