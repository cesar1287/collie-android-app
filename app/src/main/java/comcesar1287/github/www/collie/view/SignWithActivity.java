package comcesar1287.github.www.collie.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import comcesar1287.github.www.collie.R;

public class SignWithActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_with);

        initToolbar();
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
}
