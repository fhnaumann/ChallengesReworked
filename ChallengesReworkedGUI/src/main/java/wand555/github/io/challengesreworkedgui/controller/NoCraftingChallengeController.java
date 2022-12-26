package wand555.github.io.challengesreworkedgui.controller;

import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallenge;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.controlsfx.control.ListSelectionView;
import wand555.github.io.challengesreworkedgui.DeserializationNotImplementedException;
import wand555.github.io.challengesreworkedgui.MaterialRow;
import wand555.github.io.challengesreworkedgui.util.CopyUtil;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class NoCraftingChallengeController extends PunishableChallengeController implements NoCraftingChallenge {
    @FXML
    private ToggleButton activateButton;
    @FXML
    private ListView<MaterialRow> craftableItemsList;
    @FXML
    private TitledPane noCraftingTitledPane;
    @FXML
    private Button addCraftableItemButton;
    @FXML
    private Button addForbiddenUIButton;
    @FXML
    private ListView<InventoryType> forbiddenUIList;

    @FXML
    @Override
    protected void initialize() {
        common = new NoCraftingChallengeCommon();
        super.initialize();
        craftableItemsList.setItems(FXCollections.observableArrayList(getCommon().getAllowedToCraft().stream().map(MaterialRow::new).toList()));
        addCraftableItemButton.setOnAction(event -> {
            initCraftableItemsUI();
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
        stage.setOnCloseRequest(event1 -> craftableItemsList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems())));
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
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoCraftingChallengeCommon getCommon() {
        return (NoCraftingChallengeCommon) super.getCommon();
    }

    @Override
    public void register() {

    }
}
