package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.Adapter.AutoSuggestAdapter;
import com.example.dictionary.Adapter.MeaningAdapter;
import com.example.dictionary.Adapter.PhoneticAdapter;
import com.example.dictionary.Models.APIResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemOnclick {



    MaterialButton translateNavigateBtn;

    AutoCompleteTextView ACTV;

    AutoSuggestAdapter adapter;
    List<String> suggestions = new ArrayList<>();


//    TextView textView_word;
//
//    RecyclerView recycler_phonetics, recycler_meanings;

//    PhoneticAdapter phoneticAdapter;
//    MeaningAdapter meaningAdapter;

    // CardView: choose to show all favorite words
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


        suggestions= dataBaseHelper.getSuggestWords("");

        ACTV = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ACTV.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getWordMeaning(listener, ACTV.getText().toString());
                    dataBaseHelper.insertSuggestWord(ACTV.getText().toString());
                    return true;
                }
                return false;
            }
        });

        adapter = new AutoSuggestAdapter(this, R.layout.item_layout, suggestions,dataBaseHelper,this);
        ACTV.setAdapter(adapter);
        ACTV.setThreshold(1);

        translateNavigateBtn = findViewById(R.id.TranslateAcitivityBtn);
        translateNavigateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TranslateActivity.class);
            startActivity(intent);
        });
//        textView_word = findViewById(R.id.textView_word);
//        recycler_phonetics = findViewById(R.id.recycler_phonetics);
//        recycler_meanings = findViewById(R.id.recycler_meanings);

        cardView = findViewById(R.id.card_view_favourites);

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoriteWordsActivity.class);
            startActivity(intent);
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            if(apiResponse == null){
                Toast.makeText(MainActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                return;
            }
//            showData(apiResponse);
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("data", apiResponse);
            startActivity(intent);
        }

        @Override
        public void onError(String message) {
               Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void ItemOnClicl(String value) {
        ACTV.setText(value);
        RequestManager manager = new RequestManager(MainActivity.this);
        manager.getWordMeaning(listener, value);
    }

    @Override
    public void DeleteItem(String value) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.deleteSuggestWord(value);
        adapter= new AutoSuggestAdapter(this, R.layout.item_layout, dataBaseHelper.getSuggestWords(""),dataBaseHelper,this);
        ACTV.setAdapter(adapter);
    }

//    private void showData(APIResponse apiResponse) {
//        textView_word.setText("Word: " + apiResponse.getWord());
//        recycler_phonetics.setHasFixedSize(true);
//        recycler_phonetics.setLayoutManager(new GridLayoutManager(this, 1));
//        phoneticAdapter = new PhoneticAdapter(MainActivity.this, apiResponse.getPhonetics());
//        recycler_phonetics.setAdapter(phoneticAdapter);
//
//        recycler_meanings.setHasFixedSize(true);
//        recycler_meanings.setLayoutManager(new GridLayoutManager(this, 1));
//        meaningAdapter = new MeaningAdapter(MainActivity.this, apiResponse.getMeanings());
//        recycler_meanings.setAdapter(meaningAdapter);
//    }

}