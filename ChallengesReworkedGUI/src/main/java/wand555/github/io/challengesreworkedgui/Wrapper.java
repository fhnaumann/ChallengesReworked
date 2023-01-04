package wand555.github.io.challengesreworkedgui;

import javafx.fxml.FXMLLoader;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.controllers.Controller;
import wand555.github.io.challengesreworkedgui.controllers.punishments.PunishmentController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wrapper {

    /**
     * Uses the deserialized common objects to find their corresponding controller classes.
     * The naming scheme follows an order (e.g.):
     * NoCraftingChallengeCommon -> NoCraftingChallengeController
     * HealthPunishmentCommon -> HealthPunishmentController
     * MobGoalCommon -> MobGoalController
     * @param commons list of common objects
     * @param controllers existing controllers where the common object will be "injected" to.
     */
    public static void setDataInControllerFrom(List<? extends Common> commons, List<? extends Controller> controllers) {
        for(Common common : commons) {
            String commonClassName = common.getClass().getSimpleName();
            commonClassName = commonClassName.replace("Common", "") + "Controller";
            String finalCommonClassName = commonClassName;
            controllers.stream()
                    .filter(challengeController -> {
                        System.out.println(challengeController.getClass().getSimpleName());
                        return challengeController.getClass().getSimpleName().equals(finalCommonClassName);
                    })
                    .findFirst()
                    .ifPresentOrElse(challengeController -> {
                        challengeController.setDataFromCommon(common, true);
                    }, () -> {
                        throw new RuntimeException("Cannot find matching controller class for common object %s".formatted(common));
                    });
        }
    }

    /**
     * Wraps punishment common into its controller class and also sets the common variable there.
     * @param punishmentCommon
     * @return
     */
    public static PunishmentController wrapAndInject(PunishmentCommon punishmentCommon, boolean globallyEnabled) {
        // load fxml file where the associated punishment controller matches the punishment common type.
        // call setDataFromCommon on the controller
        // return controller
        String commonClassName = punishmentCommon.getClass().getSimpleName(); //e.g. HealthPunishmentCommon
        String baseClassName = commonClassName.replace("Common", ""); //e.g. HealthPunishment
        String[] splitByWords = baseClassName.split("(?=\\p{Upper})");
        String fxmlFileName = Stream.of(splitByWords).map(String::toLowerCase).collect(Collectors.joining("_"));
        fxmlFileName = fxmlFileName + ".fxml";
        ResourceBundle bundle = new ResourceBundleWrapper(ResourceBundle.getBundle("wand555/github/io/challengesreworkedgui/lang_punishments"));
        URL url = ChallengeApplication.class.getResource("punishments/" + fxmlFileName);
        if(url == null) {
            throw new RuntimeException("Failed to load fxml file '%s' based on common '%s'.".formatted(fxmlFileName, punishmentCommon));
        }
        FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PunishmentController punishmentController = fxmlLoader.getController();
        punishmentController.setOnlyGlobalChanges(globallyEnabled);
        punishmentController.setDataFromCommon(punishmentCommon, true);
        return punishmentController;
    }
}
