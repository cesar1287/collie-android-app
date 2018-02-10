package comcesar1287.github.www.collie.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comcesar1287.github.www.collie.R;

public class CategoryRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_register);

        initToolbar();
        initComponent();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btn_responsible:
                startActivity(new Intent(this, SignWithActivity.class));
                finish();
                break;
            case R.id.btn_dependent:
                startActivity(new Intent(this, RegisterEditActivity.class));
                finish();
                break;
        }
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.hide();
        }
    }

    private void initComponent() {
        Button btResponsible = findViewById(R.id.btn_responsible);
        btResponsible.setOnClickListener(this);
        Button btDependent = findViewById(R.id.btn_dependent);
        btDependent.setOnClickListener(this);
    }
}
