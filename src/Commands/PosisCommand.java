package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PosisCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		if (label.equalsIgnoreCase("posis")) {
			Player player = (Player) sender;
			if (player instanceof Player) {
				Runnable runnable8 = () -> {
					int Abstand;
					String Biom = "";
					
					for (Player allplayerscurrentplayer : Bukkit.getOnlinePlayers()) {
						int Abstandx = Math.max((int)allplayerscurrentplayer.getLocation().getX(), (int)player.getLocation().getX()) - 
								Math.min((int)allplayerscurrentplayer.getLocation().getX(), (int)player.getLocation().getX());
						
						int Abstandy = Math.max((int)allplayerscurrentplayer.getLocation().getY(), (int)player.getLocation().getY()) - 
								Math.min((int)allplayerscurrentplayer.getLocation().getY(), (int)player.getLocation().getY());
						
						int Abstandz = Math.max((int)allplayerscurrentplayer.getLocation().getZ(), (int)player.getLocation().getZ()) - 
								Math.min((int)allplayerscurrentplayer.getLocation().getZ(), (int)player.getLocation().getZ());
						
						if (allplayerscurrentplayer.getWorld().getName().endsWith("world")) { Biom = "Overworld";}
						else if (allplayerscurrentplayer.getWorld().getName().endsWith("_nether")) {Biom = "Nether";}
						else {Biom = "The End";}
						
						Abstand = Abstandx + Abstandy + Abstandz;
						
						if (allplayerscurrentplayer != player) {
 							player.sendMessage("§b" + allplayerscurrentplayer.getName()
							+ ":\n >§6 x = " + (int)allplayerscurrentplayer.getLocation().getX() + "§a y = " + (int)allplayerscurrentplayer.getLocation().getY() + "§4 z = " + (int)allplayerscurrentplayer.getLocation().getZ()
							+ "\n >§f [§c" + Biom + ", " + allplayerscurrentplayer.getLocation().getBlock().getBiome() + "§f]\n > Abstand: [§a" + Abstand + "§f]\n " );
 							allplayerscurrentplayer.sendMessage("§f[§bPosis§f]§a Eine Gruppensuche wurde durchgeführt!");
 						}
					}
					

				};
				
				Thread thread8 = new Thread(runnable8);
				thread8.run();				
				}
			
				else {
						sender.sendMessage("Konsole");
				}

			}
			
			else {
				
			}
			
		return false;
	}

}
