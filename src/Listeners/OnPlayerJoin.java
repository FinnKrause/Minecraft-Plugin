package Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {
	private File messages = new File("plugins//FinnsPluginII//messages.yml");
	@EventHandler
	public void SendPlayerInfosAtJoin(PlayerJoinEvent event) {
		Player player = (Player) event.getPlayer();
		event.setJoinMessage(null);
		
		for (Player currentPlayerinLoop : Bukkit.getOnlinePlayers()) {
			if (currentPlayerinLoop != player) {
				currentPlayerinLoop.sendMessage("ße" + player.getName() + " joined the game");
			}
		}

		player.sendMessage("------ ßaWillkommenßf ------\n"
				+ "ßaAktuelle Commands:\n"
				+ "ßb > posi/posis\n"
				+ " > home\n"
				+ " > claimchunk\n"
				+ " > upgrade-unnormal (bow/stick)\n"
				+ " > upgrade ßa(options)\nßb"
				+ " > enderperlen\n"
				+ " > downgrade ßa(options)\nßb"
				+ " > message ßa<Empf‰nger> <Nachricht> ßb\n"
				+ "ßf------ßa Viel Spaﬂßf ------");
		

		YamlConfiguration yamlmessages = YamlConfiguration.loadConfiguration(messages);
		if (yamlmessages.getStringList(player.getName()) == null) {
			return;
		}
		
		for (String message : yamlmessages.getStringList(player.getName())) {
			player.sendMessage(message);
		}
		yamlmessages.set(player.getName(), null);
		try{yamlmessages.save(this.messages);}catch(Exception e){};

	
	}
	

}
