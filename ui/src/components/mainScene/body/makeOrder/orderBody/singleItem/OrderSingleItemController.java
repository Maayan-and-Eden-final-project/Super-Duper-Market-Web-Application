package components.mainScene.body.makeOrder.orderBody.singleItem;

import components.mainScene.app.AppController;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import sdm.enums.PurchaseCategory;

public class OrderSingleItemController {

    @FXML
    private VBox singleItemVbox;

    @FXML
    private Label itemNameValue;

    @FXML
    private HBox itemIdHbox;

    @FXML
    private Label itemIdLabel;

    @FXML
    private Label itemIdValue;

    @FXML
    private Label purchaseCategoryValue;

    @FXML
    private HBox priceHbox;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Label itemPriceValue;

    @FXML
    private TextField itemAmountTextField;

    @FXML
    private Button addItemButton;

    @FXML
    private Label messageLabel;

    @FXML private PathTransition pathTransition;
    @FXML private Path path;
    //@FXML private Circle circle;
    private Stage stage;
    private ImageView imageView;

    @FXML
    private ImageView itemsImage;

    private AppController appController;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    private void setAnimation() {
        this.path = new Path();
        this.pathTransition = new PathTransition();

        MoveTo moveTo = new MoveTo( 0,0);
        LineTo lineTo = new LineTo();
        Bounds imageCoords = imageView.localToScene(imageView.getBoundsInLocal());
        lineTo.setX(imageCoords.getMaxX() - messageLabel.localToScene(messageLabel.getLayoutBounds()).getMaxX());

        lineTo.setY(imageCoords.getMaxY() -   messageLabel.localToScene(messageLabel.getLayoutBounds()).getMaxY() - 50);
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(itemsImage);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setAutoReverse(false);
    }


    @FXML
    void addItemAction(ActionEvent event) {

        if(!this.itemAmountTextField.getText().equals("")
                && ((this.purchaseCategoryValue.textProperty().getValue().equals(PurchaseCategory.QUANTITY.toString())
        && this.itemAmountTextField.getText().matches("[0-9]+"))
                || (this.purchaseCategoryValue.textProperty().getValue().equals(PurchaseCategory.WEIGHT.toString())
        && this.itemAmountTextField.getText().matches("^[0-9]*([.]{1}[0-9]{0,2}){0,1}$")))) {
            this.messageLabel.setVisible(false);
            updateOrderItem();
            this.messageLabel.setText("Added " + itemAmountTextField.getText() + " " + itemNameValue.getText());
            this.messageLabel.setTextFill(Color.GREEN);
            this.messageLabel.setVisible(true);

            if(appController.isIsAnimation()) {
                itemsImage.setVisible(true);
                setAnimation();
                pathTransition.play();
                pathTransition.statusProperty().addListener(new ChangeListener<Animation.Status>() {
                    @Override
                    public void changed(ObservableValue<? extends Animation.Status> observable, Animation.Status oldValue, Animation.Status newValue) {
                        if(newValue == Animation.Status.STOPPED) {
                            itemsImage.setVisible(false);
                        }
                    }
                });

            }
            this.itemAmountTextField.setText("");
        } else {
            this.messageLabel.setText("Can't add " + itemAmountTextField.getText() + " " + itemNameValue.getText());
            this.messageLabel.setTextFill(Color.RED);
            this.messageLabel.setVisible(true);
            this.itemAmountTextField.setText("");
        }
    }

    private void updateOrderItem() {

        if (appController.getCurrentOrderMethod().equals("Static")) {
            appController.getCurrentStaticOrderItems()
                    .get(Integer.parseInt(itemIdValue.getText())).setAmountAndTotalPrice(Float.parseFloat(itemAmountTextField.getText()));

        } else if (appController.getCurrentOrderMethod().equals("Dynamic")) {
            if (appController.getCurrentDynamicOrderItems().containsKey(Integer.parseInt(itemIdValue.getText()))) {
                appController.getCurrentDynamicOrderItems()
                        .put(Integer.parseInt(itemIdValue.getText()),
                                appController.getCurrentDynamicOrderItems().get(Integer.parseInt(itemIdValue.getText()))
                                        + Float.parseFloat(itemAmountTextField.getText()));
            } else {
                appController.getCurrentDynamicOrderItems()
                        .put(Integer.parseInt(itemIdValue.getText()), Float.parseFloat(itemAmountTextField.getText()));
            }
        }
    }

    public TextField getItemAmountTextField() {
        return itemAmountTextField;
    }

    public Label getItemPriceLabel() {
        return itemPriceLabel;
    }

    public Button getAddItemButton() {
        return addItemButton;
    }

    public Label getItemNameValue() {
        return itemNameValue;
    }

    public Label getItemIdValue() {
        return itemIdValue;
    }

    public Label getPurchaseCategoryValue() {
        return purchaseCategoryValue;
    }

    public Label getItemPriceValue() {
        return itemPriceValue;
    }
}
