package wand555.github.io.challengesreworkedgui.controllers.challenges.nocrafting;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallenge;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.controlsfx.control.ListSelectionView;
import wand555.github.io.challengesreworkedgui.controllers.challenges.PunishableChallengeController;
import wand555.github.io.challengesreworkedgui.rows.InventoryTypeRow;
import wand555.github.io.challengesreworkedgui.rows.MaterialRow;
import wand555.github.io.challengesreworkedgui.util.CopyUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoCraftingChallengeController extends PunishableChallengeController implements NoCraftingChallenge {
    @FXML
    private ListView<MaterialRow> craftableItemsList;
    @FXML
    private Button addCraftableItemButton;
    @FXML
    private Button addForbiddenUIButton;
    @FXML
    private ListView<InventoryTypeRow> forbiddenUIList;

    @FXML
    @Override
    protected void initialize() {
        common = new NoCraftingChallengeCommon();
        super.initialize();
        craftableItemsList.setItems(FXCollections.observableArrayList(getCommon().getAllowedToCraft().stream().map(MaterialRow::new).toList()));
        addCraftableItemButton.setOnAction(event -> {
            initCraftableItemsUI();
        });
        forbiddenUIList.setItems(FXCollections.observableArrayList(getCommon().getForbiddenToUse().stream().map(InventoryTypeRow::new).toList()));
        addForbiddenUIButton.setOnAction(event -> {
            initForbiddenUIs();
        });


    }

    private void initCraftableItemsUI() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        ListSelectionView<MaterialRow> listSelectionView = new ListSelectionView<>();
        List<Material> obtainableItems = Stream.of(Material.values())
                .filter(Material::isItem)
                .filter(Predicate.not(Material::isAir))
                .filter(Predicate.not(Material::isLegacy))
                .filter(material -> craftableItemsList.getItems().stream().noneMatch(materialRow -> materialRow.getMaterial() == material))
                .sorted(Comparator.comparing(Enum::toString))
                .toList();
        List<MaterialRow> rows = obtainableItems.stream().map(MaterialRow::new).toList();
        listSelectionView.setSourceItems(FXCollections.observableArrayList(rows));
        listSelectionView.setTargetItems(CopyUtil.deepCopy(craftableItemsList.getItems()));
        root.getChildren().add(listSelectionView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner();
        stage.show();
        stage.setOnCloseRequest(event1 -> {
            craftableItemsList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems()));
            getCommon().setAllowedToCraft(craftableItemsList.getItems().stream().map(MaterialRow::getMaterial).collect(Collectors.toSet()));
        });
        /*
        listSelectionView.setCellFactory(param -> {
            ListCell<MaterialRow> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu(new MenuItem("TEST"));

            cell.emptyProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue) {
                    cell.setContextMenu(null);
                }
                else {
                    cell.setContextMenu(contextMenu);
                }
            });
            cell.graphicProperty().bind(cell.itemProperty());
            return cell;
        });

         */
    }

    private void initForbiddenUIs() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        ListSelectionView<InventoryTypeRow> listSelectionView = new ListSelectionView<>();
        List<InventoryType> inventoryTypes = Stream.of(InventoryType.values())
                .filter(inventoryType -> forbiddenUIList.getItems().stream().noneMatch(inventoryTypeRow -> inventoryTypeRow.getInventoryType() == inventoryType))
                .sorted(Comparator.comparing(Enum::toString))
                .toList();
        List<InventoryTypeRow> rows = inventoryTypes.stream().map(InventoryTypeRow::new).toList();
        ObservableList<InventoryTypeRow> inventoryTypeRowObservableList = FXCollections.observableArrayList(rows);
        SortedList<InventoryTypeRow> sortedRows = new SortedList<>(
                inventoryTypeRowObservableList,
                Comparator.comparing(inventoryTypeRow -> inventoryTypeRow.getInventoryType().toString())
        );
        listSelectionView.setSourceItems(inventoryTypeRowObservableList);
        listSelectionView.setTargetItems(CopyUtil.deepCopy(forbiddenUIList.getItems()));
        root.getChildren().add(listSelectionView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner();
        stage.show();
        stage.setOnCloseRequest(event1 -> {
            forbiddenUIList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems()));
            getCommon().setForbiddenToUse(forbiddenUIList.getItems().stream().map(InventoryTypeRow::getInventoryType).collect(Collectors.toSet()));
        });
    }

    @Override
    public void setDataFromCommon(Common from) {
        super.setDataFromCommon(from);
        List<MaterialRow> materialRows = getCommon().getAllowedToCraft().stream().map(MaterialRow::new).toList();
        craftableItemsList.setItems(FXCollections.observableArrayList(materialRows));
        List<InventoryTypeRow> inventoryTypeRows = getCommon().getForbiddenToUse().stream().map(InventoryTypeRow::new).toList();
        forbiddenUIList.setItems(FXCollections.observableArrayList(inventoryTypeRows));
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoCraftingChallengeCommon getCommon() {
        return (NoCraftingChallengeCommon) super.getCommon();
    }
}
