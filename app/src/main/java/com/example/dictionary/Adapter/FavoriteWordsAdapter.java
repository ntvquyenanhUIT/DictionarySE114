package com.example.dictionary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dictionary.R;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoriteWordsAdapter extends RecyclerView.Adapter<FavoriteWordsAdapter.FavoriteWordViewHolder> {

    private Context mContext;
    private List<String> mFavoriteWords;
    private final OnItemClickListener listener;

    public FavoriteWordsAdapter(Context context, List<String> favoriteWords, OnItemClickListener listener) {
        mContext = context;
        mFavoriteWords = favoriteWords;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String word);
    }

    @NonNull
    @Override
    public FavoriteWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_word, parent, false);
        return new FavoriteWordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteWordViewHolder holder, int position) {
        String word = mFavoriteWords.get(position);
        holder.bind(word, listener);
    }

    @Override
    public int getItemCount() {
        return mFavoriteWords.size();
    }

    public static class FavoriteWordViewHolder extends RecyclerView.ViewHolder {
        private final TextView textWord;

        public FavoriteWordViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = itemView.findViewById(R.id.text_word);
        }

        public void bind(final String word, final OnItemClickListener listener) {
            textWord.setText(word);
            itemView.setOnClickListener(v -> listener.onItemClick(word));
        }
    }
}
