package com.example.hw3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> data = new MutableLiveData<>("");

    public void incrementData(char a) {
        data.setValue(data.getValue() + a);
    }

    public LiveData<String> getData() {
        return data;
    }

    public void decrementData() {
        if (data.getValue() != null && data.getValue().length() > 0) {
            data.setValue(data.getValue().substring(0, data.getValue().length() - 1));
        }
    }
}
