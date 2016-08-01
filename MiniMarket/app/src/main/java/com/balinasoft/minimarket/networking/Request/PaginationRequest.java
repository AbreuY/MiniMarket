package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class PaginationRequest {
    private int offset;
    private int limit;

    public PaginationRequest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void incrementPage(){
        setOffset(getOffset()+getLimit());
    }
}
