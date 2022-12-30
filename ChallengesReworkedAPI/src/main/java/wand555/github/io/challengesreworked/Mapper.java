package wand555.github.io.challengesreworked;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.text.WordUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.TreeMap;

public class Mapper {

    public static String mapToString(Object obj) {
        //best effort match
        if(obj instanceof Player player) {
            return player.getDisplayName();
        }
        //if(obj instanceof Material || obj instanceof EntityType) {
        if(obj instanceof Enum<?>) {
            return WordUtils.capitalizeFully(obj.toString().replace("_", " ").toLowerCase());
        }
        if(obj instanceof ItemStack itemStack) {
            return "%ox%s".formatted(itemStack.getAmount(), mapToString(itemStack.getType()));
        }
        if(obj instanceof PotionEffect potionEffect) {
            return "%s %s".formatted(mapToString(potionEffect.getType()), toRoman(potionEffect.getAmplifier())+1); //+1 because it starts at 0 internally
        }
        return obj.toString();
    }

    public static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

}
