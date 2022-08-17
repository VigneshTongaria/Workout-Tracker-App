package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.appsearch.GetByDocumentIdRequest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ArrayList<Exercise> Exercises = new ArrayList<>();
    String[][] Weights = new String[20][8];
    String[][] Reps = new String[20][8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ArrayList<Exercise> Exercises = new ArrayList<>();*/
        this.setTitle(SimpleDateFormat.getDateInstance().format(Calendar.getInstance().getTime()));
        String title = this.getTitle().toString();
        load_data(title,this);
    }

    private void set_adapter(ArrayList<Exercise> exercises,String[][] weights,String[][] reps) {
        RecyclerView main = findViewById(R.id.LeftRecycleViewer);
        menu_manager(exercises);
        RecyclerAdapter adapter = new RecyclerAdapter();
        adapter.setSets(exercises);
        adapter.setWeights(weights);
        adapter.setReps(reps);
        main.setAdapter(adapter);
        main.setLayoutManager(new LinearLayoutManager(this));
    }

    private void menu_manager(ArrayList<Exercise> exercises) {
        EditText Add_Exercise_name = findViewById(R.id.add_exercise_name);
        Button Add_Exercise_button = findViewById(R.id.add_exercise_button);
        Add_Exercise_name.setVisibility(View.GONE);
        Add_Exercise_button.setVisibility(View.GONE);
        Add_Exercise_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_name = Add_Exercise_name.getText().toString();
                exercises.add(new Exercise(new_name,0,null));
                Add_Exercise_name.setVisibility(View.GONE);
                Add_Exercise_button.setVisibility(View.GONE);
                findViewById(R.id.LeftRecycleViewer).setVisibility(View.VISIBLE);
                Add_Exercise_name.setText(null);
            }
        });
    }
   /* private void set_remove_sets_on_click_listener(ArrayList<Exercise> exercises){
        for(int i =0;i < exercises.size();i++)
        {  int j = i;
            Button remove_exercise = exercises.get(i).getRemove_sets_button();
            Log.e("TAG", Integer.toString(remove_exercise.getId()));
        }    }*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_exercise_item:
                findViewById(R.id.add_exercise_name).setVisibility(View.VISIBLE);
                findViewById(R.id.add_exercise_button).setVisibility(View.VISIBLE);
                findViewById(R.id.LeftRecycleViewer).setVisibility(View.GONE);
                return true;
            case R.id.set_date_item:
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            Calendar new_calendar = Calendar.getInstance();
                            new_calendar.set(i,i1,i2);
                            load_data(SimpleDateFormat.getDateInstance().format(new_calendar.getTime()),MainActivity.this);
                       MainActivity.this.setTitle(SimpleDateFormat.getDateInstance().format(new_calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
                return true;
            case R.id.save_data:
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("shared preferences",MODE_PRIVATE);
                SharedPreferences sharedPreferences_1 = MainActivity.this.getSharedPreferences("shared preferences_1",MODE_PRIVATE);
                SharedPreferences sharedPreferences_2 = MainActivity.this.getSharedPreferences("shared preferences_2",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SharedPreferences.Editor editor_1 = sharedPreferences_1.edit();
                SharedPreferences.Editor editor_2 = sharedPreferences_2.edit();
                Gson gson = new Gson();
                String array_decoder = gson.toJson(Exercises);
                String weights_decoder = gson.toJson(Weights);
                String reps_decoder = gson.toJson(Reps);
                String title = MainActivity.this.getTitle().toString();
                editor.putString(title,array_decoder);
                editor_1.putString(title,weights_decoder);
                editor_2.putString(title,reps_decoder);
                editor.apply();
                editor_1.apply();
                editor_2.apply();
            default: return super.onOptionsItemSelected(item);
        }
    }
    private void load_data(String date, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences sharedPreferences_1 = context.getSharedPreferences("shared preferences_1",MODE_PRIVATE);
        SharedPreferences sharedPreferences_2 = context.getSharedPreferences("shared preferences_2",MODE_PRIVATE);
        Gson gson = new Gson();
        String array_encoder = sharedPreferences.getString(date,null);
        String weights_encoder = sharedPreferences_1.getString(date,null);
        String reps_encoder = sharedPreferences_2.getString(date,null);
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        Type type_string = new TypeToken<String[][]>(){}.getType();
        Exercises = gson.fromJson(array_encoder,type);
        Weights = gson.fromJson(weights_encoder,type_string);
        Reps = gson.fromJson(reps_encoder,type_string);
        if(Exercises == null) {Exercises = new ArrayList<>();}
        if(Weights == null) {Weights = new String[20][8];}
        if(Reps == null){Reps = new String[20][8];}
        set_adapter(Exercises,Weights,Reps);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }
    public void remove_keyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}