package com.example.enjelwater.Listener;

import com.example.enjelwater.Model.HistoryModel;

import java.util.List;

public interface IHistoryLoadListener {
    void onHistoryLoadSuccess(List<HistoryModel> historyModelList);
    void onHistoryLoadFailed(String message);
}
