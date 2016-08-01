package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.Comments.Comment;

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
