package levelspeicher;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class XPVillager implements Listener, CommandExecutor {

	private File villager = new File("plugins//FinnsPluginII//Villager.yml");
	private YamlConfiguration yamlvillager = YamlConfiguration.loadConfiguration(villager);
	
	private File KannVillagerHaben = new File("plugins//FinnsPluginII//KannVillagerHaben.yml");
	private YamlConfiguration yamlKannVillagerHaben = YamlConfiguration.loadConfiguration(KannVillagerHaben);
	
	static final String Anfang = "§f[§bXP§f]";
	
	Inventory inv;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		loadConfig();
		if (sender instanceof Player) {
			Player player = (Player) sender;	
			loadConfig();
			if (yamlKannVillagerHaben.getBoolean(player.getName())) {
				Villager xpspeicher = (Villager) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
				xpspeicher.setCustomName((args.length > 0 && !args[0].equals("")) ? (player.getName() + "'s " + args[0]) : (player.getName() + ": Hol dir nen Custom namen mit /xpvillager <Name>"));
				xpspeicher.setCustomNameVisible(true);
				xpspeicher.setCanPickupItems(false);
				xpspeicher.setVillagerType(Villager.Type.SWAMP);
				xpspeicher.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 500));
				
				yamlvillager.set(player.getName(), yamlvillager.getInt(player.getName()));
				try{yamlvillager.save(this.villager);}catch(Exception e) {} 
				
				yamlKannVillagerHaben.set(player.getName(), false);
				try{yamlKannVillagerHaben.save(this.KannVillagerHaben);}catch(Exception e) {}
			}
			else {
				player.sendMessage(Anfang + " §cDu hast schon einen, oder du darfst noch keinen XP-Villager haben!");
				return true;
			}
				
		}
		return false;
	}
	
	@EventHandler
	public void HandleXPKlick(PlayerInteractEntityEvent event) {
		if(event == null) return;
		loadConfig();
		if(event.getRightClicked() instanceof Villager) {
			Player player = (Player) event.getPlayer();
			Villager villager = (Villager) event.getRightClicked();
			if (villager.getCustomName().contains(player.getName()) && villager.getPotionEffect(PotionEffectType.SLOW).getAmplifier() == 500) {
				inv = Bukkit.createInventory(player, 9, event.getRightClicked().getCustomName());
				for (int i = 0; i < 4; i++) {
					
					ItemStack stack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
					ItemMeta metaPane = stack.getItemMeta();
					
					switch (i) {
						case 0: metaPane.setDisplayName("§aEinzahlen 5"); break;
						case 1: metaPane.setDisplayName("§aEinzahlen 20");break;
						case 2: metaPane.setDisplayName("§aEinzahlen 100");break;
						case 3: metaPane.setDisplayName("§aEinzahlen 50%");break;
					}
					
					stack.setItemMeta(metaPane);
					inv.setItem(i, stack);
				}
				
				ItemStack Buch = new ItemStack(Material.BOOK, 1);
				ItemMeta meta = Buch.getItemMeta();
				meta.setDisplayName(String.valueOf(yamlvillager.getInt(player.getName())));
				Buch.setItemMeta(meta);
				inv.setItem(4, Buch);
				
				for (int i = 5; i < 9; i++) {
					ItemStack stack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
					ItemMeta metaPane = stack.getItemMeta();
					switch (i) {
						case 8: metaPane.setDisplayName("§cAuszahlen 5");break;
						case 7: metaPane.setDisplayName("§cAuszahlen 20");break;
						case 6: metaPane.setDisplayName("§cAuszahlen 100");break;
						case 5: metaPane.setDisplayName("§cAuszahlen 50%");break;
					}
					stack.setItemMeta(metaPane);
					inv.setItem(i, stack);
				}
				
				player.openInventory(inv);
				
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void HandleInventory(InventoryClickEvent event) {
		loadConfig();
		if (event.getView().getTitle().contains(event.getWhoClicked().getName())) {
			event.setCancelled(true);
			if (event.getCurrentItem().getType() != null && event.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
				Player player = (Player) event.getWhoClicked();
		
				if (player.getLevel() < 1) {
					event.getWhoClicked().sendMessage(Anfang + "§c Du hast nicht genug xp!");
					return;
				}
				
				if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§a", "").equals("Einzahlen 5")) xpeinzahlen(player, 5);
				else if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§a", "").equals("Einzahlen 20")) xpeinzahlen(player, 20);
				else if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§a", "").equals("Einzahlen 100")) xpeinzahlen(player, 100);
				else if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§a", "").equals("Einzahlen 50%")) xpeinzahlen(player, 50);
				
				updateBuchCounter(event, player);
			}
			
			else if (event.getCurrentItem().getType() != null && event.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
				Player player = (Player) event.getWhoClicked();
				
				if (yamlvillager.getInt(event.getWhoClicked().getName()) < 1) {
					event.getWhoClicked().sendMessage(Anfang + "§c Auf deinem Konto ist leider nicht genug xp!");
					return;
				}

				if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§c", "").equals("Auszahlen 5")) xpauszahlen(player, 5);
				else if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§c", "").equals("Auszahlen 20")) xpauszahlen(player, 20);
				else if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§c", "").equals("Auszahlen 100")) xpauszahlen(player, 100);
				else if (event.getCurrentItem().getItemMeta().getDisplayName().toString().replace("§c", "").equals("Auszahlen 50%")) xpauszahlen(player, 50);
				
				updateBuchCounter(event, player);
			}
		
		}
	}
	
	private void updateBuchCounter(InventoryClickEvent event, Player player) {
		ItemStack Buch = new ItemStack(Material.BOOK, 1);
		ItemMeta meta = event.getInventory().getItem(4).getItemMeta();
		meta.setDisplayName(yamlvillager.getInt(player.getName()) + "");
		Buch.setItemMeta(meta);
		event.getClickedInventory().setItem(4, Buch);
	}
	
	@EventHandler
	public void DeathOfVillager(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Villager)) return;
		Villager villager = (Villager) event.getEntity();
		if (!villager.isCustomNameVisible()) return;
		if (villager.getCustomName().contains(event.getDamager().getName())) {
			villager.setHealth(0);
			
			yamlKannVillagerHaben.set(event.getDamager().getName(), true);
			try{yamlKannVillagerHaben.save(this.KannVillagerHaben);}catch(Exception e) {}
			return;
		}
		event.setCancelled(true);
	}
	
	private int getNumber(Player player, String mode) {
		int returnvalue;
		if (mode.equals("ein")) {
			double rechnung = player.getLevel() / 2;
			
			if ((player.getLevel() / 2) % 2 == 0.5) {
				returnvalue = (int)(rechnung + 0.5);
				return returnvalue;
			}
			else if (player.getLevel() == 1) {
				return 1;
			}
			else {
				returnvalue = (int) rechnung;
				return returnvalue;
			}
		}
		else {
			double rechnung = yamlvillager.getInt(player.getName()) / 2;
			
			if ((yamlvillager.getInt(player.getName()) / 2) % 2 == 0.5) {
				returnvalue = (int)(rechnung + 0.5);
				return returnvalue;
			}
			else if (yamlvillager.getInt(player.getName()) == 1) {
				return 1;
			}
			else {
				returnvalue = (int) rechnung;
				return returnvalue;
			}
		}
	}
	
	private void xpeinzahlen(Player player, int amount) {
		int calc;
		if (amount == 50) calc = getNumber(player, "ein");
		else calc = amount;
		
		if(player.getLevel() <= amount) calc = player.getLevel();
		
		yamlvillager.set(player.getName(), yamlvillager.getInt(player.getName()) + calc);
		player.setLevel(player.getLevel() - calc);
		
		try{yamlvillager.save(this.villager);}catch(Exception e) {}
	}
	
	private void xpauszahlen(Player player, int amount) {
		int calc;
		if (amount == 50) calc = getNumber(player, "ab");
		else calc =  amount;
		
		if(yamlvillager.getInt(player.getName()) <= amount) calc = yamlvillager.getInt(player.getName());
		
		yamlvillager.set(player.getName(), yamlvillager.getInt(player.getName()) - calc);
		player.setLevel(player.getLevel() + calc);
		try{yamlvillager.save(this.villager);}catch(Exception e) {}
	}
		

	
	private void loadConfig() {
		yamlvillager = YamlConfiguration.loadConfiguration(villager);
		yamlKannVillagerHaben = YamlConfiguration.loadConfiguration(KannVillagerHaben);
	}
	 
}
