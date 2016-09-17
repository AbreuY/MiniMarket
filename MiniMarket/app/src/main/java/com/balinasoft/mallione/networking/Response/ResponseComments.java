package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.Comments.Comment;

import java.util.ArrayList;

/**
 * Created by Microsoft on 02.07.2016.
 */
public class ResponseComments extends BaseResponse {
    public ArrayList<Comment> getResult() {
        return result;
    }

    public void setResult(ArrayList<Comment> result) {
        this.result = result;
    }

    ArrayList<Comment> result;
}
