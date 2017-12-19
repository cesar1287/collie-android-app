package comcesar1287.github.www.collie.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import comcesar1287.github.www.collie.R;

public class SplashScreenActivity extends AppCompatActivity {

    TextView tvGreeting;
    int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.hide();
        }

        startActivityMainDelay();

        hourText();
    }

    private void startActivityMainDelay() {
        // Show splash screen for 2 seconds
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, ConfigBlockTimeActivity.class);
                startActivity(i);
                finish(); // kill current activity
            }
        };
        new Timer().schedule(task, 2000);
    }

    public void hourText(){
        tvGreeting = findViewById(R.id.splash_screen_greeting);

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);

        if (hour>=5 && hour<12){
            tvGreeting.setText(R.string.splash_good_morning);
        }

        else if (hour>=12 && hour<18){
            tvGreeting.setText(R.string.splash_good_afternoon);
        }

        else if(hour>=18){
            tvGreeting.setText(R.string.splash_good_evening);
        }

    }
}
