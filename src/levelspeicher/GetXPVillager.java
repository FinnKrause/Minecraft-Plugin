package levelspeicher;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.concurrent.ThreadLocalRandom;

public class GetXPVillager implements CommandExecutor, Listener{

	private File KannFrageHaben = new File("plugins//FinnsPluginII//KannFrageHaben.yml");
	private YamlConfiguration yamlKannFrageHaben = YamlConfiguration.loadConfiguration(KannFrageHaben);
	
	private File Fragen = new File("plugins//FinnsPluginII//Fragen.yml");
	private YamlConfiguration yamlFragen = YamlConfiguration.loadConfiguration(Fragen);
	
	private File FragenWarteListe = new File("plugins//FinnsPluginII//FragenWarteListe.yml");
	private YamlConfiguration yamlFragenWarteListe = YamlConfiguration.loadConfiguration(FragenWarteListe);
	
	private File Richtige = new File("plugins//FinnsPluginII//Richtige.yml");
	private YamlConfiguration yamlRichtige = YamlConfiguration.loadConfiguration(Richtige);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		loadConfig();
		Player player = (Player) sender;
		if (args.length < 1) {player.sendMessage(XPVillager.Anfang + "§c Bitte verwende: §a\"/answer <Antwort>\"§f!");return false;}
		if (yamlFragenWarteListe.getStringList(player.getName()).toString() == "[]") {player.sendMessage(XPVillager.Anfang + "§c Du hast gerade keine offne Frage!"); return false;}
		
		String Antwort = "";
		for (int i = 0; i < args.length-1; i++) {
			Antwort += args[i] + " ";
		}
		Antwort += args[args.length-1];

		if (!(yamlFragenWarteListe.getStringList(player.getName()).get(1).replace(" ", "").equalsIgnoreCase(Antwort.replace(" ", "")))) {
			player.sendMessage(XPVillager.Anfang + "§c Deine Antwort ist leider nicht richtig! Überprüfe nochmal deine Informationen und deine Rechtschreibung, Groß/Kleinschreibung kannst du vernachlässigen!");
			yamlRichtige.set(player.getName(), 0);
			try{yamlRichtige.save(Richtige);}catch(Exception e) {}
			player.sendMessage(XPVillager.Anfang + " " + yamlRichtige.getInt(player.getName()) +"/10");
			return false;
		}
		
		Bukkit.broadcastMessage(XPVillager.Anfang + "§c " + player.getName() + "§a hat gerade eine Frage richtig beantwortet!");
		yamlRichtige.set(player.getName(), yamlRichtige.getInt(player.getName()) + 1);
		try{yamlRichtige.save(Richtige);}catch(Exception e) {}
		player.sendMessage(XPVillager.Anfang + " " + yamlRichtige.getInt(player.getName()) +"/10");
		
		if (yamlRichtige.getInt(player.getName()) >= 10) {
			player.sendMessage(XPVillager.Anfang + "§a GLÜCKWUNSCH! Du kannst dir jetzt einen XP_Villager holen! Nutze dafür §c\"/xpvillager\"§a!");
			yamlRichtige.set(player.getName(), 0);
			try{yamlRichtige.save(Richtige);}catch(Exception e) {}
			
			File KannVillagerHaben = new File("plugins//FinnsPluginII//KannVillagerHaben.yml");
			YamlConfiguration yamlKannVillagerHaben = YamlConfiguration.loadConfiguration(KannVillagerHaben);
			
			yamlKannVillagerHaben.set(player.getName(), true);
			try{yamlKannVillagerHaben.save(KannVillagerHaben);}catch(Exception e) {}
		}
		
		yamlFragenWarteListe.set(player.getName(), null);
		try{yamlFragenWarteListe.save(FragenWarteListe);}catch(Exception e) {}
		
		
		return false;
	}
	
	@EventHandler
	public void detectEnderEiKlick(PlayerInteractEvent event) {
		if (event == null) return;
		if (event.getClickedBlock() == null) return;
		loadConfig();
		if (event.getClickedBlock().getType() != Material.DRAGON_EGG) return;
		event.setCancelled(true);
		Player player = (Player) event.getPlayer();
		
		
		if(yamlFragenWarteListe.getStringList(player.getName()).toString() != "[]") {
			player.sendMessage(XPVillager.Anfang + "§c Du hast aktuell noch eine Frage offen! Beantworte erst diese! §f" + yamlFragenWarteListe.getStringList(player.getName()).get(0));
			return;
		}
		
		if (!yamlKannFrageHaben.getBoolean(player.getName())) {
			player.sendMessage(XPVillager.Anfang + "§c Du kannst leider aktuell keine Frage bekommen!");
			return;
		}
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, yamlFragen.getInt("FragenMenge"));
		player.sendMessage(XPVillager.Anfang +"§a Es gibt eine Neue Frage:");
		player.sendMessage(XPVillager.Anfang + "§a Frage: §f" + yamlFragen.getStringList(randomNum+"").get(0));
		yamlKannFrageHaben.set(player.getName(), false);
		try{yamlKannFrageHaben.save(KannFrageHaben);}catch(Exception e) {}
		
		yamlFragenWarteListe.set(player.getName(), yamlFragen.getStringList(randomNum+""));
		try{yamlFragenWarteListe.save(FragenWarteListe);}catch(Exception e) {}
	}
	
	private void loadConfig() {
		yamlKannFrageHaben = YamlConfiguration.loadConfiguration(KannFrageHaben);
		yamlFragen = YamlConfiguration.loadConfiguration(Fragen);
		yamlFragenWarteListe = YamlConfiguration.loadConfiguration(FragenWarteListe);
		yamlRichtige = YamlConfiguration.loadConfiguration(Richtige);
	}
	
}
