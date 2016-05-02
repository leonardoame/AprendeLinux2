package mx.edu.utng.aprendelinux;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import mx.edu.utng.aprendelinux.util.DBAdapter;


public class ExamenFinalActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txvPreguntaUno,txvPreguntaDos,txvPreguntaTres, txvPreguntaCuatro,txvPreguntaCinco;
    private Button btnComprobar;
    private RadioGroup rgbGrupoUno, rgbGrupoDos;
    private CheckBox chkUno,chkDos,chkTres,chkUnoDos,chkDosDos,chkTresDos;
    private TextView txvParteUno,txvParteTres;
    private EditText edtParteDos;
    private RadioButton rbtOpcionUno,rbtOpcionDos,rbtOpcionUnoDos,rbtOpcionDosDos;
    //Timepo
    private TextView txvTiempo,txvTituloExam;

    int calificacion=0;
    private Bundle valorResividos;
    private Bundle bundle;
    //SQlite
    private DBAdapter dbAdapter;
    CounterClass timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examen_final_layout);
        initComponents();
    }//nombreModulo

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        valorResividos =getIntent().getExtras();
        bundle=new Bundle();
        //TXV de las preguntas
        txvPreguntaUno=(TextView)findViewById(R.id.txv_pregunta_uno);
        txvPreguntaDos=(TextView)findViewById(R.id.txv_pregunta_dos);
        txvPreguntaTres=(TextView)findViewById(R.id.txv_pregunta_tres);
        txvPreguntaCuatro =(TextView)findViewById(R.id.txv_pregunta_cuatro);
        txvPreguntaCinco=(TextView)findViewById(R.id.txv_pregunta_cinco);
        //TXV extras
        txvTituloExam=(TextView)findViewById(R.id.titulo_exam);
        //BTN para comprobar
        btnComprobar=(Button)findViewById(R.id.btn_comprobar);
        //RGB de los radio
        rgbGrupoUno =(RadioGroup)findViewById(R.id.rbg_opciones_uno);
        rgbGrupoDos =(RadioGroup)findViewById(R.id.rbg_opciones_dos);
        //RBT radio
        rbtOpcionUno =(RadioButton)findViewById(R.id.rbt_repuesta_uno);
        rbtOpcionDos =(RadioButton)findViewById(R.id.rbt_repuesta_dos);
        rbtOpcionUnoDos =(RadioButton)findViewById(R.id.rbt_repuesta_uno_dos);
        rbtOpcionDosDos =(RadioButton)findViewById(R.id.rbt_repuesta_dos_dos);
        //CHK
        chkUno=(CheckBox)findViewById(R.id.chk_caja_uno);
        chkDos=(CheckBox)findViewById(R.id.chk_caja_dos);
        chkTres=(CheckBox)findViewById(R.id.chk_caja_tres);
        chkUnoDos=(CheckBox)findViewById(R.id.chk_caja_uno_dos);
        chkDosDos=(CheckBox)findViewById(R.id.chk_caja_dos_dos);
        chkTresDos=(CheckBox)findViewById(R.id.chk_caja_tres_dos);
        //Equipo
        txvParteUno=(TextView)findViewById(R.id.txv_parte_uno);
        txvParteTres=(TextView)findViewById(R.id.txv_parte_tres);
        edtParteDos=(EditText)findViewById(R.id.edt_parte_dos);

        //Tiempo
        txvTiempo=(TextView)findViewById(R.id.tiempo);
        txvTiempo.setText("00:01:00");

        timer = new CounterClass(90000, 1000);
        timer.start();

        txvTituloExam.setText(valorResividos.getString("nombreModulo"));
        switch (valorResividos.getInt("numeroExamne")){
            case 1:
                txvPreguntaUno.setText("1\nEn este directorio se almacenan las librerías de programación básicas\n" +
                        "de GNU/Linux.");
                rbtOpcionUno.setText("/lib");
                rbtOpcionDos.setText("/home");

                txvPreguntaDos.setText("2\ncomando que se encargará de copiar el archivo lilo.conf (ubicado\n" +
                        "en el directorio /etc) al directorio /tmp.");
                chkUno.setText("cp /etc/lilo.conf /tmp");
                chkDos.setText("cp home/etc/lilo.conf /tmp");
                chkTres.setText("cp bin/etc/lilo.conf /tmp");

                txvPreguntaTres.setText("3\nComandos para borrar archivos" );
                txvParteUno.setText("");
                //edtParteDos
                txvParteTres.setText("");

                txvPreguntaCuatro.setText("permite trabajar en modo de consola, es bastante útil para usuarios intermedios y avanzados");
                rbtOpcionUnoDos.setText("Terminal");
                rbtOpcionDosDos.setText("Gestor de archivadores");

                txvPreguntaCinco.setText("\nRealiza una copia de seguridad antes de copiar.");
                chkUnoDos.setText("-b");
                chkDosDos.setText("-a");
                chkTresDos.setText("-u");
                break;
            case 2: ///////-----------------------------------------------aqui me quede
                txvPreguntaUno.setText("1\nLa sección de Red permite examinar la red a la que esta conectado el\n" +
                        "ordenador o las disponibles para conectarlo.");
                rbtOpcionUno.setText("Cierto");
                rbtOpcionDos.setText("Falso");

                txvPreguntaDos.setText("2\nes la sección del disco duro que contiene la\n" +
                        " instalación del sistema operativo");
                chkUno.setText("La ventana del gestor de archivos");
                chkDos.setText("Sistema de archivos");
                chkTres.setText("sección de Red");

                txvPreguntaTres.setText("3\nComando para crear directorios ");
                txvParteUno.setText("");
                //edtParteDos


                txvPreguntaCuatro.setText("4\nEn el Lanzador, principalmente se presentan las memorias usb (pendrive) para su fácil acceso y retiro.");
                rbtOpcionUnoDos.setText("Verdadero");
                rbtOpcionDosDos.setText("Falso");

                txvPreguntaCinco.setText("5\npresenta en la parte superior izquierda la sección de Dispositivos.");
                chkUnoDos.setText("La ventana del gestor de archivos");
                chkDosDos.setText("Sistema de archivos");
                chkTresDos.setText("sección de Red");
                break;
            case 3:
                txvPreguntaUno.setText("1\nsi queremos crear un acceso directo al archivo /etc/passwd en el directorio\n" +
                        "/root");
                rbtOpcionUno.setText("ln /etc/passwd /root/mienlace");
                rbtOpcionDos.setText("cp /etc/passwd /root/mienlace");

                txvPreguntaDos.setText("2\nDefine el tipo de línea de comandos.");
                chkUno.setText("SHELL");
                chkDos.setText("PATH");
                chkTres.setText("PS1");

                txvPreguntaTres.setText("3\nComando para asignar propietarios a los archivos");
                txvParteUno.setText("");
                //edtParteDos
                txvParteTres.setText("");

                txvPreguntaCuatro.setText("4\nEl nombre del dispositivo en el que el servidor de ventanas X mostrará\n" +
                        "su salida.");
                rbtOpcionUnoDos.setText("HOME");
                rbtOpcionDosDos.setText("DISPLAY");

                txvPreguntaCinco.setText("5\nEl nombre utilizado en el login.");
                chkUnoDos.setText("LOGNAME");
                chkDosDos.setText("HOME");
                chkTresDos.setText("PATH");
                break;
            case 4:
                txvPreguntaUno.setText("1\nMuestra la memoria libre y utilizada.");
                rbtOpcionUno.setText("free");
                rbtOpcionDos.setText("halt");

                txvPreguntaDos.setText("2\nMuestra las últimas líneas de un archivo.");
                chkUno.setText("tac");
                chkDos.setText("tail");
                chkTres.setText("tar");

                txvPreguntaTres.setText("3\ncomando para cambiar de directorio");
                txvParteUno.setText("");
                //edtParteDos
                txvParteTres.setText("");

                txvPreguntaCuatro.setText("4\nMuestra información de los usuarios actualmente conectados.");
                rbtOpcionUnoDos.setText("who");
                rbtOpcionDosDos.setText("id");

                txvPreguntaCinco.setText("5\nMuestra las páginas del manual de un determinado comando.");
                chkUnoDos.setText("info");
                chkDosDos.setText("cal");
                chkTresDos.setText("man");
                break;
            case 5:
                ///////////////////////////////////////////////////////////////////////////////////
                txvTituloExam.setText("Examen final");
                txvPreguntaUno.setText("1\nCambiar la clave de acceso de un determinado usuario.");
                rbtOpcionUno.setText("passwd");
                rbtOpcionDos.setText("id");

                txvPreguntaDos.setText("2\nPermite ver y cambiar el día y la hora actuales.");
                chkUno.setText("ls");
                chkDos.setText("cal");
                chkTres.setText("date");

                txvPreguntaTres.setText("3\nDevuelve la cadena correspondiente al directorio actual.");
                txvParteUno.setText("");
                //edtParteDos
                txvParteTres.setText("");

                txvPreguntaCuatro.setText("4\nsi queremos crear un acceso directo al archivo /etc/passwd en el directorio\n");
                rbtOpcionUnoDos.setText("cp /etc/passwd /root/mienlace");
                rbtOpcionDosDos.setText("ln /etc/passwd /root/mienlace");

                txvPreguntaCinco.setText("3\nComando para encontrar un archivo");
                chkUnoDos.setText("info");
                chkDosDos.setText("find");
                chkTresDos.setText("man");
                break;
            default:
                Log.e("NO entro en el switch", "initComponents: ");
                break;
        }

        btnComprobar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int idModul=dbAdapter.idPrimerModuloIns(FormLoginActivity.ID_USU_LOGEADO, "Modulo 1");
        int califTraida;

        switch (valorResividos.getInt("numeroExamne")){
            case 1:
                if (rbtOpcionUno.isChecked()){
                    calificacion++;
                }
                if (chkUno.isChecked()){
                    calificacion++;
                }
                if (edtParteDos.getText().toString().equals("rm")){
                    calificacion++;
                }
                if (rbtOpcionUnoDos.isChecked()){
                    calificacion++;
                }
                if (chkUnoDos.isChecked()){
                    calificacion++;
                }
                dbAdapter.activarTema(idModul, 1, 8);
                califTraida=dbAdapter.traerCalificacion(1, FormLoginActivity.ID_USU_LOGEADO);
                //Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                califTraida=califTraida/2;
                Log.e("Calificacion traida", "onClick: " + califTraida + " " + calificacion);
                //El que inserta
                if (calificacion>califTraida) {
                    dbAdapter.setCalifModulo(FormLoginActivity.ID_USU_LOGEADO, 1, calificacion);
                }
                //el que trae la calif en toast
                califTraida=dbAdapter.traerCalificacion(1,FormLoginActivity.ID_USU_LOGEADO);
                Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                //--------------------------------------------
                Log.e("Calificacion incertada", "onClick: "+califTraida );
                timer.cancel();
                timer.onFinish();
                break;
            case 2:
                /////////////////////////////////////////
                if (rbtOpcionUno.isChecked()){
                    calificacion++;
                }
                if (chkDos.isChecked()){
                    calificacion++;
                }
                if (edtParteDos.getText().toString().equals("mkdir")){
                    calificacion++;
                }
                if (rbtOpcionUnoDos.isChecked()){
                    calificacion++;
                }
                if (chkUnoDos.isChecked()){
                    calificacion++;
                }
                dbAdapter.activarTema(idModul, 2, 6);
                califTraida=dbAdapter.traerCalificacion(2, FormLoginActivity.ID_USU_LOGEADO);
                //Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                califTraida=califTraida/2;
                Log.e("Calificacion traida", "onClick: " + califTraida + " " + calificacion);
                //El que inserta
                if (calificacion>califTraida) {
                    dbAdapter.setCalifModulo(FormLoginActivity.ID_USU_LOGEADO, 2, calificacion);
                }
                //el que trae la calif en toast
                califTraida=dbAdapter.traerCalificacion(2,FormLoginActivity.ID_USU_LOGEADO);
                Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                //--------------------------------------------
                Log.e("Calificacion incertada", "onClick: " + califTraida);
                timer.cancel();
                timer.onFinish();
                /////////////////////////////////////////
                break;
            case 3:
                if (rbtOpcionUno.isChecked()){
                    calificacion++;
                }
                if (chkTres.isChecked()){
                    calificacion++;
                }
                if (edtParteDos.getText().toString().equals("chown")){
                    calificacion++;
                }
                if (rbtOpcionDosDos.isChecked()){
                    calificacion++;
                }
                if (chkUnoDos.isChecked()){
                    calificacion++;
                }
                dbAdapter.activarTema(idModul, 3, 5);
                califTraida=dbAdapter.traerCalificacion(3, FormLoginActivity.ID_USU_LOGEADO);
                //Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                califTraida=califTraida/2;
                Log.e("Calificacion traida", "onClick: " + califTraida + " " + calificacion);
                //El que inserta
                if (calificacion>califTraida) {
                    dbAdapter.setCalifModulo(FormLoginActivity.ID_USU_LOGEADO, 3, calificacion);
                }
                //el que trae la calif en toast
                califTraida=dbAdapter.traerCalificacion(3,FormLoginActivity.ID_USU_LOGEADO);
                Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                //--------------------------------------------
                Log.e("Calificacion incertada", "onClick: " + califTraida);
                timer.cancel();
                timer.onFinish();
                /////////////////////////////////////////
                break;
            case 4:
                if (rbtOpcionUno.isChecked()){
                    calificacion++;
                }
                if (chkDos.isChecked()){
                    calificacion++;
                }
                if (edtParteDos.getText().toString().equals("cd")){
                    calificacion++;
                }
                if (rbtOpcionUnoDos.isChecked()){
                    calificacion++;
                }
                if (chkTresDos.isChecked()){
                    calificacion++;
                }
                dbAdapter.activarTema(idModul, 4, 7);
                califTraida=dbAdapter.traerCalificacion(4, FormLoginActivity.ID_USU_LOGEADO);
                //Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                califTraida=califTraida/2;
                Log.e("Calificacion traida", "onClick: " + califTraida + " " + calificacion);
                //El que inserta
                if (calificacion>califTraida) {
                    dbAdapter.setCalifModulo(FormLoginActivity.ID_USU_LOGEADO, 4, calificacion);
                }
                //el que trae la calif en toast
                califTraida=dbAdapter.traerCalificacion(4,FormLoginActivity.ID_USU_LOGEADO);
                Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                //--------------------------------------------
                Log.e("Calificacion incertada", "onClick: " + califTraida);
                timer.cancel();
                timer.onFinish();
                /////////////////////////////////////////
                break;
            case 5:
                if (rbtOpcionUno.isChecked()){
                    calificacion++;
                }
                if (chkTres.isChecked()){
                    calificacion++;
                }
                if (edtParteDos.getText().toString().equals("pwd")){
                    calificacion++;
                }
                if (rbtOpcionDosDos.isChecked()){
                    calificacion++;
                }
                if (chkDosDos.isChecked()){
                    calificacion++;
                }
                califTraida=dbAdapter.traerCalificacion(5, FormLoginActivity.ID_USU_LOGEADO);
                //Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                califTraida=califTraida/2;
                Log.e("Calificacion traida", "onClick: " + califTraida + " " + calificacion);
                //El que inserta
                if (calificacion>califTraida) {
                    dbAdapter.setCalifModulo(FormLoginActivity.ID_USU_LOGEADO, 5, calificacion);
                }
                //el que trae la calif en toast
                califTraida=dbAdapter.traerCalificacion(5,FormLoginActivity.ID_USU_LOGEADO);
                Toast.makeText(ExamenFinalActivity.this, "Su calif: "+califTraida, Toast.LENGTH_SHORT).show();
                //--------------------------------------------
                Log.e("Calificacion incertada", "onClick: " + califTraida);
                //startActivity(new Intent());
                timer.cancel();
                timer.onFinish();
                startActivity(new Intent(ExamenFinalActivity.this,EjemploEmailActivity.class));
                break;
            default:
                Log.e("NO entro", "onClick:  ");
                break;
        }
        finish();
    }

    public void tiempoTerminado(int tipo){
        bundle=new Bundle();
        bundle.putString("elegido", valorResividos.getString("nombreModulo"));
        bundle.putInt("posicion", valorResividos.getInt("modulo"));
        bundle.putBoolean("logeo", valorResividos.getBoolean("logeo"));
        switch (tipo){
            case 1:
                //pantalla con la calificacion(Splash)
                //dos diferentes tipos una que aprobo y otra que no
                break;
            case 2:
                Toast.makeText(ExamenFinalActivity.this, "Tiempo terminado", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(ExamenFinalActivity.this, SeleccionTemaActivity.class).putExtras(bundle));
                finish();
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onFinish() {
            if (calificacion>0){
                tiempoTerminado(1);
            }else {
                txvTiempo.setText("00:00:00");
                tiempoTerminado(2);
            }
            //--------------------------------------------------------------------------------------------------------------------
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            long millis = millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            System.out.println(hms);
            txvTiempo.setText(hms);

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        calificacion=0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
