package edu.aucegypt.project_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen_Activity extends AppCompatActivity {

    private static final int splash_screen_timeout = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Animation fadOut = new AlphaAnimation(1,0);
        fadOut.setInterpolator(new AccelerateDecelerateInterpolator());
        fadOut.setStartOffset(800);
        fadOut.setDuration(2000);

        TextView text1  = findViewById(R.id.welcome_t);
        TextView text2 = findViewById(R.id.welcome2_t);
        ImageView image = findViewById(R.id.comfort_image);

        text1.setAnimation(fadOut);
        text2.setAnimation(fadOut);
        image.setAnimation(fadOut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        },splash_screen_timeout);

    }
}