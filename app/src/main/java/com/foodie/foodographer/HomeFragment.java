package com.foodie.foodographer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView res_recycler;
    private LinearLayout search_bar;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        //put restraunts information into an arraylist of size 10
        ArrayList<Article> articles = new ArrayList<Article>(10);
        articles.add(new Article("Hello Hamburger",
                "https://amp.businessinsider.com/images/5a7dc169d03072af008b4bf2-750-562.jpg",
                "https://en.m.wikipedia.org/w/index." +
                        "php?title=Hamburger&mobileaction=toggle_view_mobile"));
        articles.add(new Article("Hello XLB",
                "https://daily.jstor.org/wp-content" +
                        "/uploads/2017/11/dim_sum_dumplings_1050x700.jpg",
                "https://en.m.wikipedia.org/wiki/Xiaolongbao"));
        articles.add(new Article("Hello JianBing",
                "https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D400/" +
                        "sign=57ab32c9cfcec3fd8b3ea675e689d4b6/" +
                        "a50f4bfbfbedab64edf682c5fb36afc379311e2b.jpg",
                "https://en.m.wikipedia.org/wiki/Jianbing"));
        articles.add(new Article("Hello Red Bean Double Skin Milk",
                "https://media-cdn.tripadvisor.com/" +
                        "media/daodao/photo-s/04/19/9e/1a/caption.jpg",
                "https://en.m.wikipedia.org/wiki/Double_skin_milk"));
        articles.add(new Article("Hello Crab",
                "https://cp1.douguo.com/upload/caiku" +
                        "/3/9/b/600x400_39fa22c44f4f02d73e5c1fd953c0171b.jpg",
                "https://learnchinesefood.com/detail%3D9807"));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        search_bar = view.findViewById(R.id.search);
        search_bar.setOnClickListener(this);

        //recyclerview for restrauant list
        res_recycler = (RecyclerView) view.findViewById(R.id.res_recycler);
        res_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //declare the RecyclerArticle adapter
        RecyclerArticleList adapter = new RecyclerArticleList(articles);
        res_recycler.setAdapter(adapter);
        res_recycler.setItemAnimator(new DefaultItemAnimator());
        //return this layout
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
        if (v == search_bar) {
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.frame_container, new SearchBarFragment());
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
