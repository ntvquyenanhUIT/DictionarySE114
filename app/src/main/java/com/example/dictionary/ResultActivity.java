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

public class ResultActivity extends AppCompatActivity {


    SearchView search_view;
    TextView textView_word;

    RecyclerView recycler_phonetics, recycler_meanings;

    PhoneticAdapter phoneticAdapter;
    MeaningAdapter meaningAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView_word = findViewById(R.id.textView_word);
        recycler_phonetics = findViewById(R.id.recycler_phonetics);
        recycler_meanings = findViewById(R.id.recycler_meanings);
        search_view = findViewById(R.id.result_search_view);

        //Show word of the first search
        Intent intent = getIntent();
        APIResponse apiResponse = (APIResponse) intent.getSerializableExtra("data");
        showData(apiResponse);


        // Show word of the next searches
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager manager = new RequestManager(ResultActivity.this);
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
                Toast.makeText(ResultActivity.this, "No such word found!", Toast.LENGTH_SHORT).show();
                return;
            }
          showData(apiResponse);

        }

        @Override
        public void onError(String message) {
            Toast.makeText(ResultActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(APIResponse apiResponse) {
        textView_word.setText("Word: " + apiResponse.getWord());
        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(this, 1));
        phoneticAdapter = new PhoneticAdapter(ResultActivity.this, apiResponse.getPhonetics());
        recycler_phonetics.setAdapter(phoneticAdapter);

        recycler_meanings.setHasFixedSize(true);
        recycler_meanings.setLayoutManager(new GridLayoutManager(this, 1));
        meaningAdapter = new MeaningAdapter(ResultActivity.this, apiResponse.getMeanings());
        recycler_meanings.setAdapter(meaningAdapter);
    }
}