package wand555.github.io.challengesreworkedgui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.Commonable;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworkedgui.controllers.challenges.ChallengeController;
import wand555.github.io.challengesreworkedgui.controllers.punishments.PunishmentController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wrapper {

    public static void setDataInControllerFrom(List<ChallengeCommon> commons, List<ChallengeController> challengeControllers) {
        for(ChallengeCommon common : commons) {
            String commonClassName = common.getClass().getSimpleName();
            commonClassName = commonClassName.replace("Common", "") + "Controller";
            String finalCommonClassName = commonClassName;
            challengeControllers.stream()
                    .filter(challengeController -> {
                        System.out.println(challengeController.getClass().getSimpleName());
                        return challengeController.getClass().getSimpleName().equals(finalCommonClassName);
                    })
                    .findFirst()
                    .ifPresentOrElse(challengeController -> {
                        challengeController.setDataFromCommon(common);
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
    public static PunishmentController wrapAndInject(PunishmentCommon punishmentCommon) {
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
        punishmentController.setDataFromCommon(punishmentCommon);
        return punishmentController;
    }
}
