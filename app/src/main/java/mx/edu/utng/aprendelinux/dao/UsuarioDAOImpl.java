package mx.edu.utng.aprendelinux.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import mx.edu.utng.aprendelinux.model.Usuario;



public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void agregar(Usuario usuario, SQLiteDatabase db) {
        ContentValues values= new ContentValues();
        values.put(DBHelper.NAME, usuario.getNombre());
        values.put(DBHelper.MAIL, usuario.getCorreo());
        values.put(DBHelper.PASSWORD, usuario.getContrasena());
        db.insert(DBHelper.TABLE_NAME_1, null, values);
    }

    @Override
    public void modificar(Usuario usuario, SQLiteDatabase db) {
        ContentValues values= new ContentValues();
        values.put(DBHelper.NAME, usuario.getNombre());
        values.put(DBHelper.MAIL, usuario.getCorreo());
        values.put(DBHelper.PASSWORD, usuario.getContrasena());
        db.update(DBHelper.TABLE_NAME_1, values, DBHelper.ID + "=" + usuario.getIdUsuario(), null);
    }

    @Override
    public void eliminar(Usuario usuario, SQLiteDatabase db) {
        db.delete(DBHelper.TABLE_NAME_1, DBHelper.ID + " = " + usuario.getIdUsuario(), null);
        db.delete(DBHelper.TABLE_NAME_3,DBHelper.USER_ID + " = " + usuario.getIdUsuario(),null);

        try {
            //DBAdapter dbAdapter = new DBAdapter();
            //dbAdapter.eliminarModulosTemas(usuario.getIdUsuario());
        }catch (Error e){
            Log.e("UsuarioDaoImpl", "eliminar: "+e.toString());
        }

    }

    @Override
    public Cursor listar(SQLiteDatabase db) {
        return db.query(DBHelper.TABLE_NAME_1,null,null,null,null,null,null);
    }

}
