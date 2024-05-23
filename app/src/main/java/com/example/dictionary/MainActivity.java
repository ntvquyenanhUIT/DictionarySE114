package com.example.dictionary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;

import com.example.dictionary.Adapter.AutoSuggestAdapter;
import com.example.dictionary.Models.APIResponse;
import com.example.dictionary.Models.WordOfTheDay;
import com.example.dictionary.Models.WordOfTheDayAPIResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemOnclick {



    static int CheckIfAPIFetched = 0;
    MaterialButton translateNavigateBtn, WOTDNavigateBtn, FavoriteWordBtn;

    LinearLayout clickableLayout;

    TextView dayTime_TV, word_TV;
    ImageButton micBtn;

    WordOfTheDay todayWord;
    AutoCompleteTextView ACTV;

    AutoSuggestAdapter adapter;
    List<String> suggestions = new ArrayList<>();


//    TextView textView_word;
//
//    RecyclerView recycler_phonetics, recycler_meanings;

//    PhoneticAdapter phoneticAdapter;
//    MeaningAdapter meaningAdapter;

    // CardView: choose to show all favorite words


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dayTime_TV = findViewById(R.id.dateTime_TV);
        clickableLayout = findViewById(R.id.clickable_layout);
        word_TV = findViewById(R.id.wod_TV);
        suggestions= dataBaseHelper.getSuggestWords("");
        WOTDNavigateBtn = findViewById(R.id.WordOfTheDayActivityBtn);
        RequestManager manager = new RequestManager(MainActivity.this);

        //Nếu chưa fetch thì mới fetch
        if(CheckIfAPIFetched == 0)
        {
            manager.getWordOfTheDay(wordOfTheDayListener);
        }
        else
        {
            String word = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("word", "");
            String date = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("date", "");
            word_TV.setText(word);
            dayTime_TV.setText(date);
        }





        ACTV = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ACTV.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    manager.getWordMeaning(listener, ACTV.getText().toString());

                    dataBaseHelper.insertSuggestWord(ACTV.getText().toString());
                    return true;
                }
                return false;
            }
        });

        ACTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (ACTV.getRight() - ACTV.getCompoundDrawables()[2].getBounds().width())) {
                        // your action here
                        startVoiceRecognition();
                        return true;
                    }
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

        WOTDNavigateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListWOTDActivity.class);
//            intent.putExtra("data", DataPasser);
            startActivity(intent);
        });

//        textView_word = findViewById(R.id.textView_word);
//        recycler_phonetics = findViewById(R.id.recycler_phonetics);
//        recycler_meanings = findViewById(R.id.recycler_meanings);

        FavoriteWordBtn = findViewById(R.id.FavoriteWordsActivityBtn);

        FavoriteWordBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoriteWordsActivity.class);
            startActivity(intent);
        });

//        micBtn = findViewById(R.id.MicrophoneButton);
//        micBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
//            startActivityForResult(intent, 100);
//        });


        clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WOTDResultActivity.class);
//                if(CheckIfDataPassed ==1){
//                    intent.putExtra("data", todayWord);
//                }
                startActivity(intent);
            }
        });


    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
       intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
       startActivityForResult(intent, 100);
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


    private final WordOfTheDayOnFetchDataListener wordOfTheDayListener = new WordOfTheDayOnFetchDataListener() {
        @Override
        public void onFetchData(WordOfTheDayAPIResponse apiResponse, String message) {
            if(apiResponse == null){
                Toast.makeText(MainActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                return;
            }
            // Set the textView for the firsttime
            List<WordOfTheDay> ListOfWord = apiResponse.getList();
            todayWord = ListOfWord.get(0);
            word_TV.setText(todayWord.getWord());
            dayTime_TV.setText(todayWord.getDate());

            //Save the API Response to Shared Preferences
            SharedPreferences API_sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = API_sharedPreferences.edit();
            myEdit.putString("APIResponse", new Gson().toJson(apiResponse));
            myEdit.putString("word", todayWord.getWord());
            myEdit.putString("date", todayWord.getDate());
            myEdit.putString("author", todayWord.getAuthor());
            myEdit.putString("example", todayWord.getExample());
            myEdit.putString("definition", todayWord.getDefinition());
            myEdit.apply();
            CheckIfAPIFetched++;


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            RequestManager manager = new RequestManager(MainActivity.this);
            ACTV.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            manager.getWordMeaning(listener, ACTV.getText().toString());

        }
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