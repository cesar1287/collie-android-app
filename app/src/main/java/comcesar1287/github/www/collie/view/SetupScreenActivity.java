package comcesar1287.github.www.collie.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import comcesar1287.github.www.collie.R;

public class SetupScreenActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_screen);

        initToolbar();
        initComponents();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent intent;

        switch (id){
            case R.id.setup_screen_explanation_simple:
                intent = new Intent(this, CategoryBlockActivity.class);
                intent.putExtra(getString(R.string.setup_screen_block),getString(R.string.setup_screen_simple));
                startActivity(intent);
                break;
            case R.id.setup_screen_explanation_time:
                intent = new Intent(this, CategoryBlockActivity.class);
                intent.putExtra(getString(R.string.setup_screen_block),getString(R.string.setup_screen_time));
                startActivity(intent);
                break;
            case R.id.setup_screen_explanation_points:
                intent = new Intent(this, CategoryBlockActivity.class);
                intent.putExtra(getString(R.string.setup_screen_block),getString(R.string.setup_screen_points));
                startActivity(intent);
                break;
            case R.id.setup_screen_select_simple:
                typeBlockDialog(getString(R.string.setup_screen_simple));
                break;
            case R.id.setup_screen_select_time:
                typeBlockDialog(getString(R.string.setup_screen_time));
                break;
            case R.id.setup_screen_select_points:
                typeBlockDialog(getString(R.string.setup_screen_points));
                break;

        }
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.hide();
        }
    }

    private void initComponents() {
        ImageView btSetupScreenExplanationSimple = findViewById(R.id.setup_screen_explanation_simple);
        btSetupScreenExplanationSimple.setOnClickListener(this);

        ImageView btSetupScreenSelectSimple = findViewById(R.id.setup_screen_select_simple);
        btSetupScreenSelectSimple.setOnClickListener(this);

        ImageView btSetupScreenExplanationTime = findViewById(R.id.setup_screen_explanation_time);
        btSetupScreenExplanationTime.setOnClickListener(this);

        ImageView btSetupScreenSelectTime = findViewById(R.id.setup_screen_select_time);
        btSetupScreenSelectTime.setOnClickListener(this);

        ImageView btSetupScreenExplanationPoints = findViewById(R.id.setup_screen_explanation_points);
        btSetupScreenExplanationPoints.setOnClickListener(this);

        ImageView btSetupScreenSelectPoints = findViewById(R.id.setup_screen_select_points);
        btSetupScreenSelectPoints.setOnClickListener(this);
    }

    private void typeBlockDialog(String type) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que deseja escolher o bloqueio " + type+ "?")
                .setPositiveButton(R.string.setup_screen_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doSelectTypeBlock();
                    }
                })
                .setNegativeButton(R.string.setup_screen_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    private void doSelectTypeBlock() {
    }
}
