package Commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class AfkMessage implements CommandExecutor {

	private File messages = new File("plugins//FinnsPluginII//messages.yml");
	public String beginning = "§f[§bMessages§f]";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		YamlConfiguration yamlmessages = YamlConfiguration.loadConfiguration(messages);
		Player player = (Player) sender;
		
		if (args.length < 2) {
			 player.sendMessage(beginning + "§c Bitte verwende \"§a/message <Empfänger> <Nachricht>§c!");
			 return false;
		}
		
		/*if (Bukkit.getPlayer(args[1]) == null) {
			player.sendMessage(beginning + " §c Dieser Spieler ist leider nicht auf diesem Server! Bitte schau nochmal nach, ob du dich verschrieben hast!");
			return false;
		}*/
		
		if(!isonServer(args[0], player)) {
			player.sendMessage(beginning + " §c Dieser Spieler ist leider nicht auf diesem Server! Bitte schau nochmal nach, ob du dich verschrieben hast!");
			return false;
		}
		
		String Message = "§b" + player.getName() + ":§a ";
		for (int i = 1; i < args.length; i++) {
			Message += args[i] + " ";
		}
	
		ArrayList<String> speichern = new ArrayList<String>(yamlmessages.getStringList(args[0]));
		speichern.add(Message);

		yamlmessages.set(args[0], speichern);
		try {yamlmessages.save(this.messages);} catch (IOException e) {e.printStackTrace();}
		
		player.sendMessage(beginning + "§a Deine Nachricht wurde erfolgreich gespeichert! Sie wird §c" + args[0] + "§a beim nächsten mal joinen angezeigt!");
		return true;
	}
	
	private boolean isonServer(String name, Player debug) {
		for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
			if(onlineplayer.getName().equals(name)) {
				return true;
			}
		}
		for (OfflinePlayer playeroffline : Bukkit.getOfflinePlayers()) {
			if (playeroffline.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	 
}
