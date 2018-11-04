package com.foodie.foodographer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Filter extends AppCompatActivity implements View.OnClickListener{
    private Button compBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        compBut = (Button) findViewById(R.id.complete_button);
        compBut.setOnClickListener(this);

        Spinner expertSpinner = (Spinner) findViewById(R.id.expert_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> expertAdapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        // Specify the layout to use when the list of choices appears
        expertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        expertSpinner.setAdapter(expertAdapter);


        Spinner ratingSpinner = (Spinner) findViewById(R.id.rating_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.rating_array));
        // Specify the layout to use when the list of choices appears
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ratingSpinner.setAdapter(ratingAdapter);

        Spinner distanceSpinner = (Spinner) findViewById(R.id.distance_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> distanceAdapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.distance_array));
        // Specify the layout to use when the list of choices appears
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        distanceSpinner.setAdapter(distanceAdapter);

    }

    @Override
    public void onClick(View view){
        startActivity(new Intent(this,SearchResult.class));
    }
}
