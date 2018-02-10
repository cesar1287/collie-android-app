package comcesar1287.github.www.collie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.domain.Atividade;
import comcesar1287.github.www.collie.model.CollieDAO;

public class ReportsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Relatórios");
    }

    private void initComponent() {
        ListView listViewReports = findViewById(R.id.reports_list);

        CollieDAO collieDAO = new CollieDAO(this);
        List<Atividade> atividadeList = collieDAO.getListaAtividades();

        Collections.reverse(atividadeList);

        ArrayAdapter<Atividade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, atividadeList);

        listViewReports.setAdapter(adapter);
    }
}
