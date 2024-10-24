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
    public void initialize(){
        if(Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css"){
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }

        String[] menuItems = {"Home", "Interpolation", "Polynomial Interpolation"};
        Breadcrumbs.BreadCrumbItem<String> rootItem = Breadcrumbs.buildTreeModel(
                menuItems
        );

        breadCrumbs.setSelectedCrumb(rootItem);

        firstMatrixHyperLink.setOnAction(event -> {
            Matrix temp = null;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Matrix File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            // Show the file chooser
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Load the matrix from the selected file
                temp = (loadMatrixFromTxt(selectedFile));
            }
            if(temp != null){
                firstMatrix = temp;
                firstMatrixInput.setText(outputPaddedMatrix(firstMatrix));
                messageBox.setVisible(false);
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
            } else {
                errorNotification("Import from file failed.\nMake sure you choose the right file and data format is correct!");
            }
        });


        resultMatrixHyperLink.setOnAction(event -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Text File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            // Show the save dialog
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                // Save the text to the selected file
                try {
                    saveTextToFile(file, resultMatrixOutput.getText());
                    System.out.println((resultMatrixOutput.getText()));
                    messageBox.setVisible(false);
                    successNotification("File successfully saved!");
                } catch (IOException e) {
                    errorNotification("Export to file failed.\n File cannot be created, or cannot be opened for some reason.");
                }
            }
        });

        // Set a tooltip to show multi-line prompt
        Tooltip exampleTooltip = new Tooltip("Example:\n    -0.3 -2 1 2 -1 1 3 4 2 0 5 6");
        exampleTooltip.setFont(firstMatrixInput.getFont());
        firstMatrixInput.setTooltip(exampleTooltip);

        Tooltip exampleValueTooltip = new Tooltip("Example:\n  3");
        exampleValueTooltip.setFont(firstMatrixInput.getFont());
        valueInput.setTooltip(exampleValueTooltip);

        getPredictionButton.setOnMouseClicked(event -> {
            try {
                firstMatrix = new Matrix(1, 1);
                firstMatrix.inputMatrixFromString(firstMatrixInput.getText());
                firstMatrixInput.setText(outputPaddedMatrix(firstMatrix));
                if(firstMatrix.getRowsCount() != 1 || firstMatrix.getColsCount() != 16){
                    throw new Exception("4x4");
                }
                if(valueInput.getText().isEmpty()){
                    throw new IllegalAccessException("X value is Empty");
                }
                if(valueInput1.getText().isEmpty()){
                    throw new IllegalAccessException("Y value is Empty");
                }
                Matrix value = new Matrix(1, 1);
                value.inputMatrixFromString(valueInput.getText());
                if(value.getColsCount() != 1 && value.getRowsCount() != 1){
                    throw new IllegalArgumentException("X value and Y should be 1");
                }
                Matrix value1 = new Matrix(1, 1);
                value1.inputMatrixFromString(valueInput1.getText());
                if(value1.getColsCount() != 1 && value1.getRowsCount() != 1){
                    throw new IllegalArgumentException("X value and Y should be 1");
                }
                double res = 0;
                try {
                    Matrix XInverse = Interpolation.getXInverseBicubicSpline();
                    res = Interpolation.bicubicSplineInterpolation(firstMatrix, value.getData(0, 0), value1.getData(0, 0), XInverse);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                predictionOutput.setText(res + "");
                messageBox.setVisible(false);
                valueInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                valueInput1.pseudoClassStateChanged(Styles.STATE_DANGER, false);
            } catch (IllegalArgumentException e){
                if(e.getMessage().startsWith("X value")) {
                    valueInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    valueInput1.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    errorNotification("Number of x or y value must be 1");
                }else {
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    errorNotification("Please input points with the correct size and format");
                }
            } catch (IllegalAccessException e){
                if(Objects.equals(e.getMessage(), "X value is Empty")) {
                    valueInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    valueInput1.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    errorNotification("Please input the x value and y value first");
                }
            } catch (RuntimeException e) {
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                valueInput.setText("");
                valueInput1.setText("");
                predictionOutput.setText("");
                errorNotification("Interpolation failed.");
            }
            catch (Exception e){
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                valueInput.setText("");
                valueInput1.setText("");
                predictionOutput.setText("");
                errorNotification("Matrix should be 1x16");
            }
        });


        firstMatrixInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // Focus lost
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
            }
        });
        valueInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // Focus lost
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
            }
        });
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
