package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchBar_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchBar_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchBar_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String name;
    String location;
    android.widget.SearchView nameSearch;
    android.widget.SearchView locationSearch;

    search_result_fragment search_result = new search_result_fragment();

    public SearchBar_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchBar_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchBar_fragment newInstance(String param1, String param2) {
        SearchBar_fragment fragment = new SearchBar_fragment();
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
        View view = inflater.inflate(R.layout.fragment_search_bar_fragment, container, false);
        nameSearch = view.findViewById(R.id.name_search);
        nameSearch.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Bundle args = new Bundle();
                args.putString("name", name);
                args.putString("location", location);
                search_result.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.search_result_container, search_result).commit();
                //gotoSearch(name, location);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                name = nameSearch.getQuery().toString();
                return true;
            }
        });

        locationSearch = view.findViewById(R.id.location_search);
        locationSearch.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle args = new Bundle();
                args.putString("name", name);
                args.putString("location", location);
                search_result.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.search_result_container, search_result).commit();
                //gotoSearch(name, location);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                location = locationSearch.getQuery().toString();
                return true;
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

    public void gotoSearch(String rest_name, String rest_location) {

        Intent intent = new Intent(getActivity(), SearchResult.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", rest_name);
        bundle.putString("location", rest_location);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void gotoSearch_result_fragment(String rest_name, String rest_location){

    }
}
