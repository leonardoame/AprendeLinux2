package mx.edu.utng.aprendelinux;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TabHost;
import android.widget.TextView;


public class SeleccionTabActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txvTema;
    private TextView txvTemaContenido;
    private String contDelTem="";
    private MediaController mediaController;
    private Uri uri;
    private Bundle valoresRecibidosSec;
    private Bundle bundle;
    private Button btnTabInfoEvaluacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_tab_tema_layout);
        initComponents();
    }
    private void initComponents(){
        final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        //Para el tab de informacion
        TabHost.TabSpec spec=tabHost.newTabSpec(getString(R.string.tab_informacon));
        spec.setContent(R.id.tab_informacion);
        spec.setIndicator(getString(R.string.tab_informacon), getResources().getDrawable(R.drawable.ic_info));
        tabHost.addTab(spec);
        //Para el tab de Video
        spec=tabHost.newTabSpec(getString(R.string.tab_video));
        spec.setContent(R.id.tab_video);
        spec.setIndicator(getString(R.string.tab_video), getResources().getDrawable(R.drawable.ic_video));
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        txvTema= (TextView) findViewById(R.id.txv_tema);
        txvTemaContenido= (TextView) findViewById(R.id.txv_tema_contenido);
        btnTabInfoEvaluacion=(Button)findViewById(R.id.btn_tab_info_evaluacion);

        btnTabInfoEvaluacion.setOnClickListener(this);

        valoresRecibidosSec = getIntent().getExtras();
        //txvTema.setText(valoresRecibidosSec.getString("temaElegido"));
        bundle=new Bundle();

        //Se almacenan los datos utilizables a un bundel
        bundle.putInt("moduloS",valoresRecibidosSec.getInt("modulo"));
        bundle.putInt("posicionTemaS",valoresRecibidosSec.getInt("posicionTema"));
        bundle.putString("temaElegidoS",valoresRecibidosSec.getString("temaElegido"));

        //Se Vincula el video
        final Button vdvVideo  = (Button) findViewById(R.id.vdv_video);

        //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxx
        //Se inicializan los controles para el dieo
        mediaController = new MediaController(this);
        mediaController.setAnchorView(vdvVideo);

        switch (valoresRecibidosSec.getInt("modulo")){
            case 0:
                switch (valoresRecibidosSec.getInt("posicionTema")){
                    case 0://Sistema operativo
                        contDelTem=getResources().getString(R.string.html_uno_sobre_ruby);
                        uri = Uri.parse("https://www.youtube.com/watch?v=aHjh4a8d8Ow");
                        break;
                    case 1://Entorno grafico
                        contDelTem=getResources().getString(R.string.html_uno_medio_ambiente_configuracion);
                        uri = Uri.parse("https://www.youtube.com/watch?v=8VHlEcLBwpA");
                        break;
                    case 2://pruebas de sistema
                        contDelTem=getResources().getString(R.string.html_uno_sintaxis);
                        uri = Uri.parse("https://www.youtube.com/watch?v=lZ99Oa8NWNw");
                        break;
                    case 3://aplicaciones de serie
                        contDelTem=getResources().getString(R.string.html_uno_palabras_reservadas);
                        uri = Uri.parse("https://www.youtube.com/watch?v=6xYtURnQfjU");
                        break;
                    case 4://sistema de archivos
                        contDelTem=getResources().getString(R.string.html_uno_variables);
                        uri = Uri.parse("https://www.youtube.com/watch?v=6diWrSfTHkg");
                        break;
                    case 5://copiando archivos
                        contDelTem=getResources().getString(R.string.html_uno_operadores);
                        uri = Uri.parse("https://www.youtube.com/watch?v=4kwCcs5d-KQ");
                        break;
                    case 6://borrando archivos
                        contDelTem=getResources().getString(R.string.html_uno_comentarios);
                        uri = Uri.parse("https://www.youtube.com/watch?v=NsorfOS5Q94");
                        break;
                    default:

                        break;
                }
                break;
            case 1:

                switch (valoresRecibidosSec.getInt("posicionTema")){
                    case 0://carpetas de trabajo
                        contDelTem=getResources().getString(R.string.html_dos_ciclos);
                        uri = Uri.parse("https://www.youtube.com/watch?v=HIB3ekSNCNE");
                        break;
                    case 1://manipulacion de archivos
                        contDelTem=getResources().getString(R.string.html_dos_metodos);
                        uri = Uri.parse("https://www.youtube.com/watch?v=Txfpqv-etec");
                        break;
                    case 2://ordenar mis archivos
                        contDelTem=getResources().getString(R.string.html_dos_modulos);
                        uri = Uri.parse("https://www.youtube.com/watch?v=T_fGF6ZE9Es");
                        break;
                    case 3://conexin de dispositivos
                        contDelTem=getResources().getString(R.string.html_dos_bloques);
                        uri = Uri.parse("https://www.youtube.com/watch?v=s7cnWzGFWSY");
                        break;
                    case 4://creacion de directoris
                        contDelTem=getResources().getString(R.string.html_dos_mix);
                        uri = Uri.parse("https://www.youtube.com/watch?v=XfKWdkrj0m0");
                        break;
                    default:
                        break;
                }
                break;
            case 2:

                switch (valoresRecibidosSec.getInt("posicionTema")){
                    case 0://perisis
                        contDelTem=getResources().getString(R.string.html_tres_strings);
                        uri = Uri.parse("https://www.youtube.com/watch?v=XfKWdkrj0m0");
                        break;
                    case 1://due;os
                        contDelTem=getResources().getString(R.string.html_tres_arreglos);
                        uri = Uri.parse("https://www.youtube.com/watch?v=eOYkiP4dNio");
                        break;
                    case 2://enlaces
                        contDelTem=getResources().getString(R.string.html_tres_hashes);
                        uri = Uri.parse("https://www.youtube.com/watch?v=uNvTJoQOjkU");
                        break;
                    case 3://variables de entorno
                        contDelTem=getResources().getString(R.string.html_tres_fecha_hora);
                        uri = Uri.parse("https://www.youtube.com/watch?v=VSmfKeTkL48");
                        break;
                    default:

                        break;
                }
                break;
            case 3:

                switch (valoresRecibidosSec.getInt("posicionTema")){
                    case 0://manejo de archivos
                        contDelTem=getResources().getString(R.string.html_cuatro_rangos);
                        uri = Uri.parse("https://www.youtube.com/watch?v=NsorfOS5Q94");
                        break;
                    case 1://manejo de prcesos
                        contDelTem=getResources().getString(R.string.html_cuatro_iteradores);
                        uri = Uri.parse("https://www.youtube.com/watch?v=gtVPhXJ7Bhc");
                        break;
                    case 2://manejo de usuarios
                        contDelTem=getResources().getString(R.string.html_cuatro_directorios);
                        uri = Uri.parse("https://www.youtube.com/watch?v=bP6zMG2sshk");
                        break;
                    case 3://otreos comandos
                        contDelTem=getResources().getString(R.string.html_cuatro_excepciones);
                        uri = Uri.parse("https://www.youtube.com/watch?v=KLP4TtilIV0");
                        break;
                    case 4://creacion de un nuevo usuario
                        contDelTem=getResources().getString(R.string.html_cuatro_orientado_objetos);
                        uri = Uri.parse("https://www.youtube.com/watch?v=utZmcraNT1k");
                        break;
                    case 5://eliminacion de un uusario
                        contDelTem=getResources().getString(R.string.html_cuatro_expreciones_regulares);
                        uri = Uri.parse("https://www.youtube.com/watch?v=la9sZxqSdlE");
                        break;
                    default:

                        break;
                }
                break;
            default:

                break;
        }

        vdvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        txvTema.setText(valoresRecibidosSec.getString("nombreModulo")+": "+valoresRecibidosSec.getString("temaElegido"));
        CharSequence textoInterpretado = Html.fromHtml(contDelTem);
        txvTemaContenido.setText(textoInterpretado);

        //menda el mensaje de que pestaña fue oprimid
    }

    @Override
    public void onClick(View v) {

        byte posicionTmea= (byte) (valoresRecibidosSec.getInt("posicionTema")+1);//Se le aumenta uno ya que la posicion empiesa desde 0.
        byte pares= (byte) (posicionTmea%2);
        bundle.putBoolean("logeo",valoresRecibidosSec.getBoolean("logeo"));
        if (posicionTmea==3|posicionTmea==6){//EditText
            //3,6 ---> se le va arestar uno cunado se pase en el suich de los cuises
            startActivity(new Intent(this, CuestionarioEditTextActivity.class).putExtras(bundle));

        }else if (pares==0){//RadioButton

            startActivity(new Intent(this, CuestionarioRadioButtonActivity.class).putExtras(bundle));
            //2,4  ---> se le va arestar uno cunado se pase en el suich de los cuises

        }else {//Checkbox

            startActivity(new Intent(this, CuestionarioCheckBoxActivity.class).putExtras(bundle));
            //1,5,7 ---> se le va arestar uno cunado se pase en el suich de los cuises
        }
        finish();
    }
}