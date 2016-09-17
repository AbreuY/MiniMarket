package com.balinasoft.mallione.networking.Response;

/**
 * Created by Anton on 11.07.2016.
 */
public class ResponseCountNotification extends BaseResponse {

    public Count getResult() {
        return result;
    }

    public void setResult(Count result) {
        this.result = result;
    }

    Count result;

    public static class Count{
        int count;

        public Count(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
