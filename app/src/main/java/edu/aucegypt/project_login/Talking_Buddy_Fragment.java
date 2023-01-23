package edu.aucegypt.project_login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.SingleDateSelector;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Talking_Buddy_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Talking_Buddy_Fragment extends Fragment {

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


        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setPositiveButtonText("Next")
                        .build();
                datePicker.show(getFragmentManager(), "Material_Date_Picker");
                String next = "Next";
                datePicker.addOnPositiveButtonClickListener (new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance();
                        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).
                                setHour(calendar.get(Calendar.HOUR_OF_DAY))
                                .setMinute(calendar.get(Calendar.MINUTE))
                                .setPositiveButtonText("Confirm")
                                .build();
                        materialTimePicker.show(getFragmentManager(), "Time");
                        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int Hour  = materialTimePicker.getHour();
                                int Minute = materialTimePicker.getMinute();
                            }
                        });
                    }
                    });
                };
        });
        Schedule.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showOptionsDialogue();
                                        }

            private void showOptionsDialogue() {
                final String[] selected = {"male"};
                                            String [] genders = {"one", "two", "three"};
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setTitle("Choose Gender");
                                            builder.setSingleChoiceItems(genders, 0, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int which) {
                                                    selected[0] = genders[which];
                                                }
                                            });
                                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            builder.show();



            }
        });
                // Inflate the layout for this fragment
        return view;
    }
}