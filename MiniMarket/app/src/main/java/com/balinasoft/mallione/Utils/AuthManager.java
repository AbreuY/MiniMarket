package com.balinasoft.mallione.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.balinasoft.mallione.Ui.Activities.BuerActivity;
import com.balinasoft.mallione.Ui.Activities.CourierActivity;
import com.balinasoft.mallione.Ui.Activities.DispatcherActivity;
import com.balinasoft.mallione.Ui.Activities.ManagerActivity;
import com.balinasoft.mallione.interfaces.UsersListener;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.City;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.Dispatcher;
import com.balinasoft.mallione.models.modelUsers.Manager;
import com.balinasoft.mallione.models.modelUsers.User;

public class AuthManager {

    public interface ExtractListener extends UsersListener{
        void onEmpty();
    }

    public interface RetentiveAdapter {
       User getUser();
    }

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ExtractListener extractListener;
    private RetentiveAdapter retentiveAdapter;

    public AuthManager(Context context) {
        sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public AuthManager setExtractListener(ExtractListener extractListener) {
        this.extractListener = extractListener;
        return this;
    }

    public AuthManager setAdapter(RetentiveAdapter retentiveAdapter) {
        this.retentiveAdapter = retentiveAdapter;
        return this;
    }

    public AuthManager save() {
        if (retentiveAdapter.getUser() != null) {
            if(retentiveAdapter.getUser().getClass()==Buer.class )
                saveBuyer((Buer) retentiveAdapter.getUser());
            if(retentiveAdapter.getUser().getClass()== Courier.class)
                saveCourier((Courier) retentiveAdapter.getUser());
            if(retentiveAdapter.getUser().getClass()== Manager.class)
                saveManager((Manager) retentiveAdapter.getUser());
            if(retentiveAdapter.getUser().getClass()==Dispatcher.class){
                saveDispatcher((Dispatcher)retentiveAdapter.getUser());
            }
        }
        editor.commit();
        return this;
    }


    public AuthManager logout(){
        editor.clear();
        editor.commit();
        return this;
    }

    public AuthManager extract() {
        switch (sharedPreferences.getInt("group_id",0)){
            case Buer.GROUP_ID:
                extractListener.onBuer(extractBuer());
                break;
            case Courier.GROUP_ID:
                extractListener.onCourier(extractCourier());
                break;
            case Manager.GROUP_ID:
                extractListener.onManager(extractManager());
                break;
            case Dispatcher.GROUP_ID:
                extractListener.onDispatcher(extractDispatcher());
            case 0: extractListener.onEmpty();
                break;
        }
        return this;
    }


    private void saveManager(Manager manager) {
        saveUser(manager);
    }

    private void saveCourier(Courier courier) {
        editor.putString("c_driver_license", courier.getC_driver_license());
        editor.putString("c_car_brand", courier.getC_car_brand());
        editor.putString("c_car_number", courier.getC_car_number());
        editor.putString("c_car_color", courier.getC_car_color());
        editor.putString("c_status", courier.getC_status());
        editor.putFloat("c_rating", courier.getC_rating());
        saveUser(courier);
    }

    private void saveBuyer(Buer buer) {
        editor.putString("u_address_1", buer.getU_address_1());
        editor.putString("u_addres_2", buer.getU_address_2());
        saveUser(buer);
    }
    private void saveDispatcher(Dispatcher dispatcher) {
        editor.putString("o_organization",dispatcher.getO_organization());
        editor.putString("o_inn",dispatcher.getO_inn());
        editor.putString("o_address",dispatcher.getO_address());
        saveUser(dispatcher);
    }

    private void saveUser(User user) {
        editor.putString("fio", user.getFio());
        editor.putString("phone", user.getPhone());
        editor.putString("email", user.getEmail());
        editor.putInt("id", user.getId());
        editor.putInt("group_id", user.getGroup_id());
        editor.putString("session_id", user.getSession_id());
        saveCity(user.getCity());
    }

    private void saveCity(City city) {
        editor.putInt("city_id", city.getCity_id());
        editor.putString("city", city.getCity());
    }

    private Buer extractBuer() {
        Buer buer = new Buer();
        extractFieldsUser(buer);
        buer.setU_address_1(sharedPreferences.getString("u_address_1", ""));
        buer.setU_address_2(sharedPreferences.getString("u_addres_2", ""));
        return buer;
    }

    private void extractCity(User user){
        City city=user.getCity();
        if(city ==null)
            city=new City();
        city.setCity_id(sharedPreferences.getInt("city_id", 0));
        city.setCity(sharedPreferences.getString("city",""));
        user.setCity(city);
    }

    private Courier extractCourier() {
        Courier courier = new Courier();
        extractFieldsUser(courier);
        courier.setC_driver_license(sharedPreferences.getString("c_driver_license", ""));
        courier.setC_car_brand(sharedPreferences.getString("c_car_brand", ""));
        courier.setC_car_number(sharedPreferences.getString("c_car_number", ""));
        courier.setC_car_color(sharedPreferences.getString("c_car_color", ""));
        courier.setC_status(sharedPreferences.getString("c_status", ""));
        courier.setC_rating(sharedPreferences.getFloat("c_rating", 0));
        return courier;
    }

    private Manager extractManager() {
        Manager manager = new Manager();
        extractFieldsUser(manager);
        manager.setC_status(sharedPreferences.getString("c_status", ""));
        return manager;
    }

    private Dispatcher extractDispatcher() {
        Dispatcher dispatcher=new Dispatcher();
        extractFieldsUser(dispatcher);
        dispatcher.setO_organization(sharedPreferences.getString("o_organization",""));
        dispatcher.setO_inn(sharedPreferences.getString("o_inn",""));
        dispatcher.setO_address(sharedPreferences.getString("o_address",""));
        return dispatcher;
    }

    private void extractFieldsUser(User user) {
        user.setFio(sharedPreferences.getString("fio", ""));
        user.setPhone(sharedPreferences.getString("phone", ""));
        user.setEmail(sharedPreferences.getString("email", ""));
        user.setId(sharedPreferences.getInt("id", 0));
        user.setGroup_id(sharedPreferences.getInt("group_id", 0));
        user.setSession_id(sharedPreferences.getString("session_id", ""));
        extractCity(user);
    }

    public static abstract  class SimpleExtractListener implements ExtractListener{

        @Override
        public void onManager(Manager manager) {
            onUser(manager);
        }

        @Override
        public void onCourier(Courier courier) {
            onUser(courier);
        }

        @Override
        public void onBuer(Buer buer) {
            onUser(buer);
        }

        @Override
        public void onDispatcher(Dispatcher dispatcher) {
            onUser(dispatcher);
        }

        abstract public void onEmpty();

        abstract public void onUser(User user);

    }

    public static abstract class StartActivityExtractUser implements ExtractListener{
        Context context;
        public StartActivityExtractUser(Context context){
            this.context=context;
        }
        @Override
        public void onBuer(Buer buer) {
            Intent intent = new Intent(context, BuerActivity.class);
            intent.putExtra(Buer.class.getCanonicalName(),buer);
            startActivity(intent);
        }

        @Override
        public void onManager(Manager manager) {
            Intent intent=new Intent(context, ManagerActivity.class);
            intent.putExtra(Manager.class.getCanonicalName(),manager);
            startActivity(intent);
        }

        @Override
        public void onCourier(Courier courier) {
            Intent intent=new Intent(context, CourierActivity.class);
            startActivity(intent);
        }

        @Override
        public void onDispatcher(Dispatcher dispatcher) {
            Intent intent=new Intent(context, DispatcherActivity.class);
            startActivity(intent);
        }

        private void startActivity(Intent intent) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }


       abstract public void onEmpty();
    }
}
