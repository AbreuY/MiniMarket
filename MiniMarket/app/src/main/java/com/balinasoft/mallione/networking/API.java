package com.balinasoft.mallione.networking;

import com.balinasoft.mallione.models.FormModel.LoginForm;
import com.balinasoft.mallione.models.FormModel.RegistrationForm;
import com.balinasoft.mallione.models.FormModel.UpdateUserDataForm;
import com.balinasoft.mallione.networking.Request.PaginationRequest;
import com.balinasoft.mallione.networking.Request.RequestAddRecord;
import com.balinasoft.mallione.networking.Request.RequestAssess;
import com.balinasoft.mallione.networking.Request.RequestCloseDispute;
import com.balinasoft.mallione.networking.Request.RequestComment;
import com.balinasoft.mallione.networking.Request.RequestConfirmPayment;
import com.balinasoft.mallione.networking.Request.RequestCountNotification;
import com.balinasoft.mallione.networking.Request.RequestCourierStatus;
import com.balinasoft.mallione.networking.Request.RequestCouriers;
import com.balinasoft.mallione.networking.Request.RequestDeleteNotification;
import com.balinasoft.mallione.networking.Request.RequestFullDispute;
import com.balinasoft.mallione.networking.Request.RequestItem;
import com.balinasoft.mallione.networking.Request.RequestItemTime;
import com.balinasoft.mallione.networking.Request.RequestItems;
import com.balinasoft.mallione.networking.Request.RequestLogout;
import com.balinasoft.mallione.networking.Request.RequestOrder;
import com.balinasoft.mallione.networking.Request.RequestOrderCourier;
import com.balinasoft.mallione.networking.Request.RequestOrderDispatcher;
import com.balinasoft.mallione.networking.Request.RequestOrderManager;
import com.balinasoft.mallione.networking.Request.RequestRecord;
import com.balinasoft.mallione.networking.Request.RequestReviews;
import com.balinasoft.mallione.networking.Request.RequestShops;
import com.balinasoft.mallione.networking.Request.RequestUser;
import com.balinasoft.mallione.networking.Request.RequestUserData;
import com.balinasoft.mallione.networking.Request.SearchRequest;
import com.balinasoft.mallione.networking.Response.BaseResponse;
import com.balinasoft.mallione.networking.Response.CouriersDispatcherResponse;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;
import com.balinasoft.mallione.networking.Response.ResponseAthorization;
import com.balinasoft.mallione.networking.Response.ResponseBasketItems;
import com.balinasoft.mallione.networking.Response.ResponseBlurb;
import com.balinasoft.mallione.networking.Response.ResponseCategories;
import com.balinasoft.mallione.networking.Response.ResponseCities;
import com.balinasoft.mallione.networking.Response.ResponseComments;
import com.balinasoft.mallione.networking.Response.ResponseCountNotification;
import com.balinasoft.mallione.networking.Response.ResponseCourier;
import com.balinasoft.mallione.networking.Response.ResponseDispatchers;
import com.balinasoft.mallione.networking.Response.ResponseDisput;
import com.balinasoft.mallione.networking.Response.ResponseDisputes;
import com.balinasoft.mallione.networking.Response.ResponseGroupUser;
import com.balinasoft.mallione.networking.Response.ResponseItem;
import com.balinasoft.mallione.networking.Response.ResponseItemTime;
import com.balinasoft.mallione.networking.Response.ResponseItems;
import com.balinasoft.mallione.networking.Response.ResponseNotification;
import com.balinasoft.mallione.networking.Response.ResponseOrderBuyer;
import com.balinasoft.mallione.networking.Response.ResponseOrderCourier;
import com.balinasoft.mallione.networking.Response.ResponseOrderManger;
import com.balinasoft.mallione.networking.Response.ResponseOrders;
import com.balinasoft.mallione.networking.Response.ResponseRecords;
import com.balinasoft.mallione.networking.Response.ResponseRegistration;
import com.balinasoft.mallione.networking.Response.ResponseSearch;
import com.balinasoft.mallione.networking.Response.ResponseShop;
import com.balinasoft.mallione.networking.Response.ResponseShops;
import com.balinasoft.mallione.networking.Response.ResponseSuppliers;
import com.balinasoft.mallione.networking.Response.ResposeReviewsShopForBuyer;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Microsoft on 30.05.2016.
 */
public interface API {
//    String BASE_URL = "http://b2b-mallione.ru/index.php/Api/";
    String BASE_URL = "http://balinasoft.com/minimarket/index.php/Api/";

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

    @POST("AddReview")
    Call<ResponseAnswer> addReview(@Body MultipartBody filePart);

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
    @POST("OrderManagerResponse")
    Call<ResponseBody> orderManagerResponse2(@Part("order_manager_response") RequestOrderManager requestOrderManager);

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
    @POST("SetStatus")
    Call<BaseResponse> setCourierStatus(@Part("set_status")RequestCourierStatus requestStatus);

    @Multipart
    @POST("DeleteNotification")
    Call<BaseResponse> deleteNotification(@Part("delete_notification")RequestDeleteNotification requestDeleteNotification);

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
    @Multipart
    @POST("ConfirmPayment")
    Call<ResponseAnswer> confirmPayment(@Part("confirm_payment")RequestConfirmPayment request);
}

