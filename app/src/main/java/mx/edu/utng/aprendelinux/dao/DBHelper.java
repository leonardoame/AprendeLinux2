package mx.edu.utng.aprendelinux.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public  static final String DATABASE_NAME = "utng.db";
    public  static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_1 = "tbl_usuario";
    public static final String TABLE_NAME_2 = "tbl_tema";
    public static final String TABLE_NAME_3 = "tbl_modulo";
    public static final String NAME = "nombre";
    public static final String ACTIVO = "activo";
    public static final String PASSWORD = "contrasena";
    public static final String MAIL = "correo";
    public static final String USER_ID = "id_usuario";
    public static final String MOD_ID = "id_modulo";
    public static final String ID = "_id";
    public static final String CALIF = "calificacion";

    private static final String CREATE_TABLE_1 =
            "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_1
                    +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +NAME+" TEXT, "
                    +MAIL+" TEXT, "
                    +PASSWORD+" TEXT);";


    private static final String CREATE_TABLE_2 =//temas
            "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_2
                    +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +NAME+" TEXT, "
                    +ACTIVO+" INTEGER DEFAULT 0, "
                    +MOD_ID+" INTEGER, FOREIGN KEY("+MOD_ID+") REFERENCES "+TABLE_NAME_2+" ("+ID+"));";


    private static final String CREATE_TABLE_3 =//modulos
            "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_3
                    +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +NAME+" TEXT, "
                    +CALIF+" INTEGER DEFAULT 0, "
                    +ACTIVO+" INTEGER DEFAULT 0, "
                    +USER_ID+" INTEGER, FOREIGN KEY("+USER_ID+") REFERENCES "+TABLE_NAME_1+" ("+ID+"));";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_1);
    db.execSQL(CREATE_TABLE_2);
    db.execSQL(CREATE_TABLE_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_3);
        onCreate(db);
    }

}