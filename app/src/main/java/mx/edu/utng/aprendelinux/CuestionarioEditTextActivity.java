package mx.edu.utng.aprendelinux;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class CuestionarioEditTextActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtRespuesta;
    private Button btnComprobarEscrito;
    private TextView txvOracionParteUno,txvOracionParteDos;
    private String oracionUno,oracionDos;
    private Bundle valoresResividos;
    private DBAdapter dbAdapter;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuestionario_edit_text_layout);
        initComponents();
    }

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        edtRespuesta= (EditText) findViewById(R.id.edt_respuesta);
        btnComprobarEscrito= (Button) findViewById(R.id.btn_comprobar_escrito);

        txvOracionParteUno= (TextView) findViewById(R.id.txv_oracion_parte_uno);
        txvOracionParteDos= (TextView) findViewById(R.id.txv_oracion_parte_dos);


        valoresResividos=getIntent().getExtras();

        switch (valoresResividos.getInt("moduloS")){
            case 0://Modulo 1
                switch (valoresResividos.getInt("posicionTemaS")){
                    case 2://Pruebas de sistema
                        oracionUno="Teclas que debes presionar simultáneamente para navegar entre las miniaturas de las aplicaciones abiertas. Alt +... ";
                        oracionDos=".";
                        edtRespuesta.setMaxWidth(120);
                        break;
                    case 5://copiando archivos
                        oracionUno="comando que nos permite copiar archivos";
                        oracionDos=".";
                        break;
                    default:
                        break;
                }
                break;
            case 1://Modulo 2
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 2://Ordenar mis archivos
                        oracionUno="Los sistemas Gnu Linux fueron de los primeros en dar soporte a dispositivos ";
                        oracionDos=".";
                    default:
                        break;
                }
                break;
            case 2://Modulo 3
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 2://Hashes
                        oracionUno="Comando que se encarga de crear el enlace automaticamnete";
                        oracionDos=".";
                        break;
                    default:
                        break;
                }
                break;
            case 3://Modulo 4
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 2://manejo de usuarios
                        oracionUno="comando para cambiar de usuario  ";
                        oracionDos=".";
                        break;
                    case 5://Expreciónes Regulares
                        oracionUno=" todo lo que deben hacer es borrar su\n" +
                                "entrada correspondiente en el archivo /etc/passwd, /etc/ ";
                        oracionDos=".";
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        edtRespuesta.setMaxWidth(120);



        txvOracionParteUno.setText(oracionUno);
        txvOracionParteDos.setText(oracionDos);
        btnComprobarEscrito.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        String respuesta = "Incorrecto";
       // String espacios="\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020";
        int idModul=dbAdapter.idPrimerModuloIns(FormLoginActivity.ID_USU_LOGEADO,"Modulo 1");
        switch (valoresResividos.getInt("moduloS")){
            case 0://Modulo 1
                switch (valoresResividos.getInt("posicionTemaS")){
                    case 2://Sintaxis
                        if (edtRespuesta.getText().toString().toLowerCase().equals("tab")) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 1, 3);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioEditTextActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 5://Copiando archivos
                        if (edtRespuesta.getText().toString().toLowerCase().equals("cp")) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 1, 6);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioEditTextActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            case 1://Modulo 2
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 2://ordebar mis archivos
                        if (edtRespuesta.getText().toString().toLowerCase().equals("usb")) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 2, 3);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioEditTextActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                    default:
                        break;
                }
                break;
            case 2://Modulo 3
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 2://Enlces
                        if (edtRespuesta.getText().toString().toLowerCase().equals("ln")) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 3, 3);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioEditTextActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            case 3://Modulo 4
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 2://Directorios
                        if (edtRespuesta.getText().toString().toLowerCase().equals("su")) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            //int idModul=dbAdapter.idPrimerModuloIns(FormLoginActivity.ID_USU_LOGEADO,"Modulo 1");
                            dbAdapter.activarTema(idModul, 4, 3);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioEditTextActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 5://Expreciónes Regulares
                        if (edtRespuesta.getText().toString().toLowerCase().equals("shadow")) {
                            respuesta ="Correcto; Examen del módulo desbloqueado";
                            dbAdapter.activarTema(idModul, 4, 6);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioEditTextActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
