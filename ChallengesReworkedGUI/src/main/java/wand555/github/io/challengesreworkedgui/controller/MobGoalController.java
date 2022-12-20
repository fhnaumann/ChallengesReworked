package wand555.github.io.challengesreworkedgui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bukkit.entity.EntityType;
import org.controlsfx.control.ListSelectionView;
import wand555.github.io.challengesreworkedgui.MaterialRow;
import wand555.github.io.challengesreworkedgui.MobRow;
import wand555.github.io.challengesreworkedgui.util.CopyUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MobGoalController {
    @FXML
    private TitledPane titledPane;
    @FXML
    private ToggleButton activateButton;
    @FXML
    private Button addMobsToKill;
    @FXML
    private ListView<MobRow> mobsToKillList;

    @FXML
    private void initialize() {
        mobsToKillList.setItems(FXCollections.observableArrayList(new MobRow(EntityType.ENDER_DRAGON, 1)));
        addMobsToKill.setOnAction(event -> {
            initMobsToKillUI();
        });
    }
    private void initMobsToKillUI() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        ListSelectionView<MobRow> listSelectionView = new ListSelectionView<>();
        List<EntityType> killableMobs = Stream.of(EntityType.values())
                .filter(EntityType::isAlive)
                .sorted(Comparator.comparing(Enum::toString))
                .toList();
        List<MobRow> rows = killableMobs.stream().map(MobRow::new).toList();
        listSelectionView.setSourceItems(FXCollections.observableArrayList(rows));
        listSelectionView.setTargetItems(CopyUtil.deepCopy(mobsToKillList.getItems()));
        root.getChildren().add(listSelectionView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> mobsToKillList.setItems(CopyUtil.deepCopy(listSelectionView.getTargetItems())));

    }
}
