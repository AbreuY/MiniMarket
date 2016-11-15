package com.balinasoft.mallione.networking.Request;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class RequestItem{
    private String user_id;
    private String item_id;
    private String session_id;

    public RequestItem(String user_id, String item_id, String session_id) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.session_id = session_id;
    }

    public RequestItem() {
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}

//public class RequestItem extends PaginationRequest{
//   private String user_id;
//   private String item_id;
//
//    public RequestItem(String user_id, String item_id,String offset,String limit) {
//        super(offset,limit);
//        this.user_id = user_id;
//        this.item_id = item_id;
//
//    }
//
//    public RequestItem(String offset, String limit) {
//        super(offset, limit);
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getItem_id() {
//        return item_id;
//    }
//
//    public void setItem_id(String item_id) {
//        this.item_id = item_id;
//    }
//}
