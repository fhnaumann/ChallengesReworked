package wand555.github.io.challengesreworkedgui.controllers.goals;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.controlsfx.control.ListSelectionView;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.Mapper;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import wand555.github.io.challengesreworked.goals.itemcollect.ItemCollectGoal;
import wand555.github.io.challengesreworked.goals.itemcollect.ItemCollectGoalCommon;
import wand555.github.io.challengesreworkedgui.rows.MaterialRow;
import wand555.github.io.challengesreworkedgui.rows.MobRow;
import wand555.github.io.challengesreworkedgui.rows.Row;
import wand555.github.io.challengesreworkedgui.util.CopyUtil;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCollectGoalController extends GoalController implements ItemCollectGoal {


    @FXML
    private Button addItemsToCollect;
    @FXML
    private ListView<ItemCollectRow> itemsToCollectList;

    @FXML
    @Override
    protected void initialize() {
        common = new ItemCollectGoalCommon();
        super.initialize();

        addItemsToCollect.setOnAction(event -> initItemsToCollectUI());

    }

    @Override
    public ItemCollectGoalCommon getCommon() {
        return (ItemCollectGoalCommon) super.getCommon();
    }

    private void initItemsToCollectUI() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        ListSelectionView<ItemCollectRow> listSelectionView = new ListSelectionView<>();
        List<Material> obtainableItems = Stream.of(Material.values())
                .filter(Material::isItem)
                .filter(Predicate.not(Material::isAir))
                .filter(Predicate.not(Material::isLegacy))
                .filter(material -> itemsToCollectList.getItems().stream().noneMatch(materialRow -> materialRow.getMaterial() == material))
                .sorted(Comparator.comparing(Enum::toString))
                .toList();
        List<ItemCollectRow> rows = obtainableItems.stream()
                .map(ItemCollectRow::new)
                .toList();
        listSelectionView.setSourceItems(FXCollections.observableArrayList(rows));
        listSelectionView.setTargetItems(CopyUtil.deepCopy(itemsToCollectList.getItems()));
        root.getChildren().add(listSelectionView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> {
            itemsToCollectList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems()));
            getCommon().setToCollect(listSelectionView.getTargetItems().stream()
                    .collect(Collectors.toMap(
                            materialRow -> materialRow.getMaterial(),
                            materialRow -> materialRow.getCollect()
                    )));
        });
    }

    @Override
    public void setDataFromCommon(Common from, boolean thisActive) {
        super.setDataFromCommon(from, thisActive);
        List<ItemCollectRow> rows = getCommon().getToCollect().entrySet().stream()
                .map(materialCollectEntry -> new ItemCollectRow(materialCollectEntry.getKey(), materialCollectEntry.getValue()))
                .toList();
        itemsToCollectList.setItems(FXCollections.observableArrayList(rows));
    }

    private static class ItemCollectRow extends MaterialRow {

        private final Collect collect;

        public ItemCollectRow(Material material) {
            this(material, 1);
        }

        public ItemCollectRow(Material material, int amountNeeded) {
            this(material, new Collect(amountNeeded, 0));
        }

        public ItemCollectRow(Material material, Collect collect) {
            super(material);
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
            getChildren().add(new StackPane(textField));
        }

        public Collect getCollect() {
            return collect;
        }

        @Override
        public Row copy() {
            return new ItemCollectRow(getMaterial(), getCollect());
        }
    }
}
