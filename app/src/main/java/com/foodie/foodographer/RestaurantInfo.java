package com.foodie.foodographer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;

public class RestaurantInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        ArrayList<Review> reviews = new ArrayList<Review>(10);

        reviews.add(new Review("https://en.wikipedia.org/wiki/Spider-Man#/media/File:Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float)3.0, "3 months ago", "Had a wonderful Sunday breakfast with good friends. My buddy had the Eggs Benedict and said it was the best he even had. I wanted a bite but it disappeared off his play too fast. Both the mushroom and western omelettes are very good. To top it off excellent delightful service. Thank you Kay!"));
        reviews.add(new Review("https://en.wikipedia.org/wiki/Spider-Man#/media/File:Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float)3.0, "3 months ago","content"));
        reviews.add(new Review("https://en.wikipedia.org/wiki/Spider-Man#/media/File:Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float)3.0, "3 months ago","content"));
        reviews.add(new Review("https://en.wikipedia.org/wiki/Spider-Man#/media/File:Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float)3.0, "3 months ago","content"));
        reviews.add(new Review("https://en.wikipedia.org/wiki/Spider-Man#/media/File:Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float)3.0, "3 months ago","content"));

        // making articles recycle
        RecyclerReviewList adapter = new RecyclerReviewList(reviews);
        RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);
    }


}
