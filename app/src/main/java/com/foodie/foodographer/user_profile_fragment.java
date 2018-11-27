package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_profile_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_profile_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_profile_fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FirebaseAuth mAuth3; // get firebase auth

    private Button signout;
    private Button settingBut;
    private TextView userEmail_textview;
    private DatabaseReference profileRefer;
    private String currentUserID;
    private CircleImageView userImageView;
    private String totalExpertise;
    private TextView expertiseText;
    private TextView mostFavRest;
    private DatabaseReference profileRestRefer;
    private FirebaseUser currentUser;
    private int checkSize=0;
    private  String userFavRest;
    private Button favListBut;
    private Button recentViewListBut;
    private Button commentBut;


    public user_profile_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_profile_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user_profile_fragment newInstance(String param1, String param2) {
        user_profile_fragment fragment = new user_profile_fragment();
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
        View view = inflater.inflate(R.layout.fragment_user_profile_fragment, container, false);

        //declare signout button and set ClickListener
        signout= (Button) view.findViewById(R.id.logoutButton);
        signout.setOnClickListener(this);

        //declare setting button and set ClickListener
        settingBut=(Button) view.findViewById(R.id.setting);
        settingBut.setOnClickListener(this);

        //declare firebase auth
        mAuth3=FirebaseAuth.getInstance();
        currentUser=mAuth3.getCurrentUser();
        
        //go back to login if this user is not exist
        if(currentUser==null){
            getFragmentManager().beginTransaction().replace(R.id.userProfile, new LoginFragment()).commit();
        }
        //save user's information, set signout, setting, favList, recent view and comment buttons.
        else {
            currentUserID = currentUser.getUid();
            profileRefer = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);

            userEmail_textview = (TextView) view.findViewById(R.id.username);
            String email = currentUser.getEmail();
            String emailUserName = email.substring(0,email.indexOf('@'));
            userEmail_textview.setText(emailUserName);
            
            signout = (Button) view.findViewById(R.id.logoutButton);
            signout.setOnClickListener(this);
            
            settingBut = (Button) view.findViewById(R.id.setting);
            settingBut.setOnClickListener(this);
            
            userImageView = (CircleImageView) view.findViewById(R.id.profile_image);
            expertiseText = (TextView) view.findViewById(R.id.perosonalExpertise);
            mostFavRest = (TextView) view.findViewById(R.id.favoriteRest);
            
            favListBut =(Button) view.findViewById(R.id.listFavor);
            favListBut.setOnClickListener(this);
            
            recentViewListBut=(Button) view.findViewById(R.id.listView);
            recentViewListBut.setOnClickListener(this);
            
            commentBut=(Button) view.findViewById(R.id.reviewList);
            commentBut.setOnClickListener(this);
            
            //give user the ability to change or add expert in.
            profileRefer.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String imageOfUser = dataSnapshot.child("profileImageUrl").getValue().toString();
                    Log.d("checking", imageOfUser + "checking id");
                    String firstExpertise = dataSnapshot.child("Expert").child("Expert1").
                        getValue().toString();
                    String secondExpertise = dataSnapshot.child("Expert").child("Expert2").
                        getValue().toString();
                    String thirdExpertise = dataSnapshot.child("Expert").child("Expert3").
                        getValue().toString();

                    totalExpertise = firstExpertise +" | "+ secondExpertise +" | "+ thirdExpertise;
                    
                    Picasso.with(getActivity()).load(imageOfUser).into(userImageView);
                    expertiseText.setText(totalExpertise);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // return the layout
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // If clicked signout, signout and go to login page
        if(v == signout){
            mAuth3.signOut();
            getFragmentManager().beginTransaction().replace(R.id.userProfile, new LoginFragment()).
                addToBackStack(null).commit();
        }
        //go to account setting page is clicked setting button
        if(v == settingBut){
            getFragmentManager().beginTransaction().replace(R.id.userProfile, new account_setting_fragment()).
                addToBackStack(null).commit();
        }
        //go to favorite restaurant page if clicked favlist button
        if(v==favListBut){
            startActivity(new Intent(getActivity(), UserFavoriteRestaurants.class));
        }
        //go to recent view page if clicked recentview button
        if(v==recentViewListBut){
            startActivity(new Intent(getActivity(), UserRecentViewRestaurants.class));
        }
        //go to comment page if clicked comment button
        if(v==commentBut){
            startActivity(new Intent(getActivity(), UserCommentRestaurant.class));
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
