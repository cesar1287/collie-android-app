package comcesar1287.github.www.collie.view;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import comcesar1287.github.www.collie.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout etEmail;

    String email;

    private FirebaseAuth mAuth;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initComponents();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.forgot_password_button_send:
                sendEmailRecoverPassword();
                break;
            case R.id.forgot_password_button_cancel:
                finish();
                break;
        }
    }

    private void initComponents() {
        Utils.init(getApplication());

        mAuth = FirebaseAuth.getInstance();;

        etEmail = findViewById(R.id.forgot_email);

        Button btSend = findViewById(R.id.forgot_password_button_send);
        btSend.setOnClickListener(this);

        Button btCancel = findViewById(R.id.forgot_password_button_cancel);
        btCancel.setOnClickListener(this);
    }

    private void sendEmailRecoverPassword() {
        email = etEmail.getEditText().getText().toString();
        if(EmptyUtils.isNotEmpty(email)) {
            etEmail.setErrorEnabled(false);
            dialog = ProgressDialog.show(ForgotPasswordActivity.this,"",
                    "Enviando email de recuperação da conta...", true, false);
            mAuth.sendPasswordResetEmail(email)
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            if (e instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(ForgotPasswordActivity.this,
                                        "Esse email não pertence a nenhuma conta registrada no nosso sitema.",
                                        Toast.LENGTH_LONG).show();
                            }else if (!RegexUtils.isEmail(email)) {
                                Toast.makeText(ForgotPasswordActivity.this,
                                        getResources().getString(R.string.error_invalid_email),
                                        Toast.LENGTH_SHORT).show();
                                etEmail.getEditText().setText("");
                                etEmail.requestFocus();
                            }else{
                                Toast.makeText(ForgotPasswordActivity.this,
                                        getResources().getString(R.string.error_unknown_error),
                                        Toast.LENGTH_SHORT).show();
                                etEmail.getEditText().setText("");
                                etEmail.requestFocus();
                            }
                        }
                    })
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this,
                                        "Email para recuperação da conta enviado com sucesso.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
        }else{
            etEmail.setError(getResources().getString(R.string.error_required_field));
        }
    }
}
