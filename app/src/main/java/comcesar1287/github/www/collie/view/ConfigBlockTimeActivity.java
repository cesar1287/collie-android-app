package comcesar1287.github.www.collie.view;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.collie.controller.fragment.TimePickerFragment;
import comcesar1287.github.www.collie.controller.util.Utility;

public class ConfigBlockTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTimeBeforeBlock, etTimeError;

    private Button btClassInitialHour, btClassFinalHour, btTaskInitialHour, btTaskFinalHour, btNightInitialHour, btNightFinalHour;

    private CheckBox cbClass, cbTask, cbNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_block_time);

        initComponents();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        DialogFragment fragment = new TimePickerFragment();
        Bundle bundle = new Bundle();

        switch (id) {
            case R.id.config_time_button_send:
                registerConfig();
                break;
            case R.id.config_time_button_cancel:
                finish();
                break;
            case R.id.config_simple_cb1:
                if(cbClass.isChecked()){
                    btClassInitialHour.setEnabled(true);
                    btClassFinalHour.setEnabled(true);
                }else{
                    btClassInitialHour.setEnabled(false);
                    btClassFinalHour.setEnabled(false);
                }
                break;
            case R.id.config_simple_cb2:
                if(cbTask.isChecked()){
                    btTaskInitialHour.setEnabled(true);
                    btTaskFinalHour.setEnabled(true);
                }else{
                    btTaskInitialHour.setEnabled(false);
                    btTaskFinalHour.setEnabled(false);
                }
                break;
            case R.id.config_simple_cb3:
                if(cbNight.isChecked()){
                    btNightInitialHour.setEnabled(true);
                    btNightFinalHour.setEnabled(true);
                }else{
                    btNightInitialHour.setEnabled(false);
                    btNightFinalHour.setEnabled(false);
                }
                break;
            case R.id.config_simple_initial_hour1:
                bundle.putInt("ID", R.id.config_simple_initial_hour1);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_final_hour1:
                bundle.putInt("ID", R.id.config_simple_final_hour1);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_initial_hour2:
                bundle.putInt("ID", R.id.config_simple_initial_hour2);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_final_hour2:
                bundle.putInt("ID", R.id.config_simple_final_hour2);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_initial_hour3:
                bundle.putInt("ID", R.id.config_simple_initial_hour3);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_final_hour3:
                bundle.putInt("ID", R.id.config_simple_final_hour3);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
        }
    }

    private void initComponents() {
        Button btSend = findViewById(R.id.config_time_button_send);
        btSend.setOnClickListener(this);

        Button btCancel = findViewById(R.id.config_time_button_cancel);
        btCancel.setOnClickListener(this);

        etTimeBeforeBlock = findViewById(R.id.config_block_time_no_block);
        etTimeBeforeBlock.setOnClickListener(this);
        TextWatcher timeBeforeMask = Utility.insertMask(getResources().getString(R.string.hour_mask), etTimeBeforeBlock);
        etTimeBeforeBlock.addTextChangedListener(timeBeforeMask);

        etTimeError = findViewById(R.id.config_block_time_no_hit);
        etTimeError.setOnClickListener(this);
        TextWatcher timeErrorMask = Utility.insertMask(getResources().getString(R.string.hour_mask), etTimeError);
        etTimeError.addTextChangedListener(timeErrorMask);

        cbClass = findViewById(R.id.config_simple_cb1);
        cbClass.setOnClickListener(this);

        btClassInitialHour = findViewById(R.id.config_simple_initial_hour1);
        btClassInitialHour.setOnClickListener(this);

        btClassFinalHour = findViewById(R.id.config_simple_final_hour1);
        btClassFinalHour.setOnClickListener(this);

        cbTask = findViewById(R.id.config_simple_cb2);
        cbTask.setOnClickListener(this);

        btTaskInitialHour = findViewById(R.id.config_simple_initial_hour2);
        btTaskInitialHour.setOnClickListener(this);

        btTaskFinalHour = findViewById(R.id.config_simple_final_hour2);
        btTaskFinalHour.setOnClickListener(this);

        cbNight = findViewById(R.id.config_simple_cb3);
        cbNight.setOnClickListener(this);

        btNightInitialHour = findViewById(R.id.config_simple_initial_hour3);
        btNightInitialHour.setOnClickListener(this);

        btNightFinalHour = findViewById(R.id.config_simple_final_hour3);
        btNightFinalHour.setOnClickListener(this);
    }

    private void registerConfig() {
        boolean allFieldsFilled = true;
        boolean opcionalFieldFilled = true;

        if(etTimeBeforeBlock.getText().toString().equals("")){
            allFieldsFilled = false;
            Toast.makeText(this, "Obrigatório preenchimento do tempo limite antes do bloqueio", Toast.LENGTH_LONG).show();
        }

        if(etTimeError.getText().toString().equals("")){
            allFieldsFilled = false;
            Toast.makeText(this, "Obrigatório preenchimento do tempo de bloqueio em caso de erro", Toast.LENGTH_LONG).show();
        }

        if(allFieldsFilled){
            String initialHour[], finalHour[];

            if(cbClass.isChecked()){
                if(btClassInitialHour.getText().equals(getString(R.string.config_block_simple_time_initial)) ||
                        btClassFinalHour.getText().equals(getString(R.string.config_block_simple_time_final))){
                    opcionalFieldFilled = false;
                    Toast.makeText(this, "É necessário preencher o horário de início e fim do bloqueio, no período de aula", Toast.LENGTH_LONG).show();
                }else if(cbClass.isChecked()){
                    initialHour = btClassInitialHour.getText().toString().split(":");
                    finalHour = btClassFinalHour.getText().toString().split(":");

                    Date dateInitial = new Date();
                    dateInitial.setHours(Integer.parseInt(initialHour[0]));
                    dateInitial.setMinutes(Integer.parseInt(initialHour[1]));

                    Date dateFinal = new Date();
                    dateFinal.setHours(Integer.parseInt(finalHour[0]));
                    dateFinal.setMinutes(Integer.parseInt(finalHour[1]));

                    if(!dateInitial.before(dateFinal)){
                        Toast.makeText(this, "A hora de início do bloqueio no período de aula não pode ser posterior a hora de término", Toast.LENGTH_LONG).show();
                        opcionalFieldFilled = false;
                    }
                }
            }
            if(cbTask.isChecked()){
                if(btTaskInitialHour.getText().equals(getString(R.string.config_block_simple_time_initial)) ||
                        btTaskFinalHour.getText().equals(getString(R.string.config_block_simple_time_final))){
                    opcionalFieldFilled = false;
                    Toast.makeText(this, "É necessário preencher o horário de início e fim do bloqueio, no período de tarefas", Toast.LENGTH_LONG).show();
                }else if(cbTask.isChecked()){
                    initialHour = btTaskInitialHour.getText().toString().split(":");
                    finalHour = btTaskFinalHour.getText().toString().split(":");

                    Date dateInitial = new Date();
                    dateInitial.setHours(Integer.parseInt(initialHour[0]));
                    dateInitial.setMinutes(Integer.parseInt(initialHour[1]));

                    Date dateFinal = new Date();
                    dateFinal.setHours(Integer.parseInt(finalHour[0]));
                    dateFinal.setMinutes(Integer.parseInt(finalHour[1]));

                    if(!dateInitial.before(dateFinal)){
                        Toast.makeText(this, "A hora de início do bloqueio no período de tarefas não pode ser posterior a hora de término", Toast.LENGTH_LONG).show();
                        opcionalFieldFilled = false;
                    }
                }
            }
            if(cbNight.isChecked()){
                if(btNightInitialHour.getText().equals(getString(R.string.config_block_simple_time_initial)) ||
                        btNightFinalHour.getText().equals(getString(R.string.config_block_simple_time_final))){
                    opcionalFieldFilled = false;
                    Toast.makeText(this, "É necessário preencher o horário de início e fim do bloqueio, no período noturno", Toast.LENGTH_LONG).show();
                }else if(cbNight.isChecked()){
                    initialHour = btNightInitialHour.getText().toString().split(":");
                    finalHour = btNightFinalHour.getText().toString().split(":");

                    Date dateInitial = new Date();
                    dateInitial.setHours(Integer.parseInt(initialHour[0]));
                    dateInitial.setMinutes(Integer.parseInt(initialHour[1]));

                    Date dateFinal = new Date();
                    dateFinal.setHours(Integer.parseInt(finalHour[0]));
                    dateFinal.setMinutes(Integer.parseInt(finalHour[1]));

                    if(!dateInitial.before(dateFinal)){
                        Toast.makeText(this, "A hora de início do bloqueio no período noturno não pode ser posterior a hora de término", Toast.LENGTH_LONG).show();
                        opcionalFieldFilled = false;
                    }
                }
            }

            if(opcionalFieldFilled) {
                String type = getString(R.string.setup_screen_time);
                StringBuilder options = new StringBuilder();
                if(cbClass.isChecked()) {
                    options.append(getString(R.string.config_block_simple_title1));
                    options.append(":");
                    options.append(btClassInitialHour.getText());
                    options.append(",");
                    options.append(btClassFinalHour.getText());
                }
                options.append(";");
                if(cbTask.isChecked()) {
                    options.append(getString(R.string.config_block_simple_title2));
                    options.append(":");
                    options.append(btTaskInitialHour.getText());
                    options.append(",");
                    options.append(btTaskFinalHour.getText());
                }
                options.append(";");
                if(cbNight.isChecked()) {
                    options.append(getString(R.string.config_block_simple_title3));
                    options.append(":");
                    options.append(btNightInitialHour.getText());
                    options.append(",");
                    options.append(btNightFinalHour.getText());
                }
                options.append(";");

                if(mAuth.getCurrentUser()!=null) {
                    String uid = mAuth.getCurrentUser().getUid();
                    String nameTask1 = etTask1Name.getText().toString();
                    String bonusTask1 = etTask1Bonus.getText().toString();
                    String nameTask2 = etTask2Name.getText().toString();
                    String bonusTask2 = etTask2Bonus.getText().toString();

                    FirebaseHelper.writeNewConfigBlock(mDatabase, uid, type, options.toString(), nameTask1, bonusTask1, nameTask2, bonusTask2);
                    Toast.makeText(this, "Configuração de bloqueio registrado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(this, "Configuração de bloqueio registrado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
