package comcesar1287.github.www.collie.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.data.SharedPref;

public class InstructionSlideActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnJump, btnNextOne, btnBackTwo, btnNextTwo,
            btnBackThree, btnNextThree, btnBackFour, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_slide_one);

        SharedPref sharedPref = new SharedPref(this);
        sharedPref.setFirstExecute();

        initToolbar();

        initComponentOne();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.hide();
        }
    }

    private void initComponentOne() {
        btnJump = (Button) findViewById(R.id.instrution_slide_button_jump);
        btnJump.setOnClickListener(this);

        btnNextOne = (Button) findViewById(R.id.instrution_slide_one_button_next);
        btnNextOne.setOnClickListener(this);
    }

    private void initComponentTwo() {
        btnBackTwo = (Button) findViewById(R.id.instrution_slide_two_button_previous);
        btnBackTwo.setOnClickListener(this);

        btnNextTwo = (Button) findViewById(R.id.instrution_slide_two_button_next);
        btnNextTwo.setOnClickListener(this);
    }

    private void initComponentThree() {
        btnBackThree = (Button) findViewById(R.id.instrution_slide_three_button_previous);
        btnBackThree.setOnClickListener(this);

        btnNextThree = (Button) findViewById(R.id.instrution_slide_three_button_next);
        btnNextThree.setOnClickListener(this);
    }

    private void initComponentFour() {
        btnBackFour = (Button) findViewById(R.id.instrution_slide_four_button_previous);
        btnBackFour.setOnClickListener(this);

        btnContinue = (Button) findViewById(R.id.instrution_slide_four_button_continues);
        btnContinue.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.instrution_slide_button_jump:
                startActivity(new Intent(this, MainActivity.class));
            break;
            case R.id.instrution_slide_one_button_next:
                setContentView(R.layout.instruction_slide_two);
                initComponentTwo();
                break;
            case R.id.instrution_slide_two_button_previous:
                setContentView(R.layout.instruction_slide_one);
                initComponentOne();
                break;
            case R.id.instrution_slide_two_button_next:
                setContentView(R.layout.instruction_slide_three);
                initComponentThree();
                break;
            case R.id.instrution_slide_three_button_previous:
                setContentView(R.layout.instruction_slide_two);
                initComponentTwo();
                break;
            case R.id.instrution_slide_three_button_next:
                setContentView(R.layout.instruction_slide_four);
                initComponentFour();
                break;
            case R.id.instrution_slide_four_button_previous:
                setContentView(R.layout.instruction_slide_three);
                initComponentThree();
                break;
            case R.id.instrution_slide_four_button_continues:
                finish();
                break;
        }
    }
}
