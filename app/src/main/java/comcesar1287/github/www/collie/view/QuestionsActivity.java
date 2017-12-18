package comcesar1287.github.www.collie.view;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import comcesar1287.github.www.collie.R;

public class QuestionsActivity extends AppCompatActivity {

    TextView time_regressive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        initComponents();
        countdown();
    }

    private void initComponents() {
        time_regressive = (TextView) findViewById(R.id.question_time);
    }

    private void countdown(){
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                time_regressive.setText(""+millisUntilFinished / 1000);
            }

            public void onFinish() {

            }
        }.start();
    }
}
