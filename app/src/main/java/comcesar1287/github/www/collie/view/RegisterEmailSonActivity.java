package comcesar1287.github.www.collie.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.data.SharedPref;
import comcesar1287.github.www.collie.controller.firebase.FirebaseHelper;

public class RegisterEmailSonActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout etEmail;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private DatabaseReference mDatabase;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email_son);

        initComponents();
        initFirebase();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.register_email_son_button_send:
                attemptRegister();
                break;
            case R.id.register_email_son_button_cancel:
                finish();
                break;
        }
    }

    private void initComponents() {

        etEmail = findViewById(R.id.register_email_son_email);

        Button btSend = findViewById(R.id.register_email_son_button_send);
        btSend.setOnClickListener(this);

        Button btCancel = findViewById(R.id.register_email_son_button_cancel);
        btCancel.setOnClickListener(this);

    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void attemptRegister() {
        String email;

        boolean allFieldsFilled = true;

        email = etEmail.getEditText().getText().toString();

          
        if (email.equals("")) {
            allFieldsFilled = false;
            etEmail.setError(getString(R.string.error_required_field));
        } else {
            etEmail.setErrorEnabled(false);
        }

        if (allFieldsFilled) {
            registerChild(email, "0", "0", "0");
        }
    }

    private void registerChild(String emailChild, String block, String latitude, String longitude) {
        user = mAuth.getCurrentUser();
        FirebaseHelper.writeNewChildren(mDatabase, emailChild, user.getUid(), block, latitude, longitude);
        SharedPref sharedPref = new SharedPref(RegisterEmailSonActivity.this);
        sharedPref.setEmailChildren(emailChild);
        sharedPref.setEmailFather(user.getUid());
        sharedPref.setTypeBlock(block);
        sharedPref.setLatitude(latitude);
        sharedPref.setLongitude(longitude);
        startActivity(new Intent(RegisterEmailSonActivity.this, MainActivity.class));
        finish();
    }
}
