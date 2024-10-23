package algeo01_23002.gui.controllers;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import atlantafx.base.controls.ToggleSwitch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private double width;
    private double height;

    @FXML
    ToggleSwitch themeToggler;

    @FXML
    MFXButton linearSystemSolverButton;
    @FXML
    MFXButton interpolationButton;
    @FXML
    MFXButton regressionButton;
    @FXML
    MFXButton matrixTransformationButton;
    @FXML
    MFXButton matrixOperationsButton;
    @FXML
    MFXButton matrixPropertiesButton;
    @FXML
    MFXButton aboutButton;
    @FXML
    MFXButton helpButton;

    public void initialize() {
        if(Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css"){
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }
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
    public void onLinearSystemSolverButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/linear-system-solver-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }

    @FXML
    public void onInterpolationMenuClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/interpolation-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);

        stage.show();
    }

    @FXML
    public void onRegressionButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/regression-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onMatrixTransformationButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/matrix-transformation-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onMatrixOperationsButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/matrix-operations-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onMatrixPropertiesButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/matrix-properties-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onAboutButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/about-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onHelpMenuClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/help-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        width = stage.getWidth();
        height = stage.getHeight();
        scene = new Scene(fxmlLoader.load());
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setScene(scene);
        stage.show();
    }
}
