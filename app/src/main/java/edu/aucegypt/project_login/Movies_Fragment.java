package edu.aucegypt.project_login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Movies_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Movies_Fragment extends Fragment {

    View view;
    Button rando;
    ArrayList<String> movies_data =  new ArrayList<String>();
    ArrayList<String> tv_data =  new ArrayList<String>();
    ListView Mlisting;
    ListView Tlisting;
    ArrayList<String> temp =  new ArrayList<String>();
    ArrayList<String> temp2 =  new ArrayList<String>();
    String sendT,sendN,sendM;
    boolean pass1 = false, pass3 = false;


    String Mood = Home_Activity.chosen_mood;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Movies_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Movies_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Movies_Fragment newInstance(String param1, String param2) {
        Movies_Fragment fragment = new Movies_Fragment();
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
        view = inflater.inflate(R.layout.fragment_movies_, container, false);
        rando = view.findViewById(R.id.rand);


        display();

        rando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp != null && temp2 != null && movies_data != null && tv_data != null )
                {
                    temp.clear();
                    temp2.clear();
                    movies_data.clear();
                    tv_data.clear();
                }
                display();
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.CaddB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(getContext());
                AlertDialog.Builder mydialog2 = new AlertDialog.Builder(getContext());
                AlertDialog.Builder mydialog3 = new AlertDialog.Builder(getContext());

                mydialog.setTitle("Add Recommendation");
                mydialog2.setTitle("Add Recommendation");
                mydialog3.setTitle("Add Recommendation");

                EditText txt1 = new EditText(getContext());
                EditText txt2 = new EditText(getContext());
                EditText txt3 = new EditText(getContext());

                mydialog.setView(txt1);
                mydialog2.setView(txt2);
                mydialog3.setView(txt3);

                txt1.setHint("Enter Movie or TvShow");
                txt2.setHint("Enter Name");
                txt3.setHint("Enter either Happy, Sad or Neutral Mood");


                mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendT = txt1.getText().toString();
                        if(!sendT.toLowerCase(Locale.ROOT).equals("movie") &&
                                !sendT.toLowerCase(Locale.ROOT).equals("tvshow"))
                        {
                            Toast.makeText(getContext(), "invalid type selection", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mydialog2.show();
                        }


                    }
                });
                mydialog2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendN = txt2.getText().toString();
                        mydialog3.show();


                    }
                });
                mydialog3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendM = txt3.getText().toString();
                        if(!sendM.toLowerCase(Locale.ROOT).equals("happy") &&
                                !sendM.toLowerCase(Locale.ROOT).equals("sad") &&
                                !sendM.toLowerCase(Locale.ROOT).equals("neutral"))
                        {
                            Toast.makeText(getContext(), "invalid mood selection", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(sendT.toLowerCase(Locale.ROOT).equals("movie"))
                            {
                                sendT =  "Movie";
                            }
                            if(sendT.toLowerCase(Locale.ROOT).equals("tvshow"))
                            {
                                sendT =  "TvShow";
                            }

                            if(sendM.toLowerCase(Locale.ROOT).equals("happy"))
                            {
                                sendM = "Happy";
                            }
                            if(sendM.toLowerCase(Locale.ROOT).equals("sad"))
                            {
                                sendM = "Sad";
                            }
                            if(sendM.toLowerCase(Locale.ROOT).equals("neutral"))
                            {
                                sendM = "Neutral";
                            }
                            setAPI Data = new setAPI();
                            String check = "http://10.0.2.2:3000/setMovShows?name="
                                    +sendN+"&type="+sendT+"&mood="+sendM;
                            check = check.replace(" ", "%20");
                            Data.execute(new String[] {check});
                        }


                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mydialog2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mydialog3.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mydialog.show();
            }
        });


        return view;
    }


    public void display()
    {
        getAPI data = new getAPI();
        data.execute(new String[]{"http://10.0.2.2:3000/getMovShows?type=Movie&mood="+Mood,
                                "http://10.0.2.2:3000/getMovShows?type=TvShow&mood="+Mood});
    }



    class getAPI extends AsyncTask<String,Void, JSONArray> {
        JSONArray jArrM;
        JSONArray jArrT;

        @SuppressLint("WrongThread")
        @Override
        protected JSONArray doInBackground(String... Url) {
            try
            {

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

                jArrM = new JSONArray(inputLine);
                ////---------------------------------
                HttpClient client2 = new DefaultHttpClient();
                HttpGet request2 = new HttpGet();
                try {
                    request2.setURI(new URI(Url[1]));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                HttpResponse response2 = client2.execute(request2);
                BufferedReader in2 = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
                String inputLine2 = in2.readLine();

                jArrT = new JSONArray(inputLine2);


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return jArrM;
        }
        @Override
        protected void onPostExecute(JSONArray jArrM)
        {
            String Mname;
            String Tname;
            for (int i = 0; i < jArrM.length(); i++)
            {
                try
                {
                    Mname = jArrM.getJSONObject(i).getString("MovShows_name");
                    movies_data.add("- "+Mname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            int min = 0;
            int max = movies_data.size()-1;
            int random1 = new Random().nextInt((max - min) + 1) + min;
            int random2 = new Random().nextInt((max - min) + 1) + min;
            while(random2 == random1)
            {
                random2 = new Random().nextInt((max - min) + 1) + min;
            }
            int random3 = new Random().nextInt((max - min) + 1) + min;
            while(random3 == random2 || random3 == random1)
            {
                random3 = new Random().nextInt((max - min) + 1) + min;
            }
            temp.add(movies_data.get(random1));
            temp.add(movies_data.get(random2));
            temp.add(movies_data.get(random3));

            MoviesAdapter Movies_adapter = new MoviesAdapter();
            Mlisting =  view.findViewById(R.id.movies_list_view);
            Mlisting.setAdapter(Movies_adapter);
            //------------/////

            for (int i = 0; i < jArrT.length(); i++)
            {
                try
                {
                    Tname = jArrT.getJSONObject(i).getString("MovShows_name");
                    tv_data.add("- "+Tname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
             int max2 = tv_data.size()-1;
             int random1_2 = new Random().nextInt((max2 - min) + 1) + min;
             int random2_2 = new Random().nextInt((max2 - min) + 1) + min;
            while(random2_2 == random1_2)
            {
                random2_2 = new Random().nextInt((max2 - min) + 1) + min;
            }
             int random3_2 = new Random().nextInt((max2 - min) + 1) + min;
            while(random3_2 == random2_2 || random3_2 == random1_2)
            {
                random3_2 = new Random().nextInt((max2 - min) + 1) + min;
            }
            temp2.add(tv_data.get(random1_2));
            temp2.add(tv_data.get(random2_2));
            temp2.add(tv_data.get(random3_2));

            TvAdapter tv_adapter = new TvAdapter();
            Tlisting =  view.findViewById(R.id.TvShows_list_view);
            Tlisting.setAdapter(tv_adapter);
        }
    }

    class setAPI extends AsyncTask<String,Void, JSONArray> {
        JSONArray JArr;

        @SuppressLint("WrongThread")
        @Override
        protected JSONArray doInBackground(String... Url) {
            try
            {

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

                JArr = new JSONArray(inputLine);



            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return JArr;
        }

    }

    private class MoviesAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View view = getLayoutInflater().inflate(R.layout.list_events,null);

            TextView movie = view.findViewById(R.id.events);
            movie.setText(temp.get(position));

            return view;

        }
    }
    private class TvAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View view = getLayoutInflater().inflate(R.layout.list_events,null);

            TextView tv = view.findViewById(R.id.events);
            tv.setText(temp2.get(position));

            return view;

        }
    }
}