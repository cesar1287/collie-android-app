package comcesar1287.github.www.collie.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Collections;
import java.util.List;

import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.domain.Atividade;
import comcesar1287.github.www.collie.model.CollieDAO;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        setTitle("Tarefas");
    }

    private void initComponent() {
        ListView listViewTasks = findViewById(R.id.task_list);

        CollieDAO collieDAO = new CollieDAO(this);
        List<Atividade> atividadeList = collieDAO.getListaAtividadesDiaria();

        if(atividadeList.size()==0){
            Toast.makeText(this, "Você ainda não tem atividades hoje", Toast.LENGTH_SHORT).show();
            finish();
        }

        Collections.reverse(atividadeList);

        ArrayAdapter<Atividade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, atividadeList);

        listViewTasks.setAdapter(adapter);
    }
}
