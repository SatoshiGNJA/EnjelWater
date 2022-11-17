package com.example.enjelwater.Listener;

import com.example.enjelwater.Model.RiderModel;

import java.util.List;

public interface IRiderLoadListener {
    void onRiderLoadSuccess(List<RiderModel> riderModelList);
    void onRiderLoadFailed(String message);
}
