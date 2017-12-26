package comcesar1287.github.www.collie.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import comcesar1287.github.www.collie.R;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private ImageView plusScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initToolbar();
        initComponent();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setTitle("Agenda");
    }

    private void initComponent() {
        plusScheduler = (ImageView)findViewById(R.id.schedule_button_plus);
        plusScheduler.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.schedule_button_plus:
                startActivity(new Intent(this, AddScheduleActivity.class));
                break;
        }
    }
}
