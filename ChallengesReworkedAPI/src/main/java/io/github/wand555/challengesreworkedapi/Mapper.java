package io.github.wand555.challengesreworkedapi;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.text.WordUtils;

public class Mapper {

    public static String mapToString(Object obj) {
        //best effort match
        if(obj instanceof Player player) {
            return player.getDisplayName();
        }
        if(obj instanceof Material || obj instanceof EntityType) {
            return WordUtils.capitalizeFully(obj.toString().replace("_", " ").toLowerCase());
        }
        if(obj instanceof ItemStack itemStack) {
            return "%ox%s".formatted(itemStack.getAmount(), mapToString(itemStack.getType()));
        }
        return obj.toString();
    }
}
