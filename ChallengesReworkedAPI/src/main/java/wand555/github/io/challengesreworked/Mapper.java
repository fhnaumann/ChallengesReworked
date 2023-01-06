package wand555.github.io.challengesreworked;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.text.WordUtils;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

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

    public static List<String> fromEnumSetToFileList(Set<? extends Enum<?>> set) {
        return set.stream().map(Enum::toString).toList();
    }

    public static <T extends Enum<T>> Set<T> fromFileListToEnumSet(List<?> listFromFile, Class<T> listType) {
        return listFromFile.stream()
                .map(o -> str2Enum(o, listType))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static <T extends Enum<T>> T str2Enum(Object obj, Class<T> type) {
        try {
            return type.cast(type.getMethod("valueOf", String.class).invoke(obj, obj.toString()));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> fromEnumKeyMapToFileMap(Map<? extends Enum<?>, ?> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey().toString(),
                Map.Entry::getValue
        ));
    }

    public static <T extends Enum<T>,S> Map<T, S> fromFileMapToEnumKeyMap(Map<?, ?> mapFromFile, Class<T> keyType, Class<S> valueType) {
        return mapFromFile.entrySet().stream().collect(Collectors.toMap(
                entry -> str2Enum(entry.getKey(), keyType),
                entry -> valueType.cast(entry.getValue())
        ));
    }

    public static Map<String, Object> fromEnumKeyEnumValueMapToFileMap(Map<? extends Enum<?>, ? extends Enum<?>> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey().toString(),
                entry -> entry.getValue().toString()
        ));
    }

    public static <T extends Enum<T>, S extends Enum<S>> Map<T, S> fromFileMapToEnumKeyEnumValueMap(Map<?, ?> mapFromFile, Class<T> keyType, Class<S> valueType) {
        return mapFromFile.entrySet().stream().collect(Collectors.toMap(
                entry -> str2Enum(entry.getKey(), keyType),
                entry -> str2Enum(entry.getValue(), valueType)
        ));
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
