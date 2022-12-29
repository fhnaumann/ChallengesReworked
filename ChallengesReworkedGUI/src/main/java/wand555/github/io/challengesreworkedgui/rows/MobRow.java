package wand555.github.io.challengesreworkedgui.rows;

import wand555.github.io.challengesreworked.Mapper;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.bukkit.entity.EntityType;

public class MobRow extends AmountableRow {

    private final EntityType entityType;


    public MobRow(EntityType entityType) {
        this(entityType, 1);
    }
    public MobRow(EntityType entityType, int amount) {
        super(amount);
        this.entityType = entityType;
        getChildren().add(0, new StackPane(new Label(Mapper.mapToString(entityType))));
    }

    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public Row copy() {
        return new MobRow(getEntityType(), getAmount());
    }
}
