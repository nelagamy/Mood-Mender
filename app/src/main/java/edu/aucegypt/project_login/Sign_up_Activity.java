package edu.aucegypt.project_login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_up_Activity extends AppCompatActivity {
    EditText eMail;
    EditText userName;
    EditText passWord;
    EditText rePassword;
    Button signUp;
    int msg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eMail = findViewById(R.id.email_t);
        userName = findViewById(R.id.UniqueUser_t);
        passWord = findViewById(R.id.UniquePass_t);
        rePassword = findViewById(R.id.ReUniquePass_t);
        signUp = findViewById(R.id.signUP_B);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = 0;
                Pattern p = Pattern.compile("@aucegypt.edu");
                Matcher m = p.matcher(eMail.getText().toString());
                Pattern p2 = Pattern.compile("");
                Matcher m2 = p2.matcher(eMail.getText().toString());

                if (eMail.getText().toString().isEmpty() ||
                        userName.getText().toString().isEmpty() ||
                        passWord.getText().toString().isEmpty() ||
                        rePassword.getText().toString().isEmpty()) {
                    Toast.makeText(Sign_up_Activity.this, "Please Enter all the data", Toast.LENGTH_SHORT).show();
                } else {
                    if (m.find()) {
                        getAPI api = new getAPI();
                        api.execute(new String[]{"http://10.0.2.2:3000/checkEmail?Email=" + eMail.getText().toString(),
                                "http://10.0.2.2:3000/checkUser?User=" + userName.getText().toString(),
                                "http://10.0.2.2:3000/reg?User=" + userName.getText().toString() +
                                        "&Pass=" + passWord.getText().toString() + "&Email=" + eMail.getText().toString()});

                    }

                    else {
                        Toast.makeText(Sign_up_Activity.this, "invalid email", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
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

                if (jArr.getInt(0) == 0) {
                    msg = 1;
                } else {
                    try {
                        request.setURI(new URI(Url[1]));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    response = client.execute(request);
                    in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    inputLine = in.readLine();
                    jArr = new JSONArray(inputLine);

                    if (jArr.getInt(0) == 0) {
                        msg = 2;
                    } else {
                        if (passWord.getText().toString().equals(rePassword.getText().toString())) {
                            try {
                                request.setURI(new URI(Url[2]));
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }

                            response = client.execute(request);
                            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                            inputLine = in.readLine();


                            jArr = new JSONArray(inputLine);
                            Intent login = new Intent(Sign_up_Activity.this , Login_Activity.class);
                            startActivity(login);
                        }
                        else {
                            msg = 3;
                        }


                    }
                }


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return jArr;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(msg == 1){
                Toast.makeText(Sign_up_Activity.this, "Email is already taken", Toast.LENGTH_SHORT).show();
            }
            else if(msg == 2){
                Toast.makeText(Sign_up_Activity.this, "Username is already taken", Toast.LENGTH_SHORT).show();
            }
            else if (msg == 3){
                Toast.makeText(Sign_up_Activity.this, "invalid matching passwords", Toast.LENGTH_SHORT).show();
            }
        }
    }
}