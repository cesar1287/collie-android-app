package comcesar1287.github.www.collie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import comcesar1287.github.www.collie.R;

public class ReportsActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initToolbar();
        initComponent();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setTitle("Relatórios");
    }

    private void initComponent() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

        }
    }
}
