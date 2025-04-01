package com.TechInfinityStudios.TapTapTap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private List<Integer> highScores;
    int scorePosition = 0;
    String pos = "";

    public ScoreAdapter(List<Integer> scores) {
        this.highScores = scores;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position){
        setScorePosition(position+1);
        holder.scoreTextView.setText((position+1) + pos + ": " + highScores.get(position));
    }

    @Override
    public int getItemCount() {
        return highScores.size();
    }
    public void updateScores(List<Integer> highScores) {
        this.highScores = highScores;
        notifyDataSetChanged();
    }
    public void setScorePosition(int position) {
        if(position == 1){
            pos = "st";
        }
        else if(position == 2){
            pos = "nd";
        }
        else if(position == 3){
            pos = "rd";
        }
        else{
            pos = "th";
        }
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView scoreTextView;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}
