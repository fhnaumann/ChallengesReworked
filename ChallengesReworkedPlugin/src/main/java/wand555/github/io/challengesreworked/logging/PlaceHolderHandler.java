package wand555.github.io.challengesreworked.logging;


import wand555.github.io.challengesreworked.Mapper;

import java.util.Map;
import java.util.Set;

public class PlaceHolderHandler {
    public static final String PLAYER_PLACEHOLDER = "player";
    public static final String AMOUNT_PLACEHOLDER = "amount";
    public static final String MATERIAL_PLACEHOLDER = "material";
    public static final String TIME_PLACEHOLDER = "time";
    public static final String CURRENT_AMOUNT_PLACEHOLDER = "current_amount";
    public static final String AMOUNT_NEEDED_PLACEHOLDER = "amount_needed";
    public static final String MOB_PLACEHOLDER = "mob";

    public static final String ITEMSTACK_PLACEHOLDER = "itemstack";

    private static final Set<String> PLACEHOLDERS = Set.of(
            PLAYER_PLACEHOLDER,
            AMOUNT_PLACEHOLDER,
            MATERIAL_PLACEHOLDER,
            TIME_PLACEHOLDER,
            CURRENT_AMOUNT_PLACEHOLDER,
            AMOUNT_NEEDED_PLACEHOLDER,
            MOB_PLACEHOLDER,
            ITEMSTACK_PLACEHOLDER
    );

    public static String replacePlaceHolders(String message, Map<String, Object> data) {
        return PLACEHOLDERS.stream().reduce(message, (tempMessage, placeholder) -> {
            if(data.containsKey(placeholder)) {
                Object obj = data.get(placeholder);
                return tempMessage.replace("%"+placeholder+"%", Mapper.mapToString(obj));
            }
            return tempMessage;
        });
    }
}
