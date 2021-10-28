package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import java.util.*;

public class SchlafenCommand implements Listener{
	@EventHandler
	public void PlayerBedEnterEvent(PlayerBedEnterEvent event) {
		Runnable runnable = () -> {
			Player player = event.getPlayer();
			if (!(player.getWorld().getTime() > 0 && player.getWorld().getTime() < 12300) && !areotherssleeping(player.getName()) && wievieleSpieler() > 1) {
				for (Player player2 : Bukkit.getOnlinePlayers()) {
					player2.sendMessage("§f[§bSchlafen§f] §e§l" + String.valueOf(player.getName().charAt(0)).toUpperCase() + player.getName().substring(1) + " möchte schlafen!  "
							+ "\n§f    --> §cFehlend: " + welcheLeutefehlen(player.getName()));
				}
			}
			
			else if (!(player.getWorld().getTime() > 0 && player.getWorld().getTime() < 12300) && areotherssleeping(player.getName()) && wievieleSpieler() > 1) {
				for (Player player2 : Bukkit.getOnlinePlayers()) {
					player2.sendMessage("§f[§bSchlafen§f] §a" + String.valueOf(player.getName().charAt(0)).toUpperCase() + player.getName().substring(1) + " hat sich angeschlossen!"
							+ "\n§f    --> §cFehlend: " + welcheLeutefehlen(player.getName()));
				}
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	private boolean areotherssleeping(String playername) {
		boolean ausgabe = false;
		//Runnable Runnable2 = () -> {
			for (Player player3 : Bukkit.getOnlinePlayers()) {
				if (player3.isSleeping() && player3 != Bukkit.getPlayer(playername) && player3.getWorld() == Bukkit.getWorld("world")) {
					ausgabe = true;
				}
			}
		/*};
		
		Thread thread = new Thread(Runnable2);
		thread.start(); */
		
		

		return ausgabe;
	}
	
	private int wievieleSpieler() {
		int ausgabe = 0;
		for (@SuppressWarnings("unused") Player player : Bukkit.getOnlinePlayers()) {
			ausgabe++;
		}
		return ausgabe;
	}
	
	
	private List<String> welcheLeutefehlen(String playername) {
		
		List<String> Spieler = new ArrayList<String>();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			if(!player.isSleeping() && player.getName() != playername && player.getWorld() == Bukkit.getWorld("world")) {
				Spieler.add(player.getName());
			}
				
		}
		
		if (Spieler.size() == 0) {
			Spieler.add("Niemand");
		}
		return Spieler;
	}
}
