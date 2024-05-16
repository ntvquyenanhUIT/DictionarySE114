package com.example.dictionary.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.recycler_view_favorites);
    }
}
