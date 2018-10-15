package com.foodie.foodographer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity  implements View.OnClickListener {private Button registBut;
    private EditText getemail;
    private EditText getpassword;
    private TextView signupText2;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth=FirebaseAuth.getInstance();

        progressDialog= new ProgressDialog(this);
        registBut = (Button) findViewById(R.id.checkingRegister);
        getemail= (EditText) findViewById(R.id.EmailAdress);
        getpassword=(EditText) findViewById(R.id.create_password);
        signupText2=(TextView) findViewById(R.id.givinghint);
        registBut.setOnClickListener(this);
        signupText2.setOnClickListener(this);
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
    private void registerUser(){
        String infoEmail=getemail.getText().toString().trim();
        String infoPassword=getpassword.getText().toString().trim();

        if(TextUtils.isEmpty(infoEmail)){
            Toast.makeText(this,"Please enter an email !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(infoPassword)){
            Toast.makeText(this,"Please enter password !",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(infoEmail,infoPassword)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            finish();
                            Intent myintent6 = new Intent(getApplicationContext(), profileforuser.class);
                            startActivity(myintent6);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this,"Register failed, PLease try again",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    @Override
    public void onClick(View view){
        if(view==registBut){
            registerUser();
        }
        if(view==signupText2){
            //Intent myintent2 = new Intent(this, login_test.class);
            startActivity(new Intent(this,Log_inActivity.class));
        }
    }

}
