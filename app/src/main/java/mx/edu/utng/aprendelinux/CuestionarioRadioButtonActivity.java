package mx.edu.utng.aprendelinux;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class CuestionarioRadioButtonActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioGroup rbgOpciones;
    private RadioButton rbtRespuestaUno,rbtRespuestaDos;
    private Button btnComprobarRes;
    private Bundle valoresResividos;
    private TextView txvPregunta;
    private String pregunta="",opcionUno="",opcionDos="";
    private DBAdapter dbAdapter;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuestionario_radio_button_layout);
        initComponents();
    }

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        rbgOpciones = (RadioGroup) findViewById(R.id.rbg_opciones);
        rbtRespuestaUno = (RadioButton) findViewById(R.id.rbt_repuesta_uno);
        rbtRespuestaDos = (RadioButton) findViewById(R.id.rbt_repuesta_dos);
        btnComprobarRes = (Button) findViewById(R.id.btn_comprobar_res);
        txvPregunta=(TextView)findViewById(R.id.txv_pregunta);



        valoresResividos=getIntent().getExtras();

        switch (valoresResividos.getInt("moduloS")){
            case 0://Modulo 1
                switch (valoresResividos.getInt("posicionTemaS")){
                    case 1://Entorno Grafico
                        pregunta="El escritorio de trabajo de Linux cuenta con un “lanzador de aplicaciones” ";
                        opcionUno="Cierto";
                        opcionDos="Falso";
                        break;
                    case 3://Aplicaciones de serie
                        pregunta="El gestor de actividades sirve para crear cd-dvd con archivos multimedia o con datos informáticos ";
                        opcionUno="Cierto";
                        opcionDos="Falso";
                        break;
                    default:
                        break;
                }
                break;
            case 1://Modulo 2
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 1://Método
                        pregunta="puedes usar y transformar esos archivos a formatos libres o simplemente\n" +
                                "instalar aplicaciones";
                        opcionUno="Verdadero";
                        opcionDos="Falso";
                        break;
                    case 3://Modulo
                        pregunta="aplicación instalada de serie para administrar su colección de\n" +
                                "fotografías.";
                        opcionUno="Shotwel";
                        opcionDos="visorView";
                        break;
                    default:
                        break;
                }
                break;
            case 2://Due;os
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 1://due;os
                        pregunta="A cada archivo le corresponde a un usuario o aun grupo de usuarios ";
                        opcionUno="Verdadero";
                        opcionDos="Falso";
                        break;
                    case 3://Variables de entorno
                        pregunta="Esta variable define el tipo de terminal que estamos utilizando. ";
                        opcionUno="SHELL";
                        opcionDos="TERM";
                        break;
                    default:
                        break;
                }
                break;
            case 3://Modulo 4
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 1://Iterators
                        pregunta="Elcomando top nos sirve para cerrar el sistema";
                        opcionUno="Falso";
                        opcionDos="Verdadero";
                        break;
                    case 3://Otros comandos
                        pregunta="El comando cal muestra el calendario ";
                        opcionUno="Verdadero";
                        opcionDos="Falso";
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        txvPregunta.setText(pregunta);
        rbtRespuestaUno.setText(opcionUno);
        rbtRespuestaDos.setText(opcionDos);

        btnComprobarRes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String respuesta = "Incorrecto";
        //String espacios="\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020";
        int idModul=dbAdapter.idPrimerModuloIns(FormLoginActivity.ID_USU_LOGEADO,"Modulo 1");
        switch (valoresResividos.getInt("moduloS")){
            case 0://Modulo 1
                switch (valoresResividos.getInt("posicionTemaS")){
                    case 1://Medio ambiente de configuración

                        if (rbtRespuestaUno.isChecked()) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 1, 2);

                        } else if (rbtRespuestaDos.isChecked()) {
                                vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 3://Aplicaciones de serie
                        if (rbtRespuestaUno.isChecked()) {
                            vibrator.vibrate(1000);
                        } else if (rbtRespuestaDos.isChecked()) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 1, 4);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            case 1://Modulo 2
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 1://conexion de sispositivos
                        if (rbtRespuestaDos.isChecked()) {
                            vibrator.vibrate(1000);
                        } else if (rbtRespuestaUno.isChecked()) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 2, 2);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 3://Modulo
                        if (rbtRespuestaUno.isChecked()) {
                            vibrator.vibrate(1000);
                        } else if (rbtRespuestaDos.isChecked()) {
                            respuesta = "Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 2, 4);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            case 2://Modulo 3
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 1://Mmanejo de procesos
                        if (rbtRespuestaUno.isChecked()) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 3, 2);
                        } else if (rbtRespuestaDos.isChecked()) {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 3://Fecha y hora
                        if (rbtRespuestaUno.isChecked()) {
                            vibrator.vibrate(1000);
                        } else if (rbtRespuestaDos.isChecked()) {
                            respuesta ="Correcto; Examen del módulo desbloqueado";
                            dbAdapter.activarTema(idModul, 3, 4);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            case 3://Modulo 4
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 1://Iterators
                        if (rbtRespuestaDos.isChecked()) {
                            vibrator.vibrate(1000);
                        } else if (rbtRespuestaUno.isChecked()) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 4, 2);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 3://Excepciones
                        if (rbtRespuestaDos.isChecked()) {
                            vibrator.vibrate(1000);
                        } else if (rbtRespuestaUno.isChecked()) {
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 4, 4);
                        }
                        Toast.makeText(CuestionarioRadioButtonActivity.this, respuesta, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
