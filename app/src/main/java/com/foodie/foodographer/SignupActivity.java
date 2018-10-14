package com.foodie.foodographer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Spinner mySpinner = (Spinner) findViewById(R.id.ExpertiseSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SignupActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        // Specify the layout to use when the list of choices appears
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mySpinner.setAdapter(myAdapter);

        Spinner mySpinner2 = (Spinner) findViewById(R.id.FlavorSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(SignupActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.flavor_array));
        // Specify the layout to use when the list of choices appears
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mySpinner2.setAdapter(myAdapter2);


    }


}
