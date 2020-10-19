package me.steelhayb.supermanhunt.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.steelhayb.supermanhunt.Main;
import me.steelhayb.supermanhunt.teams.Demons;
import me.steelhayb.supermanhunt.teams.Team;
import me.steelhayb.supermanhunt.utils.Utils;

public class Listeners implements Listener{
	
	private Main plugin;
	
	private HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	
	public Listeners (Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void track(PlayerMoveEvent event) {
		if (Team.getSpeedrunner().size() != 1 || Team.getDemons().size() < 1) {
//			Bukkit.broadcastMessage(Utils.chat("&c There are no Angels/Speedrunners/Demons or there is more than one Angel/Speedrunner online! Enter '/player list' to see who is in what team."));
			return;
		} else {		
			Player speedrunner = event.getPlayer();
			Player demons = (Player) Team.getDemons();
		
			if (Team.isInSpeedrunner(speedrunner)) {
				demons.setCompassTarget(speedrunner.getLocation());
				demons.sendMessage(Utils.chat(plugin.getConfig().getString("tracking_message").replace("<player>", speedrunner.getName())));
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerRespawnEvent event) {
		Player p = event.getPlayer();
		
		if (Team.isInDemons(p)) {
			Demons.givePerks(p);
		}
		
	}
	
//	public void isNearAngel() {
//		
//		if (Team.getSpeedrunner().size() != 1 || Team.getAngel().size() != 1 || Team.getDemons().size() < 1) {
//			Bukkit.broadcastMessage(Utils.chat("&c There are no Angels/Speedrunners/Demons or there is more than one Angel/Speedrunner online! Enter '/player list' to see who is in what team."));
//			return;
//		} else {
//			Player angel = (Player) Team.getAngel();
//			Player demons = (Player) Team.getDemons();
//			Player speedrunner = (Player) Team.getSpeedrunner();
//			
//			Entity mobs = Monster.class.cast(null);
//			
//			float demonsYaw = demons.getLocation().getYaw();
//			float angelYaw = angel.getLocation().getYaw();
//			float speedrunnerYaw = speedrunner.getLocation().getYaw();
//			
//			while(demonsYaw - angelYaw == 3.0 || demonsYaw - angelYaw == -3.0) {
//				demons.showPlayer(plugin, speedrunner);
//				Bukkit.broadcastMessage(Utils.chat("&dA &c&ldemon &dhas been revealed!"));
//			}
////			demons.hidePlayer(plugin, speedrunner);
//			
//			while ((speedrunnerYaw - angelYaw <= 3.0 || speedrunnerYaw - angelYaw >= -3.0) && (speedrunner.getLocation().getY() - angel.getLocation().getY() <= 3 || speedrunner.getLocation().getY() - angel.getLocation().getY() >=-3)) {
//				speedrunner.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999999, 2));
//			}
//			
//			if (-3.0 <= angelYaw - mobs.getLocation().getYaw() || angelYaw - mobs.getLocation().getYaw() <= 3.0) {
//				((Monster) mobs).setHealth(20);
//			}
//			
//		}
//	}
	
	public void isNearAngelFAKE() {
		Player angel = (Player) Team.getAngel();
		Player demons = (Player) Team.getDemons();
		Player speedrunner = (Player) Team.getSpeedrunner();
		
		Entity mobs = Monster.class.cast(null);
		
		float demonsYaw = demons.getLocation().getYaw();
		float angelYaw = angel.getLocation().getYaw();
		float speedrunnerYaw = speedrunner.getLocation().getYaw();
		
		while(demonsYaw - angelYaw == 3.0 || demonsYaw - angelYaw == -3.0) {
			demons.showPlayer(plugin, speedrunner);
			Bukkit.broadcastMessage(Utils.chat("&dA &c&ldemon &dhas been revealed!"));
		}
//		demons.hidePlayer(plugin, speedrunner);
		
		while ((speedrunnerYaw - angelYaw <= 3.0 || speedrunnerYaw - angelYaw >= -3.0) && (speedrunner.getLocation().getY() - angel.getLocation().getY() <= 3 || speedrunner.getLocation().getY() - angel.getLocation().getY() >=-3)) {
			speedrunner.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999999, 2));
		}
		
		if (-3.0 <= angelYaw - mobs.getLocation().getYaw() || angelYaw - mobs.getLocation().getYaw() <= 3.0) {
			((Monster) mobs).setHealth(20);
		}
	}
	
	@EventHandler
	public void noclip(PlayerGameModeChangeEvent event) {
		Player p = event.getPlayer();
		
		if (Team.isInDemons(p) && p.getGameMode() == GameMode.SPECTATOR) {
			long start = System.nanoTime();
			long end = System.nanoTime();
			
			while (end - start < 2_000_000_000L) {
				end = System.nanoTime();
				continue;
			}
			p.setGameMode(GameMode.SURVIVAL);
//			p.isPermissionSet("gmsp.use");
		}
		PermissionAttachment attachment = p.addAttachment(plugin);
		perms.put(p.getUniqueId(), attachment);
			
		PermissionAttachment pPerms = perms.get(p.getUniqueId());
		pPerms.setPermission("gmsp.use", false);
			
		long cooldownStart = System.nanoTime();
		long cooldownEnd = System.nanoTime();
			
		while (cooldownEnd - cooldownStart < 30_000_000_000L) {
			cooldownEnd = System.nanoTime();
			if (((cooldownStart - cooldownEnd) % 1000000000) == 0) { //shouldn't it be cooldownEnd - cooldownStart here? Idk anymore it's been too long...
				p.sendMessage(ChatColor.RED + "You have " + ((30_000_000_000L / 1000000000) - ((cooldownEnd - cooldownStart) / 1000000000)) + " seconds left!");
			}
			continue;
		}			
		pPerms.setPermission("gmsp.use", true);
	}
	
	@EventHandler (ignoreCancelled = true)
	public void noArmour(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getSlotType().equals(SlotType.ARMOR) && event.getInventory().getItem(event.getSlot()) == null && Team.isInDemons(player)) {
			event.setCancelled(true);
		}
	}
	
//	@EventHandler
//	
//	public void killMobs(EntityEvent event, Player angel) {
//		if (Team.isInAngel(angel) /*&& event.getEntityType() == EntityType.ZOMBIE */) {
//			Monster mobs = (Monster) event.getEntity();
//			if (event.getEntityType() != EntityType.ENDER_DRAGON) {
//				float angelYaw = angel.getLocation().getYaw();
//				float mobYaw = mobs.getLocation().getYaw();
//				if ((-3.0 <= (angelYaw - mobYaw) || (angelYaw - mobYaw) <= 3.0)) {
//					mobs.setHealth(0);
//				}
//			}
//		}
//	}
	
//	@EventHandler
//	public void mobNear(EntityEvent event) {
////		Entity entity = event.getEntity();
////		Location monLoc = event.getLocation();
//		Entity mobs = event.getEntity();
//		Player angel = (Player) Team.getAngel();
//		float mobYaw = mobs.getLocation().getYaw();
//		float angelYaw = angel.getLocation().getYaw();
//		double radius = angelYaw - mobYaw;
//		
//		
//		if (mobs instanceof Monster) {
//			if (-3.0 <= radius || radius <= 3.0) {
//				((Monster) mobs).setHealth(20);
//			}
//		}
//	}
}
