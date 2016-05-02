package mx.edu.utng.aprendelinux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import mx.edu.utng.aprendelinux.noticia.Noticia;


public class NoticiaActivity extends Activity {


    ////////////////////////////////////////////////////////////////////////////////////////
    //									CAMPOS 											  //
    ////////////////////////////////////////////////////////////////////////////////////////


    private TextView txtTitulo;				// Campo gráfico que muestra el títutlo
    private TextView txtDescripcion;		// Campo gráfico que muestra la descripción
    private TextView txtFecha;				// Campo gráfico que muestra la fecha
    private TextView txtLink;				// Campo gráfico que muestra el link

    ////////////////////////////////////////////////////////////////////////////////////////
    //									CAMPOS 											  //
    ////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////
    //									MÉTODOS 										  //
    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Al iniciar la actividad
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        // INICIALIZACIÓN CAMPOS ///////////////////////////////////////////////////////

        // Inicialización de campos gráficos
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        txtFecha = (TextView) findViewById(R.id.txtFecha);
        txtLink = (TextView) findViewById(R.id.txtLink);

        // FIN INICIALIZACIÓN CAMPOS ///////////////////////////////////////////////////

        // Obtiene la noticia y la muestra
        obtenerNoticia();
    }

    /**
     * Obtiene la noticia y la muestra
     */
    public void obtenerNoticia(){

        // Recibe el intent con la noticia
        Intent intent = getIntent();
        Noticia noticia = (Noticia) intent.getSerializableExtra("NOTICIA");

        // Muestra el contenido de la noticia
        txtTitulo.setText(noticia.getTitutlo());
        txtDescripcion.setText(noticia.getDescripcion());
        txtFecha.setText(noticia.getFechaPublicacion());
        txtLink.setText(noticia.getUrl());
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //									FIN MÉTODOS 									  //
    ////////////////////////////////////////////////////////////////////////////////////////
}

