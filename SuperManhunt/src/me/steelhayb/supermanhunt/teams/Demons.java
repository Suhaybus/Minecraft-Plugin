package me.steelhayb.supermanhunt.teams;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Demons {

	public static void givePerks(Player player) {
		if (Team.isInDemons(player)) {
			ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD, 1);
			diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			diamondSword.addEnchantment(Enchantment.VANISHING_CURSE, 1);
			
			if (!(player.getInventory().contains(diamondSword))) {
				player.getInventory().addItem(diamondSword);
			}
			
			if (!(player.getInventory().contains(Material.COMPASS))) {
				player.getInventory().addItem(new ItemStack(Material.COMPASS));
			}
			
			//giving demon speed
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 0));
			
		}
	}
}
