package com.balinasoft.minimarket.interfaces;

import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;

public interface Transaction{
        void put(SuperProductItem productItem,String idShop);
        void delete(SuperProductItem productItem,String idShop);

    }