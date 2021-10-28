package Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.FinnKrause.me.Enchantments;
import de.FinnKrause.me.Steuern;

public class UpgradeCommand implements CommandExecutor{

	public static int Level_Needed = 30;
	public static int Diamonds_Needed = 10;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//upgrade2 Command
		if(args.length == 1) {
			Enchantment searchedEntschy = null;
			Enchantments ent = new Enchantments();
			
			if (args[0].contains("options")) {
				Player player = (Player) sender;
				player.sendMessage("[§bEnchantements§f]§a Es gibt folgende Optionen: " + ent.EnchantmentsList);
				return false;
			} 
			Player player = (Player) sender;
			//hier ist der StickPart
			if (args[0].equals("debug")) {
				Material mats = player.getInventory().getItemInOffHand().getType();
				if (player.getInventory().getItemInMainHand().getType() != Material.STICK || mats != Material.DIAMOND || (mats == Material.STICK && player.getInventory().getItemInMainHand().getAmount() > 1)) {
					player.sendMessage("[§bEnchantements§f]§c Bitte schau, dass du §a5 Diamanten §cin deiner Offhand und §a1 Stick§c in deiner Main Hand hast!");
					return false;
				}
				ItemStack stack = new ItemStack(Material.DEBUG_STICK,1);
				ItemStack stack2 = new ItemStack(Material.DIAMOND, player.getInventory().getItemInOffHand().getAmount() - 5);
				Steuern steuern = new Steuern();
				steuern.addSteuern(Material.DIAMOND, 5);
				player.getInventory().setItemInMainHand(stack);
				player.getInventory().setItemInOffHand(stack2);
				return false;
			}
			try {
				searchedEntschy = ent.getEnchantment(args[0]);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			if (searchedEntschy != null) {
				if(player instanceof Player && player != null) {
					if(player.getLevel() >= Level_Needed) {
						if (player.getInventory().getItemInOffHand().getType() == Material.DIAMOND) {
							if (player.getInventory().getItemInOffHand().getAmount() >= Diamonds_Needed) {
								if (searchedEntschy.getMaxLevel() != player.getInventory().getItemInMainHand().getEnchantmentLevel(searchedEntschy)) {
									if (player.getInventory().getItemInMainHand().getType() != Material.BOW) {
										try {
											player.getInventory().getItemInMainHand().addEnchantment(searchedEntschy, player.getInventory().getItemInMainHand().getEnchantmentLevel(searchedEntschy) + 1);
											player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - Diamonds_Needed);
											player.setLevel(player.getLevel() - Level_Needed);
											Steuern steuern = new Steuern();
											steuern.addSteuern(Material.DIAMOND, Diamonds_Needed);
											Bukkit.broadcastMessage("[§bEnchantements§f]§c " + player.getName() + "§a hat sich gerade ein Item mit §c" + args[0] +"§a geupgradet!");
										}
										catch (Exception ex) {
											player.sendMessage("[§bEnchantements§f]§c Du kannst dieses Item nicht verzaubern oder verwende §a/upgrade-unnormal§c!");
										}
									}
									
									else {
										if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.ARROW_INFINITE) && searchedEntschy == Enchantment.MENDING) {
											player.sendMessage("[§bEnchantements§f]§c Bitte verwende §a/upgrade-unnormal§c!");
										}
										else if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.MENDING) && searchedEntschy == Enchantment.ARROW_INFINITE) {
											player.sendMessage("[§bEnchantements§f]§c Bitte verwende §a/upgrade-unnormal§c!");
										}
										else {
											player.getInventory().getItemInMainHand().addEnchantment(searchedEntschy, player.getInventory().getItemInMainHand().getEnchantmentLevel(searchedEntschy) + 1);
											player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - Diamonds_Needed);
											player.setLevel(player.getLevel() - Level_Needed);
											Steuern steuern = new Steuern();
											steuern.addSteuern(Material.DIAMOND, Diamonds_Needed);
											Bukkit.broadcastMessage("[§bEnchantements§f]§c " + player.getName() + "§a hat sich gerade ein Item mit §c" + args[0] +" §a geupgradet!");
										}

									}

								}
								else {
									//Schon maximales Level
									player.sendMessage("[§bEnchantements§f]§c Du hast schon die maximale Stufe des Entchantments!");
								}

							}
							else {
								//Nicht genug Diamanten
								player.sendMessage("[§bEnchantements§f]§c Bitte nimm weitere §a" + (Diamonds_Needed - player.getInventory().getItemInOffHand().getAmount()) + "§c Diamanten in die Off-Hand!");
							}

						}
						else {
							//Keine Diamods in der Off-Hand
							player.sendMessage("[§bEnchantements§f]§c Bitte nimm §a" + Diamonds_Needed + "§c Diamanten in deine Off-Hand!");
						}

					}
					else {
						//Zu wenig Level!
						player.sendMessage("[§bEnchantements§f]§c Du brauchst §a" + (Level_Needed - player.getLevel()) + "§c Level mehr um das Upgrade zurchzuführen!");
					}
					

					
				}
				else {
					sender.sendMessage("[§bEnchantements§f]§c Fehler oder Konsole!");
				}
			}
			
			else {
				player.sendMessage("[§bEnchantements§f]§c Dieses Enchantement gibt es nicht! Um nachzusehen benutzt §b\"/upgrade options\"§c!");
			}

		}
		
		else {
			//Keine Argumente
			Player player = (Player) sender;
			player.sendMessage("[§bEnchantements§f]§c Bitte verwende: §e/upgrade <Enchantment>§c oder sieh dir die Optionen mit §e/upgrade options§c!");
		}

		
		return false;
	}
	
}
