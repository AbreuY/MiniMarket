package com.balinasoft.mallione.interfaces;

import com.balinasoft.mallione.models.ProductItems.SuperProductItem;

public interface Transaction{
        void put(SuperProductItem productItem,String idShop);
        void delete(SuperProductItem productItem,String idShop);

    }