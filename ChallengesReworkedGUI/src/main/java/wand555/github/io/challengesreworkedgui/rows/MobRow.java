package wand555.github.io.challengesreworkedgui.rows;

import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.Mapper;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.bukkit.entity.EntityType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MobRow extends AmountableRow {

    private final EntityType entityType;
    private final Collect collect;

    private final BiConsumer<EntityType, Integer> onAmountChange;

    public MobRow(EntityType entityType, BiConsumer<EntityType, Integer> onAmountChange) {
        this(entityType, new Collect(1), onAmountChange);
    }
    public MobRow(EntityType entityType, Collect collect, BiConsumer<EntityType, Integer> onAmountChange) {
        super(collect.getCurrentAmount());
        this.entityType = entityType;
        this.collect = collect;
        this.onAmountChange = onAmountChange;
        getChildren().add(0, new StackPane(new Label(Mapper.mapToString(entityType))));
    }

    @Override
    public void setAmount(int amount) {
        super.setAmount(amount);
        onAmountChange.accept(getEntityType(), getAmount());
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Collect getCollect() {
        return collect;
    }

    @Override
    public Row copy() {
        return new MobRow(getEntityType(), getCollect(), onAmountChange);
    }
}
