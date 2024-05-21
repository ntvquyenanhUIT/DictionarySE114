package com.example.dictionary.Models;

import java.io.Serializable;
import java.util.List;

public class WordOfTheDayAPIResponse implements Serializable {

    private List<WordOfTheDay> list;

    public List<WordOfTheDay> getList() {
        return list;
    }

    public void setList(List<WordOfTheDay> list) {
        this.list = list;
    }
}
