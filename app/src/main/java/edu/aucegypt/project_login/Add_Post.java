package edu.aucegypt.project_login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
 * Use the {@link Add_Post#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Post extends Fragment {
    ArrayList<String> msgs;
    ArrayList<String> sender;
    static ArrayList<Integer> anoy;
    EditText editT;
    View v;
    Button postB;
    String user = Login_Activity.user;

    FragmentManager fm;
    FragmentTransaction ft;
    Fragment f;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Add_Post() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Post.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Post newInstance(String param1, String param2) {
        Add_Post fragment = new Add_Post();
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

                try {
                    request.setURI(new URI(Url[1]));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                response = client.execute(request);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                inputLine = in.readLine();
                jArr = new JSONArray(inputLine);


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return jArr;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            editT.setText("");
            int size = jArr.length();
            msgs.clear();
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
            Explore_buttons.anoy = anoy;
            ft.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add__post, container, false);
        msgs = Explore_buttons.msgs;
        sender = Explore_buttons.sender;
        anoy = new ArrayList<Integer>();
        editT = (EditText)v.findViewById(R.id.e1);
        postB = v.findViewById(R.id.postMsg);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        f = fm.findFragmentById(R.id.frag);
        f= new Explore_posts();
        ft = fm.beginTransaction();
        ft.replace(R.id.frag, f, "posts");
        ft.addToBackStack(null);

        postB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                String m = editT.getText().toString();
                if(!m.isEmpty() && m.trim().length()>0){
                    m = m.replace(" ", "%20");
                    m = m.replace("\n","%0A");
                    getAPI api = new getAPI();
                    CheckBox c = (CheckBox)v.findViewById(R.id.beAnoy);
                    int temp = 0;
                    if(c.isChecked() == true){
                        temp = 1;
                    }
                    api.execute(new String[]{"http://10.0.2.2:3000/addPost?User=" + user+"&Msg="+m+"&An="+temp,
                            "http://10.0.2.2:3000/getPosts"});
                }
                else{
                    Toast.makeText(getContext(), "Your Post is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return v;
    }


}