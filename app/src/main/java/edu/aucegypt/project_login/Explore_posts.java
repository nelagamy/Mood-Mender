package edu.aucegypt.project_login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Explore_posts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Explore_posts extends Fragment {
    ArrayList<String> msgs;
    ArrayList<String> sender;
    ArrayList<Integer> anoy;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Explore_posts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Explore_posts.
     */
    // TODO: Rename and change types and number of parameters
    public static Explore_posts newInstance(String param1, String param2) {
        Explore_posts fragment = new Explore_posts();
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
        View v;
        msgs = Explore_buttons.msgs;
        sender = Explore_buttons.sender;
        anoy = Explore_buttons.anoy;
        v = inflater.inflate(R.layout.fragment_explore_posts, container, false);

        madeAdaptor a;

        ListView list = (ListView)v.findViewById(R.id.lists1);



        a =new madeAdaptor(getContext(), msgs);


        list.setAdapter(a);


        return v;
    }


    private class madeAdaptor extends ArrayAdapter<String> {

        public madeAdaptor(Context context, ArrayList<String> msgs) {
            super(context,0, msgs);
        }

        @Override
        public View getView(int p, View v, ViewGroup g) {
            View v1 = getLayoutInflater().inflate(R.layout.posts_list, null);
            Button postB =   v1.findViewById(R.id.a_post);
            TextView se = v1.findViewById(R.id.sender);


            postB.setText(msgs.get(p));
            se.setText(sender.get(p));
            if(anoy.get(p) == 1){
                se.setText("Anonymous");
            }





            return v1;
        }


    }
}