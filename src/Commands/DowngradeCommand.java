package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import de.FinnKrause.me.Enchantments;

public class DowngradeCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Enchantments entsch = new Enchantments();
		Player player = (Player) sender;
	
		if (args.length == 1) {
			Enchantment searchedEntschy = entsch.getEnchantment(args[0]);
			if (args[0].contains("options")) {
				player.sendMessage("[§DDowngrade§f]§a Es gibt folgende Optionen: " + entsch.EnchantmentsList);
				return false;
			} 
			
			else if(searchedEntschy == null) {
				player.sendMessage("[§DDowngrade§f]§c Dieses Enchantment gibt es nicht, bitte verwende: §e/downgrade <Enchantment>§c oder sieh dir die Optionen mit §e/downgrade options§c an!");
			}
			
			else if(!player.getInventory().getItemInMainHand().containsEnchantment(searchedEntschy)) {
				player.sendMessage("[§DDowngrade§f]§c Dein Item hat §a" + args[0]+ "§c nicht!");
				return false;
			}
			else {
				try {
					int EnchantmentLevel = (player.getInventory().getItemInMainHand().getEnchantmentLevel(searchedEntschy) - 1 != 0) ? player.getInventory().getItemInMainHand().getEnchantmentLevel(searchedEntschy) - 1 : 500;
					player.getInventory().getItemInMainHand().removeEnchantment(searchedEntschy);
					if (EnchantmentLevel == 500) {
						Bukkit.broadcastMessage("[§DDowngrade§f]§c " + player.getName() + "§a hat gerade ein Item von §c" + args[0] + "§a befreit!");
						return false;
					}
					
					player.getInventory().getItemInMainHand().addEnchantment(searchedEntschy, EnchantmentLevel);
					player.setLevel(player.getLevel() + 1);
					Bukkit.broadcastMessage("[§DDowngrade§f]§c " + player.getName() + "§a hat sich gerade ein Item mit §c" + args[0] + "§a gedowngradet!");
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		else {
			player.sendMessage("[§DDowngrade§f]§c Bitte verwende: §e/downgrade <Enchantment>§c oder sieh dir die Optionen mit §e/downgrade options§c!");
		}

		return false;
	}


}
