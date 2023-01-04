package wand555.github.io.challengesreworkedgui.controllers.goals;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.bukkit.entity.EntityType;
import org.controlsfx.control.ListSelectionView;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.Mapper;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import wand555.github.io.challengesreworked.goals.mob.MobGoal;
import wand555.github.io.challengesreworked.goals.mob.MobGoalCommon;
import wand555.github.io.challengesreworkedgui.rows.MobRow;
import wand555.github.io.challengesreworkedgui.rows.Row;
import wand555.github.io.challengesreworkedgui.util.CopyUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MobGoalController extends GoalController implements MobGoal {

    @FXML
    private Button addMobsToKill;
    @FXML
    private ListView<MobRow> mobsToKillList;

    @FXML
    protected void initialize() {
        common = new MobGoalCommon();
        super.initialize();

        mobsToKillList.setItems(FXCollections.observableArrayList(new MobRow(
                EntityType.ENDER_DRAGON, 1)));
        addMobsToKill.setOnAction(event -> {
            initMobsToKillUI();
        });
        //enable by default
        Platform.runLater(() -> activateButton.setSelected(true));
    }



    private void initMobsToKillUI() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        ListSelectionView<MobRow> listSelectionView = new ListSelectionView<>();
        List<EntityType> killableMobs = Stream.of(EntityType.values())
                .filter(EntityType::isAlive)
                .sorted(Comparator.comparing(Enum::toString))
                .toList();
        List<MobRow> rows = killableMobs.stream()
                .map(entityType -> new MobRow(entityType, 1))
                .toList();
        listSelectionView.setSourceItems(FXCollections.observableArrayList(rows));
        listSelectionView.setTargetItems(CopyUtil.deepCopy(mobsToKillList.getItems()));
        root.getChildren().add(listSelectionView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> {
            mobsToKillList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems()));
            getCommon().setToKill(listSelectionView.getTargetItems().stream()
                    .collect(Collectors.toMap(
                            mobRow -> mobRow.getEntityType(),
                            mobRow -> mobRow.getCollect()
                    )));
        });

    }

    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        super.setDataFromCommon(from, thisActive);
        List<MobRow> rows = getCommon().getToKill().entrySet().stream()
                .map(entityTypeCollectEntry -> new MobRow(entityTypeCollectEntry.getKey(), entityTypeCollectEntry.getValue()))
                .toList();
        mobsToKillList.setItems(FXCollections.observableArrayList(rows));
    }

    @Override
    public MobGoalCommon getCommon() {
        return (MobGoalCommon) common;
    }

    private static class MobRow extends Row {

        private final EntityType entityType;
        private final Collect collect;

        public MobRow(EntityType entityType, int amountNeeded) {
            this(entityType, new Collect(amountNeeded));
        }

        public MobRow(EntityType entityType, Collect collect) {
            super(5);
            this.entityType = entityType;
            this.collect = collect;

            TextField textField = new TextField(String.valueOf(collect.getAmountNeeded()));
            textField.setAlignment(Pos.CENTER);
            textField.setPrefWidth(40);
            textField.setTextFormatter(new TextFormatter<>(
                    new IntegerStringConverter(),
                    collect.getAmountNeeded(),
                    change -> {
                        String newText = change.getControlNewText();
                        if(newText.isEmpty()) {
                            collect.setAmountNeeded(0);
                            return change;
                        }
                        if(newText.matches("^[0-9]+$")) {
                            int writtenAmount = Integer.parseInt(newText);
                            if(writtenAmount <= 99999) {
                                collect.setAmountNeeded(writtenAmount);
                                return change;
                            }
                        }
                        return null;
                    }));
            getChildren().addAll(new StackPane(new Label(Mapper.mapToString(entityType))), textField);
        }

        public Collect getCollect() {
            return collect;
        }

        public EntityType getEntityType() {
            return entityType;
        }

        @Override
        public Row copy() {
            return new MobRow(getEntityType(), getCollect());
        }
    }
}
