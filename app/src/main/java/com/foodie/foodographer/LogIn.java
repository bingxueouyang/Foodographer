package com.foodie.foodographer;
//import everything need for this java
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private Button loginBut;
    private EditText user_input_email;
    private EditText user_input_password;
    private TextView loginText;
    private ProgressDialog progressDialog2;
    private FirebaseAuth mAuth2;
    private TextView forgotpasswordLink;
    private Boolean checkingEmail_true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //connect to firebase
        mAuth2=FirebaseAuth.getInstance();
        //check user already login
        if(mAuth2.getCurrentUser()!= null){
            // then go into to profile page
            finish();

            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }
        
        progressDialog2= new ProgressDialog(this);
        //evoking the button, edit text and textview from login xml file
        user_input_email = (EditText) findViewById(R.id.user_enter_email);
        user_input_password = (EditText) findViewById(R.id.user_enter_password);
        loginBut= (Button) findViewById(R.id.loginRegister);
        loginText=(TextView) findViewById(R.id.hint_signup);
        forgotpasswordLink=(TextView) findViewById(R.id.forgot_password);
        // make the button, edit text and textview clickable
        loginBut.setOnClickListener(this);
        loginText.setOnClickListener(this);
        forgotpasswordLink.setOnClickListener(this);
    }

    private void userLogin(){
        String email2=user_input_email.getText().toString().trim();
        String password2=user_input_password.getText().toString().trim();
        if(TextUtils.isEmpty(email2)){
            Toast.makeText(this,"Please enter an email !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password2)){
            Toast.makeText(this,"Please enter password !",Toast.LENGTH_SHORT).show();
            return;
        }
        // let user to see the process of login 
        progressDialog2.setMessage("Login Account...");
        progressDialog2.show();
        //function to receive user email and password with firebase
        
        mAuth2.signInWithEmailAndPassword(email2,password2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog2.dismiss();
                        if(task.isSuccessful()){
                            email_verified();
                        }else {
                            // If sign in fails, display a message to the user.
                            String grab_error=task.getException().getMessage();
                            Toast.makeText(LogIn.this,"Error occur:"+grab_error,Toast.LENGTH_SHORT).show();
                        }
                        progressDialog2.dismiss();

                    }
                });
    }
    // check user's email is verified.
    private void email_verified(){
        FirebaseUser user=mAuth2.getCurrentUser();
        checkingEmail_true= user.isEmailVerified();
        String savedExtra = getIntent().getStringExtra("Value");
        Log.d("checkingUser","Check"+savedExtra);

        if(checkingEmail_true==true){
            if(savedExtra==null){
                onBackPressed();
            }
            finish();

            startActivity(new Intent(getApplicationContext(), UserProfile.class));
            //finish();
        }else{
            //tell user to verified his email first
            Toast.makeText(LogIn.this,"Go verified your email please!",Toast.LENGTH_SHORT).show();
            mAuth2.signOut();
        }
    }
    //on click function for jumping activity to activity when a button or textView or link clicked.
    @Override
    public void onClick(View view){
        if(view==loginBut){
            userLogin();

        }
        if(view ==loginText){
            finish();
            startActivity(new Intent(this,SignUp.class));
        }
        if(view ==forgotpasswordLink){
            finish();
            startActivity(new Intent(this,ResetPassword.class));
        }
    }
}
