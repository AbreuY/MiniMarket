package com.balinasoft.minimarket.networking;

import com.balinasoft.minimarket.models.FormModel.LoginForm;
import com.balinasoft.minimarket.models.FormModel.RegistrationForm;
import com.balinasoft.minimarket.models.FormModel.UpdateUserDataForm;
import com.balinasoft.minimarket.networking.Request.PaginationRequest;
import com.balinasoft.minimarket.networking.Request.RequestAddRecord;
import com.balinasoft.minimarket.networking.Request.RequestAssess;
import com.balinasoft.minimarket.networking.Request.RequestCloseDispute;
import com.balinasoft.minimarket.networking.Request.RequestComment;
import com.balinasoft.minimarket.networking.Request.RequestCountNotification;
import com.balinasoft.minimarket.networking.Request.RequestCourierStatus;
import com.balinasoft.minimarket.networking.Request.RequestCouriers;
import com.balinasoft.minimarket.networking.Request.RequestFullDispute;
import com.balinasoft.minimarket.networking.Request.RequestItem;
import com.balinasoft.minimarket.networking.Request.RequestItemTime;
import com.balinasoft.minimarket.networking.Request.RequestItems;
import com.balinasoft.minimarket.networking.Request.RequestLogout;
import com.balinasoft.minimarket.networking.Request.RequestOrder;
import com.balinasoft.minimarket.networking.Request.RequestOrderCourier;
import com.balinasoft.minimarket.networking.Request.RequestOrderDispatcher;
import com.balinasoft.minimarket.networking.Request.RequestOrderManager;
import com.balinasoft.minimarket.networking.Request.RequestRecord;
import com.balinasoft.minimarket.networking.Request.RequestReviews;
import com.balinasoft.minimarket.networking.Request.RequestShops;
import com.balinasoft.minimarket.networking.Request.RequestUserData;
import com.balinasoft.minimarket.networking.Request.RequestUser;
import com.balinasoft.minimarket.networking.Request.SearchRequest;
import com.balinasoft.minimarket.networking.Response.BaseResponse;
import com.balinasoft.minimarket.networking.Response.CouriersDispatcherResponse;
import com.balinasoft.minimarket.networking.Response.ResponseAnswer;
import com.balinasoft.minimarket.networking.Response.ResponseAthorization;
import com.balinasoft.minimarket.networking.Response.ResponseBasketItems;
import com.balinasoft.minimarket.networking.Response.ResponseBlurb;
import com.balinasoft.minimarket.networking.Response.ResponseCategories;
import com.balinasoft.minimarket.networking.Response.ResponseCities;
import com.balinasoft.minimarket.networking.Response.ResponseComments;
import com.balinasoft.minimarket.networking.Response.ResponseCountNotification;
import com.balinasoft.minimarket.networking.Response.ResponseCourier;
import com.balinasoft.minimarket.networking.Response.ResponseDispatchers;
import com.balinasoft.minimarket.networking.Response.ResponseDisput;
import com.balinasoft.minimarket.networking.Response.ResponseDisputes;
import com.balinasoft.minimarket.networking.Response.ResponseGroupUser;
import com.balinasoft.minimarket.networking.Response.ResponseItem;
import com.balinasoft.minimarket.networking.Response.ResponseItemTime;
import com.balinasoft.minimarket.networking.Response.ResponseItems;
import com.balinasoft.minimarket.networking.Response.ResponseNotification;
import com.balinasoft.minimarket.networking.Response.ResponseOrderBuyer;
import com.balinasoft.minimarket.networking.Response.ResponseOrderCourier;
import com.balinasoft.minimarket.networking.Response.ResponseOrderManger;
import com.balinasoft.minimarket.networking.Response.ResponseOrders;
import com.balinasoft.minimarket.networking.Response.ResponseRecords;
import com.balinasoft.minimarket.networking.Response.ResponseRegistration;
import com.balinasoft.minimarket.networking.Response.ResponseSearch;
import com.balinasoft.minimarket.networking.Response.ResponseShop;
import com.balinasoft.minimarket.networking.Response.ResponseShops;
import com.balinasoft.minimarket.networking.Response.ResponseSuppliers;
import com.balinasoft.minimarket.networking.Response.ResposeReviewsShopForBuyer;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Microsoft on 30.05.2016.
 */
public interface API {
    public static final String BASE_URL = "http://balinasoft.com/minimarket/index.php/Api/";

    @Multipart
    @POST("Login")
    Call<ResponseAthorization> login(@Part("login") LoginForm login);

    @Multipart
    @POST("Registration")
    Call<ResponseRegistration> registration(@Part("registration") RegistrationForm form);

    @Multipart
    @POST("UpdateUserData")
    Call<ResponseAthorization> updateUserData(@Part("update_user_data") UpdateUserDataForm registrationForm);

    @Multipart
    @POST("GetItem")
    Call<ResponseItem> item(@Part("get_item") HashMap<String, String> hashMap);
    @Multipart
    @POST("GetCategories")
    Call<ResponseCategories> catigories(@Part("get_categories")RequestUser requetsUser);

    @POST("GetCategories")
    Call<ResponseCategories> catigories();

    @Multipart
    @POST("GetItems")
    Call<ResponseItems> items(@Part("get_items") RequestItems requestItems);

    @Multipart
    @POST("GetItems")
    Call<ResponseBasketItems> itemsBasket(@Part("get_items") RequestItems requestItems);

    @Multipart
    @POST("GetComments")
    Call<ResponseComments> comments(@Part("get_comments") RequestComment requestComment);

    @Multipart
    @POST("GetItem")
    Call<ResponseItem> item(@Part("get_item") RequestItem requestItem);

    @Multipart
    @POST("GetShops")
    Call<ResponseShops> shops(@Part("get_shops") RequestShops requestShops);

    @POST("GetCities")
    Call<ResponseCities> cities();

    @POST("GetGroups")
    Call<ResponseGroupUser> groupsUser();

    @POST("GetSuppliers")
    Call<ResponseSuppliers> getSuppliers();

    @POST("GetDispatchers")
    Call<ResponseDispatchers> dispatchers();

    @Multipart
    @POST("Order")
    Call<ResponseAnswer> order(@Part("order") RequestOrder requestOrder);

    @Multipart
    @POST("GetCountNotifications")
    Call<ResponseCountNotification> countNotification(@Part("get_count_notifications") RequestCountNotification countNotification);

    @Multipart
    @POST("GetNotifications")
    Call<ResponseNotification> notifications(@Part("get_notifications") RequestUserData requestUserData);

    @Multipart
    @POST("GetItemTime")
    Call<ResponseItemTime> itemTime(@Part("get_item_time") RequestItemTime itemTime);

    @Multipart
    @POST("AddRecord")
    Call<ResponseAnswer> addRecord(@Part("add_record") RequestAddRecord request);

    @Multipart
    @POST("GetRecords")
    Call<ResponseRecords> records(@Part("get_records") RequestUserData requestUserData);

    @Multipart
    @POST("GetOrders")
    Call<ResponseOrders> orders(@Part("get_orders") RequestUserData requestUserData);

    @Multipart
    @POST("AddReview")
    Call<ResponseAnswer> addReview(@Part("add_review") RequestAssess requestAssess);

    @Multipart
    @POST("GetOrder")
    Call<ResponseOrderBuyer> getOrderBuyer(@Part("get_order") RequestRecord requestRecord);


    @Multipart
    @POST("GetReviews")
    Call<ResposeReviewsShopForBuyer> reviewForBuyer(@Part("get_reviews") RequestReviews requestReview);

    @Multipart
    @POST("GetShop")
    Call<ResponseShop> shop(@Part("get_shop") HashMap<String, String> hashMap);

    @Multipart
    @POST("GetAds")
    Call<ResponseBlurb> blurb(@Part("get_ads")PaginationRequest paginationRequest);

    @Multipart
    @POST("SearchItems")
    Call<ResponseSearch> search(@Part("search_items") SearchRequest searchRequest);
    @POST("SearchItems")
    Call<ResponseSearch> search();
    @Multipart
    @POST("Logout")
    Call<BaseResponse> logout(@Part("logout") RequestLogout requestLogout);

    @Multipart
    @POST("GetDisputes")
    Call<ResponseDisputes> disputs(@Part("get_disputes") RequestUserData userData);


    @Multipart
    @POST("GetDispute")
    Call<ResponseDisput> disput(@Part("get_dispute") RequestFullDispute requestFullDispute);

    @Multipart
    @POST("CloseDispute")
    Call<ResponseAnswer> closeDisput(@Part("close_dispute") RequestCloseDispute requestFullDispute);
    @Multipart
    @POST("GetOrder")
    Call<ResponseOrderManger> orderManager(@Part("get_order") RequestRecord requestRecord);
    @Multipart
    @POST("GetOrder")
    Call<ResponseOrderCourier> orderCourier(@Part("get_order") RequestRecord requestRecord);
    @Multipart
    @POST("OrderManagerResponse")
    Call<ResponseAnswer> orderManagerResponse(@Part("order_manager_response") RequestOrderManager requestOrderManager);
    @Multipart
    @POST("OrderCourierResponse")
    Call<ResponseAnswer> orderCourierResponse(@Part("order_courier_response")RequestOrderCourier requestOrderCourier);
    @Multipart
    @POST("GetDispatchers")
    Call<ResponseDispatchers> dispatcher(@Part("get_dispatchers")HashMap<String,String> hashMap);

    @Multipart
    @POST("GetCouriers")
    Call<ResponseCourier> couriers(@Part("get_couriers")RequestCouriers requestCouriers);
    @Multipart
    @POST("SetCourierStatus")
    Call<BaseResponse> setCourierStatus(@Part("set_courier_status")RequestCourierStatus requestStatus);
    @POST("OpenDispute")
    Call<ResponseAnswer> openDispute(@Body MultipartBody filePart);

    @Multipart
    @POST("OrderCourierConfirm")
    Call<ResponseAnswer> orderCourierConfrim(@Part("order_courier_confirm")RequestOrderCourier requestOrderCourier);

    @Multipart
    @POST("OrderCourierFinish")
    Call<BaseResponse> orderCourierFinish(@Part("order_courier_finish")RequestOrderCourier requestOrderCourier);
    @Multipart
    @POST("CouriersDispatcher")
    Call<CouriersDispatcherResponse> couriersDispatcher(@Part("couriers")RequestUserData requestUserData);
    @Multipart
    @POST("OrderDispatcherResponse")
    Call<ResponseAnswer> orderDispatcherResponse(@Part("order_dispatcher_response")RequestOrderDispatcher requestOrderDispatcher);
    @Multipart
    @POST("PasswordRecovery")
    Call<ResponseAnswer> forgotPassword(@Part("password_recovery")HashMap<String, String> hashMap);
}

