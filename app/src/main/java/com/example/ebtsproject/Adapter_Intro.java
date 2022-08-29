package com.example.ebtsproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Intro extends RecyclerView.Adapter<Adapter_Intro.BoardingViewHolder> {

    private List<Model_Intro> modelIntros;

    public Adapter_Intro(List<Model_Intro> modelIntros){
        this.modelIntros = modelIntros;
    }

    @NonNull
    @Override
    public BoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.itemlist_intro, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BoardingViewHolder holder, int position) {
        holder.setBoardingData(modelIntros.get(position));
    }

    @Override
    public int getItemCount() {
        return modelIntros.size();
    }

    class BoardingViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageBoarding;


        public BoardingViewHolder(@NonNull View itemView) {
            super(itemView);


            imageBoarding = itemView.findViewById(R.id.imageBoarding);
        }

        void setBoardingData(Model_Intro modelIntro){
            imageBoarding.setImageResource(modelIntro.getImage());
        }
    }
}
