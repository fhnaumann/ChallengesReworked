package wand555.github.io.challengesreworkedgui.controllers.challenges.noblockbreaking;

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
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.noblockbreaking.NoBlockBreakingChallenge;
import wand555.github.io.challengesreworked.challenges.noblockbreaking.NoBlockBreakingChallengeCommon;
import wand555.github.io.challengesreworked.challenges.noblockplacing.NoBlockPlacingChallengeCommon;
import wand555.github.io.challengesreworkedgui.controllers.challenges.PunishableChallengeController;
import wand555.github.io.challengesreworkedgui.rows.MaterialRow;
import wand555.github.io.challengesreworkedgui.util.CopyUtil;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoBlockBreakingChallengeController extends PunishableChallengeController implements NoBlockBreakingChallenge {

    @FXML
    private Button addBreakableBlocksButton;
    @FXML
    private ListView<MaterialRow> breakableBlocksList;

    @FXML
    @Override
    protected void initialize() {
        common = new NoBlockBreakingChallengeCommon();
        super.initialize();
        breakableBlocksList.setItems(FXCollections.observableArrayList(getCommon().getAllowedToBreak().stream().map(MaterialRow::new).toList()));
        addBreakableBlocksButton.setOnAction(event -> {
            initBreakableBlocksUI();
        });
    }

    private void initBreakableBlocksUI() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        ListSelectionView<MaterialRow> listSelectionView = new ListSelectionView<>();
        List<Material> breakableBlockMaterials = Stream.of(Material.values())
                .filter(Material::isBlock)
                .filter(Predicate.not(Material::isAir))
                .filter(Predicate.not(Material::isLegacy))
                .filter(material -> breakableBlocksList.getItems().stream().noneMatch(materialRow -> materialRow.getMaterial() == material))
                .sorted(Comparator.comparing(Enum::toString))
                .toList();
        List<MaterialRow> rows = breakableBlockMaterials.stream().map(MaterialRow::new).toList();
        listSelectionView.setSourceItems(FXCollections.observableArrayList(rows));
        listSelectionView.setTargetItems(CopyUtil.deepCopy(breakableBlocksList.getItems()));
        root.getChildren().add(listSelectionView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner();
        stage.show();
        stage.setOnCloseRequest(event1 -> {
            breakableBlocksList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems()));
            getCommon().setAllowedToBreak(breakableBlocksList.getItems().stream().map(MaterialRow::getMaterial).collect(Collectors.toSet()));
            });
    }

    @Override
    public void setDataFromCommon(Common from) {
        super.setDataFromCommon(from);
        List<MaterialRow> allowedToBreak = getCommon().getAllowedToBreak().stream().map(MaterialRow::new).toList();
        breakableBlocksList.setItems(FXCollections.observableArrayList(allowedToBreak));
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoBlockBreakingChallengeCommon getCommon() {
        return (NoBlockBreakingChallengeCommon) super.getCommon();
    }
}
