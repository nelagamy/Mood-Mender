package edu.aucegypt.project_login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class Login_Activity extends AppCompatActivity {
    EditText username;
    EditText password;
    TextView signup;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.user_t);
        password = findViewById(R.id.pass_t);
        signup = findViewById(R.id.signup_T);
        login = findViewById(R.id.login_B);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                //startActivity(intent);
                getAPI data = new getAPI();
                data.execute(new String[]{"http://10.0.2.2:3000/users?User="+username.getText().toString()+"&Pass="+password.getText().toString()});
            }

            class getAPI extends AsyncTask<String,Void, JSONArray>
            {
                JSONArray jArr;

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

                        jArr = new JSONArray(inputLine);



                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    return jArr;
                }

                @Override
                protected void onPostExecute(JSONArray jArr)
                {
                    if(jArr.length() != 0)
                    {
                        Toast.makeText(Login_Activity.this,"valid input",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Login_Activity.this,"invalid input",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Login_Activity.this, Sign_up_Activity.class);
                startActivity(intent);
            }
        });
    }
}