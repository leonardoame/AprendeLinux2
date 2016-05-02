package mx.edu.utng.aprendelinux.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mx.edu.utng.aprendelinux.model.Usuario;


public interface UsuarioDAO {
    void agregar(Usuario usuario, SQLiteDatabase db);
    void modificar(Usuario usuario, SQLiteDatabase db);
    void eliminar(Usuario usuario, SQLiteDatabase db);
    Cursor listar(SQLiteDatabase db);
}
