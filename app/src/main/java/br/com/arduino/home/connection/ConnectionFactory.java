package br.com.arduino.home.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionFactory extends SQLiteOpenHelper {

    private static final String DATABASE = "bd.home";
    private static final int VERSION = 1;
    private String sql = "";

    public ConnectionFactory(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "create table controls(id integer primary key," +
                "url text," +
                "descricao text," +
                "ativo text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sql = "drop table if exists controls";
        db.execSQL(sql);
        onCreate(db);
    }
}
