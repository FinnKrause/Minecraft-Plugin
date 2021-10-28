package Listeners;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class ListenForEventsToClaim implements Listener{
	private File permissions = new File("plugins//FinnsPluginII//permissions.yml");
	private YamlConfiguration yamlpermissions = YamlConfiguration.loadConfiguration(permissions);
	String beginning = "§f[§bClaim-Chunks§f] ";
	
	@EventHandler
	public void PlayerAbbauEvent(BlockBreakEvent event) {
		yamlpermissions = YamlConfiguration.loadConfiguration(permissions);
		Runnable runnable = () -> {
			Player abbauer = event.getPlayer();
			ClaimChunks chunks = new ClaimChunks();
			ArrayList<String> rückgabe = chunks.alreadyTaken(event.getBlock().getLocation());
			if (rückgabe.toArray()[0].toString() == "true" && (rückgabe.toArray()[1].toString() != abbauer.getName() &&  !yamlpermissions.getStringList(rückgabe.toArray()[1].toString()).contains(abbauer.getName()))) { //yamlpermissions.getStringList(rückgabe.toArray()[1].toString()).contains(abbauer.getName())
				event.setCancelled(true);
				abbauer.sendMessage(chunks.Anfang + "§cDieses Grundstück gehört§a " + rückgabe.toArray()[1].toString() + "§c du kannst hier nicht abbauen!");
				try {
					Player gehörender = Bukkit.getPlayer(rückgabe.toArray()[1].toString());
					gehörender.sendMessage(beginning+"§a"+abbauer.getName() + "§c versuchte gerade §a" + event.getBlock().getType() + "§c bei dir abzubauen!");
				}catch(Exception e) {
				}
			}
		};
		Thread threadAbbauen = new Thread(runnable);
		threadAbbauen.run();
		try {threadAbbauen.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@EventHandler
	public void PlayerBlockPlaceEvent(BlockPlaceEvent event) {
		yamlpermissions = YamlConfiguration.loadConfiguration(permissions);
		Runnable runnable = () -> {
			Player abbauer = event.getPlayer();
			ClaimChunks chunks = new ClaimChunks();
			ArrayList<String> rückgabe = chunks.alreadyTaken(event.getBlock().getLocation());
			if (rückgabe.toArray()[0].toString() == "true" && (rückgabe.toArray()[1].toString() != abbauer.getName() &&  !yamlpermissions.getStringList(rückgabe.toArray()[1].toString()).contains(abbauer.getName()))) {
				event.setCancelled(true);
				abbauer.sendMessage(chunks.Anfang + "§cDieses Grundstück gehört§a " + rückgabe.toArray()[1].toString() + "§c du kannst hier nicht bauen!");
				try {
					Player gehörender = Bukkit.getPlayer(rückgabe.toArray()[1].toString());
					gehörender.sendMessage(beginning+"§a"+abbauer.getName() + "§c versuchte gerade §a" + event.getBlock().getType() + "§c bei dir zu bauen!");
				}catch(Exception e) {}
			}
		};
		Thread threadAbbauen = new Thread(runnable);
		threadAbbauen.run();
		try {threadAbbauen.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@EventHandler
	public void PlayerBucketEvent(PlayerBucketEmptyEvent event) {
		BucketEvent(event);
	}
	
	@EventHandler
	public void PlayerBucketEvent(PlayerBucketFillEvent event) {
		BucketEvent(event);
	}
	
	private void BucketEvent(PlayerBucketEvent event) {

			yamlpermissions = YamlConfiguration.loadConfiguration(permissions);
			Runnable runnable = () -> {
				Player abbauer = event.getPlayer();
				ClaimChunks chunks = new ClaimChunks();
				ArrayList<String> rückgabe = chunks.alreadyTaken(event.getBlock().getLocation());
				if (rückgabe.toArray()[0].toString() == "true" && (rückgabe.toArray()[1].toString() != abbauer.getName() &&  !yamlpermissions.getStringList(rückgabe.toArray()[1].toString()).contains(abbauer.getName()))) {
					event.setCancelled(true);
					abbauer.sendMessage(chunks.Anfang + "§cDieses Grundstück gehört§a " + rückgabe.toArray()[1].toString() + "§c du kannst hier nicht bauen!");
					try {
						Player gehörender = Bukkit.getPlayer(rückgabe.toArray()[1].toString());
						gehörender.sendMessage(beginning+"§a"+abbauer.getName() + "§c versuchte gerade einen Block bei dir zu bauen!");
					}catch(Exception e) {}
				}
			};
			Thread threadAbbauen = new Thread(runnable);
			threadAbbauen.run();
			try {threadAbbauen.join();} catch (InterruptedException e) {e.printStackTrace();}
		}
	
}
