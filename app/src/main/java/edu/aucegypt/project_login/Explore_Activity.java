package edu.aucegypt.project_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Explore_Activity extends AppCompatActivity {

    ArrayList<String> msgs = Add_Activity.msgs;
     madeAdaptor a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_posts);

        ListView list = (ListView)findViewById(R.id.lists1);

        a =new madeAdaptor(this, msgs);


        list.setAdapter(a);

    }


    private class madeAdaptor extends ArrayAdapter<String> {

        public madeAdaptor(Context context, ArrayList<String> msgs) {
            super(context,0, msgs);
        }

        @Override
        public View getView(int p, View v, ViewGroup g) {
            View v1 = getLayoutInflater().inflate(R.layout.posts_list, null);
            TextView country_name =   v1.findViewById(R.id.a_post);

            country_name.setText(msgs.get(p));


            return v1;
        }


    }

}