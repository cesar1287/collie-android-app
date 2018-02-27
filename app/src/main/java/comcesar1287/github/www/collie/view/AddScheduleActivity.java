package comcesar1287.github.www.collie.view;

import android.app.DialogFragment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comcesar1287.github.www.collie.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.collie.model.CollieDAO;
import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.domain.Atividade;
import comcesar1287.github.www.collie.controller.fragment.DatePickerFragment;
import comcesar1287.github.www.collie.controller.fragment.TimePickerFragment;

public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout tilName, tilDescription;
    private TextView tvHour, tvDate;
    private Spinner spinnerSelectUser;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private DatabaseReference mDatabase;

    private String idChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        tvHour = findViewById(R.id.add_schedule_button_time);
        tvHour.setOnClickListener(this);

        tvDate = findViewById(R.id.add_schedule_button_date);
        tvDate.setOnClickListener(this);

        Button btAdd = findViewById(R.id.button_schedule_add);
        btAdd.setOnClickListener(this);

        Button btCancel = findViewById(R.id.button_schedule_cancel);
        btCancel.setOnClickListener(this);

        tilName = findViewById(R.id.add_schedule_name);
        tilDescription = findViewById(R.id.add_schedule_description);

<<<<<<< HEAD
        initFirebase();
=======
        spinnerSelectUser = (findViewById(R.id.add_schedule_select_user));
>>>>>>> a10b2b507f4970fd5bde8d146ebb18e4b36b2636
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        DialogFragment fragment = new TimePickerFragment();
        DialogFragment fragmentDate = new DatePickerFragment();
        Bundle bundle = new Bundle();

        switch (id){
            case R.id.add_schedule_button_time:
                bundle.putInt("ID", R.id.add_schedule_button_time);
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.add_schedule_button_date:
                bundle.putInt("ID", R.id.add_schedule_button_date);
                fragmentDate.setArguments(bundle);
                fragmentDate.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.button_schedule_add:
                addSchedule();
                break;
            case R.id.button_schedule_cancel:
                finish();
                break;
        }
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void addSchedule() {
        String name = tilName.getEditText().getText().toString();
        String description = tilDescription.getEditText().getText().toString();
        String hour = tvHour.getText().toString();
        String date = tvDate.getText().toString();

        boolean allFieldsFilled = true;

        if(name.isEmpty()){
            allFieldsFilled = false;
            tilName.setError(getString(R.string.error_required_field));
        }else{
            tilName.setErrorEnabled(false);
        }

        if(description.isEmpty()){
            allFieldsFilled = false;
            tilDescription.setError(getString(R.string.error_required_field));
        }else{
            tilDescription.setErrorEnabled(false);
        }

        if(date.equals(getString(R.string.add_schedule_date))){
            allFieldsFilled = false;
            Toast.makeText(this, "Campo data é obrigatório", Toast.LENGTH_SHORT).show();
        }

        if(hour.equals(getString(R.string.add_schedule_time))){
            allFieldsFilled = false;
            Toast.makeText(this, "Campo hora é obrigatório", Toast.LENGTH_SHORT).show();
        }

        if(allFieldsFilled){
            user = mAuth.getCurrentUser();
            FirebaseHelper.writeNewActivity(mDatabase, idChild, name, date, hour, description, 0);
//            CollieDAO collieDAO = new CollieDAO(this);
//            Atividade atividade = new Atividade();
//            atividade.setId_usuario(1);
//            atividade.setNome(name);
//            atividade.setDescricao(description);
//            atividade.setData(date);
//            atividade.setHora(hour);
//            collieDAO.insert(atividade);
//            collieDAO.close();
            finish();
        }
    }
}
