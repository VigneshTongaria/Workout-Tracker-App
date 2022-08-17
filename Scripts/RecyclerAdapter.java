package com.example.myapplication;

import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Exercise> Exercises = new ArrayList<>();
    private String[][] Weights = new String[20][8];
    private String[][] Reps = new String[20][8];
    /*public RecyclerAdapter(ArrayList<Exercise> exercises) {
        this.Exercises = exercises;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view,parent,false);
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Exercise_name.setText(Exercises.get(position).getName());
        int pos = position;
        int threshold = 1;
        for(int i = 7 ; i>=0; i--){
            if(Weights[pos][i] != null|| Reps[pos][i] != null){threshold = i+1;
                break;}
        }
        for(int i = 7 ; i > threshold -1 ; i--)
        {
            holder.sets.get(i).setVisibility(View.GONE);
        }
        for(int i = 0 ; i < threshold ; i++){
            holder.weights.get(i).setText(Weights[pos][i]);
            holder.reps.get(i).setText(Reps[pos][i]);
        }
        holder.add_sets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Exercises.get(pos).getSets_size()<7) {
                    Exercises.get(pos).setSets_size(Exercises.get(pos).getSets_size() + 1);
                    holder.sets.get(Exercises.get(pos).getSets_size()).setVisibility(View.VISIBLE);
                }
            }
        });
        holder.Remove_sets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Exercises.get(pos).getSets_size()>=1)
                {
                    holder.sets.get(Exercises.get(pos).getSets_size()).setVisibility(View.GONE);
                    Weights[pos][Exercises.get(pos).getSets_size()] = null;
                    Reps[pos][Exercises.get(pos).getSets_size()] = null;
                    notifyDataSetChanged();
                    Exercises.get(pos).setSets_size(Exercises.get(pos).getSets_size() - 1);
                }
                else{
                    Weights[pos][Exercises.get(pos).getSets_size()] = null;
                    Reps[pos][Exercises.get(pos).getSets_size()] = null;
                    Exercises.remove(pos);
                    notifyDataSetChanged();
                }
            }
        });
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.remove_sets:
                        if (Exercises.get(pos).getSets_size() >= 1) {
                            holder.sets.get(Exercises.get(pos).getSets_size()).setVisibility(View.GONE);
                            Exercises.get(pos).setSets_size(Exercises.get(pos).getSets_size() - 1);
                        }
                    case R.id.add_sets:
                        Exercises.get(pos).setSets_size(Exercises.get(pos).getSets_size()+1);
                        holder.sets.get(Exercises.get(pos).getSets_size()).setVisibility(View.VISIBLE);
                }
            }
        });*/
        for (int i =0;i<8;i++)
        {   int j = i;
            holder.weights.get(i).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(textView.getText().toString() == null) return false;
                    else {
                        Weights[pos][j] = textView.getText().toString();
                        Log.d("TAG", Weights[pos][j]);
                        return true;
                    }
                }
            });
            holder.reps.get(i).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(textView.getText().toString() == null) return false;
                    else {
                        Reps[pos][j] = textView.getText().toString();
                        Log.d("TAG", Reps[pos][j]);
                        return true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
     private TextView Exercise_name;
     private ArrayList<EditText> weights = new ArrayList<EditText>();
     private ArrayList<EditText> reps = new ArrayList<EditText>();
     private ArrayList<LinearLayout> sets = new ArrayList<LinearLayout>();
     private Button add_sets_button;
     private Button Remove_sets_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Exercise_name = itemView.findViewById(R.id.SetsHeading);
            add_sets_button = itemView.findViewById(R.id.add_sets);
            Remove_sets_button = itemView.findViewById(R.id.remove_sets);
            sets.add(itemView.findViewById(R.id.set_1));
            sets.add(itemView.findViewById(R.id.set_2));
            sets.add(itemView.findViewById(R.id.set_3));
            sets.add(itemView.findViewById(R.id.set_4));
            sets.add(itemView.findViewById(R.id.set_5));
            sets.add(itemView.findViewById(R.id.set_6));
            sets.add(itemView.findViewById(R.id.set_7));
            sets.add(itemView.findViewById(R.id.set_8));
            weights.add(itemView.findViewById(R.id.Weights));
            weights.add(itemView.findViewById(R.id.Weights_1));
            weights.add(itemView.findViewById(R.id.Weights_2));
            weights.add(itemView.findViewById(R.id.Weights_3));
            weights.add(itemView.findViewById(R.id.Weights_4));
            weights.add(itemView.findViewById(R.id.Weights_5));
            weights.add(itemView.findViewById(R.id.Weights_6));
            weights.add(itemView.findViewById(R.id.Weights_7));
            reps.add(itemView.findViewById(R.id.reps));
            reps.add(itemView.findViewById(R.id.reps_1));
            reps.add(itemView.findViewById(R.id.reps_2));
            reps.add(itemView.findViewById(R.id.reps_3));
            reps.add(itemView.findViewById(R.id.reps_4));
            reps.add(itemView.findViewById(R.id.reps_5));
            reps.add(itemView.findViewById(R.id.reps_6));
            reps.add(itemView.findViewById(R.id.reps_7));
        }
    }

    public void setSets(ArrayList<Exercise> exercise) {
        Exercises = exercise;
        notifyDataSetChanged();
    }

    public void setWeights(String[][] weights) {
        Weights = weights;
        notifyDataSetChanged();
    }

    public void setReps(String[][] reps) {
        this.Reps = reps;
        notifyDataSetChanged();
    }
}
