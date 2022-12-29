package wand555.github.io.challengesreworkedgui.rows;

import javafx.scene.control.Label;
import org.bukkit.event.inventory.InventoryType;
import wand555.github.io.challengesreworked.Mapper;

public class InventoryTypeRow extends Row {

    private final InventoryType inventoryType;

    public InventoryTypeRow(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
        String userFriendly = Mapper.mapToString(inventoryType);
        getChildren().add(new Label(userFriendly));
    }

    @Override
    public Row copy() {
        return new InventoryTypeRow(inventoryType);
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }
}
