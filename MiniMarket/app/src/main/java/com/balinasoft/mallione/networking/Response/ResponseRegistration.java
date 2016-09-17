package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.networking.Result.AnswerResult;

/**
 * Created by Microsoft on 30.05.2016.
 */
public class ResponseRegistration extends BaseResponse {
    public AnswerResult getResult() {
        return result;
    }

    public void setResult(AnswerResult result) {
        this.result = result;
    }

    private AnswerResult result;
}
