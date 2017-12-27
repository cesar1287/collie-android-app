package comcesar1287.github.www.collie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import comcesar1287.github.www.collie.R;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        initToolbar();
    }

    private void initToolbar() {
        setTitle("Tarefas");
    }
}
