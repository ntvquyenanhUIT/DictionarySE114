package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.Adapter.MeaningAdapter;
import com.example.dictionary.Adapter.PhoneticAdapter;
import com.example.dictionary.Models.APIResponse;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    SearchView search_view;
    MaterialButton translateNavigateBtn;
//    TextView textView_word;
//
//    RecyclerView recycler_phonetics, recycler_meanings;

//    PhoneticAdapter phoneticAdapter;
//    MeaningAdapter meaningAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_view = findViewById(R.id.search_view);
        translateNavigateBtn = findViewById(R.id.TranslateAcitivityBtn);
        translateNavigateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TranslateActivity.class);
            startActivity(intent);
        });
//        textView_word = findViewById(R.id.textView_word);
//        recycler_phonetics = findViewById(R.id.recycler_phonetics);
//        recycler_meanings = findViewById(R.id.recycler_meanings);


        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getWordMeaning(listener, query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
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