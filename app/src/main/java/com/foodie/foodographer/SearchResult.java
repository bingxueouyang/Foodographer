package com.foodie.foodographer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SearchResult extends AppCompatActivity implements View.OnClickListener {
    private ImageButton filterBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        filterBut = (ImageButton) findViewById(R.id.filter_button);
        filterBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        startActivity(new Intent(this,Filter.class));
    }
}
