package de.FinnKrause.me;

import org.bukkit.enchantments.Enchantment;

public class Enchantments {
	public String EnchantmentsList = "\n§e > protection\n > fireprotection\n > mending\n > featherfalling\n > blastprotection\n > blastprotection\n > projectileprotection\n > respiration\n > frostwalker\n > aquaaffinity\n > thorns\n > sweepingedge\n > unbreaking\n > sharpness\n > smite\n > baneofarthropods\n > knockback\n > fireaspect\n > looting\n > looting\n > efficiency\n > silktouch\n > fortune\n > power\n > punch\n > flame\n > infinity\n > soulspeed\n > multishot\n > debug\n > quickcharge";
	
	public Enchantment getEnchantment(String name) {
	    switch (name.trim().toLowerCase()) {
	        case "protection":
	            return Enchantment.PROTECTION_ENVIRONMENTAL;
	        case "channeling":
	        	return Enchantment.CHANNELING;
	        case "loyalty":
	        	return Enchantment.LOYALTY;
	        case "fireprotection":
	            return Enchantment.PROTECTION_FIRE;
	        case "mending":
	        	return Enchantment.MENDING;
	        case "featherfalling":
	            return Enchantment.PROTECTION_FALL;
	        case "blastprotection":
	            return Enchantment.PROTECTION_EXPLOSIONS;
	        case "projectileprotection":
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "respiration":
	            return Enchantment.OXYGEN;
	        case "frostwalker":
	        	return Enchantment.FROST_WALKER;
	        case "aquaaffinity":
	            return Enchantment.WATER_WORKER;
	        case "thorns":
	            return Enchantment.THORNS;
	        case "piercing":
	        	return Enchantment.PIERCING;
	        case "sweepingedge":
	        	return Enchantment.SWEEPING_EDGE;
	        case "unbreaking":
	            return Enchantment.DURABILITY;
	        case "sharpness":
	            return Enchantment.DAMAGE_ALL;
	        case "smite":
	            return Enchantment.DAMAGE_UNDEAD;
	        case "baneofarthropods":
	            return Enchantment.DAMAGE_ARTHROPODS;
	        case "knockback":
	            return Enchantment.KNOCKBACK;
	        case "fireaspect":
	            return Enchantment.FIRE_ASPECT;
	        case "looting":
	            return Enchantment.LOOT_BONUS_MOBS;
	        case "efficiency":
	            return Enchantment.DIG_SPEED;
	        case "silktouch":
	            return Enchantment.SILK_TOUCH;
	        case "fortune":
	            return Enchantment.LOOT_BONUS_BLOCKS;
	        case "power":
	            return Enchantment.ARROW_DAMAGE;
	        case "punch":
	            return Enchantment.ARROW_KNOCKBACK;
	        case "flame":
	            return Enchantment.ARROW_FIRE;
	        case "infinity":
	            return Enchantment.ARROW_INFINITE;
	        case "soulspeed":
	        	return Enchantment.SOUL_SPEED;
	        case "multishot":
	        	return Enchantment.MULTISHOT;
	        case "quickcharge":
	        	return Enchantment.QUICK_CHARGE;
	        default:
	            return null;
	    }
	}
}
