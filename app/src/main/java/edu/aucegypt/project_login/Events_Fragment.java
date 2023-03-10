package edu.aucegypt.project_login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
 * Use the {@link Events_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Events_Fragment extends Fragment {

    View view;
    ArrayList<String> event_data =  new ArrayList<String>();
    ListView listing;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Events_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Events_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Events_Fragment newInstance(String param1, String param2) {
        Events_Fragment fragment = new Events_Fragment();
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
        view =  inflater.inflate(R.layout.fragment_events_, container, false);

        display();

        return view;
    }

    public void display()
    {
        getAPI data = new getAPI();
        data.execute(new String[]{"http://10.0.2.2:3000/getEvents"});

    }

    class getAPI extends AsyncTask<String,Void, JSONArray> {
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
        protected void onPostExecute(JSONArray jArr)
        {
            String Ename,Edate,Etime;
            if(jArr.length() != 0)
            {
                for (int i = 0; i < jArr.length(); i++)
                {
                    try
                    {
                        Ename = jArr.getJSONObject(i).getString("E_name");
                        Edate = jArr.getJSONObject(i).getString("E_date");
                        Etime = jArr.getJSONObject(i).getString("E_time");
                        event_data.add("- "+Ename+"\t "+"("+Edate+",\t"+Etime+")");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                EventsAdapter Events_adapter = new EventsAdapter();
                listing =  view.findViewById(R.id.Events_list_view);
                listing.setAdapter(Events_adapter);
            }
            else
            {
                event_data.clear();
                event_data.add("No events");
            }
        }
    }
    private class EventsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return event_data.size();
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

            TextView event = view.findViewById(R.id.events);

            String[] arr = event_data.toArray(new String[0]);
            event.setText(arr[position]);

            return view;

        }
    }
}