package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountSettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    private OnFragmentInteractionListener mListener;

    public AccountSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountSettingFragment newInstance(String param1, String param2) {
        AccountSettingFragment fragment = new AccountSettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_setting_fragment,
                container, false);
        mAuthSetting = FirebaseAuth.getInstance();
        currentUserID=mAuthSetting.getCurrentUser().getUid();
        profileRefer=FirebaseDatabase.getInstance().getReference().
                child("users").child(currentUserID);
        storeUserImage=FirebaseStorage.getInstance().getReference().child("Profile Images");

        savingBut = (Button) view.findViewById(R.id.savingInformation);
        savingBut.setOnClickListener(this);
        image =(CircleImageView) view.findViewById(R.id.profileSettingImage);
        ArrayAdapter<String> expertAdapter = new ArrayAdapter<String>( getContext(),
                android.R.layout.simple_list_item_1, getResources().
                getStringArray(R.array.expertise_array));
        // Specify the layout to use when the list of choices appears
        expertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       
        // Apply the adapter to the spinner
        changeExpertSpinner = (Spinner) view.findViewById(R.id.expertiseChange1);
        changeExpertSpinner.setAdapter(expertAdapter);

        changeExpertSpinner2 = (Spinner) view.findViewById(R.id.expertiseChange2);
        changeExpertSpinner2.setAdapter(expertAdapter);
        changeExpertSpinner2.setVisibility(View.VISIBLE);

        changeExpertSpinner3 = (Spinner) view.findViewById(R.id.expertiseChange3);
        changeExpertSpinner3.setAdapter(expertAdapter);
        changeExpertSpinner3.setVisibility(View.VISIBLE);



        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>( getContext(),
                android.R.layout.simple_list_item_1, getResources().
                getStringArray(R.array.expertise_array));
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //get the user input for expert in and 
        changeInterestSpinner = (Spinner) view.findViewById(R.id.InterestChange1);
        changeInterestSpinner.setAdapter(interestAdapter);

        changeInterestSpinner2 = (Spinner) view.findViewById(R.id.InterestChange2);
        changeInterestSpinner2.setAdapter(interestAdapter);
        changeInterestSpinner2.setVisibility(View.VISIBLE);

        changeInterestSpinner3 = (Spinner) view.findViewById(R.id.InterestChange3);
        changeInterestSpinner3.setAdapter(interestAdapter);
        changeInterestSpinner3.setVisibility(View.VISIBLE);

        changeInterestSpinner4 = (Spinner) view.findViewById(R.id.InterestChange4);
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

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    
    //update the new user information. (experties, interest and profile picture)
    private void updateInformation(){
        final String expert1 = changeExpertSpinner.getSelectedItem().toString().trim();
        final String expert2 = changeExpertSpinner2.getSelectedItem().toString().trim();
        final String expert3 = changeExpertSpinner3.getSelectedItem().toString().trim();

        final String interest1 = changeInterestSpinner.getSelectedItem().toString().trim();
        final String interest2 = changeInterestSpinner2.getSelectedItem().toString().trim();
        final String interest3 = changeInterestSpinner3.getSelectedItem().toString().trim();
        final String interest4 = changeInterestSpinner4.getSelectedItem().toString().trim();

        //experties
        profileReferExpert=profileRefer.child("Expert");
        HashMap usermap= new HashMap();
        usermap.put("Expert1",expert1);
        usermap.put("Expert2",expert2);
        usermap.put("Expert3",expert3);
        profileReferExpert.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText( getContext(),
                            "Update account information successfully",
                            Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().
                            replace(getId(), new UserProfileFragment()).commit();
                }else{
                    String grab_message=task.getException().getMessage();
                    Toast.makeText( getContext(),
                            "Error occured:"+grab_message,Toast.LENGTH_SHORT).show();
                }

            }
        });
        
        //interest
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
                    Toast.makeText( getContext(),
                            "Update account information successfully",
                            Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().
                            replace(getId(), new UserProfileFragment()).commit();
                }else{
                    String grab_message=task.getException().getMessage();
                    Toast.makeText( getContext(),
                            "Error occured:"+grab_message,Toast.LENGTH_SHORT).show();
                }

            }
        });
        
        //upload user profile picture
        if(resultUri!= null){
            final StorageReference filePath = storeUserImage.child(currentUserID);
            Bitmap bitmap=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().
                        getApplication().getContentResolver(), resultUri);
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
                        Toast.makeText(getContext(),
                                "Profile Image stored successfully to Firebase storage...",
                                Toast.LENGTH_SHORT).show();

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v == savingBut){
            updateInformation();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
