package users;

import sdm.enums.AccountAction;

public class SingleAccountAction {
    private AccountAction accountAction;
    private String actionDate;
    private Float actionAmount;
    private Float balanceAfterAction;
    private Float balanceBeforeAction;

    public SingleAccountAction(AccountAction accountAction, String actionDate, Float actionAmount, Float balanceAfterAction, Float balanceBeforeAction) {
        this.accountAction = accountAction;
        this.actionDate = actionDate;
        this.actionAmount = actionAmount;
        this.balanceAfterAction = balanceAfterAction;
        this.balanceBeforeAction = balanceBeforeAction;
    }

    public AccountAction getAccountAction() {
        return accountAction;
    }

    public String getActionDate() {
        return actionDate;
    }

    public Float getActionAmount() {
        return actionAmount;
    }

    public Float getBalanceAfterAction() {
        return balanceAfterAction;
    }

    public Float getBalanceBeforeAction() {
        return balanceBeforeAction;
    }
}
