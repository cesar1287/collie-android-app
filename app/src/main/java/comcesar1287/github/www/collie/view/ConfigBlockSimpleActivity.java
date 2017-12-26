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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.collie.controller.fragment.TimePickerFragment;
import comcesar1287.github.www.collie.controller.util.Utility;

public class ConfigBlockSimpleActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox cbClass, cbTask, cbNight;

    private Button btClassInitialHour, btClassFinalHour, btTaskInitialHour, btTaskFinalHour, btNightInitialHour, btNightFinalHour;

    private EditText etTask1Name, etTask1Bonus, etTask2Name, etTask2Bonus;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_block_simple);

        initComponents();
        initFirebase();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        DialogFragment fragment = new TimePickerFragment();
        Bundle bundle = new Bundle();

        switch (id){
            case R.id.config_simple_button_send:
                registerConfig();
                break;
            case R.id.config_simple_button_cancel:
                finish();
                break;
            case R.id.config_simple_cb_class:
                if(cbClass.isChecked()){
                    btClassInitialHour.setEnabled(true);
                    btClassFinalHour.setEnabled(true);
                }else{
                    btClassInitialHour.setEnabled(false);
                    btClassFinalHour.setEnabled(false);
                }
                break;
            case R.id.config_simple_cb_task:
                if(cbTask.isChecked()){
                    btTaskInitialHour.setEnabled(true);
                    btTaskFinalHour.setEnabled(true);
                }else{
                    btTaskInitialHour.setEnabled(false);
                    btTaskFinalHour.setEnabled(false);
                }
                break;
            case R.id.config_simple_cb_night:
                if(cbNight.isChecked()){
                    btNightInitialHour.setEnabled(true);
                    btNightFinalHour.setEnabled(true);
                }else{
                    btNightInitialHour.setEnabled(false);
                    btNightFinalHour.setEnabled(false);
                }
                break;
            case R.id.config_simple_initial_hour_class:
                bundle.putInt("ID", R.id.config_simple_initial_hour_class);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_final_hour_class:
                bundle.putInt("ID", R.id.config_simple_final_hour_class);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_initial_hour_task:
                bundle.putInt("ID", R.id.config_simple_initial_hour_task);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_final_hour_task:
                bundle.putInt("ID", R.id.config_simple_final_hour_task);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_initial_hour_night:
                bundle.putInt("ID", R.id.config_simple_initial_hour_night);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.config_simple_final_hour_night:
                bundle.putInt("ID", R.id.config_simple_final_hour_night);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
        }
    }

    private void initComponents() {
        Button btSend = findViewById(R.id.config_simple_button_send);
        btSend.setOnClickListener(this);

        Button btCancel = findViewById(R.id.config_simple_button_cancel);
        btCancel.setOnClickListener(this);

        cbClass = findViewById(R.id.config_simple_cb_class);
        cbClass.setOnClickListener(this);

        btClassInitialHour = findViewById(R.id.config_simple_initial_hour_class);
        btClassInitialHour.setOnClickListener(this);

        btClassFinalHour = findViewById(R.id.config_simple_final_hour_class);
        btClassFinalHour.setOnClickListener(this);

        cbTask = findViewById(R.id.config_simple_cb_task);
        cbTask.setOnClickListener(this);

        btTaskInitialHour = findViewById(R.id.config_simple_initial_hour_task);
        btTaskInitialHour.setOnClickListener(this);

        btTaskFinalHour = findViewById(R.id.config_simple_final_hour_task);
        btTaskFinalHour.setOnClickListener(this);

        cbNight = findViewById(R.id.config_simple_cb_night);
        cbNight.setOnClickListener(this);

        btNightInitialHour = findViewById(R.id.config_simple_initial_hour_night);
        btNightInitialHour.setOnClickListener(this);

        btNightFinalHour = findViewById(R.id.config_simple_final_hour_night);
        btNightFinalHour.setOnClickListener(this);

        etTask1Name = findViewById(R.id.config_block_simple_name_task1);
        etTask1Bonus = findViewById(R.id.config_block_simple_bonus_task1);
        TextWatcher bonusMask1 = Utility.insertMask(getResources().getString(R.string.hour_mask), etTask1Bonus);
        etTask1Bonus.addTextChangedListener(bonusMask1);

        etTask2Name = findViewById(R.id.config_block_simple_name_task2);
        etTask2Bonus = findViewById(R.id.config_block_simple_bonus_task2);
        TextWatcher bonusMask2 = Utility.insertMask(getResources().getString(R.string.hour_mask), etTask2Bonus);
        etTask2Bonus.addTextChangedListener(bonusMask2);
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void registerConfig() {
        if(!cbClass.isChecked() && !cbTask.isChecked() && !cbNight.isChecked()){
            Toast.makeText(this, "Deve ser selecionado pelo menos um dos períodos para bloqueio", Toast.LENGTH_LONG).show();
        }else{
            boolean allFieldsFilled = true;
            boolean allFieldsFilledCorrectly = true;

            if(cbClass.isChecked()){
                if(btClassInitialHour.getText().equals(getString(R.string.config_block_simple_time_initial)) ||
                        btClassFinalHour.getText().equals(getString(R.string.config_block_simple_time_final))){
                    allFieldsFilled = false;
                    Toast.makeText(this, "É necessário preencher o horário de início e fim do bloqueio, no período de aula", Toast.LENGTH_LONG).show();
                }
            }
            if(cbTask.isChecked()){
                if(btTaskInitialHour.getText().equals(getString(R.string.config_block_simple_time_initial)) ||
                        btTaskFinalHour.getText().equals(getString(R.string.config_block_simple_time_final))){
                    allFieldsFilled = false;
                    Toast.makeText(this, "É necessário preencher o horário de início e fim do bloqueio, no período de tarefas", Toast.LENGTH_LONG).show();
                }
            }
            if(cbNight.isChecked()){
                if(btNightInitialHour.getText().equals(getString(R.string.config_block_simple_time_initial)) ||
                        btNightFinalHour.getText().equals(getString(R.string.config_block_simple_time_final))){
                    allFieldsFilled = false;
                    Toast.makeText(this, "É necessário preencher o horário de início e fim do bloqueio, no período noturno", Toast.LENGTH_LONG).show();
                }
            }

            if((!etTask1Name.getText().toString().equals("") && etTask1Bonus.getText().toString().equals("")) ||
                    (etTask1Name.getText().toString().equals("") && !etTask1Bonus.getText().toString().equals(""))){
                allFieldsFilled = false;
                Toast.makeText(this, "Preencha a descrição da tarefa 1 completamente ou a deixe vazia", Toast.LENGTH_SHORT).show();
            }

            if((!etTask2Name.getText().toString().equals("") && etTask2Bonus.getText().toString().equals("")) ||
                    (etTask2Name.getText().toString().equals("") && !etTask2Bonus.getText().toString().equals(""))){
                allFieldsFilled = false;
                Toast.makeText(this, "Preencha a descrição da tarefa 2 completamente ou a deixe vazia", Toast.LENGTH_SHORT).show();
            }

            if(allFieldsFilled){
                String initialHour[], finalHour[];

                if(cbClass.isChecked()){
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
                        allFieldsFilledCorrectly = false;
                    }
                }

                if(cbTask.isChecked()){
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
                        allFieldsFilledCorrectly = false;
                    }
                }

                if(cbNight.isChecked()){
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
                        allFieldsFilledCorrectly = false;
                    }
                }

                if(!etTask1Bonus.getText().toString().equals("")){
                    if(etTask1Bonus.length()!=9){
                        allFieldsFilledCorrectly = false;
                        Toast.makeText(this, "Preencha corretamente o tempo de bônus da tarefa 1", Toast.LENGTH_SHORT).show();
                    }
                }

                if(!etTask2Bonus.getText().toString().equals("")){
                    if(etTask2Bonus.length()!=9){
                        allFieldsFilledCorrectly = false;
                        Toast.makeText(this, "Preencha corretamente o tempo de bônus da tarefa 2", Toast.LENGTH_SHORT).show();
                    }
                }

                if(allFieldsFilledCorrectly) {
                    String type = getString(R.string.setup_screen_simple);
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
                }
            }
        }
    }
}