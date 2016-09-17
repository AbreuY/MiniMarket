package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.networking.Result.AnswerResult;

/**
 * Created by Microsoft on 08.07.2016.
 */
public class ResponseAnswer extends BaseResponse {
    public AnswerResult getResult() {
        return result;
    }

    public void setResult(AnswerResult result) {
        this.result = result;
    }

    AnswerResult result;
}
