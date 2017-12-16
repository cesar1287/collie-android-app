package comcesar1287.github.www.collie.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import comcesar1287.github.www.collie.R;

public class CategoryBlockActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String typeBlock = getIntent().getStringExtra(getString(R.string.setup_screen_block));
        if(typeBlock.equals(getString(R.string.setup_screen_simple))) {
            setContentView(R.layout.activity_category_block_simple);
        }else if(typeBlock.equals(getString(R.string.setup_screen_time))) {
            setContentView(R.layout.activity_category_block_time);
        }else if(typeBlock.equals(getString(R.string.setup_screen_points))) {
            setContentView(R.layout.activity_category_block_points);
        }

        initComponent();
    }

    private void initComponent() {
        Button btReturn = findViewById(R.id.category_block_button_return);
        btReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.category_block_button_return:
                finish();
                break;
        }
    }
}
