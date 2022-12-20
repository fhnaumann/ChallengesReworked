package wand555.github.io.challengesreworkedgui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.bukkit.Material;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.io.InputStream;

public class MaterialRow extends Row {

    private final Material material;

    public MaterialRow(Material material) {
        super(5);
        this.material = material;
        String userFriendly = PlaceHolderHandler.mapToString(material);
        InputStream inputStream = ChallengeApplication.class.getResourceAsStream("item/%s.png".formatted(material.toString().toLowerCase()));
        if(inputStream != null) {
            ImageView imageView = new ImageView(new Image(inputStream));
            getChildren().add(imageView);
        }
        getChildren().add(new Label(userFriendly));
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public Row copy() {
        return new MaterialRow(getMaterial());
    }
}
