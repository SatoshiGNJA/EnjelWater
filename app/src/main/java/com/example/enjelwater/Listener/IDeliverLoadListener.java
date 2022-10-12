package com.example.enjelwater.Listener;

import com.example.enjelwater.Model.DeliverModel;

import java.util.List;

public interface IDeliverLoadListener {
    void onDeliverLoadSuccess(List<DeliverModel> deliverModelList);
    void onDeliverLoadFailed(String message);
}
