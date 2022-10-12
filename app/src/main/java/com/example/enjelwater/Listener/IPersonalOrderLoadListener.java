package com.example.enjelwater.Listener;

import com.example.enjelwater.Model.PersonalOrderModel;

import java.util.List;

public interface IPersonalOrderLoadListener {
    void onPersonalOrderLoadSuccess(List<PersonalOrderModel> personalOrderModelList);
    void onPersonalOrderLoadFailed(String message);
}
