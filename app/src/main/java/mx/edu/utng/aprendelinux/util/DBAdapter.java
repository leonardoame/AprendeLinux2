package mx.edu.utng.aprendelinux.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import mx.edu.utng.aprendelinux.FormLoginActivity;
import mx.edu.utng.aprendelinux.dao.DBHelper;
import mx.edu.utng.aprendelinux.model.Modulo;
import mx.edu.utng.aprendelinux.model.Tema;


/**
 * Created by Juan Ramon Delgado Mendoza on 07/03/2016.
 * @author Juan Ramon Delgado Mendoza
 * @email mon-ra16@hotmail.com
 *
 */
public class DBAdapter {
    /**
     * Variables de la clase DBAdapter
     */
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;
    /**
     * Contructor con un parametro.
     * @param c
     */
    public DBAdapter(Context c){
        this.context=c;
    }
    /**
     * Se abre la base de datos
     * @return
     * @throws SQLException
     */
    public DBAdapter open() throws SQLException{
        dbHelper=new DBHelper(context, DBHelper.DATABASE_NAME,null, DBHelper.DATABASE_VERSION);
        db=dbHelper.getWritableDatabase();
        return this;
    }
    /**
     * Se cierra la base de datos
     */
    public void close(){
        dbHelper.close();
    }
    /**
     * Se hace una consulta en la base de datos tomando como parametros el nombre y la contraseñas ingresadas por el usuario.
     * Retorna un arreglo de enteros, el cual lleva dos valores
     * En la posicion [0] --si el logeo fue exitoso (0=no y 1=si)
     * En la posicion [1] --el id del usuario logeado (0=por defecto)
     * @param nomUsu
     * @param contrUsu
     * @return
     */
    public int[] login(String nomUsu,String contrUsu){
        Cursor cursor=db.rawQuery(
                "SELECT * FROM " + DBHelper.TABLE_NAME_1 + " WHERE nombre=? AND contrasena=?", new String[]{nomUsu, contrUsu});
        int[] datosLogeo=new int[2];
        datosLogeo[0]=0;
        datosLogeo[1]=0;
       if (cursor!=null){
            if (cursor.getCount()>0){
                datosLogeo[0]=1;
                cursor.moveToFirst();
                datosLogeo[1]=cursor.getInt(cursor.getColumnIndex(DBHelper.ID));
                return datosLogeo;
            }
       }
        return datosLogeo;
    }
    /**
     * Hace una consulta en la base de datos para ver si exite un usuario en la base.
     * Es utilizado para llamar la actividad del login o l a del registro.
     * Si no hay ningun registro manda llamar el registro y si existe uno entonces se manda llamar el login.
     * @return
     */
    public boolean existUsu(){
        Cursor cursor=db.query(DBHelper.TABLE_NAME_1, new String[]{DBHelper.NAME}, null, null, null, null, null);
        if (cursor!=null){
            if (cursor.getCount()>0){
                return true;
            }
        }
        return false;
    }
    /**
     * Hace una consulñta a la tabla de usuario y me trae todos los nombres de los usuario.
     * Se utiliza para llenar el AutocompleteTextView.
     * @return
     */
    public String[] getAllUsuDB(){
        Cursor cursor= db.query(DBHelper.TABLE_NAME_1, new String[]{DBHelper.NAME}, null, null, null, null, null);
        String[] strUsu=new String[cursor.getCount()];
        int i=0;
        while (cursor.moveToNext()){
            strUsu[i]=cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
            i++;
        }
        return strUsu;
    }
    /**
     * Hace una consulta al la tabla de usuarios tomando como parametro el id del usuario logeado.
     * Retorna le una rreglo de cadenas, con dos valores.
     * el [0] --En nombre del usuario.
     * el [1] --En correo del usuario.
     * @param idUsu
     * @return
     */
    public String[] informacionUsuario(String idUsu){

        Cursor cursor=db.rawQuery(
                "SELECT * FROM " + DBHelper.TABLE_NAME_1 + " WHERE _id=? ", new String[]{idUsu});

        String[] infoUsu=new String[2];
        cursor.moveToFirst();
        infoUsu[0]=cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
        infoUsu[1]=cursor.getString(cursor.getColumnIndex(DBHelper.MAIL));
        return infoUsu;
    }
    /**
     * Creamos todos los modulos que va tener la tabla modulos junto con el id del usuario y depues llamammos un metodo para
     * inceratarlos uno x uno a la tabla.
     * @param idUsuario
     */
    public void addModulos(int idUsuario){
        Modulo modulo;

        modulo=new Modulo("Modulo 1",idUsuario,true,0);
        this.addModulo(modulo);
        modulo=new Modulo("Modulo 2",idUsuario,false,0);
        this.addModulo(modulo);
        modulo=new Modulo("Modulo 3",idUsuario,false,0);
        this.addModulo(modulo);
        modulo=new Modulo("Modulo 4",idUsuario,false,0);
        this.addModulo(modulo);
        modulo=new Modulo("Examen Final",idUsuario,false,0);
        this.addModulo(modulo);
        //casteamos el id a cadena.
        String idUsu=String.valueOf(idUsuario);
        //Traemos el id mediante un metodo.
        int idMod= idPrimerModuloIns(idUsu, "Modulo 1");
        //Insertacion de los temas con el id de su modulo respectivo.
        addTemas(1,idMod);
        idMod++;
        addTemas(2,idMod);
        idMod++;
        addTemas(3,idMod);
        idMod++;
        addTemas(4,idMod);
    }
    /**
     * Incerta modulo x modulo con sus respectivos valores en la tabla modulo.
     * @param modulo
     */
    private void addModulo(Modulo modulo){
        ContentValues values=new ContentValues();
        values.put(DBHelper.NAME, modulo.getNombre());
        values.put(DBHelper.USER_ID, modulo.getUsuario());
        values.put(DBHelper.ACTIVO, modulo.isActivo() == true ? 1 : 0);
        values.put(DBHelper.CALIF, modulo.getCalificacion());
        db.insert(DBHelper.TABLE_NAME_3, null, values);
    }
    /**
     * Se crean todos los temas que va tener cada modulo con el id de cada modulo y despues manda llmar un metodo que incerta uno x uno.
     * @param posicionMod
     * @param idModulo
     */
    private void addTemas(int posicionMod,int idModulo){
        Tema tema;

        //Para ingresarle a cada tema su id del modulo corespondiente
        switch (posicionMod){
            case 1:
                tema=new Tema("Modulo1_Tema1",idModulo,true);//Sistema Operativo
                addTema(tema);
                tema=new Tema("Modulo1_Tema2",idModulo,false);//Entorno Grafico
                addTema(tema);
                tema=new Tema("Modulo1_Tema3",idModulo,false);//Pruebas de Sistema
                addTema(tema);
                tema=new Tema("Modulo1_Tema4",idModulo,false);//Aplicaciones de serie
                addTema(tema);
                tema=new Tema("Modulo1_Tema5",idModulo,false);//
                addTema(tema);
                tema=new Tema("Modulo1_Tema6",idModulo,false);//
                addTema(tema);
                tema=new Tema("Modulo1_Tema7",idModulo,false);//
                addTema(tema);
                tema=new Tema("Modulo1_Examen",idModulo,false);
                addTema(tema);
                break;
            case 2:
                tema=new Tema("Modulo2_Tema1",idModulo,true);//Ciclos
                addTema(tema);
                tema=new Tema("Modulo2_Tema2",idModulo,false);//Metodos
                addTema(tema);
                tema=new Tema("Modulo2_Tema3",idModulo,false);//Bloques
                addTema(tema);
                tema=new Tema("Modulo2_Tema4",idModulo,false);//Modulos
                addTema(tema);
                tema=new Tema("Modulo2_Tema5",idModulo,false);//Mixins
                addTema(tema);
                tema=new Tema("Modulo2_Examen",idModulo,false);
                addTema(tema);
                break;
            case 3:
                tema=new Tema("Modulo3_Tema1",idModulo,true);//Strings
                addTema(tema);
                tema=new Tema("Modulo3_Tema2",idModulo,false);//Arreglos
                addTema(tema);
                tema=new Tema("Modulo3_Tema3",idModulo,false);//Hashes
                addTema(tema);
                tema=new Tema("Modulo3_Tema4",idModulo,false);//Fecha y hora
                addTema(tema);
                tema=new Tema("Modulo3_Examen",idModulo,false);
                addTema(tema);
                break;
            case 4:
                tema=new Tema("Modulo4_Tema1",idModulo,true);//Rangos
                addTema(tema);
                tema=new Tema("Modulo4_Tema2",idModulo,false);//Iterators
                addTema(tema);
                tema=new Tema("Modulo4_Tema3",idModulo,false);//Directorios
                addTema(tema);
                tema=new Tema("Modulo4_Tema4",idModulo,false);//Excepciones
                addTema(tema);
                tema=new Tema("Modulo4_Tema5",idModulo,false);//Orientado a objetos
                addTema(tema);
                tema=new Tema("Modulo4_Tema6",idModulo,false);//Expreciones Regulares
                addTema(tema);
                tema=new Tema("Modulo4_Examen",idModulo,false);
                addTema(tema);
                break;
        }
    }
    /**
     * Inserta los temas uno x uno a la tabla temas
     * @param tema
     */
    private void addTema(Tema tema){
        ContentValues values=new ContentValues();
        values.put(DBHelper.NAME, tema.getNombre());
        values.put(DBHelper.MOD_ID, tema.getModulo());
        values.put(DBHelper.ACTIVO, tema.isActivo()==true?1:0);

        db.insert(DBHelper.TABLE_NAME_2,null,values);
    }
    /**
     * Hace una consulta en la base de datos tomando como parametro el id del usuario y retorna el id del modulo insertado por primera vez
     * @param idUsuario
     * @param nombreModulo
     * @return
     */
    public int idPrimerModuloIns(String idUsuario,String nombreModulo){
        Cursor cursor=db.rawQuery(
                "SELECT * FROM "+DBHelper.TABLE_NAME_3+" WHERE nombre LIKE ? AND  "+DBHelper.USER_ID+" = ?",new String[]{nombreModulo,idUsuario});
        int idMod=0;
        if (cursor!=null){
            cursor.moveToFirst();
            idMod=cursor.getInt(cursor.getColumnIndex(DBHelper.ID));
            return idMod;
        }
        return idMod;
    }
    /**
     * Hace una consulta para saber cunatos Modulos existen
     * (sirve para comprobar si cuando se elimina el usuario se eliminan tambien sus modulos correspondientes)
     * @return
     */
    private int totalModulos(){
        int row=0;
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_3, null);
        row=cursor.getCount();
        return row;
    }
    /**
     * Metodo que hace una consulta para saber cunatos Temas existen
     * (sirve para comprobar si cuando se elimina el usuario se eliminan tambien sus temas correspondientes)
     * @return
     */
    public int totalTemas(){
        int row=0;
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_2, null);
        row=cursor.getCount();
        return row;
    }
    /**
     * Elimina los modulos y temas de un usuario en especifico (Mediante el id de usuario)
     * @param idUsuario
     */
    public void eliminarModulosTemas(int idUsuario){
        String idUsu=String.valueOf(idUsuario);
        int idModuloUno = idPrimerModuloIns(idUsu,"Modulo 1");

        //     -----Eliminamos modulos con el id del usuario.------
        db.delete(DBHelper.TABLE_NAME_3, DBHelper.USER_ID + " = " + idUsuario,null);

        //     -----Eliminamos los temas con los id de los 4 modulos.-----
        //Modulo 1
        db.delete(dbHelper.TABLE_NAME_2,DBHelper.MOD_ID+" = "+idModuloUno,null);
        //Modulo 2
        idModuloUno++;
        db.delete(dbHelper.TABLE_NAME_2,DBHelper.MOD_ID+" = "+idModuloUno,null);
        //Modulo 3
        idModuloUno++;
        db.delete(dbHelper.TABLE_NAME_2,DBHelper.MOD_ID+" = "+idModuloUno,null);
        //Modulo 4
        idModuloUno++;
        db.delete(dbHelper.TABLE_NAME_2,DBHelper.MOD_ID+" = "+idModuloUno,null);
        //Examen final
        idModuloUno++;
        db.delete(dbHelper.TABLE_NAME_2,DBHelper.MOD_ID+" = "+idModuloUno,null);
    }


    /**
     * Metodo que hace una cpnsulta y retorna un booleano segun si el modulo esta bloqueado o activo
     * @param modulo
     * @param idUsuario
     * @return
     */
    public boolean moduloActivo(int modulo,int idUsuario){
        String nomMod="";
        String idUsu=String.valueOf(idUsuario);

        switch (modulo){
            case 1:
                nomMod="Modulo 1";
                Cursor cursor=db.rawQuery(
                        "SELECT * FROM "+DBHelper.TABLE_NAME_3+" WHERE nombre LIKE ? AND  "+DBHelper.USER_ID+" = ?",
                        new String[]{nomMod,idUsu});
                cursor.moveToFirst();
                boolean activo=cursor.getInt(cursor.getColumnIndex(DBHelper.ACTIVO))==1?true:false;

                return activo;
            case 2:
                nomMod="Modulo 2";
                Cursor cursor2=db.rawQuery(
                        "SELECT * FROM "+DBHelper.TABLE_NAME_3+" WHERE nombre LIKE ? AND  "+DBHelper.USER_ID+" = ?",new String[]{nomMod,idUsu});
                cursor2.moveToFirst();
                boolean activo2=cursor2.getInt(cursor2.getColumnIndex(DBHelper.ACTIVO))==1?true:false;

                return activo2;
            case 3:
                nomMod="Modulo 3";
                Cursor cursor3=db.rawQuery(
                        "SELECT * FROM "+DBHelper.TABLE_NAME_3+" WHERE nombre LIKE ? AND  "+DBHelper.USER_ID+" = ?",new String[]{nomMod,idUsu});
                cursor3.moveToFirst();
                boolean activo3=cursor3.getInt(cursor3.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                return activo3;
            case 4:
                nomMod="Modulo 4";
                Cursor cursor4=db.rawQuery(
                        "SELECT * FROM "+DBHelper.TABLE_NAME_3+" WHERE nombre LIKE ? AND  "+DBHelper.USER_ID+" = ?",new String[]{nomMod,idUsu});
                cursor4.moveToFirst();
                boolean activo4=cursor4.getInt(cursor4.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                return activo4;
            case 5:
                nomMod="Examen Final";
                Cursor cursor5=db.rawQuery(
                        "SELECT * FROM "+DBHelper.TABLE_NAME_3+" WHERE nombre LIKE ? AND  "+DBHelper.USER_ID+" = ?",new String[]{nomMod,idUsu});
                cursor5.moveToFirst();
                boolean activo5=cursor5.getInt(cursor5.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                return activo5;
        }
        return false;
    }

    /**
     * Metodo que hace una consulyta a la base retorna un booleano segun si el tema esta bloqueado o activo
     * @param numMod
     * @param numeroCap
     * @param idModulo
     * @return
     */
    public boolean temaActivo(int numMod,int numeroCap,int idModulo){
        String nomTema;
        String idMod=String.valueOf(idModulo);//Solo para compara
        boolean activo=false;

        switch (numMod){
            case 1:
                switch (numeroCap){
                    case 1://sistema operativo
                        nomTema="Modulo1_Tema1";
                        Cursor cursor=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor.moveToFirst();
                        activo=cursor.getInt(cursor.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 2://entorno grafico
                        nomTema="Modulo1_Tema2";
                        Cursor cursor1=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor1.moveToFirst();
                        activo=cursor1.getInt(cursor1.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 3://pruebas de sistema
                        nomTema="Modulo1_Tema3";
                        Cursor cursor2=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor2.moveToFirst();
                        activo=cursor2.getInt(cursor2.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 4://apliciones de serie
                        nomTema="Modulo1_Tema4";
                        Cursor cursor3=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor3.moveToFirst();
                        activo=cursor3.getInt(cursor3.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 5:
                        nomTema="Modulo1_Tema5";
                        Cursor cursor4=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor4.moveToFirst();
                        activo=cursor4.getInt(cursor4.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 6:
                        nomTema="Modulo1_Tema6";
                        Cursor cursor5=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor5.moveToFirst();
                        activo=cursor5.getInt(cursor5.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 7:
                        nomTema="Modulo1_Tema7";
                        Cursor cursor6=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor6.moveToFirst();
                        activo=cursor6.getInt(cursor6.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 8:
                        nomTema="Modulo1_Examen";
                        Cursor cursor20=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor20.moveToFirst();
                        activo=cursor20.getInt(cursor20.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                }
                break;//Temina mis subtemas
            case 2://Inicia mi tema principal
                idModulo++;//Se incrementa el id del modulo
                idMod=String.valueOf(idModulo);//se buelve a hacignar al la variable de cadenas
                switch (numeroCap){//inicias mis subtemas
                    case 1:
                        nomTema="Modulo2_Tema1";
                        Cursor cursor7=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor7.moveToFirst();
                        activo=cursor7.getInt(cursor7.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 2:
                        nomTema="Modulo2_Tema2";
                        Cursor cursor8=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor8.moveToFirst();
                        activo=cursor8.getInt(cursor8.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 3:
                        nomTema="Modulo2_Tema3";
                        Cursor cursor9=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor9.moveToFirst();
                        activo=cursor9.getInt(cursor9.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 4:
                        nomTema="Modulo2_Tema4";
                        Cursor cursor10=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor10.moveToFirst();
                        activo=cursor10.getInt(cursor10.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 5:
                        nomTema="Modulo2_Tema5";
                        Cursor cursor11=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor11.moveToFirst();
                        activo=cursor11.getInt(cursor11.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 6:
                        nomTema="Modulo2_Examen";
                        Cursor cursor21=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor21.moveToFirst();
                        activo=cursor21.getInt(cursor21.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                }
                break;
            case 3:
                idModulo=idModulo+2;//Se incrementa el id del modulo
                idMod=String.valueOf(idModulo);//se buelve a hacignar al la variable de cadenas

                switch (numeroCap){
                    case 1:
                        nomTema="Modulo3_Tema1";
                        Cursor cursor7=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor7.moveToFirst();
                        activo=cursor7.getInt(cursor7.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 2:
                        nomTema="Modulo3_Tema2";
                        Cursor cursor8=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor8.moveToFirst();
                        activo=cursor8.getInt(cursor8.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 3:
                        nomTema="Modulo3_Tema3";
                        Cursor cursor9=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor9.moveToFirst();
                        activo=cursor9.getInt(cursor9.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 4:
                        nomTema="Modulo3_Tema4";
                        Cursor cursor10=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor10.moveToFirst();
                        activo=cursor10.getInt(cursor10.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 5:
                        nomTema="Modulo3_Examen";
                        Cursor cursor22=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor22.moveToFirst();
                        activo=cursor22.getInt(cursor22.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                }
                break;
            case 4:
                idModulo=idModulo+3;//Se incrementa el id del modulo
                idMod=String.valueOf(idModulo);//se buelve a hacignar al la variable de cadenas

                switch (numeroCap){
                    case 1:
                        nomTema="Modulo4_Tema1";
                        Cursor cursor7=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor7.moveToFirst();
                        activo=cursor7.getInt(cursor7.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 2:
                        nomTema="Modulo4_Tema2";
                        Cursor cursor8=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor8.moveToFirst();
                        activo=cursor8.getInt(cursor8.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 3:
                        nomTema="Modulo4_Tema3";
                        Cursor cursor9=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor9.moveToFirst();
                        activo=cursor9.getInt(cursor9.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 4:
                        nomTema="Modulo4_Tema4";
                        Cursor cursor10=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor10.moveToFirst();
                        activo=cursor10.getInt(cursor10.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 5:
                        nomTema="Modulo4_Tema3";
                        Cursor cursor11=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor11.moveToFirst();
                        activo=cursor11.getInt(cursor11.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 6:
                        nomTema="Modulo4_Tema4";
                        Cursor cursor12=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor12.moveToFirst();
                        activo=cursor12.getInt(cursor12.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                    case 7:
                        nomTema="Modulo4_Examen";
                        Cursor cursor23=db.rawQuery(
                                "SELECT * FROM "+DBHelper.TABLE_NAME_2+" WHERE nombre LIKE ? AND  "+DBHelper.MOD_ID+" = ?",new String[]{nomTema,idMod});
                        cursor23.moveToFirst();
                        activo=cursor23.getInt(cursor23.getColumnIndex(DBHelper.ACTIVO))==1?true:false;
                        return activo;
                }
                break;
        }
        return activo;
    }
    /**
     * Solo me activa temas y modulos (haciendo una actialización a la base)
     * @param idmodulo
     * @param numModulo
     * @param numTema
     */
    public void activarTema(int idmodulo, int numModulo, int numTema){
        String nombreTema="";
        boolean isTema=true;
        switch (numModulo){
            case 1:
                switch (numTema){
                    case 1:
                        nombreTema="Modulo1_Tema2";
                        break;
                    case 2:
                        nombreTema="Modulo1_Tema3";
                        break;
                    case 3:
                        nombreTema="Modulo1_Tema4";
                        break;
                    case 4:
                        nombreTema="Modulo1_Tema5";
                        break;
                    case 5:
                        nombreTema="Modulo1_Tema6";
                        break;
                    case 6:
                        nombreTema="Modulo1_Tema7";
                        break;
                    case 7:
                        nombreTema="Modulo1_Examen";
                        break;
                    case 8:
                        isTema=false;
                        int cali=traerCalificacion(1,FormLoginActivity.ID_USU_LOGEADO);
                        if (cali>=8) {
                            activarModulo(FormLoginActivity.ID_USU_LOGEADO, 1);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                idmodulo++;
                switch (numTema){
                    case 1:
                        nombreTema="Modulo2_Tema2";
                        break;
                    case 2:
                        nombreTema="Modulo2_Tema3";
                        break;
                    case 3:
                        nombreTema="Modulo2_Tema4";
                        break;
                    case 4:
                        nombreTema="Modulo2_Tema5";
                        break;
                    case 5:
                        nombreTema="Modulo2_Examen";
                        break;
                    case 6:
                        isTema=false;
                        int cali=traerCalificacion(2,FormLoginActivity.ID_USU_LOGEADO);
                        if (cali>=8) {
                            activarModulo(FormLoginActivity.ID_USU_LOGEADO, 2);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                idmodulo=idmodulo+2;
                switch (numTema){
                    case 1:
                        nombreTema="Modulo3_Tema2";
                        break;
                    case 2:
                        nombreTema="Modulo3_Tema3";
                        break;
                    case 3:
                        nombreTema="Modulo3_Tema4";
                        break;
                    case 4:
                        nombreTema="Modulo3_Examen";
                        break;
                    case 5:
                        isTema=false;
                        int cali=traerCalificacion(3, FormLoginActivity.ID_USU_LOGEADO);
                        if (cali>=8) {
                            activarModulo(FormLoginActivity.ID_USU_LOGEADO, 3);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                idmodulo=idmodulo+3;
                switch (numTema){
                    case 1:
                        nombreTema="Modulo4_Tema2";
                        break;
                    case 2:
                        nombreTema="Modulo4_Tema3";
                        break;
                    case 3:
                        nombreTema="Modulo4_Tema4";
                        break;
                    case 4:
                        nombreTema="Modulo4_Tema5";
                        break;
                    case 5:
                        nombreTema="Modulo4_Tema6";
                        break;
                    case 6:
                        nombreTema="Modulo4_Examen";
                        break;
                    case 7:
                        isTema=false;
                        int cali=traerCalificacion(4,FormLoginActivity.ID_USU_LOGEADO);
                        if (cali>=8) {
                            activarModulo(FormLoginActivity.ID_USU_LOGEADO, 4);
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        if (isTema) {
            String idModulo = String.valueOf(idmodulo);
            ContentValues values = new ContentValues();
            values.put(DBHelper.ACTIVO, 1);
            db.update(DBHelper.TABLE_NAME_2, values, DBHelper.MOD_ID + " = ? AND " + DBHelper.NAME + " LIKE ?",
                    new String[]{idModulo, nombreTema});
        }
    }

    /**
     * Metodo que activa modulos y además guarda su calificación
     * @param idUsuario
     * @param numModulo
     */
    public void activarModulo(String idUsuario, int numModulo){
        String nombreModulo="";
        switch (numModulo){
            case 1:
                nombreModulo="Modulo 2";
                break;
            case 2:
                nombreModulo="Modulo 3";
                break;
            case 3:
                nombreModulo="Modulo 4";
                break;
            case 4:
                //deve dar acceso al examen final
                nombreModulo="Examen Final";
                break;
            default:
                Log.e("defaul de sw", "addCalifModulo: No entro a ninguno" );
                break;
        }
        ContentValues values= new ContentValues();
        values.put(DBHelper.ACTIVO, 1);
        db.update(DBHelper.TABLE_NAME_3, values,DBHelper.USER_ID +" = ? AND "+ DBHelper.NAME +" LIKE ?",
                new String[]{idUsuario,nombreModulo});

    }

    public void setCalifModulo(String idUsuario,int numeroModulo,int calificacion){
        int totalCalif=0;

        if (calificacion==5){totalCalif=10;}
        else if (calificacion==4){totalCalif=8;}
        else if (calificacion==3){totalCalif=6;}
        else if (calificacion==2){totalCalif=4;}
        else if (calificacion==1){totalCalif=2;}
        else {totalCalif=0;}


            String nombre = "";
            switch (numeroModulo) {
                case 1:
                    nombre = "Modulo 1";
                    break;
                case 2:
                    nombre = "Modulo 2";
                    break;
                case 3:
                    nombre = "Modulo 3";
                    break;
                case 4:
                    nombre = "Modulo 4";
                    break;
                case 5:
                    nombre = "Examen Final";
                    break;
            }
            ContentValues values = new ContentValues();
            values.put(DBHelper.CALIF, totalCalif);
            db.update(DBHelper.TABLE_NAME_3, values, DBHelper.USER_ID + " = ? AND " + DBHelper.NAME + " LIKE ?",
                    new String[]{idUsuario, nombre});
    }

    /**
     * Pero tienes que checar si funciona, por que
     * Me trae la calificación de cada módulo
     * @param numeroModulo
     * @param idUsuarios
     * @return
     */
    public int traerCalificacion(int numeroModulo,String idUsuarios){
        int calif=0;

        String nombre = "";
        switch (numeroModulo) {
            case 1:
                nombre = "Modulo 1";
                break;
            case 2:
                nombre = "Modulo 2";
                break;
            case 3:
                nombre = "Modulo 3";
                break;
            case 4:
                nombre = "Modulo 4";
                break;
            case 5:
                nombre = "Examen Final";
                break;
        }
        Cursor cursor8=db.rawQuery(
                "SELECT * FROM "+DBHelper.TABLE_NAME_3+" WHERE nombre LIKE ? AND  "+DBHelper.USER_ID+" = ?",new String[]{nombre,idUsuarios});
        cursor8.moveToFirst();
        calif=cursor8.getInt(cursor8.getColumnIndex(DBHelper.CALIF));

        return calif;
    }

    public int getProgresoModulos (String idUsuario){
        int row=0;
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_3+" where "+DBHelper.CALIF+" >= ? and "+DBHelper.USER_ID+" = ?",
                new String[]{"8",idUsuario});
        row=cursor.getCount();
        //Cuenta de los temas
        int idMod=idPrimerModuloIns(idUsuario,"Modulo 1");
        int idUsuar=Integer.valueOf(idUsuario);
        //Progreso del modulo 1
        int row2=getProgresoTemas(idUsuar,idMod, 1);
        //Progreso del modulo 2
        idMod++;
        int row3=getProgresoTemas(idUsuar,idMod,2);
        //Progreso del modulo 3
        idMod++;
        int row4=getProgresoTemas(idUsuar,idMod,3);
        //Progreso del modulo 4
        idMod++;
        int row5=getProgresoTemas(idUsuar,idMod,4);
        int total=row+row2+row3+row4+row5;
        Log.e("Modululos", "getProgresoModulos: "+row );
        Log.e("Modul 1", "getProgresoModulos: "+row2 );
        Log.e("Modul 2", "getProgresoModulos: "+row3 );
        Log.e("Modul 3", "getProgresoModulos: "+row4 );
        Log.e("Modul 4", "getProgresoModulos: "+row5 );
        return total;//+row2+row3+row4+row5;
    }
    public int getProgresoTemas (int idUsuario,int idModulo,int numeroMod){
        String idMod=String.valueOf(idModulo);
        boolean activoo=false;
        int row40=0;
        Cursor cursor;
        switch (numeroMod){
            case 1:
                cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_2 + " where " + DBHelper.ACTIVO + " = ? and " + DBHelper.MOD_ID + " = ?",
                        new String[]{"1", idMod});
                row40=cursor.getCount();
                if (traerCalificacion(idModulo,FormLoginActivity.ID_USU_LOGEADO)>=8){
                    return row40;
                }else {
                    return row40-1;
                }
            case 2:
                cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_2 + " where " + DBHelper.ACTIVO + " = ? and " + DBHelper.MOD_ID + " = ?",
                        new String[]{"1", idMod});
                row40=cursor.getCount();
                if (traerCalificacion(idModulo,FormLoginActivity.ID_USU_LOGEADO)>=8){
                    return row40;
                }else {
                    return row40-1;
                }
            case 3:
                cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_2 + " where " + DBHelper.ACTIVO + " = ? and " + DBHelper.MOD_ID + " = ?",
                        new String[]{"1", idMod});
                row40=cursor.getCount();
                if (traerCalificacion(idModulo,FormLoginActivity.ID_USU_LOGEADO)>=8){
                    return row40;
                }else {
                    return row40-1;
                }
            case 4:
                cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_2+" where "+DBHelper.ACTIVO+" = ? and "+DBHelper.MOD_ID+" = ?",
                        new String[]{"1",idMod});
                row40=cursor.getCount();
                activoo= moduloActivo(2,idUsuario);
                if (traerCalificacion(idModulo,FormLoginActivity.ID_USU_LOGEADO)>=8){
                    return row40;
                }else {
                    return row40-1;
                }
            default:
                break;
        }
        return row40;
    }

    public boolean terminoApp(String idUsuario){
        int row=0;
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DBHelper.TABLE_NAME_3+" where "+DBHelper.CALIF+" >= ? and "+DBHelper.USER_ID+" = ? AND "+DBHelper.NAME+" LIKE ?",
                new String[]{"8",idUsuario,"Examen Final"});
        row=cursor.getCount();
        if (row>0){
            return true;
        }

        return false;
    }


}
