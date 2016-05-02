package mx.edu.utng.aprendelinux;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Cronometro  extends Activity {
    LinearLayout layTiempos, layTableHeader;
    TextView tvCount, tvLaps;
    Button btnStart, btnLap, btnStop, btnResume;
    TareaCronometro tareaCronometro;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    /**
     * Método que se ejecuta al crear la Actividad (se
     * debe consultar ciclo de vida de Actividad en caso
     * de dudas sobre este tema)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        //Definimos la orientación de la pantalla para
        //que no se modifique. De otro modo debemos guardar
        //el estado de la aplicación y restaurarla cada
        //vez que se cambie la orientación.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        //Cargamos las referencias a los controles de la UI
        initialize();

        //Deshabilitamos los botones Stop y Lap hasta que se
        //pulse el botón Start.
        btnStop.setEnabled(false);
        btnLap.setEnabled(false);
        //Ocultamos los títulos de la tabla de tiempos por vuelta
        layTableHeader.setVisibility(View.INVISIBLE);

        //Creamos una nueva instancia de nuestra tarea AsyncTask
        tareaCronometro = new TareaCronometro();

        //Qué hacemos cuando se pulsa el botón Start
        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Button btn = (Button) v;

                if (btn.getText().toString().compareTo("Start") == 0) {
                    //Habilitamos los botones Stop y Lap
                    btnStop.setEnabled(true);
                    btnLap.setEnabled(true);

                    if (tareaCronometro.getStatus() != AsyncTask.Status.RUNNING) {
                        //Arrancamos nuestra tarea asíncrona
                        tareaCronometro.execute();
                    }
                    //Iniciamos el cronómetro
                    tareaCronometro.iniciar();

                } else {
                    if (btn.getText().toString().compareTo("Resume") == 0) {
                        //Continuamos el cronómetro cuando ha sido parado
                        tareaCronometro.continuar();
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Button btn = (Button) v;

                if (btn.getText().toString().compareTo("Stop") == 0) {
                    //Paramos el cronómetro (la tarea continua en
                    //ejecución).
                    tareaCronometro.parar();
                } else {
                    if (btn.getText().toString().compareTo("Restore") == 0) {
                        //Reiniciamos el cronómetro
                        tareaCronometro.reiniciar();
                        //Deshabilitamos los botones Stop y Lap
                        btnStop.setEnabled(false);
                        btnLap.setEnabled(false);
                        //Ocultamos cabecera tabla tiempos
                        layTableHeader.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        //Qué hacemos cuando se pulsa el botón Lap
        btnLap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Registramos el tiempo actual
                tareaCronometro.marcarTiempo();
                layTableHeader.setVisibility(View.VISIBLE);
            }
        });

    }

    //Inicializamos las referencias a los controles de la
    //interfaz de usuario de la Actividad.
    private void initialize() {
        tvCount = (TextView) findViewById(R.id.tvCount);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnLap = (Button) findViewById(R.id.btnLap);
        btnLap = (Button) findViewById(R.id.btnLap);
        layTiempos = (LinearLayout) findViewById(R.id.layTiempos);
        layTableHeader = (LinearLayout)findViewById(R.id.layTableHeader);

    }

    /**
     *
     * @param startTime
     * @param timeNow
     * @param timeStopped
     * @return String
     *
     * Este método nos devuelve en una cadena de texto con el
     * formato adecuado el tiempo transcurrido entre los
     * momentos indicados por los 2 primeros parámetros de
     * entrada. El tercer parámetro indica el tiempo a
     * descontar si el cronómetro estuvo parado durante el
     * intervalo definido por los dos parámetros anteriores.
     */
    private String obtenerTiempoTranscurrido(long startTime, long timeNow,
                                             long timeStopped) {
        long diferencia, stopTime;
        int hours, minutes, seconds, millis;
        String strMillis;

        diferencia = timeNow - startTime - timeStopped;
        hours = (int) diferencia / 3600000;
        diferencia = diferencia % 3600000;
        minutes = (int) diferencia / 60000;
        diferencia = diferencia % 60000;
        seconds = (int) diferencia / 1000;
        diferencia = diferencia % 1000;
        millis = (int) diferencia;
        try {
            strMillis = String.format("%03d", millis).substring(0, 2);
            millis = Integer.parseInt(strMillis);
        } catch (IndexOutOfBoundsException e) {
            Log.d("DEPURANDO", "IndexOutOfBoundsException catched");
        }

        String res = String.format("%d:%02d:%02d.%02d", hours, minutes,
                seconds, millis);
        return res;
    }

    /**
     *
     * @author ebacelo
     *
     * Clase interna derivada de AsyncTask que nos permite realizar una
     * tarea en un hilo secundario.
     */
    class TareaCronometro extends AsyncTask<Void, String, Void> {
        private boolean salir = false;
        private boolean parado = true;
        private boolean reiniciado = false;
        private boolean lap = false;
        private boolean iniciar = false;
        private long timeStart = 0;
        private long resumeTime = 0;
        private long stopTime = 0;
        private long timeStopped = 0;
        private long timeNow = 0;
        private long timeLastLap = 0;
        private long timeLap = 0;
        private int lap_num = 1;

        public void iniciar() {
            // e0
            if (parado != false) {
                parado = false;
                timeStart = System.currentTimeMillis();
                timeLastLap = timeStart;
                timeStopped = 0;
            }
        }

        public void parar() {
            parado = true;
            stopTime = System.currentTimeMillis();
            // Ocultamos el botón Lap
            // Cambiamos el texto de Stop a Restore, y el de Start a Resume
            btnLap.setVisibility(View.INVISIBLE);
            btnStart.setText("Resume");
            btnStop.setText("Restore");
        }

        public void continuar() {
            btnStart.setText("Start");
            btnStop.setText("Stop");
            btnLap.setVisibility(View.VISIBLE);
            parado = false;
            resumeTime = System.currentTimeMillis();
            timeStopped += resumeTime - stopTime;
        }

        public void reiniciar() {
            btnStart.setText("Start");
            btnStop.setText("Stop");
            btnLap.setVisibility(View.VISIBLE);
            ////
            stopTime = System.currentTimeMillis();
            timeStopped = 0;
            parado = true;
            iniciar = true;
            reiniciado = true;

            //Limpiamos los tiempos de vuelta
            layTiempos.removeAllViews();
            lap_num = 1;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            salir = true;
            finish();
        }

        //Tareas a realizar después de comenzar la tarea en el
        //hilo secundario.
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        //Tareas a realizar antes de comenzar la tarea en el
        //hilo secundario.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            parado = true;
            iniciar = false;
        }

        //Este método se ejecutará en el hilo de la interfaz de usuario
        //cada vez que desde el método doInBackground() se invoque el
        //método publishProgress(). De este modo podemos transmitir
        //información entre los dos hilos de ejecución de forma
        //sencilla.
        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);
            // Si no esta parado actualizamos el tiempo en la pantalla
            // (obtenerTiempoTranscurrido)

            //Mostramos el tiempo transcurrido en la pantalla del
            //cronómetro.
            tvCount.setText(values[0]);

            if (lap) {
                // Añadimos en la lista de vueltas el tiempo que va a ser
                // publicado
                TextView tvTiempoVuelta = new TextView(layTiempos.getContext());
                tvTiempoVuelta.setBackgroundColor(Color.argb(45, 55, 120, 55));

                if (lap_num % 2 == 0)
                    tvTiempoVuelta.setTextColor(Color.argb(100, 33, 230, 230));
                else
                    tvTiempoVuelta.setTextColor(Color.argb(100, 230, 230, 33));

                tvTiempoVuelta.setTextSize(20);
                tvTiempoVuelta.setPadding(0, 1, 0, 1);

                //Calcular el tiempo de la vuelta con los datos recibidos
                timeNow = Long.parseLong(values[1]);
                timeLap = timeNow - timeLastLap;
                tvTiempoVuelta.setText(fijarLongCadena(String.valueOf(lap_num),2) + " - " +
                        values[0] + "   ---   " + String.valueOf(obtenerTiempoTranscurrido(0, timeLap, 0)));

                //Añadimos el nuevo tiempo de vuelta al layout del interfaz
                //de usuario para visualizarlo.
                layTiempos.addView(tvTiempoVuelta, 0);
                timeLastLap = timeNow;
                lap_num++;
                lap = false;
            }
        }


        //El contenido de este método son las tareas que se
        //realizarán en el hilo secundario (sólo estas, todos
        //los demás métodos se ejecutan en el hilo de la
        //interfaz de usuario).
        @Override
        protected Void doInBackground(Void... arg0) {

            while (!salir) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!parado) {
                    timeNow = System.currentTimeMillis();
                    String values[] = new String[2];
                    if (reiniciado) {
                        timeStart = timeNow;
                        reiniciado = false;
                    }
                    values[0] = obtenerTiempoTranscurrido(timeStart,
                            timeNow, timeStopped);
                    values[1] = String.valueOf(timeNow);

                    publishProgress(values);
                }

                if (iniciar) {
                    timeStopped = 0;
                    timeLastLap = 0;
                    timeNow = timeStart;
                    String values[] = new String[2];
                    values[0] = obtenerTiempoTranscurrido(timeStart,
                            timeNow, timeStopped);
                    values[1] = String.valueOf(timeNow);
                    publishProgress(values);
                    iniciar = false;
                    parado = true;
                }

            }
            return null;
        }

        //Permite indicar al hilo secundario que hay que marcar un tiempo
        public void marcarTiempo() {
            lap = true;

            return;
        }

        public String fijarLongCadena(String cadena, int longitud) {
            String resultado = "";
            String relleno = "";

            if (cadena.length() < longitud) {
                for (int i=0; i< longitud - cadena.length(); i++) {
                    relleno = relleno.concat("  ");
                }
                resultado = relleno.concat(cadena);
                Log.d("DEPURANDO", "Cadena1: |" + resultado + "|");
                return resultado;
            }

            Log.d("DEPURANDO", "Cadena2: |" + cadena + "|");
            return cadena;
        }
    }

}
