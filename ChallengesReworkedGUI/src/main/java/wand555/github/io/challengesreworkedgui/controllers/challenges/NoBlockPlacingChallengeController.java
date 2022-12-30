package wand555.github.io.challengesreworkedgui.controllers.challenges;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.controlsfx.control.ListSelectionView;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.challenges.noblockplacing.NoBlockPlacingChallenge;
import wand555.github.io.challengesreworked.challenges.noblockplacing.NoBlockPlacingChallengeCommon;
import wand555.github.io.challengesreworkedgui.rows.MaterialRow;
import wand555.github.io.challengesreworkedgui.util.CopyUtil;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoBlockPlacingChallengeController extends PunishableChallengeController implements NoBlockPlacingChallenge {

    @FXML
    private Button addPlaceableBlocksButton;
    @FXML
    private ListView<MaterialRow> placeableBlocksList;

    @FXML
    @Override
    protected void initialize() {
        common = new NoBlockPlacingChallengeCommon();
        super.initialize();
        placeableBlocksList.setItems(FXCollections.observableArrayList(getCommon().getAllowedToPlace().stream().map(MaterialRow::new).toList()));
        addPlaceableBlocksButton.setOnAction(event -> {
            initPlaceableBlocksUI();
        });
    }

    private void initPlaceableBlocksUI() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        ListSelectionView<MaterialRow> listSelectionView = new ListSelectionView<>();
        List<Material> obtainableItems = Stream.of(Material.values())
                .filter(Material::isBlock)
                .filter(Predicate.not(Material::isAir))
                .filter(Predicate.not(Material::isLegacy))
                .filter(material -> placeableBlocksList.getItems().stream().noneMatch(materialRow -> materialRow.getMaterial() == material))
                .sorted(Comparator.comparing(Enum::toString))
                .toList();
        List<MaterialRow> rows = obtainableItems.stream().map(MaterialRow::new).toList();
        listSelectionView.setSourceItems(FXCollections.observableArrayList(rows));
        listSelectionView.setTargetItems(CopyUtil.deepCopy(placeableBlocksList.getItems()));
        root.getChildren().add(listSelectionView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner();
        stage.show();
        stage.setOnCloseRequest(event1 -> {
            placeableBlocksList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems()));
            getCommon().setAllowedToPlace(placeableBlocksList.getItems().stream().map(MaterialRow::getMaterial).collect(Collectors.toSet()));
            });
    }


    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoBlockPlacingChallengeCommon getCommon() {
        return (NoBlockPlacingChallengeCommon) super.getCommon();
    }
}
