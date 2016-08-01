package com.balinasoft.minimarket.networking.Result;

/**
 * Created by Microsoft on 27.07.2016.
 */
public class ResultOrderCourier extends ResultOrderManager {
    String courier_confirm,courier_finish;

    public String getCourier_confirm() {
        return courier_confirm;
    }

    public void setCourier_confirm(String courier_confirm) {
        this.courier_confirm = courier_confirm;
    }

    public String getCourier_finish() {
        return courier_finish;
    }

    public void setCourier_finish(String courier_finish) {
        this.courier_finish = courier_finish;
    }
}
