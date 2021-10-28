package Commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PositionCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		if (label.equalsIgnoreCase("posi")) {
			Player player = (Player) sender;
			if (player instanceof Player) {
				Runnable runnable = () -> {
					int Abstand;
					if (arg.length == 1) {
						Player player2 = (Player) Bukkit.getPlayer(arg[0]);
						if (player2 != null) {
							
							String Biom = "";
							if (player2.getWorld().getName().endsWith("world")) { Biom = "Overworld";}
							else if (player2.getWorld().getName().endsWith("_nether")) {Biom = "Nether";}
							else {Biom = "The End";}
						
							int Abstandx = Math.max((int)player2.getLocation().getX(), (int)player.getLocation().getX()) - 
									Math.min((int)player2.getLocation().getX(), (int)player.getLocation().getX());
							
							int Abstandy = Math.max((int)player2.getLocation().getY(), (int)player.getLocation().getY()) - 
									Math.min((int)player2.getLocation().getY(), (int)player.getLocation().getY());
							
							int Abstandz = Math.max((int)player2.getLocation().getZ(), (int)player.getLocation().getZ()) - 
									Math.min((int)player2.getLocation().getZ(), (int)player.getLocation().getZ());
							
							Abstand = Abstandx + Abstandy + Abstandz;
							
							player.sendMessage("§b§l" + String.valueOf(player2.getName().charAt(0)).toUpperCase() + player2.getName().substring(1) + "'s aktueller Aufenthaltsort ist:\n"
									+ "§6 x = " + (int) player2.getLocation().getX() + "§a y = " + (int) player2.getLocation().getY() + "§4 z = " + (int) player2.getLocation().getZ()
									+ "   §f[§c" + Biom + ", " + player2.getLocation().getBlock().getBiome() + "§f]\n --> " + Abstand + " Blöcke entfernt!");
							
							
							if (player.getName() != player2.getName()) {
								player2.sendMessage("§f[§bPosi§f]§a Nach dir wurde gesucht!");
							}
							
							Runnable runnable3234 = () -> {
								player2.setGlowing(true);
								try {
									TimeUnit.SECONDS.sleep(20);
								} catch (Exception e12){
									
								}
								player2.setGlowing(false);
							};
							Thread fdfd55 = new Thread(runnable3234);
							fdfd55.start();
							

							
						}
					}
					
					else if (arg.length == 2) {
						Player player2 = (Player) Bukkit.getPlayer(arg[0]);
						Player player3 = (Player) Bukkit.getPlayer(arg[1]);
						if (player2 != null && player3 != null) {
							String Biom = "";
							if (player2.getWorld().getName().endsWith("world")) { Biom = "Overworld";}
							else if (player2.getWorld().getName().endsWith("_nether")) {Biom = "Nether";}
							else {Biom = "The End";}
						
							int Abstandx = Math.max((int)player2.getLocation().getX(), (int)player3.getLocation().getX()) - 
									Math.min((int)player2.getLocation().getX(), (int)player3.getLocation().getX());
							
							int Abstandy = Math.max((int)player2.getLocation().getY(), (int)player3.getLocation().getY()) - 
									Math.min((int)player2.getLocation().getY(), (int)player3.getLocation().getY());
							
							int Abstandz = Math.max((int)player2.getLocation().getZ(), (int)player3.getLocation().getZ()) - 
									Math.min((int)player2.getLocation().getZ(), (int)player3.getLocation().getZ());
							
							Abstand = Abstandx + Abstandy + Abstandz;
							
							player3.sendMessage("§f[§bPosi§f]§c " + player.getName() +"§f hat dir den Standort von §c" + player2.getName() + " §fgesendet:\n"
									+ "§6 x = " + (int) player2.getLocation().getX() + "§a y = " + (int) player2.getLocation().getY() + "§4 z = " + (int) player2.getLocation().getZ()
									+ "   §f[§c" + Biom + ", " + player2.getLocation().getBlock().getBiome() + "§f]\n --> " + Abstand + " Blöcke entfernt!");
							
							if (player3 != player2) {
								player2.sendMessage("§f[§bPosi§f]§a Dein Standort wunde von §c" + player.getName() + "§a an §c" + player3.getName() + "§a versendet!"); }
								
							if (player != player3) { 
								player.sendMessage("[§bPosi§f] Der Standort von §c" + player2.getName() + "§f wurde an §c" + player3.getName() + "§f versendet!"); }
							
						}
					}
				
						else {
							String Biom = "";
							if (player.getWorld().getName().endsWith("world")) { Biom = "Overworld";}
							else if (player.getWorld().getName().endsWith("_nether")) {Biom = "Nether";}
							else {Biom = "The End";}
							
							player.sendMessage("§b§lDein aktueller Aufenthaltsort ist:\n"
									+ "§6 x = " + (int)player.getLocation().getX() + "§a y = " + (int)player.getLocation().getY() + "§4 z = " + (int)player.getLocation().getZ()
									+ "§f  [§c" + Biom + ", " + player.getLocation().getBlock().getBiome()+ "§f]");
						}
				};
				
				Thread thread66 = new Thread(runnable);
				thread66.run();
				
				}
			
				else {
						sender.sendMessage("Konsole"); //Konsole
				}

			}
			
			else {
				//If equals ignorecase
			}
			
		return false;
	}

}
