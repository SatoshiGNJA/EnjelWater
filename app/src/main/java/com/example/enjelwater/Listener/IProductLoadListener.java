package com.example.enjelwater.Listener;

import com.example.enjelwater.Model.ProductModel;

import java.util.List;

public interface IProductLoadListener {
    void onProductLoadSuccess(List<ProductModel> productModelList);
    void onProductLoadFailed(String message);
}
