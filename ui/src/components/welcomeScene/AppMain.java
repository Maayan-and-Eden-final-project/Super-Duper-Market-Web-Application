package components.welcomeScene;

import components.mainScene.app.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import systemEngine.DesktopEngine;

import java.net.URL;

public class AppMain extends Application {

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("welcome.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load();

        primaryStage.setX(30);
        primaryStage.setY(30);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        WelcomeController appController = fxmlLoader.getController();
        DesktopEngine engine = new DesktopEngine(appController);
        appController.setEngine(engine);
        appController.setPrimaryStage(primaryStage);
        primaryStage.show();
    }
}
