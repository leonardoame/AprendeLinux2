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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.edu.utng.aprendelinux.dao.DBHelper;
import mx.edu.utng.aprendelinux.dao.UsuarioDAOImpl;
import mx.edu.utng.aprendelinux.model.Usuario;
import mx.edu.utng.aprendelinux.util.DBAdapter;



public class FormCreateLoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtCreateUser;
    private EditText edtCreatePass;
    private EditText edtCreatePassConfirm;
    private EditText edtCreateCorreo;
    private Button btnGuardar;
    private Button btnMasTarde;
    private MediaPlayer mp;
    private DBAdapter dbAdapter;

    //Para el ingreso
    private SQLiteDatabase db;
    private Usuario usuario;
    private DBHelper dbHelper;
    private UsuarioDAOImpl dao;
    private Bundle bundle;
    private Boolean bandera=false;
    //

    private AlertDialog.Builder builder;
    private String mensajeError="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_create_login_layout);
        initComponents();
    }
    private void initComponents(){

        dbAdapter=new DBAdapter(this);
        dbAdapter.open();

        //Para el ingrso
        dbHelper= new DBHelper(this, DBHelper.DATABASE_NAME,null, DBHelper.DATABASE_VERSION);
        db= dbHelper.getWritableDatabase();
        dao= new UsuarioDAOImpl();
        //

        edtCreateUser=(EditText) findViewById(R.id.edt_create_user);
        edtCreatePass=(EditText) findViewById(R.id.edt_create_pass);
        edtCreatePassConfirm=(EditText) findViewById(R.id.edt_create_pass_confirm);
        edtCreateCorreo=(EditText) findViewById(R.id.edt_create_correo);
        btnGuardar= (Button) findViewById(R.id.btn_create_login_guardar);
        btnMasTarde= (Button) findViewById(R.id.btn_mas_tarde_create_login);


        mp=MediaPlayer.create(this,R.raw.cli_d);


        btnGuardar.setOnClickListener(this);
        btnMasTarde.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mp.start();
        bundle= new Bundle();
        switch (v.getId()){
            case R.id.btn_create_login_guardar:
                //Guardar-----------------------------------------------------------------------------------------------
                //Validaciones
                mensajeError="";
                String titulo=getResources().getString(R.string.title_menssage_error_dos);
                builder = new AlertDialog.Builder(this);
                String passUno=String.valueOf(edtCreatePass.getText().toString());
                String passDos=String.valueOf(edtCreatePassConfirm.getText().toString());

                if (edtCreateUser.length()<1){
                    mensajeError=mensajeError+"-"+getResources().getString(R.string.usuario_create_login_registro)+"\n";
                }
                if (edtCreatePass.length()<1){
                    mensajeError=mensajeError+"-"+getResources().getString(R.string.pass_create_login_registro)+"\n";
                }
                if (edtCreatePassConfirm.length()<1){
                    mensajeError=mensajeError+"-"+getResources().getString(R.string.confirm_pass_create_login_registro)+"\n";
                }
                if (edtCreateCorreo.length()<1){
                    mensajeError=mensajeError+"-"+getResources().getString(R.string.correo_create_login_registro)+"\n";
                }
                //Para validar el tamaño
                if (mensajeError.length()==0) {

                    if (edtCreateUser.length()<4){
                        mensajeError=mensajeError+"-"+getResources().getString(R.string.usuario_create_login_registro)+" (Minimo 4 caracteres)\n";
                        titulo=getResources().getString(R.string.title_menssage_error_uno);
                    }
                    if (edtCreatePass.length()<4){
                        mensajeError=mensajeError+"-"+getResources().getString(R.string.pass_create_login_registro)+" (Minimo 4 caracteres)\n";
                        titulo=getResources().getString(R.string.title_menssage_error_uno);
                    }
                    if (edtCreateCorreo.length()<4){
                        mensajeError=mensajeError+"-"+getResources().getString(R.string.correo_create_login_registro)+" (Minimo 4 caracteres)\n";
                        titulo=getResources().getString(R.string.title_menssage_error_uno);
                    }
                    //Para validar el correo
                    /*if (mensajeError.length()==0) {
                        String buscado="@";
                        String correo=edtCreateCorreo.getText().toString();
                        int siExiste=0;

                        while (correo.indexOf(buscado)>-1){
                            correo=correo.substring(correo.indexOf(
                               buscado)+correo.length(),correo.length());
                            siExiste++;
                        }

                        if (siExiste==0){// 0--false 1--true
                            mensajeError=mensajeError+"-"+getResources().getString(R.string.correo_create_login_registro)+" (Formato incorrecto)\n";
                        }else {
                            buscado=".";
                            while (correo.indexOf(buscado)>-1){
                                correo=correo.substring(correo.indexOf(
                                        buscado)+correo.length(),correo.length());
                                siExiste++;
                            }
                            if (siExiste==0){//0--false 1--true
                                mensajeError=mensajeError+"-"+getResources().getString(R.string.correo_create_login_registro)+" (Formato incorrecto)\n";
                            }
                        }

                    }*/
                }

                if (mensajeError.length()==0) {
                    if (passUno.equals(passDos)) {
                        //Para el gusrdado
                        usuario = new Usuario(
                                0,
                                edtCreateUser.getText().toString(),
                                edtCreateCorreo.getText().toString(),
                                edtCreatePass.getText().toString()
                        );
                        if (usuario.getIdUsuario() == 0) {
                            dao.agregar(usuario, db);
                            try {
                                int[] datosLogeado=dbAdapter.login(
                                        edtCreateUser.getText().toString(),edtCreatePass.getText().toString());

                                Log.i("Id del usuario", "onClick: " + datosLogeado[1]);

                                dbAdapter.addModulos(datosLogeado[1]);
                                int totalTems=dbAdapter.totalTemas();
                                Log.i("Total temas incertados", "onClick: "+totalTems);
                            }catch (Exception e){
                                Log.e("FormCreateLogin", "onClick: "+e.toString() );
                            }
                        } /*else {
                            dao.modificar(usuario, db);
                        }*/


                        Toast toast = null;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_guardados_correctamente) +": "+edtCreateUser.getText(),
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FormCreateLoginActivity.this, FormLoginActivity.class));
                        finish();
                    } else {
                        titulo=getResources().getString(R.string.title_menssage_error_uno);
                        MensajeError(getResources().getString(R.string.menssage_error_pass_different), getResources().getString(R.string.title_menssage_error_uno));
                    }
                }else {
                    MensajeError(mensajeError,titulo);
                }
                //fin Guardar-----------------------------------------------------------------------------------------------------
                break;
            case R.id.btn_mas_tarde_create_login:
                //Mas tarde --------------------------------------------------------------------------------------------------------
                bundle.putBoolean("logeo", bandera);
                startActivity(new Intent(this, SeleccionModuloActivity.class).putExtras(bundle));
                finish();
                //Fin Mas tarde --------------------------------------------------------------------------------------------------------
                break;
            default:

                break;
        }

    }

    @Override
    protected void onStop() {
            edtCreateUser.setText("");
        edtCreateCorreo.setText("");
        edtCreatePass.setText("");
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
}