package wand555.github.io.challengesreworkedgui.rows;

import javafx.scene.layout.StackPane;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.Mapper;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bukkit.Material;
import wand555.github.io.challengesreworkedgui.ChallengeApplication;

import java.io.InputStream;

public class MaterialRow extends Row {

    private final Material material;

    public MaterialRow(Material material) {
        super(5);
        this.material = material;
        String userFriendly = Mapper.mapToString(material);
        InputStream inputStream = ChallengeApplication.class.getResourceAsStream("item/%s.png".formatted(material.toString().toLowerCase()));
        if(inputStream != null) {
            ImageView imageView = new ImageView(new Image(inputStream));
            getChildren().add(new StackPane(imageView));
        }
        getChildren().add(new StackPane(new Label(userFriendly)));
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public Row copy() {
        return new MaterialRow(getMaterial());
    }
}
