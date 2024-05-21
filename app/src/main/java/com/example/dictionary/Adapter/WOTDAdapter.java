package com.example.dictionary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;

import java.util.List;

public class WOTDAdapter extends RecyclerView.Adapter<WOTDAdapter.WOTDViewHolder>{

    private Context mContext;
    private List<String> mWordOfTheDay;

    private final OnItemClickListener listener;

    public WOTDAdapter(Context mContext, List<String> mWordOfTheDay, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mWordOfTheDay = mWordOfTheDay;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WOTDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_word, parent, false);
        return new WOTDAdapter.WOTDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WOTDViewHolder holder, int position) {
        String word = mWordOfTheDay.get(position);
        holder.bind(word, listener);
    }


    public interface OnItemClickListener {
        void onItemClick(String word);
    }

    @Override
    public int getItemCount() {
        return mWordOfTheDay.size();
    }

    public static class WOTDViewHolder extends RecyclerView.ViewHolder {
        private final TextView textWord;

        public WOTDViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = itemView.findViewById(R.id.text_word);
        }

        public void bind(final String word, final OnItemClickListener listener) {
            textWord.setText(word);
            itemView.setOnClickListener(v -> listener.onItemClick(word));
        }
    }
}
