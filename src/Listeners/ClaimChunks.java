package Listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.FinnKrause.me.Steuern;

public class ClaimChunks implements CommandExecutor, Listener {

	private File file = new File("plugins//FinnsPluginII//config.yml");
	private File AnzahlFile = new File("plugins//FinnsPluginII//Anzahlfile.yml");
	private File permissions = new File("plugins//FinnsPluginII//permissions.yml");
	
	private YamlConfiguration yamlconfiguration = YamlConfiguration.loadConfiguration(file);
	private YamlConfiguration yamlAnzahlFile = YamlConfiguration.loadConfiguration(AnzahlFile);
	private YamlConfiguration yamlpermissions = YamlConfiguration.loadConfiguration(permissions);
	
	public String Anfang = "§f[§bClaim-Chunks§f] ";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cConsole");
			return false;
		}
			Player player = (Player) sender;
			
			if (args.length == 0) {
				player.sendMessage(Anfang + "§cBitte verwende §b/claimchunk §e<add, remove, list, buy>§c oder §b/claimchunk §epermissions <Spielername>!");
				return false;
			}
			
			if (args.length == 1) {
				yamlconfiguration = YamlConfiguration.loadConfiguration(file);
				yamlAnzahlFile = YamlConfiguration.loadConfiguration(AnzahlFile);
				yamlpermissions = YamlConfiguration.loadConfiguration(permissions);
				if (args[0].contains("add")) {
					storeChunk(player.getLocation(), player);
				}
				else if (args[0].contains("remove")) {
					ArrayList<String> rückgabe = alreadyTaken(player.getLocation());
					ArrayList<String> Liste = new ArrayList<String>();
					if (rückgabe.toArray()[0].toString() == "true" && rückgabe.toArray()[1] == player.getName()) {
						Liste = (ArrayList<String>) yamlconfiguration.getStringList(player.getName());
						for (int i = 0; i <= Liste.size(); i++) {
							Liste.remove(player.getLocation().getChunk().toString());
						}
						yamlconfiguration.set(player.getName(), Liste);
						player.sendMessage(Anfang+"§aDu hast den Chunk §c" +player.getLocation().getChunk().toString() + "§a gelöscht!");
						try {yamlconfiguration.save(this.file);} catch (IOException e) {e.printStackTrace();}
						return false;
					}
					else if (rückgabe.toArray()[0].toString() == "true" && rückgabe.toArray()[1] != player.getName()) {
						Liste = (ArrayList<String>) yamlconfiguration.getStringList(player.getName());
						player.sendMessage(Anfang + "§cDu kannst dieses Grundstück nicht unclaimen, da es " + rückgabe.toArray()[1] + " gehört!");
						return false;
					}
					
					else if (rückgabe.toArray()[0].toString() == "false") {
						player.sendMessage(Anfang + "§cDieser Chunk gehört noch niemandem!");
						return false;
					}	
				}
				
				else if(args[0].contains("list")) {
					player.sendMessage(yamlconfiguration.getStringList(player.getName()).toString());
				}
				
				else if (args[0].contains("buy")) {
					if (player.getInventory().getItemInMainHand().getType() != Material.EMERALD) {
						player.sendMessage(Anfang + "§cBitte nimm 30 Emaralds in deine Main-Hand!");
						return false;
					}
					if (player.getInventory().getItemInMainHand().getAmount() < 30) {
						player.sendMessage("§cBitte nimm weitere §a" + (30 - player.getInventory().getItemInMainHand().getAmount()) + " Emeralds §cin deine Main-Hand!");
						return false;
					}
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 30);
					yamlAnzahlFile.set(player.getName(), yamlAnzahlFile.getInt(player.getName()) + 1);
					try {yamlAnzahlFile.save(AnzahlFile);} catch (IOException e) {e.printStackTrace();}
                    Steuern steuern = new Steuern();
                    steuern.addSteuern(Material.EMERALD, 30);
                    Bukkit.broadcastMessage(Anfang + "§c" + player.getName()+ "§a hat sich erfolgreich einen weiteren Chunk gekauft!");
				}
			}
			
			else if (args.length == 2) {
				if (!args[0].contains("permissions")) {
					player.sendMessage(Anfang + "§cBitte verwende §b/claimchunk §e<add, remove, list, buy>§c oder §b/claimchunk §epermissions <Spielername/list>!");
					return false;
				}
				
				if (args[1].contains("list"))
				{
					player.sendMessage(Anfang + "§a Whitelist:\n§f" + yamlpermissions.getStringList(player.getName()).toString());
					return false;
				}
				Player tochangeplayer = Bukkit.getPlayer(args[1]);
				if (tochangeplayer == null) {
					player.sendMessage(Anfang + "§cDieser Spieler existiert nicht! Sieh dir deine Whitelist mit §b/claimchunk permissions list§c!");
					return false;
				}
				if (yamlpermissions.getStringList(player.getName()).contains(tochangeplayer.getName())) {
					ArrayList<String> aktuelleListe = new ArrayList<>(yamlpermissions.getStringList(player.getName()));
					aktuelleListe.remove(tochangeplayer.getName());
					yamlpermissions.set(player.getName(), aktuelleListe);
					try {yamlpermissions.save(this.permissions);} catch (Exception e) {e.printStackTrace();}
					player.sendMessage(Anfang + "§aDu hast erfolgreich §c" + tochangeplayer.getName() + "§a von deiner Whitelist gelöscht!");
					tochangeplayer.sendMessage(Anfang + "§c " + player.getName() + "§a hat dich aus seiner Whitelist geworfen!");
					return false;
				}
				else {
					ArrayList<String> aktuelleListe = new ArrayList<>(yamlpermissions.getStringList(player.getName()));
					aktuelleListe.add(tochangeplayer.getName());
					yamlpermissions.set(player.getName(), aktuelleListe);
					try {yamlpermissions.save(this.permissions);} catch (Exception e) {e.printStackTrace();}
					player.sendMessage(Anfang + "§aDu hast erfolgreich §c" + tochangeplayer.getName() + "§a in deine Whitelist aufgenommen!");
					tochangeplayer.sendMessage(Anfang + "§c " + player.getName() + "§a hat dich in seine Whitelist aufgenommen!");
					return false;
				}
		}
		return false;
	}
	
	public ArrayList<String> alreadyTaken(Location loc) {
		ArrayList<String> Liste = new ArrayList<String>();
		ArrayList<String> Rückgabe = new ArrayList<String>();
		Runnable runnableAlready = () -> {
			Liste.clear();	
			for (OfflinePlayer checkedPlayer : Bukkit.getWhitelistedPlayers()) {
				for (String chunk : yamlconfiguration.getStringList(checkedPlayer.getName())) {
					Liste.add(chunk);}
				if (Liste.contains(loc.getChunk().toString())) {
					Rückgabe.add("true"); Rückgabe.add(checkedPlayer.getName());
					return;
				}
			}
			
			for (String chunk : yamlconfiguration.getStringList("Merlin")) {
				Liste.add(chunk);}
			if (Liste.contains(loc.getChunk().toString())) {
				Rückgabe.add("true"); Rückgabe.add("Merlin");
				return;
			}
			
			for (String chunk : yamlconfiguration.getStringList("Staatseigentum")) {
				Liste.add(chunk);}
			if (Liste.contains(loc.getChunk().toString())) {
				Rückgabe.add("true"); Rückgabe.add("Staatseigentum");
				return;
			}
			
			for (String chunk : yamlconfiguration.getStringList("Mosi&Finn")) {
				Liste.add(chunk);}
			if (Liste.contains(loc.getChunk().toString())) {
				Rückgabe.add("true"); Rückgabe.add("Mosi&Finn");
				return;
			}
			
			Rückgabe.add("false"); Rückgabe.add("");
		};
		Thread threadTaken =new Thread(runnableAlready);
		threadTaken.run();
		try {threadTaken.join();} catch (InterruptedException e) {e.printStackTrace();}
		return Rückgabe;
	}
	
	public void storeChunk(Location locisave, Player player) {
		Runnable runnable = () -> {
			ArrayList<String> positions = new ArrayList<>(yamlconfiguration.getStringList(player.getName()));
			ArrayList<String> rückgabe = alreadyTaken(locisave);
			if (rückgabe.toArray()[0] == "true") {
				player.sendMessage(Anfang + "§cDieses Grundstück gehört bereits §a" + rückgabe.toArray()[1].toString());
				player.sendMessage(Anfang + "Aktuelle Grundstücke: " + yamlconfiguration.getStringList(player.getName()).size() + "/" + (yamlAnzahlFile.getInt("Standart") + yamlAnzahlFile.getInt(player.getName())));
				return;
			}
			if (yamlconfiguration.getStringList(player.getName()).size() >= yamlAnzahlFile.getInt("Standart") + yamlAnzahlFile.getInt(player.getName())) {
				player.sendMessage(Anfang + "§cDu hast bereits zu viele Grundstücke!");
				player.sendMessage(Anfang + "Aktuelle Grundstücke: " + yamlconfiguration.getStringList(player.getName()).size() + "/" + (yamlAnzahlFile.getInt("Standart") + yamlAnzahlFile.getInt(player.getName())));
				return;
			}
			positions.add(locisave.getChunk().toString());
			yamlconfiguration.set(player.getName(), positions);
			try {yamlconfiguration.save(this.file);}catch (IOException e) {e.printStackTrace();}
			player.sendMessage(Anfang + "§aDu hast erfolgreich einen Chunk gesaved!");
			player.sendMessage(Anfang + "Aktuelle Grundstücke: " + yamlconfiguration.getStringList(player.getName()).size() + "/" + (yamlAnzahlFile.getInt("Standart") + yamlAnzahlFile.getInt(player.getName())));
			Bukkit.broadcastMessage(Anfang + "§c" + player.getName() + "§a hat sich gerade den Chunk §c" + player.getLocation().getChunk().toString() + "§a geclaimed!");
			//Bukkit.getServer().reload();
		};
		
		Thread threadStore = new Thread(runnable);
		threadStore.run();	
		try {threadStore.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
	
}