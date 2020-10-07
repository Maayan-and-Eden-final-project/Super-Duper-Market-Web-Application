package systemInfoContainers.webContainers;

import users.SingleAccountAction;

import java.util.List;

public class AccountActionsContainer {
    private List<SingleAccountAction> actions;
    private float balance;

    public AccountActionsContainer(List<SingleAccountAction> actions, float balance) {
        this.actions = actions;
        this.balance = balance;
    }
}
