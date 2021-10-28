package Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import de.FinnKrause.me.Steuern;

public class MendingUndInfinity implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                int LevelNeeded = 100;
                int LevelNeededForStick = 100;
                Player player = (Player) sender;
                
                if (args[0].contains("bow")) {
                	if (player.getLevel() >= LevelNeeded) {
                        if (player.getInventory().getItemInMainHand().getType() == Material.BOW) {
                            if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.MENDING) && !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.ARROW_INFINITE)) {
                                player.getInventory().getItemInMainHand().addEnchantment(Enchantment.ARROW_INFINITE, 1);
                                player.setLevel(player.getLevel() - 100);
                                Bukkit.broadcastMessage("[§bUnusual-Enchantements§f]§c " + player.getName() + "§a hat sich soeben seinen Bogen mit §cInfinity §aaufgebessert!");
                            } 
                            
                            else if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.ARROW_INFINITE) && !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.MENDING)) {
                                player.getInventory().getItemInMainHand().addEnchantment(Enchantment.MENDING, 1);
                                player.setLevel(player.getLevel() - LevelNeeded);
                                Bukkit.broadcastMessage("[§bUnusual-Enchantements§f]§c " + player.getName() + "§a hat sich soeben seinen Bogen mit §cMending §aaufgebessert!");
                            } 
                            
                            else if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.MENDING) && player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.ARROW_INFINITE))
                                player.sendMessage("[§bUnusual-Enchantements§f]§c Dein Bogen hat bereits beide Enchantements!");
                            
                            else 
                                player.sendMessage("[§bUnusual-Enchantements§f]§a Dein Bogen hat keine der beiden Enchantements §cMENDING§f,§c INFINITY§f!");
                            
                        } 
                        
                        else if (player.getInventory().getItemInMainHand().getType() == Material.STICK)
                            player.sendMessage("[§bUnusual-Enchantements§f] §cBitte nimm deinen Bogen oder einen Stick in die rechte Hand oder nutze §a/upgrade§c!");
                        
                        else 
                            player.sendMessage("[§bUnusual-Enchantements§f] §cBitte nimm deinen Bogen oder einen Stick in die rechte Hand!");
                        
                	}
                	
                	else 
                        player.sendMessage("[§bUnusual-Enchantements§f]§c Dir fehlen §a" + (LevelNeeded - player.getLevel()) + "§c Level um das Upgrade zu machen! ");

                } 
                
                else if (args[0].contains("stick")) {
                    
                	if (player.getLevel() >= LevelNeededForStick) {
                    	if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {
                            if (player.getInventory().getItemInOffHand().getType() == Material.DIAMOND && player.getInventory().getItemInOffHand().getAmount() >= 10) {
                            	if (player.getInventory().getItemInMainHand().getAmount() == 1) {
                                	if (!player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.KNOCKBACK)) {                            		
                                		player.getInventory().getItemInMainHand().addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
                                        player.setLevel(player.getLevel() - LevelNeededForStick);
                                        player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 10);
                                        Bukkit.broadcastMessage("[§bUnusual-Enchantements§f]§c " + player.getName() + "§a hat sich soeben seinen Stick mit §cKnockback 10 §aaufgebessert!");
                                        Steuern steuern = new Steuern();
                                        steuern.addSteuern(Material.DIAMOND, 10);
                                    } 
                                    
                                    else 
                                        Bukkit.broadcastMessage("[§bUnusual-Enchantements§f]§c Dein Stick hat bereits Knockback 10!");
                                    
                            	}
                            	else 
                            		player.sendMessage("[§bUnusual-Enchantements§f]§c Du hast zu viele Sticks in deiner Main-Hand!");

                            }
                            
                            else 
                            	player.sendMessage("[§bUnusual-Enchantements§f]§c Bitte nimm zusätzlich 10 Diamanten in die Off-Hand!");
                            

                        } 
                        
                        else if (player.getInventory().getItemInMainHand().getType() == Material.BOW)
                            player.sendMessage("[§bUnusual-Enchantements§f]§c Bitte nimm einen Stick in die Rechte Hand oder nutze §a/upgrade §dBow§c!");
                        
                    	
                        else 
                            player.sendMessage("[§bUnusual-Enchantements§f]§c Bitte nimm einen Stick in die Rechte Hand!");
                        
                	}
                	
                	else 
                        player.sendMessage("[§bUnusual-Enchantements§f]§c Dir fehlen §a" + (LevelNeededForStick - player.getLevel()) + "§c Level um das Upgrade zu machen! ");
                	

                } 
                
                else if (args[0].contains("preise")) {
                    player.sendMessage("[§bUnusual-Enchantements§f]§c Der Knockback10 Stick kostet 20 §bDiamenten§c und 100 §aLevel§c!\n Der unendliche Bogen kostet 100§a Level§c!");
                }

            } 
            

            else 
                sender.sendMessage("[§bUnusual-Enchantements§f]§c Bitte benute §a\"/upgrade-unnormal §dbow§a\" §coder §a\"/upgrade-unnormal §dstick§a\"§c!");
            
            
        } 
        
        else 
            sender.sendMessage("Du bist die Konsole");
        


        return false;
    }

}