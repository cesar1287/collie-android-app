package comcesar1287.github.www.collie.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.data.SharedPref;

public class    SonSignWithSActivityActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout etPasswordPhone;

    private FirebaseAuth mAuth;

    private ProgressDialog dialog;

    private DatabaseReference mDatabase;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son_sign_with_activity);

        initToolbar();
        initComponents();
        initFirebase();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.son_sign_with_btn_send:
                attemptLogin();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CategoryRegisterActivity.class));
        finish();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.hide();
        }
    }

    private void initComponents() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        Utils.init(getApplication());

        etPasswordPhone = findViewById(R.id.son_sign_with_phone);

        Button btContinue = findViewById(R.id.son_sign_with_btn_send);
        btContinue.setOnClickListener(this);

    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void attemptLogin() {
        final SharedPref sharedPref = new SharedPref(SonSignWithSActivityActivity.this);
        String password = etPasswordPhone.getEditText().getText().toString();

        boolean allFieldsFilled = true;

        if (EmptyUtils.isEmpty(password)) {
            allFieldsFilled = false;
            etPasswordPhone.setError(getString(R.string.error_required_field));
        }else if (!sharedPref.getEmailChildren().equals(password)) {
            allFieldsFilled = false;
            Log.d("PASSS", password);
            Log.d("EMAILLL", sharedPref.getEmailChildren());
            etPasswordPhone.setError("Falha ao entrar, o número do dependente não está cadastrado por esse responsável");
        } else {
            etPasswordPhone.setErrorEnabled(false);
        }

        if (allFieldsFilled) {
            if(NetworkUtils.isConnected()) {
                startActivity(new Intent(SonSignWithSActivityActivity.this, MainActivity.class));
                finish();
            }else{
                Toast.makeText(this, "Falha ao entrar, sem conexão com a internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String msg(){
        String user;

        Bundle extras = getIntent().getExtras();
        user = extras.getString("key");

        return user;
    }
}
