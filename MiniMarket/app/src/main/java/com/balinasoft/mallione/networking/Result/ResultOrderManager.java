package com.balinasoft.mallione.networking.Result;

/**
 * Created by Microsoft on 26.07.2016.
 */
public class    ResultOrderManager extends ResultOrderBuyer{
    String response_status_confirm,response_status_cancel;

    public String getManager_finish() {
        return manager_finish;
    }

    public void setManager_finish(String manager_finish) {
        this.manager_finish = manager_finish;
    }

    String manager_finish;

    public String getResponse_status_cancel() {
        return response_status_cancel;
    }

    public void setResponse_status_cancel(String response_status_cancel) {
        this.response_status_cancel = response_status_cancel;
    }

    public String getResponse_status_confirm() {
        return response_status_confirm;
    }

    public void setResponse_status_confirm(String response_status_confirm) {
        this.response_status_confirm = response_status_confirm;
    }
}
