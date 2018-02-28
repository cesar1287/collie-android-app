package comcesar1287.github.www.collie.controller.domain;

import java.io.Serializable;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Atividade implements Serializable{

    private String idChild, nome, data, hora, descricao;
    private int concluida;
    private int id;
    private int id_usuario;

    public Atividade() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Atividade(String idChild, String nome, String data, String hora, String descricao, int concluida) {
        this.idChild = idChild;
        this.nome = nome;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public int getConcluida() {
        return concluida;
    }

    public void setConcluida(int concluida) {
        this.concluida = concluida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return nome + "\n" +descricao + "\n" + data;
    }
}
