package components.welcomeScene;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import systemEngine.Connector;
import systemEngine.DesktopEngine;
import task.loadXmlTask.LoadXmlTask;
import validation.Validator;
import validation.XmlValidate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class WelcomeController {

    @FXML
    private VBox welcomePane;

    @FXML
    private Button loadXmlButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    private Stage primaryStage;
    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private DesktopEngine engine;
    private SimpleBooleanProperty isValidFileProperty;

    public WelcomeController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        isValidFileProperty = new SimpleBooleanProperty(true);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setProgressLabel(Label progressLabel) {
        this.progressLabel = progressLabel;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Label getProgressLabel() {
        return progressLabel;
    }

    public Button getLoadXmlButton() {
        return loadXmlButton;
    }

    public void setLoadXmlButton(Button loadXmlButton) {
        this.loadXmlButton = loadXmlButton;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public DesktopEngine getEngine() {
        return engine;
    }

    public void setEngine(DesktopEngine engine) {
        this.engine = engine;
    }

    @FXML
    private void initialize() {


    }

    @FXML
    public void openFileButtonAction(ActionEvent event) {
        loadXmlButton.setDisable(true);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml file", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            loadXmlButton.setDisable(false);
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\","/");
        selectedFileProperty.set(absolutePath);

        UiAdapter uiAdapter = new UiAdapter(primaryStage);
        uiAdapter.enableProgressBar(progressBar, progressLabel);
        engine.loadXmlFile(uiAdapter, absolutePath, isFileSelected, isValidFileProperty);

    }

    private HBox loadMinimalCartProgressBar(VBox bodyComponent) {
        HBox progressHbox = new HBox();
        Label progressLabel = new Label();
        Label messageLabel = new Label("Calculating Minimal Cart");
        ProgressBar progressBar = new ProgressBar();
        progressHbox.setAlignment(Pos.TOP_CENTER);
        messageLabel.setAlignment(Pos.TOP_CENTER);
        progressHbox.getChildren().addAll(progressBar, progressLabel);
        bodyComponent.getChildren().addAll(progressHbox, messageLabel);
        return progressHbox;
    }

    public void initMinimalCart(Task<Boolean> aTask, VBox bodyComponent) {
        HBox progressBarHbox = loadMinimalCartProgressBar(bodyComponent);
        bindTaskToUIComponents(aTask , (ProgressBar)progressBarHbox.getChildren().get(0), (Label)progressBarHbox.getChildren().get(1));
    }

    public void bindTaskToUIComponents(Task<Boolean> aTask, ProgressBar progressBar, Label progressLabel) {

        // task progress bar
        progressBar.progressProperty().bind(aTask.progressProperty());

        // task percent label
        progressLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        aTask.progressProperty(),
                                        100)),
                        " %"));
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished();
        });
    }

    public void onTaskFinished() {
        this.progressLabel.textProperty().unbind();
        this.progressBar.progressProperty().unbind();
    }

}



