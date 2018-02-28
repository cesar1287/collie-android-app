package comcesar1287.github.www.collie.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import comcesar1287.github.www.collie.controller.domain.Atividade;

public class CollieDAO extends SQLiteOpenHelper{

    private static final String DATABASE = "db_collie";
    private static final int VERSAO = 2;
    private static final String TABELA_ATIVIDADE = "Atividade";

    public CollieDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_atividade = "CREATE TABLE " + TABELA_ATIVIDADE + "(" +
                "id INTEGER PRIMARY KEY, " +
                "id_usuario INTEGER," +
                "nome TEXT, " +
                "descricao TEXT, " +
                "data TEXT," +
                "concluida INTEGER" +
                ");";

        db.execSQL(sql_atividade);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_ATIVIDADE;
        db.execSQL(sql);
        onCreate(db);
    }

    public long insert(Atividade atividade) {

        ContentValues cv = new ContentValues();
        cv.put("id_usuario", atividade.getId_usuario());
        cv.put("nome", atividade.getNome());
        cv.put("descricao", atividade.getDescricao());
        cv.put("data", atividade.getData());
        cv.put("concluida", -1);

        return getWritableDatabase().insert(TABELA_ATIVIDADE, null, cv);
    }

    public List<Atividade> getListaAtividades() {

        final List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_ATIVIDADE + " where id_usuario = ?";
        String args[] = new String[]{String.valueOf(1)};
        final Cursor c = getReadableDatabase().rawQuery(sql, args);

        while(c.moveToNext()){
            Atividade atividade = new Atividade();
            atividade.setId(c.getInt(c.getColumnIndex("id")));
            atividade.setId_usuario(c.getInt(c.getColumnIndex("id_usuario")));
            atividade.setNome(c.getString(c.getColumnIndex("nome")));
            atividade.setDescricao(c.getString(c.getColumnIndex("descricao")));
            atividade.setConcluida(c.getInt(c.getColumnIndex("concluida")));

            String dataHora = c.getString(c.getColumnIndex("data"));
            atividade.setData(dataHora);

            atividades.add(atividade);
        }
        c.close();
        return atividades;
    }

    public List<Atividade> getListaAtividadesDiaria() {

        SimpleDateFormat dateFormatted = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
        String formatted = dateFormatted.format(Calendar.getInstance().getTime());

        final List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_ATIVIDADE + " where id_usuario = ? and data = ?";
        String args[] = new String[]{String.valueOf(1), formatted};
        final Cursor c = getReadableDatabase().rawQuery(sql, args);

        while(c.moveToNext()){
            Atividade atividade = new Atividade();
            atividade.setId(c.getInt(c.getColumnIndex("id")));
            atividade.setId_usuario(c.getInt(c.getColumnIndex("id_usuario")));
            atividade.setNome(c.getString(c.getColumnIndex("nome")));
            atividade.setDescricao(c.getString(c.getColumnIndex("descricao")));
            atividade.setConcluida(c.getInt(c.getColumnIndex("concluida")));

            String dataHora = c.getString(c.getColumnIndex("data"));
            atividade.setData(dataHora);

            atividades.add(atividade);
        }
        c.close();
        return atividades;
    }
}
