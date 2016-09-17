package com.balinasoft.mallione.networking.Request;

/**
 * Created by Anton Kolotsey on 21.07.2016.
 */
public class SearchRequest extends PaginationRequestWithSession {
    public SearchRequest() {
        super();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public SearchRequest(String session_id, String user_id, String search) {
        super(session_id, user_id);

        this.search = search;
    }

    String search;
}
