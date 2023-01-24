package edu.aucegypt.project_login;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
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
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Talking_Buddy_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Talking_Buddy_Fragment extends Fragment {
    boolean choice = false;
    int chosen;
    int option;
    ArrayList<String> scheds;
    ArrayList<String> user1s;
    ArrayList<String> cals;
    ArrayList<String> clocks;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Talking_Buddy_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Talking_Buddy_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Talking_Buddy_Fragment newInstance(String param1, String param2) {
        Talking_Buddy_Fragment fragment = new Talking_Buddy_Fragment();
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
        View view;
        view = inflater.inflate(R.layout.fragment_talking__buddy_, container, false);

        Button ADD = (Button) view.findViewById(R.id.Add);
        Button Schedule = (Button) view.findViewById(R.id.Schedule);
        Button yourSchedule = (Button) view.findViewById(R.id.yourSchedule);


        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = 1;
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setPositiveButtonText("Next")
                        .build();
                datePicker.show(getFragmentManager(), "Material_Date_Picker");
                String next = "Next";
                Calendar calendar = Calendar.getInstance();
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        String datee = datePicker.getHeaderText();
                        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).
                                setHour(calendar.get(Calendar.HOUR_OF_DAY))
                                .setMinute(calendar.get(Calendar.MINUTE))
                                .setPositiveButtonText("Confirm")
                                .build();
                        materialTimePicker.show(getFragmentManager(), "Time");
                        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int Hour = materialTimePicker.getHour();
                                int Minute = materialTimePicker.getMinute();

                                String clock = String.valueOf(Hour) + ":" + String.valueOf(Minute);

                                getAPI api = new getAPI();
                                String temp_api = "http://10.0.2.2:3000/addSched?User1=" + Login_Activity.user +
                                        "&Cal=" + datee + "&Time=" + clock;
                                temp_api = temp_api.replace(" ", "%20");
                                api.execute(temp_api);
                            }

                        });
                    }
                });
            }

            ;
        });
        Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                option = 2;
                getAPI api = new getAPI();
                String temp_api = "http://10.0.2.2:3000/getSched";
                api.execute(temp_api);
            }


        });

        yourSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                option = 4;
                getAPI api = new getAPI();
                String temp_api = "http://10.0.2.2:3000/getSched";
                api.execute(temp_api);
            }


        });
        // Inflate the layout for this fragment
        return view;
    }

    public void showOptionsDialogue() {
//        final String[] selected = {"male"};

        String [] sc = scheds.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose an Appointment");
        builder.setSingleChoiceItems(sc, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
//                selected[0] = sc[which];
                chosen = which;
                choice = true;
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(scheds.size()!=0){
                    if(Login_Activity.user.equals(user1s.get(chosen))){
                        Toast.makeText(getContext(), "You cant do that", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(choice){
                            option = 3;
                            getAPI api = new getAPI();
                            String temp_api = "http://10.0.2.2:3000/selectSched?User1="+user1s.get(chosen)
                                    +"&User2="+Login_Activity.user+"&Cal="+cals.get(chosen)
                                    +"&Time="+clocks.get(chosen);
                            temp_api = temp_api.replace(" ", "%20");
                            api.execute(temp_api);
                        }
                        else{
                            Toast.makeText(getContext(), "You need to choose", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                choice = false;
                dialogInterface.dismiss();
            }
        }).setNegativeButton("Close",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = false;
            }
        });
        builder.show();}


    public void showOptions2Dialogue() {
//        final String[] selected = {"male"};

        String [] sc = scheds.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Your Appointments");
        builder.setSingleChoiceItems(sc, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
//                selected[0] = sc[which];
                chosen = which;
                choice = true;
            }
        });
        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(scheds.size()!=0){
                    option = 5;
                    if(choice){
                        getAPI api = new getAPI();
                        String temp_api = "http://10.0.2.2:3000/delSched?User1="+user1s.get(chosen)
                                +"&User2="+Login_Activity.user+"&Cal="+cals.get(chosen)
                                +"&Time="+clocks.get(chosen);
                        temp_api = temp_api.replace(" ", "%20");
                        api.execute(temp_api);

                    }
                    else{
                        Toast.makeText(getContext(), "Choose Something", Toast.LENGTH_SHORT).show();
                    }

                }
                choice = false;
                dialogInterface.dismiss();
            }
        }).setNegativeButton("Close",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = false;
            }
        });
        builder.show();}







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
            scheds = new ArrayList<String>();
            user1s = new ArrayList<String>();
            cals= new ArrayList<String>();
            clocks= new ArrayList<String>();
            if(option == 2){
                int size = jArr.length();
                for(int i = 0; i<size; i++){
                    try {
                        int reg = jArr.getJSONObject(i).getInt("reg");
                        if(reg == 0){
                            String user1 = jArr.getJSONObject(i).getString("user1");
                            String cal =jArr.getJSONObject(i).getString("calender");
                            String clock = jArr.getJSONObject(i).getString("clock");
                            String full = user1 + "  " + cal + " "+clock;
                            user1s.add(user1);
                            cals.add(cal);
                            clocks.add(clock);
                            scheds.add(full);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                showOptionsDialogue();
            }
            else if (option == 4){
                int size = jArr.length();
                for(int i = 0; i<size; i++){
                    try {
                        int reg = jArr.getJSONObject(i).getInt("reg");
                        if(reg == 1){
                            String user2 = jArr.getJSONObject(i).getString("user2");
                            if(user2.equals(Login_Activity.user)){
                                String user1 = jArr.getJSONObject(i).getString("user1");
                                String cal =jArr.getJSONObject(i).getString("calender");
                                String clock = jArr.getJSONObject(i).getString("clock");
                                String full = user1 + "  " + cal + " "+clock;
                                user1s.add(user1);
                                cals.add(cal);
                                clocks.add(clock);
                                scheds.add(full);
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                showOptions2Dialogue();
            }
        }
    }
}