package comcesar1287.github.www.collie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import comcesar1287.github.www.collie.R;

public class ConfigBlockTimeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_block_time);

        initComponents();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.config_time_button_send:
                Toast.makeText(this, "Configuração de bloqueio registrado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.config_time_button_cancel:
                finish();
                break;
        }
    }

    private void initComponents() {
        Button btSend = findViewById(R.id.config_time_button_send);
        btSend.setOnClickListener(this);

        Button btCancel = findViewById(R.id.config_time_button_cancel);
        btCancel.setOnClickListener(this);
    }
}
