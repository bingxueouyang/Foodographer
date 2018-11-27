package com.foodie.foodographer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.RatingBar;

public class Filter extends AppCompatActivity implements View.OnClickListener{
    private Button compBut;
    private Spinner expertSpinner;
    private RatingBar ratingBar;
    private Spinner distanceSpinner;
    private Button price_$;
    private Button price_$$;
    private Button price_$$$;
    private Button price_$$$$;
    private String priceSelect = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // button listeners
        price_$ = (Button) findViewById(R.id.price1_button);
        price_$.setOnClickListener(this);

        price_$$ = (Button) findViewById(R.id.price2_button);
        price_$$.setOnClickListener(this);

        price_$$$ = (Button) findViewById(R.id.price3_button);
        price_$$$.setOnClickListener(this);

        price_$$$$ = (Button) findViewById(R.id.price4_button);
        price_$$$$.setOnClickListener(this);

        compBut = (Button) findViewById(R.id.complete_btn);
        compBut.setOnClickListener(this);

        // spinner adapters
        expertSpinner = (Spinner) findViewById(R.id.expert_spinner);
        ArrayAdapter<String> expertAdapter = new ArrayAdapter<String>(Filter.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        expertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expertSpinner.setAdapter(expertAdapter);

        distanceSpinner = (Spinner) findViewById(R.id.distance_spinner);
        ArrayAdapter<String> distanceAdapter = new ArrayAdapter<String>(Filter.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.distance_array));
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(distanceAdapter);


        // ratingBar
        ratingBar = (RatingBar) findViewById(R.id.rating_rating_bar);



    }

    private void complete(){
        final String expert = expertSpinner.getSelectedItem().toString().trim();
        final String distance = distanceSpinner.getSelectedItem().toString().trim();
        final String rating = Float.toString(ratingBar.getRating());
        final String price = priceSelect;

        Intent searchResult = new Intent(this, SearchResult.class);
        searchResult.putExtra("expert", expert);
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