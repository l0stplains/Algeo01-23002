package algeo01_23002.gui.controllers;

import atlantafx.base.controls.Breadcrumbs;
import atlantafx.base.controls.Message;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.layout.InputGroup;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import atlantafx.base.util.DoubleStringConverter;
import atlantafx.base.util.IntegerStringConverter;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static algeo01_23002.imageresizing.ImageResizing.resizeImage;

public class ImageResizingController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Breadcrumbs breadCrumbs;

    @FXML
    ToggleSwitch themeToggler;

    @FXML
    private ImageView originalImageView;
    private BufferedImage originalBufferedImage;

    @FXML
    private ImageView processedImageView;
    private BufferedImage processedBufferedImage;

    @FXML
    InputGroup xInputGroup;

    @FXML
    InputGroup yInputGroup;

     @FXML
    Button uploadImageButton;

    @FXML
    Button saveImageButton;

    @FXML
    Button scaleImageButton;

    @FXML
    Message messageBox;

    @FXML
    MFXProgressSpinner loadingSpinner;


    @SuppressWarnings("unchecked")
    public void initialize(){
        if(Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css"){
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }

        String[] menuItems = {"Home", "Image Resizing"};
        Breadcrumbs.BreadCrumbItem<String> rootItem = Breadcrumbs.buildTreeModel(
                menuItems
        );

        breadCrumbs.setSelectedCrumb(rootItem);


        Tooltip exampleTooltip = new Tooltip("Scale factor\nExample: 1,2");
        Spinner<Double> xInputSpinner = new Spinner<>(0.1, 10, 1.0, 0.1);
        Spinner<Double> yInputSpinner = new Spinner<>(0.1, 10, 1.0, 0.1);
        xInputSpinner.setTooltip(exampleTooltip);
        xInputSpinner.getStyleClass().removeAll();
        xInputSpinner.setEditable(true);
        yInputSpinner.setEditable(true);
        yInputSpinner.setTooltip(exampleTooltip);

        Label xInputLabel = new Label("X");
        xInputLabel.setAlignment(Pos.CENTER);
        xInputLabel.setTooltip(exampleTooltip);

        Label yInputLabel = new Label("Y");
        yInputLabel.setAlignment(Pos.CENTER);
        yInputLabel.setTooltip(exampleTooltip);

        xInputSpinner.setDisable(true);
        yInputSpinner.setDisable(true);

        xInputGroup.getChildren().add(xInputLabel);
        xInputGroup.getChildren().add(xInputSpinner);
        yInputGroup.getChildren().add(yInputLabel);
        yInputGroup.getChildren().add(yInputSpinner);


        uploadImageButton.setOnAction(event -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    // Load and display the image
                    originalBufferedImage = ImageIO.read(file);
                    Image originalImage = SwingFXUtils.toFXImage(originalBufferedImage, null);
                    originalImageView.setImage(originalImage);
                    xInputSpinner.setDisable(false);
                    yInputSpinner.setDisable(false);
                    scaleImageButton.setDisable(false);
                } catch (IOException ex) {
                    errorNotification("Failed to upload image.");
                }
            }
        });

        scaleImageButton.setOnAction(event -> {
            if (originalBufferedImage != null) {
                processedImageView.setImage(null);
                // Show loading spinner
                loadingSpinner.setVisible(true);
                scaleImageButton.setDisable(true);

                // Run the image processing in a background thread
                Task<BufferedImage> task = new Task<BufferedImage>() {
                    @Override
                    protected BufferedImage call() throws Exception {
                        double scaleX = xInputSpinner.getValue();
                        double scaleY = yInputSpinner.getValue();
                        return resizeImage(originalBufferedImage, scaleX, scaleY);
                    }
                };

                // When the task is complete, update the UI on the JavaFX Application Thread
                task.setOnSucceeded(workerStateEvent -> {
                    successNotification("Scaling complete!");
                    processedBufferedImage = task.getValue();
                    Image processedImage = SwingFXUtils.toFXImage(processedBufferedImage, null);
                    processedImageView.setImage(processedImage);  // Display the processed image
                    loadingSpinner.setVisible(false); // Hide loading spinner
                    saveImageButton.setDisable(false);
                    scaleImageButton.setDisable(false);
                });

                // Handle failure
                task.setOnFailed(workerStateEvent -> {
                    errorNotification("Processing failed!");
                    loadingSpinner.setVisible(false); // Hide loading spinner
                    scaleImageButton.setDisable(false);
                });

                // Start the task in a new thread
                new Thread(task).start();
            } else {
                errorNotification("No image loaded for processing.");
            }
        });

        saveImageButton.setOnAction(event -> {
            if (processedBufferedImage != null) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    try {
                        ImageIO.write(processedBufferedImage, "png", file);
                        successNotification("Image successfully saved!");
                    } catch (IOException ex) {
                        errorNotification("Failed to save image!");
                    }
                }
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
