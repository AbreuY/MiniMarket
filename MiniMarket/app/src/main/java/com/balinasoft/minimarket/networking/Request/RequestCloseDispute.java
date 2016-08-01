package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class RequestCloseDispute extends RequestFullDispute{
    public RequestCloseDispute(String dispute_id, String session_id, String user_id, String result) {
        super(dispute_id, session_id, user_id);
        this.result = result;
    }

    String result;

    public RequestCloseDispute(String dispute_id, String session_id, String user_id) {
        super(dispute_id, session_id, user_id);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
