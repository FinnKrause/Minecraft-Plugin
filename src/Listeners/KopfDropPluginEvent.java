package Listeners;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.FinnKrause.me.Steuern;

import org.bukkit.event.EventHandler;

public class KopfDropPluginEvent implements Listener  {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Runnable runnable = () -> {
			double RandomZahl = Math.random();
			Player player = (Player) event.getEntity();
			if (RandomZahl <= 0.20) {
				if (player instanceof Player && player.getKiller() instanceof Player) {
					Player Killer = (Player) player.getKiller();
					Killer.getInventory().addItem(getPlayerHead(player.getName()));
                    Steuern steuern = new Steuern();
                    steuern.addSteuern(Material.PLAYER_HEAD, 1);
					Bukkit.broadcastMessage("§f[§bKopf§f]§c " + String.valueOf(Killer.getName().charAt(0)).toUpperCase() + Killer.getName().substring(1) + "§a hat den Kopf von §c" + player.getName() + "§a bekommen!");
				}
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.start();
	}


	
	@SuppressWarnings("deprecation")
	public ItemStack getPlayerHead(String player) {
		boolean isNewVersion = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");
		
		Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
		ItemStack item = new ItemStack(type, 1);
		if (!isNewVersion)
			item.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(player);
		item.setItemMeta(meta);
		
		return item;
	}
}
