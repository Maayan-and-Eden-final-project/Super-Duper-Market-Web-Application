package components.mainScene.body;
import components.mainScene.app.AppController;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class BodyController {

    @FXML
    private VBox bodyVbox;

    private AppController mainController;

    public VBox getBodyVbox() {
        return bodyVbox;
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
}
