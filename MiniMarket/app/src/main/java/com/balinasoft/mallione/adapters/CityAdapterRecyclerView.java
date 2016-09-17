package com.balinasoft.mallione.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.models.modelUsers.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 14.06.2016.
 */
public class CityAdapterRecyclerView extends RecyclerView.Adapter<CityAdapterRecyclerView.CityViewHolder>{
    List<City> cities;
    Context context;

    public OnItemClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    OnItemClickListener clickListener=new OnItemClickListener() {
        @Override
        public void onClickItem(City city) {

        }
    };
    public CityAdapterRecyclerView(List<City> cities, Context context) {
        this.cities = cities;
        this.context = context;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);

        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        final City city=cities.get(position);
        holder.tvNameCity.setText(city.getCity());
        holder.tvNameCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClickItem(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public List<City> getCities() {
        return cities;
    }
    public void addCity(City city){
        cities.add(city);
        notifyDataSetChanged();
    }
    public void addCitites(ArrayList<City> cities){
      this.cities.addAll(cities);
        notifyDataSetChanged();
    }
    public void setCities(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameCity;
        public CityViewHolder(View itemView) {
            super(itemView.getRootView());
            tvNameCity=(TextView) itemView.findViewById(R.id.itemCity_tvNameCity);
        }
    }
    public interface OnItemClickListener{
        void onClickItem(City city);
    }
}
