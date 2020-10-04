package task.loadXmlTask;

import components.welcomeScene.WelcomeController;
import components.welcomeScene.UiAdapter;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import systemEngine.Connector;
import systemEngine.DesktopEngine;
import validation.Validator;
import validation.XmlValidate;

import javax.script.Bindings;

public class LoadXmlTask extends Task<Boolean>{
    private Connector engine;
    private String absolutePath;
    private SimpleBooleanProperty isFileSelectedTaskProperty;
    private WelcomeController welcomeController;
    private UiAdapter uiAdapter;
    private SimpleBooleanProperty isValidFile;


    public LoadXmlTask(Connector engine, String path, SimpleBooleanProperty isFileSelected, WelcomeController appController, UiAdapter uiAdapter,  SimpleBooleanProperty isValidFile) {
        this.engine = engine;
        this.absolutePath = path;
        this.isFileSelectedTaskProperty = new SimpleBooleanProperty(isFileSelected.get());
        isFileSelected.bind(isFileSelectedTaskProperty);
        this.welcomeController = appController;
        this.uiAdapter = uiAdapter;
        this.isValidFile = new SimpleBooleanProperty();
        isValidFile.bind(this.isValidFile);
    }

    public boolean isIsFileSelectedTaskProperty() {
        return isFileSelectedTaskProperty.get();
    }

    public SimpleBooleanProperty isFileSelectedTaskPropertyProperty() {
        return isFileSelectedTaskProperty;
    }

    @Override
    protected Boolean call() throws Exception {
        Validator xmlValidate = new XmlValidate(engine);
        Connector tempEngine = new DesktopEngine(this.welcomeController);

        try {
            updateProgress(0,10);
            Thread.sleep(500);
            if (this.isFileSelectedTaskProperty.get()){
                tempEngine = (Connector) engine.clone();
            }
            updateProgress(2,10);
            Thread.sleep(500);
            //xmlValidate.validate(absolutePath);
            updateProgress(5,10);
            Thread.sleep(500);
            this.isFileSelectedTaskProperty.set(true);
            updateProgress(7,10);
            Thread.sleep(500);
            updateProgress(10,10);
            Thread.sleep(500);
             Platform.runLater(() -> {
                 uiAdapter.loadMainScene(welcomeController);
             });
        } catch (Exception e) {
            if (this.isFileSelectedTaskProperty.get()) {
                engine = (Connector) tempEngine.clone();
            }
            this.isFileSelectedTaskProperty.set(false);
            this.isValidFile.set(false);
            updateProgress(0,10);
            e.printStackTrace();
            uiAdapter.alertError(e.getMessage());
            uiAdapter.disableProgressBar(welcomeController.getProgressBar(), welcomeController.getProgressLabel(), welcomeController.getLoadXmlButton());
            this.cancel();
        }
        this.cancel();
        return true;
    }
}
