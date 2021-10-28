package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Testing implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		double temprandom = Math.random();
		if (sender != Bukkit.getPlayer("finn4fun")) {
			sender.sendMessage("Du bist nicht Finn hehe");
			return false;
		}
		
		
		Player Finn = (Player) sender;
		for (int i = 0; i < 100; i++) {			
			temprandom = Math.random();
			if (temprandom < 0.01) {
				Finn.sendMessage("§a"+temprandom +"");
			}else {
				Finn.sendMessage(temprandom +"");
			}

		}
		
		Bukkit.broadcastMessage("jemand hat den super geheimen special command verwendet! Niemand anderes wird ihn finden §caußer LEOPOLD! HAHAHAHAH");
			
		
		return false;
	}
	
	@EventHandler
	public void MobDeathEvent(EntityDeathEvent event) {
		double temprandom = Math.random();
		if ( event.getEntity().getKiller() == Bukkit.getPlayer("finn4fun")) {
			event.getEntity().getKiller().sendMessage("accepted");
		}
		else {event.getEntity().getKiller().sendMessage("declined");}
		if (temprandom < 0.01) {
			event.getEntity().getKiller().sendMessage("§a rnd accepted §b["+temprandom+"]");
			//event.getDrops().add(new ItemStack(Material.VILLAGER_SPAWN_EGG));

		} else {
			event.getEntity().getKiller().sendMessage("§c rnd adeclined §b["+temprandom+"]");
		}
	}

}
