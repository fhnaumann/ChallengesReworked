package wand555.github.io.challengesreworkedgui;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wand555.github.io.challengesreworked.ChallengesReworkedApi;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class ChallengeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ChallengesReworkedApi.registerTypeAdapters();

        ChallengeApplication.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());

        ResourceBundle.clearCache();
        ResourceBundle bundle = new ResourceBundleWrapper(ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_challenges"));
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