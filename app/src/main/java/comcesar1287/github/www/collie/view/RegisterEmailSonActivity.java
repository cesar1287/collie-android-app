package comcesar1287.github.www.collie.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comcesar1287.github.www.collie.R;

public class RegisterEmailSonActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email_son);

        initComponents();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.register_email_son_button_send:
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
}
