package algeo01_23002.gui.controllers.matrix_operators;

import algeo01_23002.types.Matrix;
import atlantafx.base.controls.Breadcrumbs;
import atlantafx.base.controls.Message;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static algeo01_23002.gui.Utilities.*;

public class MatrixMultiplicationController {
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
    TextArea secondMatrixInput;
    Matrix secondMatrix;

    @FXML
    TextArea resultMatrixOutput;
    Matrix resultMatrix;

    @FXML
    Hyperlink firstMatrixHyperLink;

    @FXML
    Hyperlink secondMatrixHyperLink;

    @FXML
    Hyperlink resultMatrixHyperLink;

    @SuppressWarnings("unchecked")
    public void initialize(){
        if(Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css"){
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }

        String[] menuItems = {"Home", "Matrix Operations", "Matrix Multiplication"};
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

        secondMatrixHyperLink.setOnAction(event -> {
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
                secondMatrix = temp;
                secondMatrixInput.setText(outputPaddedMatrix(secondMatrix));
                messageBox.setVisible(false);
                secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
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
        Tooltip exampleTooltip = new Tooltip("Example:\n    -0.3 -2 3\n      -1  1 3\n       2  0 1");
        exampleTooltip.setFont(firstMatrixInput.getFont());
        firstMatrixInput.setTooltip(exampleTooltip);
        secondMatrixInput.setTooltip(exampleTooltip);

        getResultButton.setOnMouseClicked(event -> {
            try {
                if(firstMatrixInput.getText().isEmpty()){
                    throw new IllegalAccessException("First Matrix is Empty");
                }
                if(secondMatrixInput.getText().isEmpty()){
                    throw new IllegalAccessException("Second Matrix is Empty");
                }
                firstMatrix = new Matrix(1, 1);
                firstMatrix.inputMatrixFromString(firstMatrixInput.getText());
                firstMatrixInput.setText(outputPaddedMatrix(firstMatrix));

                secondMatrix = new Matrix(1, 1);
                secondMatrix.inputMatrixFromString(secondMatrixInput.getText());
                secondMatrixInput.setText(outputPaddedMatrix(secondMatrix));

                resultMatrixOutput.setText("Calculating...");
                resultMatrixOutput.setText(outputPaddedMatrix(firstMatrix.multiplyByMatrix(secondMatrix)));

                resultMatrixHyperLink.setVisible(true);
                messageBox.setVisible(false);
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
            } catch (IllegalArgumentException e){
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                if(Objects.equals(resultMatrixOutput.getText(), "Calculating...")){
                    resultMatrixOutput.setText("");
                    resultMatrixHyperLink.setVisible(false);
                }
                errorNotification("Please input matrix with the correct size and format");
            } catch (IllegalAccessException e){
                if(Objects.equals(e.getMessage(), "First Matrix is Empty")) {
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    errorNotification("Please input the first matrix first");
                } else if(Objects.equals(e.getMessage(), "Second Matrix is Empty")) {
                    secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    errorNotification("Please input the second matrix first");
                }
            }

        });

        firstMatrixInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // Focus lost
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
            }
        });

        secondMatrixInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // Focus lost
                secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
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
        } else if (selectedCrumb.equals("Matrix Operations")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/matrix-operations-menu.fxml"));
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
