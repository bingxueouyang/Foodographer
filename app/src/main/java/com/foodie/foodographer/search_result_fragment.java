package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link search_result_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link search_result_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search_result_fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    YelpFusionApiFactory yelpFusionApiFactory;
    YelpFusionApi yelpFusionApi;
    String this_name;
    String this_location;
    ArrayList<Business> businesses;
    ArrayList<Restaurant> restaurants;
    ListView list;
    private LinearLayout search_bar;
    private DatabaseReference restRef = FirebaseDatabase.getInstance().getReference("Restaurants");
    private RecyclerView res_recycler;

    public search_result_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search_result_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static search_result_fragment newInstance(String param1, String param2) {
        search_result_fragment fragment = new search_result_fragment();
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
        View view = inflater.inflate(R.layout.fragment_search_result_fragment, container, false);
        thread.start();
        search_bar = view.findViewById(R.id.search);
        return view;
    }
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                yelpFusionApiFactory = new YelpFusionApiFactory();
                yelpFusionApi = yelpFusionApiFactory.createAPI(getString(R.string.apiKey));
                this_name = getArguments().getString("name");
                this_location = getArguments().getString("location");
                Map<String, String> params = new HashMap<>();
                params.put("term", this_name);
                params.put("location", this_location);
                Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);
                call.enqueue(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    //callback function
    Callback<SearchResponse> callback = new Callback<SearchResponse>() {

        @Override
        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
            Log.i("yelp", "in response");

            SearchResponse searchResponse = response.body();

            int totalNumberOfResult = searchResponse.getTotal();  // 3
            Log.i("yelp", String.valueOf(totalNumberOfResult));
            if (totalNumberOfResult == 0) {
                Toast.makeText(getActivity(), "Sorry, no result was found.", Toast.LENGTH_SHORT).show();
            }
            businesses = searchResponse.getBusinesses();
            //generate an array list of result restaurants(businesses)

            if (businesses.isEmpty()) {
                Log.i("yelp", "result business array is empty!!!");
            }

            restaurants = new ArrayList<Restaurant>(businesses.size());
            for(int i=0; i< businesses.size(); i++){
                final Restaurant myRest = new Restaurant(businesses.get(i));
                restaurants.add(myRest);
                // save the restaurant into our database whenever the user search it
                restRef.child(myRest.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Log.i("database info", "gotoRestaurant is called!");
                            return;
                        }
                        else {
                            //the restaurant is not created yet; create it first
                            restRef.child(myRest.getId()).setValue(myRest);
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }



            // write your filter algorithm here (sort and delete businesses objects)
            // https://stackoverflow.com/questions/16856554/filtering-an-arraylist-using-an-objects-field
            // https://stackoverflow.com/questions/23262445/sorting-and-filtering-listview-with-custom-array-adapter-with-two-textview

            /*String expert = getIntent().getStringExtra("expert");
            String rating = getIntent().getStringExtra("rating");
            String price = getIntent().getStringExtra("price");
            String distance = getIntent().getStringExtra("distance");
            */


            RecyclerResultList adapter = new RecyclerResultList(restaurants);
            res_recycler =  (RecyclerView)getView().findViewById(R.id.recyclerview);
            res_recycler.setHasFixedSize(true);
            res_recycler.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            res_recycler.setLayoutManager(llm);

        }

        @Override
        public void onFailure(Call<SearchResponse> call, Throwable t) {
            // HTTP error happened, do something to handle it.
        }
    };

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
        if(v == search_bar){
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.search_result_container, new SearchBar_fragment());
            fr.commit();
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

