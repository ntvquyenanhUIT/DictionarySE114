package com.example.dictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Adapter.FavoriteWordsAdapter;
import com.example.dictionary.Adapter.WOTDAdapter;
import com.example.dictionary.Models.WordOfTheDay;
import com.example.dictionary.Models.WordOfTheDayAPIResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListWOTDActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WOTDAdapter adapter;
    private List<String> WOTDList = new ArrayList<>();
    WordOfTheDayAPIResponse APIResponse;
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_wotdactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String APIResponseJson = sharedPreferences.getString("APIResponse", "");
        if (!APIResponseJson.isEmpty()) {
            APIResponse = new Gson().fromJson(APIResponseJson, WordOfTheDayAPIResponse.class);
            List<WordOfTheDay> listWords = APIResponse.getList();
            for (WordOfTheDay word : listWords) {
            WOTDList.add(word.getWord());
            }
        }

        backButton = findViewById(R.id.gobacktoMain);

        backButton.setOnClickListener(v -> {
            NavigationHelper.navigateToMainActivity(ListWOTDActivity.this);
        });

        recyclerView = findViewById(R.id.recycler_view_wotd);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListWOTDActivity.this));

        adapter = new WOTDAdapter(ListWOTDActivity.this, WOTDList, this::showWordDetails
        );
        recyclerView.setAdapter(adapter);

    }

    private void showWordDetails(String word) {
    Intent intent = new Intent(ListWOTDActivity.this, WOTDResultActivity.class);
    for (WordOfTheDay wotd : APIResponse.getList()) {
        if (wotd.getWord().equals(word)) {
            intent.putExtra("data", wotd);
            break;
        }
    }
    startActivity(intent);
}
}