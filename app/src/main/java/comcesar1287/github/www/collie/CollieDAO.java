package comcesar1287.github.www.collie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import comcesar1287.github.www.collie.controller.domain.Atividade;

public class CollieDAO extends SQLiteOpenHelper{

    private static final String DATABASE = "db_collie";
    private static final int VERSAO = 1;
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
}
