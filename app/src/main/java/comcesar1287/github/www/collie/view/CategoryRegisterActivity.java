package comcesar1287.github.www.collie.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.data.SharedPref;

public class CategoryRegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private SharedPref sharedPref;
    String typeUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_register);

        initToolbar();
        initComponent();


        sharedPref = new SharedPref(this);

        verifyUserIsLogged();
    }

    private void verifyUserIsLogged() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else{
            if(sharedPref.isFirstExecute()){
                startActivity(new Intent(this, InstructionSlideActivity.class));
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent i;

        switch (id){
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterEditActivity.class));
                finish();
                break;
            case R.id.btn_responsible:
                i = new Intent(this, SignWithActivity.class);
                i.putExtra("key", "responsible");
                startActivity(i);
                finish();
                break;
            case R.id.btn_dependent:
                i = new Intent(this, MainActivity.class);
                i.putExtra("key", "dependent");
                startActivity(i);
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
        Button btRegister = findViewById(R.id.btn_register);
        btRegister.setOnClickListener(this);
        Button btResponsible = findViewById(R.id.btn_responsible);
        btResponsible.setOnClickListener(this);
        Button btDependent = findViewById(R.id.btn_dependent);
        btDependent.setOnClickListener(this);
    }
}