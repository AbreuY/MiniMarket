package com.balinasoft.minimarket.models.Menu;

/**
 * Created by Microsoft on 30.06.2016.
 */
public class ItemMenu {
    private int id;
    String name;
    boolean press;
    int amountNotification;

    public ItemMenu(int id, String name, int amountNotifications) {
        this.id=id;
        this.name=name;
        this.amountNotification=amountNotifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPress() {
        return press;
    }

    public void setPress(boolean press) {
        this.press = press;
    }

    public int getAmountNotification() {
        return amountNotification;
    }

    public void setAmountNotification(int amountNotification) {
        this.amountNotification = amountNotification;
    }
}
