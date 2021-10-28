package de.FinnKrause.me;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Commands.AfkMessage;
import Commands.DowngradeCommand;
import Commands.EnderperlenLöschCommand;
import Commands.MendingUndInfinity;
import Commands.PosisCommand;
import Commands.PositionCommand;
import Commands.UpgradeCommand;
import Commands.bringmichausdemendCommand;
import Listeners.ClaimChunks;
import Listeners.HasSomebodyCreative;
import Listeners.KopfDropPluginEvent;
import Listeners.ListenForEventsToClaim;
import Listeners.MobSpawnRate;
import Listeners.OnPlayerJoin;
import Listeners.SchlafenCommand;
import Listeners.SecretMobDrop;
import levelspeicher.GetXPVillager;
import levelspeicher.XPVillager;

public class Main extends JavaPlugin {
 	
	@Override
	public void onEnable() {
		getCommand("posi").setExecutor(new PositionCommand());
		getCommand("home").setExecutor(new bringmichausdemendCommand());
		getCommand("posis").setExecutor(new PosisCommand());
		getCommand("upgrade-unnormal").setExecutor(new MendingUndInfinity());
		getCommand("upgrade").setExecutor(new UpgradeCommand());
		getCommand("enderperlen").setExecutor(new EnderperlenLöschCommand());
		getCommand("downgrade").setExecutor(new DowngradeCommand());
		getCommand("claimchunk").setExecutor(new ClaimChunks());
		getCommand("message").setExecutor(new AfkMessage());
		getCommand("xpvillager").setExecutor(new XPVillager());
		getCommand("answer").setExecutor(new GetXPVillager());
		getCommand("steuern").setExecutor(new Steuern());
		Bukkit.getServer().getPluginManager().registerEvents(new SchlafenCommand(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new KopfDropPluginEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new MobSpawnRate(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SecretMobDrop(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new HasSomebodyCreative(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ListenForEventsToClaim(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new XPVillager(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GetXPVillager(), this);
		
		int verzögerung = ThreadLocalRandom.current().nextInt(0, 70000);
		int repeat = ThreadLocalRandom.current().nextInt(40000, 60000);
		System.out.println(verzögerung/20/60 + "Minuten " + repeat/20/60+"Minuten");

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			File KannFrageHaben = new File("plugins//FinnsPluginII//KannFrageHaben.yml");
			YamlConfiguration yamlKannFrageHaben = YamlConfiguration.loadConfiguration(KannFrageHaben);
			@Override
			public void run() {
				 for (Player player : Bukkit.getOnlinePlayers()) {
					 yamlKannFrageHaben.set(player.getName(), true);
					 player.sendMessage("§f[§bXP§f]§a Eine neue Frage ist für dich vergügbar!");
				 }
				 try {yamlKannFrageHaben.save(KannFrageHaben);} catch (IOException e) {e.printStackTrace();}
			}
		}, verzögerung, repeat);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						switch (onlinePlayer.getName()) {
							case "SeesternVier351": return;
							default: onlinePlayer.sendMessage("Shutdown canceled");
						}
					}
					return;
				}
				else Bukkit.getServer().shutdown();
				
			}
			
		}, 18000, 18000);
	}	
}	