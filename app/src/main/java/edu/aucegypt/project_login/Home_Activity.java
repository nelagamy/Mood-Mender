package edu.aucegypt.project_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class Home_Activity extends AppCompatActivity {
// This is Matar
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    String[] moods = {"Happy","Sad","Neutral"};
    String[] rem;
    String[] Hap;
    String[] Sad;
    String[] Neu;
    int min,max1,max2,random1,random2;
    static String chosen_mood = " ";
    AutoCompleteTextView auto_complete_txt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button1 = findViewById(R.id.emotional_B);
        button2 = findViewById(R.id.entertain_B);
        button3 = findViewById(R.id.quote_B);
        button4 = findViewById(R.id.reminder_B);

        button1.setEnabled(false);
        button2.setEnabled(false);

        Resources res = getResources();
        rem = res.getStringArray(R.array.Reminders);
        Hap = res.getStringArray(R.array.Happy_Q);
        Sad = res.getStringArray(R.array.Sad_Q);
        Neu = res.getStringArray(R.array.Neutral_Q);



        auto_complete_txt =  findViewById(R.id.auto_complete_text);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_mood,moods);

        auto_complete_txt.setAdapter(adapterItems);

        auto_complete_txt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                button1.setEnabled(true);
                button2.setEnabled(true);
                chosen_mood = parent.getItemAtPosition(position).toString();
                switch (chosen_mood)
                {
                    case "Happy":
                         min = 0;
                         max1 = Hap.length-1;
                         max2 = rem.length-1;
                         random1 = new Random().nextInt((max1 - min) + 1) + min;
                         random2 = new Random().nextInt((max2 - min) + 1) + min;
                        button3.setText(Hap[random1]);
                        button4.setText(rem[random2]);
                        break;
                    case "Sad":
                         min = 0;
                         max1 = Sad.length-1;
                         max2 = rem.length-1;
                         random1 = new Random().nextInt((max1 - min) + 1) + min;
                         random2 = new Random().nextInt((max2 - min) + 1) + min;
                        button3.setText(Sad[random1]);
                        button4.setText(rem[random2]);
                        break;
                    case "Neutral":
                         min = 0;
                         max1 = Neu.length-1;
                         max2 = rem.length-1;
                         random1 = new Random().nextInt((max1 - min) + 1) + min;
                         random2 = new Random().nextInt((max2 - min) + 1) + min;
                        button3.setText(Neu[random1]);
                        button4.setText(rem[random2]);
                        break;
                }
                button3.setVisibility(View.VISIBLE);
                button4.setVisibility(View.VISIBLE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home_Activity.this, Entertainment_Activity.class);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Activity.this, Emotional_Support_Activity.class);
                startActivity(intent);
            }
        });


    }
}