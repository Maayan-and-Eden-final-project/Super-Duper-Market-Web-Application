package components.mainScene.body.addItem;

import components.mainScene.app.AppController;
import components.mainScene.body.addItem.storeOption.StoreOptionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sdm.enums.PurchaseCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItemController {
    @FXML
    private TextField itemIdTextField;

    @FXML
    private TextField itemNameTextField;

    @FXML
    private ComboBox<String> purchaseCategoryComboBox;

    @FXML
    private VBox checkStoresVbox;

    @FXML
    private Button addItemButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label storesMessageLabel;

    private List<StoreOptionController> checkedStores;
    private Integer itemIdSelection;
    private String itemNameSelection;
    private PurchaseCategory purchaseCategorySelection;
    private Map<Integer, Integer> sellingStoresIdToPrice;
    private AppController appController;

    @FXML
    void initialize() {
        this.checkedStores = new ArrayList<>();
        this.sellingStoresIdToPrice = new HashMap<>();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public List<StoreOptionController> getCheckedStores() {
        return checkedStores;
    }

    @FXML
    void addItemAction(ActionEvent event) {
        try {
            validate();
            appController.addNewItemToSystem(itemIdSelection,itemNameSelection,purchaseCategorySelection,sellingStoresIdToPrice);
            storesMessageLabel.setText("Item Added Successfully");
            messageLabel.setTextFill(Color.GREEN);
            storesMessageLabel.setVisible(true);
        } catch (Exception e) {

        }
    }

    private void validate() throws Exception {
        if(!itemIdTextField.getText().isEmpty() && this.itemIdTextField.getText().matches("[0-9]+"))
        {
            itemIdSelection = Integer.parseInt(this.itemIdTextField.getText());
            if(appController.getEngine().isItemDefinedInSystem(itemIdSelection))
            {
                itemIdSelection = null;
                this.messageLabel.setText("This Item Id Already Exist In The System");
                messageLabel.setTextFill(Color.RED);
                messageLabel.setVisible(true);
                throw new Exception();
            }
            else
            {
                this.itemIdTextField.getStyleClass().remove("not-selected");
                messageLabel.setVisible(false);
            }
        }
        else{
            this.messageLabel.setText("Not A Whole Number");
            messageLabel.setTextFill(Color.RED);
            messageLabel.setVisible(true);
            this.itemIdTextField.getStyleClass().add("not-selected");
            throw new Exception();
        }

        if(itemNameTextField.getText().isEmpty())
        {
            this.itemNameTextField.getStyleClass().add("not-selected");
            throw new Exception();
        }
        else{
            this.itemNameTextField.getStyleClass().remove("not-selected");
            itemNameSelection = itemNameTextField.getText();
        }

        if(purchaseCategoryComboBox.getSelectionModel().getSelectedItem() == null)
        {
            this.purchaseCategoryComboBox.getStyleClass().add("not-selected");
            throw new Exception();
        }
        else{
            String purchaseCategoryString = purchaseCategoryComboBox.getSelectionModel().getSelectedItem();
            purchaseCategorySelection = PurchaseCategory.valueOf(purchaseCategoryString);
            this.purchaseCategoryComboBox.getStyleClass().remove("not-selected");
        }

        int selectedStores = getCheckedStores().size();
        for(StoreOptionController storeOption : getCheckedStores())
        {
            if(!storeOption.getStoreCheckBox().isSelected())
            {
                selectedStores --;
            }
            else
            {
                String storeId = storeOption.getStoreCheckBox().getText().split(":")[1].split(",")[0];
                storeId = storeId.trim();
                if(storeOption.getPriceTextField().getText().isEmpty() || !storeOption.getPriceTextField().getText().matches("[0-9]+"))
                {
                    storesMessageLabel.setText("Please Fill A Price In The Selected Stores");
                    storesMessageLabel.setTextFill(Color.RED);
                    storesMessageLabel.setVisible(true);
                    sellingStoresIdToPrice.clear();
                    throw new Exception();
                }
                else
                {
                    storesMessageLabel.setVisible(false);
                    sellingStoresIdToPrice.put(Integer.parseInt(storeId), Integer.parseInt(storeOption.getPriceTextField().getText()));
                }
            }
        }

        if(selectedStores == 0)
        {
            storesMessageLabel.setText("At Least One Store Must Be Selected");
            storesMessageLabel.setTextFill(Color.RED);
            storesMessageLabel.setVisible(true);
            sellingStoresIdToPrice.clear();
            throw new Exception();
        }
        else
        {
            storesMessageLabel.setVisible(false);
        }
    }

    public ComboBox<String> getPurchaseCategoryComboBox() {
        return purchaseCategoryComboBox;
    }

    public VBox getCheckStoresVbox() {
        return checkStoresVbox;
    }
}
