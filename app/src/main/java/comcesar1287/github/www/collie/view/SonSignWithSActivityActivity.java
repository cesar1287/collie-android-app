package comcesar1287.github.www.collie.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.data.SharedPref;

public class SonSignWithSActivityActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout etPasswordPhone;

    private FirebaseAuth mAuth;

    private ProgressDialog dialog;

    private DatabaseReference mDatabase;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone_son);

        initToolbar();
        initComponents();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.son_sign_with_btn_send:
                //attemptLogin();
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

        etPasswordPhone = findViewById(R.id.son_sign_with_edit_phone);

        Button btContinue = findViewById(R.id.sign_with_btn_send);
        btContinue.setOnClickListener(this);

    }

//    private void attemptLogin() {
//        String password = etPasswordPhone.getEditText().getText().toString();
//
//        boolean allFieldsFilled = true;
//
//
//        if (EmptyUtils.isEmpty(password)) {
//            allFieldsFilled = false;
//            etPasswordPhone.setError(getString(R.string.error_required_field));
//        }else{
//            etPasswordPhone.setErrorEnabled(false);
//        }
//
//        if (allFieldsFilled) {
//            if(NetworkUtils.isConnected()) {
//                makeLoginRequest(password);
//            }else{
//                Toast.makeText(this, "Falha ao entrar, sem conexão com a internet", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void makeLoginRequest(String email, String password) {
//        dialog = ProgressDialog.show(SonSignWithSActivityActivity.this,"",
//                SonSignWithSActivityActivity.this.getResources().getString(R.string.processing_login), true, false);
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnFailureListener(SonSignWithSActivityActivity.this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        dialog.dismiss();
//                        if(e instanceof FirebaseAuthInvalidUserException){
//                            Toast.makeText(SonSignWithSActivityActivity.this,
//                                    getResources().getString(R.string.error_user_password_incorrect),
//                                    Toast.LENGTH_SHORT).show();
//                        }else if(e instanceof FirebaseAuthInvalidCredentialsException) {
//                            Toast.makeText(SonSignWithSActivityActivity.this,
//                                    getResources().getString(R.string.error_user_password_incorrect),
//                                    Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(SonSignWithSActivityActivity.this,
//                                    getResources().getString(R.string.error_unknown_error),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnCompleteListener(SonSignWithSActivityActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        dialog.dismiss();
//                        if (task.isSuccessful()) {
//                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.child("users")
//                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                            .child("block")
//                                            .exists()) {
//                                        SharedPref sharedPref = new SharedPref(SonSignWithSActivityActivity.this);
//                                        sharedPref.setTypeBlock((String) dataSnapshot.child("users")
//                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                .child("block").getValue());
//
//                                        Intent i = new Intent(SonSignWithSActivityActivity.this, MainActivity.class);
//                                        prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor ed = prefs.edit();
//                                        ed.putString("key", msg());
//                                        ed.apply();
//                                        startActivity(i);
//                                        finish();
//
//                                    } else {
//                                        Intent i = new Intent(SonSignWithSActivityActivity.this, SetupScreenActivity.class);
//                                        prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor ed = prefs.edit();
//                                        ed.putString("key", msg());
//                                        ed.apply();
//                                        startActivity(i);
//                                        finish();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    Toast.makeText(SonSignWithSActivityActivity.this,
//                                            getResources().getString(R.string.error_unknown_error),
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//                });
//    }

    public String msg(){
        String user;

        Bundle extras = getIntent().getExtras();
        user = extras.getString("key");

        return user;
    }
}
