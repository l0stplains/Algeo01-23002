package algeo01_23002.gui.controllers.linear_system_solver;

import algeo01_23002.solvers.LinearSystemSolver;
import algeo01_23002.types.LinearSystemSolution;
import algeo01_23002.types.Matrix;
import algeo01_23002.types.ParametricSolution;
import algeo01_23002.types.UniqueSolution;
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
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static algeo01_23002.gui.Utilities.*;

public class CramersRuleMethodController {
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
    Matrix resultMatrix;

    @FXML
    TextArea explanationMatrixOutput;
    Matrix explanationMatrix;

    @FXML
    Hyperlink firstMatrixHyperLink;

    @FXML
    Hyperlink resultMatrixHyperLink;

    @FXML
    Hyperlink explanationMatrixHyperLink;

    @SuppressWarnings("unchecked")
    public void initialize(){
        if(Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css"){
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }

        String[] menuItems = {"Home", "Linear System Solver", "Gauss Jordan Elimination Method"};
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

        explanationMatrixHyperLink.setOnAction(event -> {
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
                    saveTextToFile(file, explanationMatrixOutput.getText());
                    messageBox.setVisible(false);
                    successNotification("File successfully saved!");
                } catch (IOException e) {
                    errorNotification("Export to file failed.\n File cannot be created, or cannot be opened for some reason.");
                }
            }
        });
        // Set a tooltip to show multi-line prompt
        Tooltip exampleTooltip = new Tooltip("Example:\n    -0.3 -2 3 2\n      -1  1 3 3\n       2  0 1 4");
        exampleTooltip.setFont(firstMatrixInput.getFont());
        firstMatrixInput.setTooltip(exampleTooltip);

        getResultButton.setOnMouseClicked(event -> {
            try {
                if(firstMatrixInput.getText().isEmpty()){
                    throw new IllegalAccessException("Matrix A is Empty");
                }

                firstMatrix = new Matrix(1, 1);
                firstMatrix.inputMatrixFromString(firstMatrixInput.getText());
                firstMatrixInput.setText(outputPaddedMatrix(firstMatrix));

                resultMatrixOutput.setText("Calculating...");
                explanationMatrixOutput.setText("Calculating...");

                // Create a Task to run the transformation in the background
                Task<LinearSystemSolution> linearSystemSolutionTask = new Task<>() {
                    @Override
                    protected LinearSystemSolution call() throws Exception {
                        LinearSystemSolver solver = new LinearSystemSolver();
                        return solver.cramersRule(firstMatrix);
                    }
                };

                // Define what happens when the task succeeds
                linearSystemSolutionTask.setOnSucceeded(e -> {
                    LinearSystemSolution solution = linearSystemSolutionTask.getValue();
                    if(solution instanceof UniqueSolution){
                        resultMatrix = ((UniqueSolution) solution).getSolution();
                        resultMatrixOutput.setText(outputPaddedMatrix(resultMatrix));
                        resultMatrixHyperLink.setVisible(true);
                    } else if (solution instanceof ParametricSolution){
                        resultMatrixOutput.setText("Parametric Solution Found");
                    } else {
                        resultMatrixOutput.setText("No Solution Found");
                    }
                    explanationMatrixOutput.setText(solution.toString());
                    explanationMatrixHyperLink.setVisible(true);
                    messageBox.setVisible(false);
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                });

                // Define what happens if the task fails
                linearSystemSolutionTask.setOnFailed(e -> {
                    Throwable exception = linearSystemSolutionTask.getException();
                    try {
                        throw exception;
                    } catch (IllegalArgumentException ex){
                        firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);

                        resultMatrixOutput.setText("");
                        resultMatrixHyperLink.setVisible(false);
                        explanationMatrixOutput.setText("");
                        explanationMatrixHyperLink.setVisible(false);
                        errorNotification("Solution could not be calculated\nDeterminant is zero or invalid matrix dimension");
                    } catch (Throwable ex){
                        errorNotification("Linear system solver failed.");
                    }

                });

                // Start the task in a new thread
                new Thread(linearSystemSolutionTask).start();
            } catch (IllegalArgumentException e){
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                errorNotification("Please input matrix with the correct size and format");
            } catch (IllegalAccessException e){
                if(Objects.equals(e.getMessage(), "Matrix A is Empty")) {
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                    errorNotification("Please input the matrix first");
                }
            }

        });

        firstMatrixInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
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
        } else if (selectedCrumb.equals("Linear System Solver")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/linear-system-solver-menu.fxml"));
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
