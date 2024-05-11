package com.example.dictionary;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Adapter.FavoriteWordsAdapter;

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
        /*
        DataBaseHelper dataBaseHelper = new DataBaseHelper(FavoriteWordsActivity.this);
        favoriteWordsList = dataBaseHelper.getFavoriteWords();

        if (favoriteWordsList.isEmpty()) {
            // Hiển thị thông báo cho người dùng rằng không có từ yêu thích nào
            Toast.makeText(FavoriteWordsActivity.this, "No favorite words found.", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView = findViewById(R.id.recycler_view_favorites);
            recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteWordsActivity.this));

            adapter = new FavoriteWordsAdapter(FavoriteWordsActivity.this, favoriteWordsList);
            recyclerView.setAdapter(adapter);
        }
         */
    }
}