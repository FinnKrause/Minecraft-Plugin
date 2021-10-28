package Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Listeners.ClaimChunks;

public class bringmichausdemendCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		ClaimChunks Prüfung = new ClaimChunks();
		if (label.equalsIgnoreCase("home")) {
			Player player = (Player) sender;
			if (player instanceof Player) {
				if (arg.length == 1) {
					if (arg[0] == player.getName()) {
						player.sendMessage("§f[§bHome§f]§c Bitte gib denn Command ohne Erweiterung ein!");
					}
					
					else {
						player.sendMessage("§f[§bHome§f]§c Du kannst nur dich selbst teleportieren!");
					}

				}
				
				else if (Bukkit.getWorld("world") != player.getWorld() || (Prüfung.alreadyTaken(player.getLocation()).toArray()[0] == "true" && Prüfung.alreadyTaken(player.getLocation()).toArray()[1] != player.getName())) { // && Bukkit.getWorld("world_nether") != player.getWorld()
					Runnable runnable = () -> {
						
						if (player.getBedSpawnLocation() != null ) {
							player.teleport(player.getBedSpawnLocation());
						}
						
						else {
							Location loci = new Location(Bukkit.getWorld("world"), 2500, 70, -1200);
							player.teleport(loci);
							player.sendMessage("§f[§bHome§f]§c Du hast kein Bett, deshalb bist du hier gespawnt!");
						}
						
						player.sendMessage("§f[§bHome§f]§a  Du wurdest nach Hause gebracht!");
						
					};
					
					Thread thread = new Thread(runnable);
					thread.run();

				}
				
				else {
					player.sendMessage("§f[§bHome§f]§c Du bist nicht im End oder in einem geclaimten Chunk!");
				}
			}
		}
		return false;
	}

}
