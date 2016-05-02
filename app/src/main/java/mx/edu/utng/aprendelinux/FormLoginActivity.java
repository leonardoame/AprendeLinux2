package mx.edu.utng.aprendelinux;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.edu.utng.aprendelinux.dao.DBHelper;
import mx.edu.utng.aprendelinux.dao.UsuarioDAO;
import mx.edu.utng.aprendelinux.dao.UsuarioDAOImpl;
import mx.edu.utng.aprendelinux.util.DBAdapter;



public class FormLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private AutoCompleteTextView edtUser;
    private EditText edtPass;
    private Button btnIngresar;
    private Button btnMasTarde;
    private Intent intent;
    private Bundle bundle;
    private Boolean bandera=false;
    private MediaPlayer mp;
    private DBAdapter dbAdapter;

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private UsuarioDAOImpl dao;
    private UsuarioDAO usuarioDAO;

    private Button btnPruebaRegistro;////////////

    //Mensaje errores
    private AlertDialog.Builder builder;
    private String mensajeError="";

    //bd Autocomplete
    private  String[] listaUsu;
    private ArrayAdapter adapterUsu;
    public static String NOMBRE_USU_LOGEADO="";
    public static String CORREO_USU_LOGEADO="";
    public static String ID_USU_LOGEADO="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_login_layout);
        initComponent();
    }

    private void initComponent(){
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();

        edtUser= (AutoCompleteTextView) findViewById(R.id.edt_user);
        edtPass = (EditText) findViewById(R.id.edt_pass);

        btnIngresar= (Button) findViewById(R.id.btn_ingresar);
       // btnMasTarde= (Button) findViewById(R.id.btn_mas_tarde);
        //btnPruebaRegistro= (Button) findViewById(R.id.btn_prueba_registro);////////

        dbHelper= new DBHelper(this, DBHelper.DATABASE_NAME,null, DBHelper.DATABASE_VERSION);
        db= dbHelper.getReadableDatabase();
        dao= new UsuarioDAOImpl();

        usuarioDAO=new UsuarioDAOImpl();

        if(dbAdapter.existUsu()) {
            try {

                listaUsu =dbAdapter.getAllUsuDB();
                adapterUsu =new ArrayAdapter(this,R.layout.item_list_layout,R.id.txv_item, listaUsu);

                edtUser.setAdapter(adapterUsu);

            }catch (NullPointerException e){
                Log.e("Erro en el tray null:",e.toString());
            }catch (Exception e){
                Log.e("Erro en el tray null:",e.toString());
            }

        }

        mp=MediaPlayer.create(this,R.raw.cli_d);

        btnIngresar.setOnClickListener(this);
        //  btnMasTarde.setOnClickListener(this);
        //  btnPruebaRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        bundle= new Bundle();
        mp.start();
        switch (v.getId()){
            case R.id.btn_ingresar:
                mensajeError="";
                builder = new AlertDialog.Builder(this);

                if (edtUser.length()<1){
                    mensajeError=mensajeError+"-"+getResources().getString(R.string.usuario_create_login_registro)+"\n";
                }
                if (edtPass.length()<1){
                    mensajeError=mensajeError+"-"+getResources().getString(R.string.pass_create_login_registro)+"\n";
                }

                String nomUsu=edtUser.getText().toString();
                String conUsu=edtPass.getText().toString();

                if (mensajeError.length()==0){

                    try {
                        int[] datosLog=dbAdapter.login(nomUsu, conUsu);
                        if (datosLog[0]==1) {
                            ID_USU_LOGEADO=String.valueOf(datosLog[1]);
                            //Para traerme la informacion del usuario (Nombre, correo y calificacion de sus examenes)
                            String[] datosUsu=dbAdapter.informacionUsuario(ID_USU_LOGEADO);
                            NOMBRE_USU_LOGEADO=datosUsu[0];
                            CORREO_USU_LOGEADO=datosUsu[1];
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.mensaje_bien_venido)+NOMBRE_USU_LOGEADO,
                                    Toast.LENGTH_SHORT).show();
                            bandera = true;
                            bundle.putBoolean("logeo", bandera);
                            intent = new Intent(FormLoginActivity.this, SeleccionModuloActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();//----------------------------------------------------------------------------No estoy seguro-----------------------
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.error_autenticar, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Error en el tray (logeo)",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    MensajeError(mensajeError,getResources().getString(R.string.title_menssage_error_dos));
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onStop() {
        edtUser.setText("");
        edtPass.setText("");
        bandera=false;
        super.onStop();
    }

    private void MensajeError(String err,String titulo){
        builder.setMessage(err)
                .setTitle(titulo)
                .setCancelable(false)
                .setNeutralButton(getResources().getString(R.string.button_menssage_error),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            listaUsu =dbAdapter.getAllUsuDB();
            adapterUsu =new ArrayAdapter(this,R.layout.item_list_layout,R.id.txv_item, listaUsu);
            edtUser.setAdapter(adapterUsu);
        }catch (NullPointerException e){
            Log.e("Erro en el OnResume",e.toString());
        }catch (Exception e){
            Log.e("Erro en el OnResume",e.toString());
        }
    }
}
