package wand555.github.io.challengesreworked.punishments.inventoryclear;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.Map;

public class InventoryClearPunishmentCommon extends PunishmentCommon {

    private boolean clearEnderChest;

    public InventoryClearPunishmentCommon() {
        this(0, AffectType.ALL, false);
    }

    public InventoryClearPunishmentCommon(int amountTriggered, AffectType affectType, boolean clearEnderChest) {
        super(amountTriggered, affectType);
        this.clearEnderChest = clearEnderChest;
    }

    public boolean isClearEnderChest() {
        return clearEnderChest;
    }

    public void setClearEnderChest(boolean clearEnderChest) {
        this.clearEnderChest = clearEnderChest;
    }

    @Override
    public PunishmentCommon copy() {
        return new InventoryClearPunishmentCommon(getAmountTriggered(), getAffectType(), isClearEnderChest());
    }

    public static final TypeAdapter<InventoryClearPunishmentCommon> adapter = new TypeAdapter<InventoryClearPunishmentCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull InventoryClearPunishmentCommon inventoryClearPunishmentCommon) {
            return Map.of(
                    "amountTriggered", inventoryClearPunishmentCommon.getAmountTriggered(),
                    "affectType", inventoryClearPunishmentCommon.getAffectType().toString(),
                    "clearEnderChest", inventoryClearPunishmentCommon.isClearEnderChest()
            );
        }

        @NotNull
        @Override
        public InventoryClearPunishmentCommon deserialize(@NotNull Map<Object, Object> map) {
            return new InventoryClearPunishmentCommon(
                    (int) map.get("amountTriggered"),
                    AffectType.valueOf(map.get("affectType").toString()),
                    (boolean) map.get("clearEnderChest")
            );
        }
    };
}
