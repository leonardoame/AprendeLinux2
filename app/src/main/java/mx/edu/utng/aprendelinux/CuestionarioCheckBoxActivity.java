package mx.edu.utng.aprendelinux;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class CuestionarioCheckBoxActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txvPreguntaCheckBox;
    private CheckBox chkCajaUno,chkCajaDos,chkCajaTres;
    private Bundle valoresResividos;
    private Button btnComprobarCheckBox;
    private DBAdapter dbAdapter;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuestionario_check_box_layout);

        initComponents();
    }

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        txvPreguntaCheckBox=(TextView)findViewById(R.id.txv_pregunta_ckech_box);
        chkCajaUno=(CheckBox)findViewById(R.id.chk_caja_uno);
        chkCajaDos=(CheckBox)findViewById(R.id.chk_caja_dos);
        chkCajaTres=(CheckBox)findViewById(R.id.chk_caja_tres);
        btnComprobarCheckBox=(Button)findViewById(R.id.btn_comprobar_check_box);

        valoresResividos=getIntent().getExtras();

        switch (valoresResividos.getInt("moduloS")){
            case 0://Modulo 1
                switch (valoresResividos.getInt("posicionTemaS")){
                    case 0: //Sobre ssitema opeartivo
                        txvPreguntaCheckBox.setText("Link correcto para descargar la distribución Ubuntu Linux");
                        chkCajaUno.setText("http://www.ubuntu.com/download/desktop");
                        chkCajaDos.setText("http://www.linux.com/download/desktop");
                        chkCajaTres.setText("http://www.ubuntu.com/download/");
                        break;
                    case 4://Variables
                        txvPreguntaCheckBox.setText("En este directorio encontraremos todo lo demás: archivos de\n" +
                                "documentación, programas, más librerías, código fuente, etc.");
                        chkCajaUno.setText(" /");
                        chkCajaDos.setText(" var ");
                        chkCajaTres.setText("usr");
                        break;
                    case 6://Comentarios
                        txvPreguntaCheckBox.setText("Elimina archivos sin pedir confirmación.");
                        chkCajaUno.setText(" -i ");
                        chkCajaDos.setText(" -r ");
                        chkCajaTres.setText(" -f ");
                        break;
                    default:
                        break;

                }
                break;
            case 1://Modulo 2
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 0://Carpetas de trabajo
                        txvPreguntaCheckBox.setText("es la sección del disco duro que contiene la\n" +
                                " instalación del sistema operativo. Ahí se pueden encontrar las\n" +
                                " carpetas del sistema y los archivos principales.");
                        chkCajaUno.setText(" Sistema de archivos ");
                        chkCajaDos.setText(" La sección de Red");
                        chkCajaTres.setText(" ventana del gestor de archivos ");
                        break;
                    case 4://directorios
                        txvPreguntaCheckBox.setText("Comando para crear directorios");
                        chkCajaUno.setText(" cd ");
                        chkCajaDos.setText(" cp ");
                        chkCajaTres.setText(" mkdir ");
                        break;
                    default:
                        break;
                }
                break;
            case 2://Modulo 3
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 0://permisos de acceso
                        txvPreguntaCheckBox.setText("Comando para modificar los permisos de un archivo");
                        chkCajaUno.setText(" cp ");
                        chkCajaDos.setText(" rm ");
                        chkCajaTres.setText(" chmod ");
                        break;
                    default:
                        break;
                }
                break;
            case 3://Modulo 4
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 0://comandos Manejp de archivos
                        txvPreguntaCheckBox.setText("comando que muestra el espacio en el disco utilizado.");
                        chkCajaUno.setText(" du ");
                        chkCajaDos.setText(" df ");
                        chkCajaTres.setText(" ls ");
                        break;
                    case 4://nuevo usuario
                        txvPreguntaCheckBox.setText("Cuantas alternativas posibles tenemos para crear un nuevo usuario :");
                        chkCajaUno.setText(" 3");
                        chkCajaDos.setText("2 ");
                        chkCajaTres.setText(" 1 ");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        btnComprobarCheckBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String respuesta = "Incorrecto";
        //String espacios="\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020";
        int idModul = dbAdapter.idPrimerModuloIns(FormLoginActivity.ID_USU_LOGEADO, "Modulo 1");
        switch (valoresResividos.getInt("moduloS")){
            case 0://Modulo 1
                switch (valoresResividos.getInt("posicionTemaS")){
                    case 0: //Sistema opeartivo

                        if (chkCajaUno.isChecked()){
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            //Inicia lo del guardado------------------------------------------------------

                            if (valoresResividos.getBoolean("logeo")) {
                                dbAdapter.activarTema(idModul, 1, 1);
                                // boolean act=dbAdapter.temaActivo(1,2,idModul);
                                // Log.e("Tema 2 mod 1:", "onClick: "+act );
                            }else {
                                startActivity(new Intent(CuestionarioCheckBoxActivity.this,TerminoTemaOffActivity.class));
                                finish();
                                //mandarme a un pantalla que diga que Felizitaciones terinaste con extito el tema 1, para poder sergir con el curso te recomenadamos logearte
                                //dos botones uno que diga resgistrarme y otro que diga despues
                            }
                            //Termina lo del guardado------------------------------------------------------
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
                        finish();

                        break;
                    case 4://Sistema de archivos

                        if (chkCajaTres.isChecked()){
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 1, 5);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
                        finish();
                        break;
                    case 6://Borrar archivos

                        if (chkCajaTres.isChecked()){
                            respuesta = "Correcto; Examen del módulo desbloqueado";
                            dbAdapter.activarTema(idModul, 1, 7);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
                        finish();
                        break;
                    default:
                        break;

                }
                break;
            case 1://Modulo 2
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 0://Carpetas de trabajo
                        ///////////////////////////////////////
                        if (chkCajaUno.isChecked()){
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 2, 1);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
                        finish();
                        ///////////////////////////////////////
                        break;
                    case 4://directorios
                        if (chkCajaTres.isChecked()){
                            respuesta ="Correcto; Examen del módulo desbloqueado";
                            dbAdapter.activarTema(idModul, 2, 5);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
                        finish();
                        ///////////////////////////////////////
                        break;
                    default:
                        break;
                }
                break;
            case 2://Modulo 3
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 0://permisos de acceso
                        if (chkCajaTres.isChecked()){
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 3, 1);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
                        finish();

                        break;
                    default:
                        break;
                }
                break;
            case 3://Modulo 4
                switch (valoresResividos.getInt("posicionTemaS")) {
                    case 0://comandos Manejp de archivos
                        if (chkCajaUno.isChecked()){
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 4, 1);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
                        finish();
                        break;
                    case 4://Ceacion de un nuevo usuario
                        if (chkCajaDos.isChecked()){
                            respuesta ="Correcto; Siguiente tema desbloqueado";
                            dbAdapter.activarTema(idModul, 4, 5);
                        }else {
                            vibrator.vibrate(1000);
                        }
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                        limpiar();
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

    private void limpiar(){
        chkCajaUno.setChecked(false);
        chkCajaDos.setChecked(false);
        chkCajaTres.setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        limpiar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
