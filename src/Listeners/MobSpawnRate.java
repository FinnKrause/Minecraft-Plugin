package Listeners;

import java.util.Random;

import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnRate implements Listener{
	@EventHandler
	public void KreaturenSpawnEvent(EntitySpawnEvent event) {
		
		if (event.getEntity() instanceof Skeleton) {
		
			Random rnd = new Random();
			double random = rnd.nextDouble();
				
			if (random > 0.6) {
				//Bukkit.getPlayer("YTCracky").sendMessage("§cGelöscht: " + event.getEntity().getName());
				event.setCancelled(true);
			}
			else {
				//Bukkit.getPlayer("YTCracky").sendMessage("§aGelassen: " + event.getEntity().getName());
			}
		}
		
		else {
			//Bukkit.getPlayer("YTCracky").sendMessage(event.getEntity().getName() + "");
		}

	}
}