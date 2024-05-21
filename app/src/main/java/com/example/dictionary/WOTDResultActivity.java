package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dictionary.Models.WordOfTheDay;
import com.example.dictionary.Models.WordOfTheDayAPIResponse;

public class WOTDResultActivity extends AppCompatActivity {
    TextView wordTV,dateTV,meaningTV,exampleTV, authorTV;

    WordOfTheDay resultWord;
    ImageButton goBack;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wotd_result);
        wordTV = findViewById(R.id.word);
        dateTV = findViewById(R.id.date);
        meaningTV = findViewById(R.id.definition);
        exampleTV = findViewById(R.id.example);
        authorTV = findViewById(R.id.author);
        goBack = findViewById(R.id.goBack);

        resultWord = (WordOfTheDay) getIntent().getSerializableExtra("data");
        if(resultWord != null){
            wordTV.setText(resultWord.getWord());
            dateTV.setText(resultWord.getDate());
            meaningTV.setText(resultWord.getDefinition());
            exampleTV.setText(resultWord.getExample());
            authorTV.setText(resultWord.getAuthor());
        }
        else
        {
            String word = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("word", "");
            String date = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("word", "");
            String author = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("author", "");
            String meaning = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("definition", "");
            String example = getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("example", "");
            wordTV.setText(word);
            dateTV.setText(date);
            meaningTV.setText(meaning);
            exampleTV.setText(example);
            authorTV.setText(author);
        }

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WOTDResultActivity.this, ListWOTDActivity.class);
                startActivity(intent);
            }
        });
    }
}