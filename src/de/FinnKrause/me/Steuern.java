package de.FinnKrause.me;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class Steuern implements CommandExecutor{
	private File steuern = new File("plugins//FinnsPluginII//steuern.yml");
	private YamlConfiguration yamlsteuern = YamlConfiguration.loadConfiguration(steuern);
	
	private void reloadData() {
		yamlsteuern = YamlConfiguration.loadConfiguration(steuern);
	}
	
	public void addSteuern(Material material, int amount) {
		reloadData();
		yamlsteuern.set(material.name(), yamlsteuern.getInt(material.name()) + amount);
		try {yamlsteuern.save(steuern);}catch(Exception e) {}
	}
	
	public String[] getSteuern() throws FileNotFoundException, UnsupportedEncodingException {
		reloadData();
		String[] string2 = new String[2];
		try {
			string2[0] = ("Dias: " + yamlsteuern.get(Material.DIAMOND.name()));
			string2[1] = ("Emeralds: " + yamlsteuern.get(Material.EMERALD.name()));
		} catch (Exception e) {
			
		}

		return string2;
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			for (String ausgabe : getSteuern()) {
				sender.sendMessage(ausgabe);
			}
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {e.printStackTrace();}
		return false;
	}
}
