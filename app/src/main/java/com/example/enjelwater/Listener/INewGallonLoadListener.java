package com.example.enjelwater.Listener;

import com.example.enjelwater.Model.NewGallon;

import java.util.List;

public interface INewGallonLoadListener {
    void onNewGallonLoadSuccess(List<NewGallon> newGallonList);
    void onNewGallonLoadFailed(String message);
}
