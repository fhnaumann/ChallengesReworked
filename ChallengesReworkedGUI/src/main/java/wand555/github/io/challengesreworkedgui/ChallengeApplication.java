package wand555.github.io.challengesreworkedgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class ChallengeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle.clearCache();
        ResourceBundle bundle = ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_bundle");
        FXMLLoader loader = new FXMLLoader(ChallengeApplication.class.getResource("overview.fxml"), bundle);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}