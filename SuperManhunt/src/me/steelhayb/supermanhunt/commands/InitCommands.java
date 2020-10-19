package me.steelhayb.supermanhunt.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.steelhayb.supermanhunt.Main;
import me.steelhayb.supermanhunt.teams.Angel;
import me.steelhayb.supermanhunt.teams.Demons;
import me.steelhayb.supermanhunt.teams.Team;
import me.steelhayb.supermanhunt.teams.TeamType;
import me.steelhayb.supermanhunt.utils.Utils;

public class InitCommands implements CommandExecutor{
	
	private Main plugin;
	
	public InitCommands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("team").setExecutor(this);
		plugin.getCommand("player").setExecutor(this);
		plugin.getCommand("gmsp").setExecutor(this);
		plugin.getCommand("roll").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	
		//create team command for players to check which team they are in
		if (cmd.getName().equalsIgnoreCase("team")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Utils.chat(plugin.getConfig().getString("noconsole_message")));
				return true;
			} else {
				Player p = (Player) sender;
				if (args.length == 0) {
					if (Team.isInAngel(p)) {
						p.sendMessage(Utils.chat(plugin.getConfig().getString("isAngel_message")));
						return true;
					} else if (Team.isInSpeedrunner(p)) {
						p.sendMessage(Utils.chat(plugin.getConfig().getString("isSpeedrunner_message")));
						return true;
					} else if (Team.isInDemons(p)) {
						p.sendMessage(Utils.chat(plugin.getConfig().getString("isDemon_message")));
						return true;
					} else {
						p.sendMessage(Utils.chat(plugin.getConfig().getString("notinteam_message")));
					}
				}
				else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("leave")) {
						if (Team.isInTeam(p)) {
							Team.removeFromTeam(p);
							p.sendMessage(Utils.chat(plugin.getConfig().getString("leaveteam_message")));
						} else {
							p.sendMessage(Utils.chat(plugin.getConfig().getString("notinteam_message")));
						}
						return true;
					} else {
						p.sendMessage(Utils.chat(plugin.getConfig().getString("error_message")));
						p.sendMessage(Utils.chat(plugin.getConfig().getString("&cTry '/team leave'.")));
					}
				}
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("join")) {
						if (!(Team.isInTeam(p))) {
							if (args[1].equalsIgnoreCase("Angel")) {
								if (Team.getAngel().size() == 1) {
									p.sendMessage(Utils.chat("&cError! This team already has a player! Enter '/player list' to check."));
								} else {
									
									//adding to Angel team
									Team.addToTeam(TeamType.ANGEL, p);
									Angel.givePerks(p);
//									if (Team.getDemons().size() > 0) {
//										p.hidePlayer(plugin, (Player) Team.getDemons());
//									}
//									if (Team.getSpeedrunner().size() > 0) {
//										p.hidePlayer(plugin, (Player) Team.getSpeedrunner());
//									}
//									p.hidePlayer(plugin, (Player) Team.getDemons());
//									p.hidePlayer(plugin, (Player) Team.getSpeedrunner());
									
									//confirmation message
									p.sendMessage(Utils.chat(plugin.getConfig().getString("joinAngel_message")));
								}
							}
							else if (args[1].equalsIgnoreCase("Speedrunner")) {
								if (Team.getSpeedrunner().size() == 1) {
									p.sendMessage(Utils.chat("&cError! This team already has a player! Enter '/player list' to check."));
								} else {
									//adding to Speedrunner team
									Team.addToTeam(TeamType.SPEEDRUNNER, p);
									
									//check if Angel is online
									if (Team.getAngel().size() > 0) {
										p.hidePlayer(plugin, (Player) Team.getAngel());	//hiding Angel from speedrunner
										//i cant really show because theres no point and i cant say ok hide angel from speedrunner here, it has to be done earlier where it currently is.
									}
									//check if Demons are online
									if (Team.getDemons().size() > 0) {
										p.hidePlayer(plugin, (Player) Team.getDemons()); //hiding demons from speedrunner								
									}
									
									//confirmation message
									p.sendMessage(Utils.chat(plugin.getConfig().getString("joinSpeedrunner_message")));
								}
							}
							else if (args[1].equalsIgnoreCase("Demons")) {
								//add demon to Demons team
								Team.addToTeam(TeamType.DEMONS, p);
								
								//checking if Angel is online
								if (Team.getAngel().size() > 1) {
									p.hidePlayer(plugin, (Player) Team.getAngel()); //hiding Angel from Demons
								}
								
								//giving demon items								
								Demons.givePerks(p);
								
								//confirmation message
								p.sendMessage(Utils.chat(plugin.getConfig().getString("joinDemons_message")));
							} else {
								p.sendMessage(Utils.chat("&cThat team does not exist! Did you mean: "));
								p.sendMessage(Utils.chat("&cAngel        Speedrunner        Demons"));
							}
						} else {
							p.sendMessage(Utils.chat("&cYou are already in a team!"));
						}
						return true;
					} else {
						p.sendMessage(Utils.chat(plugin.getConfig().getString("error_message")));
						p.sendMessage(Utils.chat("&cTry '/join <team>'"));
					}
				}
				else {
					p.sendMessage(Utils.chat(plugin.getConfig().getString("error_message")));
					p.sendMessage(Utils.chat("&cDid you mean: "));
					p.sendMessage(Utils.chat("&c'/team'        '/team join <team>'        '/team leave'"));
				}
			}
		}
		
		
		
		//create player list command for players to see which players are in which team
		if (cmd.getName().equalsIgnoreCase("player")) {
			if (args[0].equalsIgnoreCase("list") && args.length == 1) {
				List<String> angel = new ArrayList<String>();
				List<String> speedrunner = new ArrayList<String>();
				List<String> demons = new ArrayList<String>();
				List<String> rest = new ArrayList<String>();
				
				for (Player player : plugin.getServer().getOnlinePlayers()) {							
					if (Team.isInAngel(player)) {
						angel.add(player.getName());
					}
					else if (Team.isInSpeedrunner(player)) {
						speedrunner.add(player.getName());
					}
					else if (Team.isInDemons(player)) {
						demons.add(player.getName());
					}
					else {
						rest.add(player.getName());
					}
				}
				
				sender.sendMessage(Utils.chat("&6Angel: " +  angel));
				sender.sendMessage(Utils.chat("&aSpeedrunner: " +  speedrunner));
				sender.sendMessage(Utils.chat("&cDemons: " + demons));
				sender.sendMessage(Utils.chat("Unassigned players: " +  rest));
				
				angel.clear();
				speedrunner.clear();
				demons.clear();
				rest.clear();
				
				return true;
			}
			
			else {
				sender.sendMessage(Utils.chat("&cInvalid arguments! Enter '/player list'."));
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("gmsp")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Utils.chat(plugin.getConfig().getString("noconsole_message")));
				return true;
			} else {
				Player p = (Player) sender;
//				Player angel = (Player) Team.getAngel();
				if (Team.isInDemons(p)) {
					if (p.hasPermission("gmsp.use")) {
						if (args.length == 0) {
							p.setGameMode(GameMode.SPECTATOR);
//							angel.showPlayer(plugin, p); //making Demons still visible to the Angel.
							return true;
						} else {
							p.sendMessage(Utils.chat(plugin.getConfig().getString("error_message")));
							p.sendMessage(Utils.chat("&cEnter '/gmsp'"));
						}
					} else {
						p.sendMessage(Utils.chat("&cYou do not have permission to use this command!"));
					}
					
				} else {
					p.sendMessage(Utils.chat("&cOnly demons may use this command!"));
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("roll")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Utils.chat(plugin.getConfig().getString("noconsole_message")));
				return true;
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("roll.use")) {
					if (args.length == 0) {
						Team.clearTeams();
						List<String> roles = new ArrayList<String>();
						roles.add(0, "Demons");
						roles.add(0, "Speedrunner");
						roles.add(0, "Angel");
						
						int number = 3;
						
						for (Player player : Bukkit.getOnlinePlayers()) {
							Random random = new Random();
							int a = random.nextInt(number);
							String selectedRole = roles.get(a);
							
							if (selectedRole == "Angel" && roles.contains("Angel")) {
								Team.addToTeam(TeamType.ANGEL, player);
								Angel.givePerks(player);
								player.sendMessage(Utils.chat(plugin.getConfig().getString("joinAngel_message")));
								
								roles.remove("Angel");
//								roles.remove(a);
								number -= 1;
							}				
							else if (selectedRole == "Speedrunner" && roles.contains("Speedrunner")) {
								Team.addToTeam(TeamType.SPEEDRUNNER, player);
								player.hidePlayer(plugin, (Player) Team.getAngel());
								player.hidePlayer(plugin, (Player) Team.getDemons());
								player.sendMessage(Utils.chat(plugin.getConfig().getString("joinSpeedrunner_message")));
								
								roles.remove("Speedrunner");
								number -= 1;
							} else {
								Team.addToTeam(TeamType.DEMONS, player);
								player.hidePlayer(plugin, (Player) Team.getAngel());								
								Demons.givePerks(player);
								player.sendMessage(Utils.chat(plugin.getConfig().getString("joinDemons_message")));
							}

						}
						
						roles.clear();
						return true;
						
					} else {
						p.sendMessage(Utils.chat(plugin.getConfig().getString("error _message")));
						p.sendMessage(Utils.chat("Try '/roll'"));
					}
				} else {
					p.sendMessage(Utils.chat("&cYou do not have permission to use this command!"));
				}
			}
		}
		
		return false;
	}

}
