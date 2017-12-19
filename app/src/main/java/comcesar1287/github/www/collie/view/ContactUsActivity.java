package comcesar1287.github.www.collie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.Utils;

import comcesar1287.github.www.collie.R;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputLayout nome, cidade, mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initComponents();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.contact_us_send:
                attemptLogin();
                break;
            case R.id.contact_us_cancel:
                finish();
                break;
        }
    }

    private void initComponents() {
        Utils.init(getApplication());
        nome = findViewById(R.id.contac_us_name);
        cidade = findViewById(R.id.contac_us_city);
        mensagem = findViewById(R.id.contact_us_message);

        Button btSend = findViewById(R.id.contact_us_send);
        btSend.setOnClickListener(this);

        Button btCancel = findViewById(R.id.contact_us_cancel);
        btCancel.setOnClickListener(this);
    }

    public void attemptLogin(){
        String valNome, valCidade, valMensagem;

        boolean allFieldsFilled = true;
        boolean allFilledRight = true;

        valNome = nome.getEditText().getText().toString();
        valCidade = cidade.getEditText().getText().toString();
        valMensagem = mensagem.getEditText().getText().toString();

        if(valNome.equals("")){
            allFieldsFilled = false;
            nome.setError("Campo obrigatório");
        }else{
            nome.setErrorEnabled(false);
        }

        if(valCidade.equals("")){
            allFieldsFilled = false;
            cidade.setError("Campo obrigatório");
        }else{
            cidade.setErrorEnabled(false);
        }

        if(valMensagem.equals("")){
            allFieldsFilled = false;
            mensagem.setError("Campo obrigatório");
        }else{
            mensagem.setErrorEnabled(false);
        }

        if(allFieldsFilled && allFilledRight) {
            messageEmail();
            finish();
        }
    }

    public StringBuilder messageEmail(){
        String nomeRemetente = nome.getEditText().getText().toString();
        String cidadeRementente = cidade.getEditText().getText().toString();
        String mensagemRemetente = mensagem.getEditText().getText().toString();

        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nomeRemetente).append("\n");
        sb.append("Cidade: ").append(cidadeRementente).append("\n");
        sb.append("\n\n\n");
        sb.append("Mensagem: ").append(mensagemRemetente).append("\n");

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[] {"islane.junior@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Fale Conosco - Collie");
        email.putExtra(Intent.EXTRA_TEXT, sb.toString());
        email.setType("plain/text");
        startActivity(Intent.createChooser(email, "Enviando Email..."));

        return sb;
    }
}