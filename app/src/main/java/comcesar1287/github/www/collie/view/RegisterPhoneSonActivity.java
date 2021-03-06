package comcesar1287.github.www.collie.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.data.SharedPref;
import comcesar1287.github.www.collie.controller.firebase.FirebaseHelper;

public class RegisterPhoneSonActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout etPhone;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private DatabaseReference mDatabase;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone_son);

        initComponents();
        initFirebase();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.register_phone_son_button_send:
                attemptRegister();
                break;
            case R.id.register_phone_son_button_cancel:
                finish();
                break;
        }
    }

    private void initComponents() {

        etPhone = findViewById(R.id.register_phone_son_email);

        Button btSend = findViewById(R.id.register_phone_son_button_send);
        btSend.setOnClickListener(this);

        Button btCancel = findViewById(R.id.register_phone_son_button_cancel);
        btCancel.setOnClickListener(this);

    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void attemptRegister() {
        String phone;

        boolean allFieldsFilled = true;

        phone = etPhone.getEditText().getText().toString();


        if (phone.equals("")) {
            allFieldsFilled = false;
            etPhone.setError(getString(R.string.error_required_field));
        } else {
            etPhone.setErrorEnabled(false);
        }

        if (allFieldsFilled) {
            registerChild(phone, "0", "0", "0");
        }
    }

    private void registerChild(String emailChild, String block, String latitude, String longitude) {
        user = mAuth.getCurrentUser();
        FirebaseHelper.writeNewChildren(mDatabase, emailChild, user.getUid(), block, latitude, longitude);
        SharedPref sharedPref = new SharedPref(RegisterPhoneSonActivity.this);
        sharedPref.setEmailChildren(emailChild);
        sharedPref.setEmailFather(user.getEmail());
        sharedPref.setTypeBlock(block);
        sharedPref.setLatitude(latitude);
        sharedPref.setLongitude(longitude);
        startActivity(new Intent(RegisterPhoneSonActivity.this, MainActivity.class));
        finish();
    }
}
