package com.foodie.foodographer;


import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.RatingBar;
import android.graphics.Color;

public class Filter extends AppCompatActivity implements View.OnClickListener{
    private Button compBut;
    private Spinner expertSpinner;
    private Spinner ratingSpinner;
    private RatingBar ratingBar;
    private Spinner distanceSpinner;
    private Button price_$;
    private Button price_$$;
    private Button price_$$$;
    private Button price_$$$$;
    private String priceSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        compBut = (Button) findViewById(R.id.complete_btn);
        compBut.setOnClickListener(this);

        expertSpinner = (Spinner) findViewById(R.id.expert_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> expertAdapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        // Specify the layout to use when the list of choices appears
        expertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        expertSpinner.setAdapter(expertAdapter);

        ratingBar = (RatingBar) findViewById(R.id.rating_rating_bar);

        price_$ = (Button) findViewById(R.id.price1_button);
        price_$.setOnClickListener(this);

        price_$$ = (Button) findViewById(R.id.price2_button);
        price_$$.setOnClickListener(this);

        price_$$$ = (Button) findViewById(R.id.price3_button);
        price_$$$.setOnClickListener(this);

        price_$$$$ = (Button) findViewById(R.id.price4_button);
        price_$$$$.setOnClickListener(this);

        ratingSpinner = (Spinner) findViewById(R.id.rating_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.rating_array));
        // Specify the layout to use when the list of choices appears
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ratingSpinner.setAdapter(ratingAdapter);

        distanceSpinner = (Spinner) findViewById(R.id.distance_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> distanceAdapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.distance_array));
        // Specify the layout to use when the list of choices appears
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        distanceSpinner.setAdapter(distanceAdapter);

    }

    private void complete(){
        final String expert = expertSpinner.getSelectedItem().toString().trim();
        final String tasteORservice = ratingSpinner.getSelectedItem().toString().trim();
        final String rating = Float.toString(ratingBar.getRating());
        final String price = priceSelect;
        final String distance = distanceSpinner.getSelectedItem().toString().trim();


        Intent searchResult = new Intent(this, SearchResult.class);
        searchResult.putExtra("expert", expert);
        searchResult.putExtra("tasteORservice", tasteORservice);
        searchResult.putExtra("rating", rating);
        searchResult.putExtra("price", price);
        searchResult.putExtra("distance",distance);
        startActivity(searchResult);
    }

    @Override
    public void onClick(View view){
        if(view == compBut){
            complete();
        }
        if(view == price_$){
            priceSelect = "$";
            price_$$$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$$.setTextColor(getResources().getColor(R.color.colorAccent));
            // to red
            price_$.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        if(view == price_$$){
            priceSelect = "$$";
            price_$$$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$.setTextColor(getResources().getColor(R.color.colorAccent));
            // to red
            price_$$.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        if(view == price_$$$){
            priceSelect = "$$$";
            // to red
            price_$$$.setTextColor(getResources().getColor(R.color.colorPrimary));
            price_$$$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        if(view == price_$$$$){
            priceSelect = "$$$$";
            // to red
            price_$$$$.setTextColor(getResources().getColor(R.color.colorPrimary));

            price_$$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$$.setTextColor(getResources().getColor(R.color.colorAccent));
            price_$.setTextColor(getResources().getColor(R.color.colorAccent));
        }

    }
}
