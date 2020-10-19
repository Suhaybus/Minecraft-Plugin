package me.steelhayb.playertracker.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
//import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.steelhayb.playertracker.Main;
import me.steelhayb.playertracker.teams.Team;
import me.steelhayb.playertracker.teams.TeamType;
import me.steelhayb.playertracker.utils.Utils;

public class TrackingListener implements Listener{
	
	private Main plugin;
	
	public TrackingListener (Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onHold(PlayerItemHeldEvent event) { //maybe i want it so that this is triggered when right-clicking compass instead of moving from compass
		if (Team.getGreenTeam().size() != 1 || Team.getRedTeam().size() < 1) {
			Bukkit.broadcastMessage(Utils.chat("&c There are no speedrunners/hunters or there are more than one speedrunners online! Enter '/player list' to see who is in what team."));
			return;
		} else {
			Player p = event.getPlayer();
			Player speedrunner = Team.getGreenTeam().get(0);
			double yaw = speedrunner.getLocation().getYaw();
		
			if (p.getInventory().getItemInMainHand().getType() == Material.COMPASS && Team.getTeamType(p) == TeamType.HUNTERS) { //getItemInMainHand is the item prior to the item you are currently holding
				p.sendMessage(Utils.chat(plugin.getConfig().getString("tracking_message").replace("<player>", speedrunner.getName())));
				p.setCompassTarget(speedrunner.getLocation());
				double newYaw = speedrunner.getLocation().getYaw();
				while (newYaw != yaw) {
					p.sendMessage(Utils.chat(plugin.getConfig().getString("tracking_message").replace("<player>", speedrunner.getName())));
					p.setCompassTarget(speedrunner.getLocation());
					yaw = newYaw;
					newYaw = speedrunner.getLocation().getYaw();
					continue;
				}
			}
			return;
		}
	}
	
	@EventHandler
	public void onDeath(PlayerRespawnEvent event) {
		Player p = event.getPlayer();
		
		if (Team.isInRedTeam(p)) {
			p.getInventory().addItem(new ItemStack(Material.COMPASS));
		}
	}
	//if im using track(), then all i need in onHold is to say Player p = event.getPlayer(), then check if p is hunter, then print tracking_message.
//	@EventHandler
//	public void track(PlayerMoveEvent event) {
//		Player speedrunner = event.getPlayer();
//		Player hunters = (Player) Team.getRedTeam();
//		if (Team.isInGreenTeam(speedrunner)) {
//			hunters.setCompassTarget(speedrunner.getLocation());
//		//print tracking_message?
//		}
//	}
}
