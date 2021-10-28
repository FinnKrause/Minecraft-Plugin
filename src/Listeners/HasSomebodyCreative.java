package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class HasSomebodyCreative implements Listener {
	@EventHandler
	public void DetectCreative(PlayerGameModeChangeEvent event) {
		Player player = (Player) event.getPlayer();
		if(event.getNewGameMode() == GameMode.CREATIVE) {
			Runnable runnable100 = () -> {
				for (int i = 0; i < 10; i++) {
						Bukkit.broadcastMessage("[§bACHTUNG§c]§c " + player.getName() + "§f ist soeben in den Creative gegangen!");
					}
			};
			
			Thread thread = new Thread(runnable100);
			thread.run();

		}
	}
}
