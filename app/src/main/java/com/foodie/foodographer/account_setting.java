package com.foodie.foodographer;


import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout;

import android.graphics.Bitmap;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import de.hdodenhof.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import android.provider.MediaStore;
import com.google.android.gms.tasks.OnFailureListener;
import java.util.Map;
public class account_setting extends AppCompatActivity implements View.OnClickListener{
    private Button savingBut;
    private Spinner changeExpertSpinner;
    private Spinner changeExpertSpinner2;
    private Spinner changeExpertSpinner3;
    private Spinner changeInterestSpinner;
    private Spinner changeInterestSpinner2;
    private Spinner changeInterestSpinner3;
    private Spinner changeInterestSpinner4;
    private FirebaseAuth mAuthSetting;
    private DatabaseReference profileRefer;
    private DatabaseReference profileReferExpert;
    private DatabaseReference profileReferInterest;
    private String currentUserID;
    private CircleImageView image;
    final static int galleryPic=1;
    private StorageReference storeUserImage;
    private Uri resultUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        mAuthSetting = FirebaseAuth.getInstance();
        currentUserID=mAuthSetting.getCurrentUser().getUid();
        profileRefer=FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);
        storeUserImage=FirebaseStorage.getInstance().getReference().child("Profile Images");

        savingBut = (Button) findViewById(R.id.savingInformation);
        savingBut.setOnClickListener(this);
        image =(CircleImageView) findViewById(R.id.profileSettingImage);
        ArrayAdapter<String> expertAdapter = new ArrayAdapter<String>( account_setting.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        // Specify the layout to use when the list of choices appears
        expertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        changeExpertSpinner = (Spinner) findViewById(R.id.expertiseChange1);
        changeExpertSpinner.setAdapter(expertAdapter);



        changeExpertSpinner2 = (Spinner) findViewById(R.id.expertiseChange2);
        changeExpertSpinner2.setAdapter(expertAdapter);
        changeExpertSpinner2.setVisibility(View.VISIBLE);

        changeExpertSpinner3 = (Spinner) findViewById(R.id.expertiseChange3);
        changeExpertSpinner3.setAdapter(expertAdapter);
        changeExpertSpinner3.setVisibility(View.VISIBLE);



        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>( account_setting.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        changeInterestSpinner = (Spinner) findViewById(R.id.InterestChange1);
        changeInterestSpinner.setAdapter(interestAdapter);


        changeInterestSpinner2 = (Spinner) findViewById(R.id.InterestChange2);
        changeInterestSpinner2.setAdapter(interestAdapter);
        changeInterestSpinner2.setVisibility(View.VISIBLE);

        changeInterestSpinner3 = (Spinner) findViewById(R.id.InterestChange3);
        changeInterestSpinner3.setAdapter(interestAdapter);
        changeInterestSpinner3.setVisibility(View.VISIBLE);

        changeInterestSpinner4 = (Spinner) findViewById(R.id.InterestChange4);
        changeInterestSpinner4.setAdapter(interestAdapter);
        changeInterestSpinner4.setVisibility(View.VISIBLE);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pictureIntent= new Intent();
                pictureIntent.setAction(Intent.ACTION_PICK);
                pictureIntent.setType("image/*");
                startActivityForResult(pictureIntent,galleryPic);
            }
        });

    }


    private void updateInformation(){
        final String expert1 = changeExpertSpinner.getSelectedItem().toString().trim();
        final String expert2 = changeExpertSpinner2.getSelectedItem().toString().trim();
        final String expert3 = changeExpertSpinner3.getSelectedItem().toString().trim();

        final String interest1 = changeInterestSpinner.getSelectedItem().toString().trim();
        final String interest2 = changeInterestSpinner2.getSelectedItem().toString().trim();
        final String interest3 = changeInterestSpinner3.getSelectedItem().toString().trim();
        final String interest4 = changeInterestSpinner4.getSelectedItem().toString().trim();


        profileReferExpert=profileRefer.child("Expert");
        HashMap usermap= new HashMap();
        usermap.put("Expert1",expert1);
        usermap.put("Expert2",expert2);
        usermap.put("Expert3",expert3);
        profileReferExpert.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText( account_setting.this,"Update account information successfully",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),UserProfile.class));
                    }else{
                        String grab_message=task.getException().getMessage();
                        Toast.makeText( account_setting.this,"Error occured:"+grab_message,Toast.LENGTH_SHORT).show();
                    }

            }
        });
        profileReferInterest=profileRefer.child("Interest");
        HashMap usermap2= new HashMap();
        usermap2.put("Interest1",interest1);
        usermap2.put("Interest2",interest2);
        usermap2.put("Interest3",interest3);
        usermap2.put("Interest4",interest4);
        profileReferInterest.updateChildren(usermap2).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText( account_setting.this,"Update account information successfully",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),UserProfile.class));
                }else{
                    String grab_message=task.getException().getMessage();
                    Toast.makeText( account_setting.this,"Error occured:"+grab_message,Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(resultUri!= null){
            final StorageReference filePath = storeUserImage.child(currentUserID);
            Bitmap bitmap=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });

            filePath.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(account_setting.this, "Profile Image stored successfully to Firebase storage...", Toast.LENGTH_SHORT).show();

                        Uri uriAddress=task.getResult();
                        Map newImage = new HashMap();
                        newImage.put("profileImageUrl", uriAddress.toString());
                        profileRefer.updateChildren(newImage);


                    }
                }
            });

        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryPic&& resultCode==RESULT_OK )
        {
            final Uri ImageUri = data.getData();
            resultUri=ImageUri;
            image.setImageURI(resultUri);
        }


    }
    public void onClick(View view) {
        if(view == savingBut){
            updateInformation();

        }

    }

}
