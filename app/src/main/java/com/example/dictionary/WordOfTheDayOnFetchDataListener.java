package com.example.dictionary;

import com.example.dictionary.Models.WordOfTheDayAPIResponse;

public interface WordOfTheDayOnFetchDataListener {

    void onFetchData(WordOfTheDayAPIResponse apiResponse, String message);
    void onError(String message);

}
