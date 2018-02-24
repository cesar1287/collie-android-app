package comcesar1287.github.www.collie.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.PasswordAuthentication;

import comcesar1287.github.www.collie.controller.data.SharedPref;
import comcesar1287.github.www.collie.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.collie.R;

public class RegisterEditActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout etNameFather, etEmailFather, etNameChild, etAgeChild, etPassword, etConfirmPassword;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private DatabaseReference mDatabase;

    private ProgressDialog dialog;

    private String nameFather;
    private String nameChild;
    private String ageChild;

    private Boolean edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit = getIntent().getBooleanExtra("edit", false);

        initToolbar();
        initComponents();
        initFirebase();
    }

    @Override
    public void onBackPressed() {
        if(!edit) {
            startActivity(new Intent(this, CategoryRegisterActivity.class));
            finish();
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.register_finish_registration:
                attemptLogin();
                break;
        }
    }

    private void initComponents() {
        etNameFather = findViewById(R.id.register_name_father);
        etEmailFather = findViewById(R.id.register_email_father);
        if(edit) {
            etEmailFather.setEnabled(false);
        }
        etNameChild = findViewById(R.id.register_name_child);
        etAgeChild = findViewById(R.id.register_age_child);
        etPassword = findViewById(R.id.register_password_father);
        etConfirmPassword = findViewById(R.id.register_confirm_passowrd);
        Button btRegister = findViewById(R.id.register_finish_registration);
        btRegister.setOnClickListener(this);

        TextView registerTitle = findViewById(R.id.register_title);
        if(edit){
            registerTitle.setText("Edite seu cadastro");
            btRegister.setText("Salvar edição");
            SharedPref sharedPref = new SharedPref(this);
            etNameFather.getEditText().setText(sharedPref.getNameFather());
            etEmailFather.getEditText().setText(sharedPref.getEmailFather());
            etNameChild.getEditText().setText(sharedPref.getNameChild());
            etAgeChild.getEditText().setText(sharedPref.getAgeChild());
        }
    }


    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.hide();
        }
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void attemptLogin() {
        String emailFather, password, confirmPassword;

        nameFather = etNameFather.getEditText().getText().toString();
        emailFather = etEmailFather.getEditText().getText().toString();
        nameChild = etNameChild.getEditText().getText().toString();
        ageChild = etAgeChild.getEditText().getText().toString();
        password = etPassword.getEditText().getText().toString();
        confirmPassword = etConfirmPassword.getEditText().getText().toString();

        boolean allFieldsFilled = true;
        boolean allFilledCorrectly = true;

        if (nameFather.equals("")) {
            allFieldsFilled = false;
            etNameFather.setError(getString(R.string.error_required_field));
        } else {
            etNameFather.setErrorEnabled(false);
        }

        if (emailFather.equals("")) {
            allFieldsFilled = false;
            etEmailFather.setError(getString(R.string.error_required_field));
        } else {
            etEmailFather.setErrorEnabled(false);
        }

        if (nameChild.equals("")) {
            allFieldsFilled = false;
            etNameChild.setError(getString(R.string.error_required_field));
        } else {
            etNameChild.setErrorEnabled(false);
        }

        if (ageChild.equals("")) {
            allFieldsFilled = false;
            etAgeChild.setError(getString(R.string.error_required_field));
        } else {
            etAgeChild.setErrorEnabled(false);
        }

        if (password.equals("")) {
            allFieldsFilled = false;
            etPassword.setError(getString(R.string.error_required_field));
        } else {
            etPassword.setErrorEnabled(false);
        }

        if (confirmPassword.equals("")) {
            allFieldsFilled = false;
            etConfirmPassword.setError(getString(R.string.error_required_field));
        } else {
            etConfirmPassword.setErrorEnabled(false);
        }

        if (allFieldsFilled) {
            if(!password.equals(confirmPassword)){
                allFilledCorrectly = false;
                etPassword.setError(getString(R.string.error_passwords_not_match));
                etPassword.getEditText().requestFocus();
                etConfirmPassword.setError(getString(R.string.error_passwords_not_match));
            }else{
                etConfirmPassword.setErrorEnabled(false);
            }
        }

        if(allFieldsFilled && allFilledCorrectly){
            if(!edit) {
                registerUser(emailFather, password);
            }
            else {
                editUser(password, nameFather, nameChild, ageChild);
            }
        }
    }

    private void editUser(final String password, String nameFather, String nameChild, String ageChild) {
        user = mAuth.getCurrentUser();
        FirebaseHelper.writeNewUser(mDatabase, mAuth.getCurrentUser().getUid(), nameFather, nameChild, ageChild);
        SharedPref sharedPref = new SharedPref(RegisterEditActivity.this);
        sharedPref.setNameFather(nameFather);
        sharedPref.setEmailFather(user.getEmail());
        sharedPref.setNameChild(nameChild);
        sharedPref.setAgeChild(ageChild);
        user.updatePassword(password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthWeakPasswordException) {
                            etPassword.setError(getString(R.string.error_password_too_small));
                            etPassword.requestFocus();
                        } else if (e instanceof FirebaseAuthRecentLoginRequiredException) {
                            etPassword.setError(getString(R.string.error_passwords_not_match));
                            etPassword.requestFocus();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            etPassword.setError(getString(R.string.error_invalid_email));
                            etPassword.requestFocus();
                        } else {
                            Toast.makeText(RegisterEditActivity.this,
                                    getResources().getString(R.string.error_unknown_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterEditActivity.this, getString(R.string.edit_user), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        startActivity(new Intent(RegisterEditActivity.this, MainActivity.class));
        finish();
    }

    private void registerUser(String emailFather, String password) {
        dialog = ProgressDialog.show(this,"", getResources().getString(R.string.sign_up_user), true, false);
        mAuth.createUserWithEmailAndPassword(emailFather, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        if (e instanceof FirebaseAuthWeakPasswordException) {
                            etPassword.setError(getString(R.string.error_password_too_small));
                            etPassword.requestFocus();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            etEmailFather.setError(getString(R.string.error_invalid_email));
                            etEmailFather.getEditText().setText("");
                            etEmailFather.requestFocus();
                        } else if (e instanceof FirebaseAuthUserCollisionException) {
                            etEmailFather.setError(getString(R.string.error_failed_signin_email_exists));
                            etEmailFather.getEditText().setText("");
                            etEmailFather.requestFocus();
                        } else {
                            Toast.makeText(RegisterEditActivity.this,
                                    getResources().getString(R.string.error_unknown_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();

                            if (user != null) {
                                FirebaseHelper.writeNewUser(mDatabase, mAuth.getCurrentUser().getUid(), nameFather, nameChild, ageChild);
                                Toast.makeText(RegisterEditActivity.this, getString(R.string.successfully_registered), Toast.LENGTH_SHORT).show();
                                SharedPref sharedPref = new SharedPref(RegisterEditActivity.this);
                                sharedPref.setNameFather(nameFather);
                                sharedPref.setEmailFather(user.getEmail());
                                sharedPref.setNameChild(nameChild);
                                sharedPref.setAgeChild(ageChild);
                                startActivity(new Intent(RegisterEditActivity.this, SetupScreenActivity.class));
                                finish();
                            }
                        }
                    }
                });
    }
}
