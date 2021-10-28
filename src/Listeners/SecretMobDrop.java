package Listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class SecretMobDrop implements Listener {
	public void getMobKillEventt(EntityDeathEvent event) {
		Random rnd = new Random();
		double random = rnd.nextDouble();
		if (random > 0.99) {
			ItemStack stack = new ItemStack(Material.NETHERITE_INGOT,1);
			event.getDrops().add(stack);
		} else return;
		
	}

}
