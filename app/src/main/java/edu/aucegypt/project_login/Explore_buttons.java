package edu.aucegypt.project_login;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
 * Use the {@link Explore_buttons#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Explore_buttons extends Fragment{
    FragmentManager fm;
    FragmentTransaction ft, ft2;
    Fragment f, f2;
    Button addB;
    Button ExpB;
    static ArrayList<String> msgs;
    static ArrayList<String> sender;
    static ArrayList<Integer> anoy;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Explore_buttons() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Explore_buttons.
     */
    // TODO: Rename and change types and number of parameters
    public static Explore_buttons newInstance(String param1, String param2) {
        Explore_buttons fragment = new Explore_buttons();
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
    class getAPI extends AsyncTask<String, Void, JSONArray> {
        JSONArray jArr;

        @SuppressLint("WrongThread")
        @Override
        protected JSONArray doInBackground(String... Url) {
            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                try {
                    request.setURI(new URI(Url[0]));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String inputLine = in.readLine();
                jArr = new JSONArray(inputLine);


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return jArr;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            int size = jArr.length();
            for(int i = 0; i<size; i++){
                try {
                    String txt = jArr.getJSONObject(i).getString("msg");
                    String nm = jArr.getJSONObject(i).getString("username");
                    int an = jArr.getJSONObject(i).getInt("unknown");
                    anoy.add(an);
                    sender.add(nm);
                    msgs.add(txt);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            ft2.commit();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        msgs = new ArrayList<String>();
        sender = new ArrayList<String>();
        anoy = new ArrayList<Integer>();
        v =inflater.inflate(R.layout.fragment_explore_buttons, container, false);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        f = fm.findFragmentById(R.id.frag);
        f= new Add_Post();
        ft = fm.beginTransaction();
        ft.replace(R.id.frag, f, "add_Posts");
        ft.addToBackStack(null);

        f2 = fm.findFragmentById(R.id.frag);
        f2= new Explore_posts();
        ft2 = fm.beginTransaction();
        ft2.replace(R.id.frag, f2, "exp");
        ft2.addToBackStack(null);


        addB = v.findViewById(R.id.startPost);
        ExpB = v.findViewById(R.id.explorePosts);

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ft.commit();
            }
        });

        ExpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAPI api = new getAPI();
                api.execute(new String[]{"http://10.0.2.2:3000/getPosts"});

            }
        });

        return v;
    }
}