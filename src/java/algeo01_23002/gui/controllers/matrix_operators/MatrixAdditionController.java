package algeo01_23002.gui.controllers.matrix_operators;

import atlantafx.base.controls.Breadcrumbs;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Styles;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import algeo01_23002.types.Matrix;

import static algeo01_23002.gui.Utilities.*;

import java.io.IOException;

public class MatrixAdditionController {
    private Stage stage;
    private Scene scene;
    private Parent root;

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

    @SuppressWarnings("unchecked")
    public void initialize(){
        if(Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css"){
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }

        String[] menuItems = {"Home", "Matrix Operations", "Matrix Addition"};
        Breadcrumbs.BreadCrumbItem<String> rootItem = Breadcrumbs.buildTreeModel(
                menuItems
        );

        breadCrumbs.setSelectedCrumb(rootItem);

        // Set a tooltip to show multi-line prompt
        firstMatrixInput.setTooltip(new Tooltip("Example:\n    -2 -2 3\n    -1 1 3\n    2 0 -0.33"));
        secondMatrixInput.setTooltip(new Tooltip("Example:\n    -2 -2 3\n    -1 1 3\n    2 0 -0.33"));

        firstMatrixInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                // You can add your logic here, e.g., validation or resetting text
                try {
                    firstMatrix = new Matrix(1, 1);
                    firstMatrix.inputMatrixFromString(firstMatrixInput.getText());
                    firstMatrixInput.setText(outputPaddedMatrix(firstMatrix));
                    if(secondMatrixInput.getText().isEmpty()){
                        throw new IllegalAccessException("First Text Area is Empty");
                    }
                    resultMatrixOutput.setText(outputPaddedMatrix(firstMatrix.add(secondMatrix)));
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                    secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                } catch (IllegalArgumentException e){
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                } catch (IllegalAccessException e){
                    secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                }
            } else { // Focus gained
                firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
            }
        });

        secondMatrixInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                // You can add your logic here, e.g., validation or resetting text
                try {
                    secondMatrix = new Matrix(1, 1);
                    secondMatrix.inputMatrixFromString(secondMatrixInput.getText());
                    secondMatrixInput.setText(outputPaddedMatrix(secondMatrix));
                    if(firstMatrixInput.getText().isEmpty()){
                        throw new IllegalAccessException("First Text Area is Empty");
                    }
                    resultMatrixOutput.setText(outputPaddedMatrix(firstMatrix.add(secondMatrix)));
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                    secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, false);
                } catch (IllegalArgumentException e){
                    secondMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                } catch (IllegalAccessException e){
                    firstMatrixInput.pseudoClassStateChanged(Styles.STATE_DANGER, true);
                }
            } else { // Focus gained
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
}
