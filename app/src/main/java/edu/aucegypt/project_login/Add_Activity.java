package edu.aucegypt.project_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class Add_Activity extends AppCompatActivity {
    static ArrayList<String> msgs = new ArrayList<String>();
    EditText editT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editT = (EditText)findViewById(R.id.e1);
    }

    public void post_msg(View v){
        String newPost = editT.getText().toString();
        if(!newPost.isEmpty() && newPost.trim().length()>0){
            msgs.add(newPost);
        }
        editT.setText("");
        Intent intent = new Intent(this, Explore_Activity.class);
        startActivity(intent);
    }
}