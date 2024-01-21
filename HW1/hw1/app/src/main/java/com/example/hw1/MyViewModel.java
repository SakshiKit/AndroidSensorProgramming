package com.example.hw1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Integer> data = new MutableLiveData<>(0);

    public void incrementData() {
        Integer currentValue = data.getValue();
        if (currentValue != null) {
            data.setValue(currentValue + 1);
        }
    }

    public LiveData<Integer> getData() {
        return data;
    }
}
