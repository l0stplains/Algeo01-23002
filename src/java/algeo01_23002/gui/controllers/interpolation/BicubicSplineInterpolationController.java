package algeo01_23002.gui.controllers.interpolation;

import algeo01_23002.mathmodels.Interpolation;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.PolynomialResult;
import atlantafx.base.controls.Breadcrumbs;
import atlantafx.base.controls.Message;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static algeo01_23002.gui.Utilities.*;

public class BicubicSplineInterpolationController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Message messageBox;

    @FXML
    MFXButton getResultButton;

    @FXML
    Breadcrumbs breadCrumbs;

    @FXML
    ToggleSwitch themeToggler;

    @FXML
    TextArea firstMatrixInput;
    Matrix firstMatrix;

    @FXML
    TextArea resultMatrixOutput;
    PolynomialResult resultMatrix;

    @FXML
    TextField valueInput;
    @FXML
    TextField valueInput1;

    @FXML
    TextField predictionOutput;

    @FXML
    MFXButton getPredictionButton;

    @FXML
    Hyperlink firstMatrixHyperLink;

    @FXML
    Hyperlink resultMatrixHyperLink;


    @SuppressWarnings("unchecked")
    public void initialize() {
        if (Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css") {
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }

        String[] menuItems = {"Home", "Interpolation", "Polynomial Interpolation"};
        Breadcrumbs.BreadCrumbItem<String> rootItem = Breadcrumbs.buildTreeModel(
                menuItems
        );

        breadCrumbs.setSelectedCrumb(rootItem);
    }


    @FXML
    public void onThemeTogglerClicked() {
        if(themeToggler.isSelected()){
            Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        } else {
            Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        }
    }

    @FXML
    public void onCrumbsAction(Breadcrumbs.BreadCrumbActionEvent event) throws IOException {
        String selectedCrumb = event.getSelectedCrumb().getValue().toString();

        if (selectedCrumb.equals("Home")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/main-menu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } else if (selectedCrumb.equals("Interpolation")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/interpolation-menu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }

    }

    private void errorNotification(String message) {
        messageBox.setDescription(message);
        messageBox.getStyleClass().removeAll();
        messageBox.getStyleClass().add(Styles.DANGER);
        messageBox.setVisible(true);
        Animations.slideInRight(messageBox, Duration.millis(500)).playFromStart();
        var fadeIn = Animations.fadeIn(messageBox, Duration.millis(500));

        fadeIn.playFromStart();
        messageBox.setOnClose(event -> {
            Animations.slideOutRight(messageBox, Duration.millis(500)).playFromStart();
            var fadeOut = Animations.fadeOut(messageBox, Duration.millis(500));
            fadeOut.setOnFinished(animationEvent -> {
                messageBox.setVisible(false);
            });

            fadeOut.playFromStart();

        });
    }
    private void successNotification(String message) {
        messageBox.setDescription(message);
        messageBox.getStyleClass().removeAll();
        messageBox.getStyleClass().add(Styles.SUCCESS);
        messageBox.setVisible(true);
        Animations.slideInRight(messageBox, Duration.millis(500)).playFromStart();
        var fadeIn = Animations.fadeIn(messageBox, Duration.millis(500));

        fadeIn.playFromStart();
        messageBox.setOnClose(event -> {
            Animations.slideOutRight(messageBox, Duration.millis(500)).playFromStart();
            var fadeOut = Animations.fadeOut(messageBox, Duration.millis(500));
            fadeOut.setOnFinished(animationEvent -> {
                messageBox.setVisible(false);
            });

            fadeOut.playFromStart();

        });
    }
}
