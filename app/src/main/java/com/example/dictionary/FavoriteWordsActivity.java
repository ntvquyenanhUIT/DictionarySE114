package com.example.dictionary;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Adapter.FavoriteWordsAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWordsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteWordsAdapter adapter;
    private List<String> favoriteWordsList;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_words);

        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> {
            NavigationHelper.navigateToMainActivity(FavoriteWordsActivity.this);
        });

        // Add your code here to display favorite words or perform other actions related to favorite words

        DataBaseHelper dataBaseHelper = new DataBaseHelper(FavoriteWordsActivity.this);
        favoriteWordsList = dataBaseHelper.getFavoriteWords();


        if (favoriteWordsList.isEmpty()) {
            Toast.makeText(FavoriteWordsActivity.this, "No favorite words found.", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView = findViewById(R.id.recycler_view_favorites);
            recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteWordsActivity.this));

            adapter = new FavoriteWordsAdapter(FavoriteWordsActivity.this, favoriteWordsList, new FavoriteWordsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String word) {
                    // solve when a card view is clicked
                    Toast.makeText(FavoriteWordsActivity.this, "Clicked word: " + word, Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setAdapter(adapter);
        }

    }
}