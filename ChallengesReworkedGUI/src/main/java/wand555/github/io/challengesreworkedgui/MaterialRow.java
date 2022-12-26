package wand555.github.io.challengesreworkedgui;

import wand555.github.io.challengesreworked.Mapper;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bukkit.Material;

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
