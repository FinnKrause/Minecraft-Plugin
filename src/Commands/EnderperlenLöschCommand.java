package Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderperlenL�schCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.getInventory().remove(Material.ENDER_PEARL);
			player.sendMessage("[�bEnderperlen�f] �aAlle deine Enderperlen wurden gel�scht!");
		}
		return false;
	}
	
}
