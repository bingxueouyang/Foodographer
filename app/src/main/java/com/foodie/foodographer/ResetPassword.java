package com.foodie.foodographer;
// import eveything need for this java

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    //initalize the button, edittext and firebase authentication
    private Button goto_email;
    private EditText user_reset_enter_email;
    private FirebaseAuth checkAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        //connect to firebase authentication
        checkAuth = FirebaseAuth.getInstance();
        // grab the button that reset email
        goto_email = (Button) findViewById(R.id.send_email);
        // grabbing the email that user try to reset
        user_reset_enter_email = (EditText) findViewById(R.id.email_box);

        goto_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_input = user_reset_enter_email.getText().toString();
                if (TextUtils.isEmpty(email_input)) {
                    Toast.makeText(ResetPassword.this,
                            "Enter the email please", Toast.LENGTH_SHORT).show();
                } else {
                    //go into firebase authentication to send reset password
                    checkAuth.sendPasswordResetEmail(email_input).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //check if it is successful
                                Toast.makeText(ResetPassword.this,
                                        "Success, Check your email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassword.this,
                                        LogIn.class));
                            } else {
                                //check if there is an error
                                String bug_error = task.getException().getMessage();
                                Toast.makeText(ResetPassword.this,
                                        "Error Something went wrong!"
                                                + bug_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}


