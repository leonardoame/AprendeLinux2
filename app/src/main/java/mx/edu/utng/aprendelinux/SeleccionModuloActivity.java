package mx.edu.utng.aprendelinux;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class SeleccionModuloActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lsvMenuPrincipal;
    private String[] itemMenuPrincipal;
    private Bundle bundle;
    private Intent intent;
    private Bundle valoresRecibidosLogin;
    ////ProgresBar Inicio
    private ProgressDialog _progressDialog;
    private int _progress = 0;
    private Handler _progressHandler;
    ////ProgresBar Fin
    private MediaPlayer mp;

    //---Solo xmientras

    private AlertDialog.Builder builder;
    private String mensajeError="";
    private DBAdapter dbAdapter;

    //----

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ProgresBar Inicio
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        //ProgresBar fin
        setContentView(R.layout.lista_modulos_layout);
        initComponents();
    }

    private void initComponents(){
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        LinearLayout  linerDelBoton=(LinearLayout) findViewById(R.id.liner_del_boton);

        if (dbAdapter.terminoApp(FormLoginActivity.ID_USU_LOGEADO)){
            Button btnCorreo=new Button(this);
            Button btnGrafica=new Button(this);
            btnCorreo.setText("Envio del progreso");
            btnGrafica.setText("Mostrar grafica");
            btnGrafica.setBackgroundResource(R.drawable.mi_boton);
            btnCorreo.setBackgroundResource(R.drawable.mi_boton);

            linerDelBoton.addView(btnGrafica);
            linerDelBoton.addView(btnCorreo);
            btnCorreo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SeleccionModuloActivity.this, EjemploEmailActivity.class));
                }
            });
            btnGrafica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SeleccionModuloActivity.this,GraficaMenuActiviry.class));
                }
            });
        }
        lsvMenuPrincipal= (ListView) findViewById(R.id.lsv_menu_principal);
        itemMenuPrincipal=getResources().getStringArray(R.array.item_menu_principal);
        ArrayAdapter adapter= new ArrayAdapter(
                getApplication(),R.layout.item_list_layout,R.id.txv_item,itemMenuPrincipal);
        lsvMenuPrincipal.setAdapter(adapter);
        mp=MediaPlayer.create(this,R.raw.cli_d);
        valoresRecibidosLogin = getIntent().getExtras();//////////
        lsvMenuPrincipal.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//falta colocar en caso de que de clic en examen final
        //------Solo xmientras
        mensajeError="";
        String titulo=getResources().getString(R.string.title_menssage_error_dos);
        builder = new AlertDialog.Builder(this);

        //-----Enviados por el Bundel
        bundle= new Bundle();
        bundle.putString("elegido", itemMenuPrincipal[position]);//Se pasa el nombre del modulo.
        bundle.putInt("posicion", position);//Se pasa la posición del modulo.
        bundle.putBoolean("logeo", valoresRecibidosLogin.getBoolean("logeo"));//Se pasa el estado del logeo.
        //---------

        if (valoresRecibidosLogin.getBoolean("logeo")){
            int pasarSiguiente=0;
            int pasarErro=0;
            int idUsuario=Integer.valueOf(FormLoginActivity.ID_USU_LOGEADO);
            boolean activo;
            switch (position){
                case 0:
                    activo=dbAdapter.moduloActivo(1,idUsuario);
                    //Log.e("Mod 1 Activo", "onItemClick: " + activo);
                    if (activo){pasarSiguiente++;}
                    else {pasarErro++;}
                    break;
                case 1:
                    activo=dbAdapter.moduloActivo(2,idUsuario);
                    //Log.e("Mod 1 Activo", "onItemClick: " + activo);
                    if (activo){pasarSiguiente++;}
                    else {pasarErro++;}
                    break;
                case 2:
                    activo=dbAdapter.moduloActivo(3, idUsuario);
                    //Log.e("Mod 1 Activo", "onItemClick: " + activo);
                    if (activo){pasarSiguiente++;}
                    else {pasarErro++;}
                    break;
                case 3:
                    activo=dbAdapter.moduloActivo(4,idUsuario);
                    //Log.e("Mod 1 Activo", "onItemClick: " + activo);
                    if (activo){pasarSiguiente++;}
                    else {pasarErro++;}
                    break;
                case 4:///--------------------------------------------------------------------------Examen Fianl 1
                    activo=dbAdapter.moduloActivo(5,idUsuario);
                    //Log.e("Mod 1 Activo", "onItemClick: " + activo);
                    if (activo) {
                        bundle.putInt("numeroExamne", 5);
                        intent = new Intent(SeleccionModuloActivity.this, ExamenFinalActivity.class).putExtras(bundle);
                        startActivity(intent);
                    }else {
                        MensajeError("Para poder accesar a este examen necesita concluir con exito todos los modulos", "Examen Bloquado");
                    }
                    break;
            }
            if (pasarSiguiente!=0){
                intent = new Intent(SeleccionModuloActivity.this,SeleccionTemaActivity.class).putExtras(bundle);
                startActivity(intent);
                pasarSiguiente=0;
            }
            if (pasarErro!=0){
                MensajeError("Para poder accesar a este modulo necesita concluir con exito el anterios", "Modulo Bloquado");
                pasarErro=0;
            }
        }else {//Por si no esta logeado solo le dara acceso al primer capitulo
            if (position==0){
                intent = new Intent(SeleccionModuloActivity.this,SeleccionTemaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }else if (position==4){
                MensajeError("Para poder accesar a este examen necesita registrarse en la aplicación", "Examen Bloquado");
            }else {
                MensajeError("Para poder accesar a este modulo necesita registrarse en la aplicación", "Modulo Bloquado");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (valoresRecibidosLogin.getBoolean("logeo")){
            inflater.inflate(R.menu.menu_with_logeo, menu);
        }else {
            inflater.inflate(R.menu.menu_without_logeo, menu);
        }
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (valoresRecibidosLogin.getBoolean("logeo")){
            switch (item.getItemId()) {
                case R.id.itm_actionbar_menu_progreso_on:
                    ///////ProgresBar Inicio
                    int puntos=dbAdapter.getProgresoModulos(FormLoginActivity.ID_USU_LOGEADO);
                    int  avances=getCalificacionParceada(puntos);//
                    Log.e("Avances ", "onOptionsItemSelected: "+avances );

                    showDialog(1);
                    _progress = 0;
                    _progressDialog.setProgress(avances);
                    //_progressHandler.sendEmptyMessage(0);
                    _progressHandler = new Handler() {
                        public void handleMessage(Message msg)
                        {
                            super.handleMessage(msg);
                            if (_progress >= 100) {
                                _progressDialog.dismiss();
                            } else {
                                _progress++;
                                //_progressDialog.incrementProgressBy(1);
                                _progressHandler.sendEmptyMessageDelayed(50, 100);
                            }
                        }
                    };
                    ///////ProgresBar fin
                    break;
                case R.id.itm_actionbar_menu_usuario_on:
                    //Usuario
                    startActivity(new Intent(SeleccionModuloActivity.this,UsuarioInformacionMenuActivity.class));
                    break;
                case R.id.itm_actionbar_menu_configuraciones_on:
                    //Configuración
                    startActivity(new Intent(SeleccionModuloActivity.this,ConfiguracionActivity.class));
                    break;
                case R.id.itm_actionbar_menu_calificar_app:
                    //Calificar
                    startActivity(new Intent(SeleccionModuloActivity.this,CalificarAppMenuActivity.class));
                    break;
                case R.id.itm_actionbar_menu_acerca_de_on:
                    //Aserca de
                    startActivity(new Intent(SeleccionModuloActivity.this,AcercaDeMenuActivity.class));
                    break;
                case R.id.itm_actionbar_menu_ayu_suge_on:
                    //Ayuda y sugerencias
                    startActivity(new Intent(SeleccionModuloActivity.this,AyudaSugerenciasMenuActivity.class));
                    break;
                case R.id.itm_actionbar_herramientas:
                    startActivity(new Intent(SeleccionModuloActivity.this,HerramientasActivity.class));
                    break;
                case R.id.itm_actionbar_video:
                    startActivity(new Intent(SeleccionModuloActivity.this,VideoActivity.class));
                    break;
                case R.id.itm_actionbar_juegos:
                    startActivity(new Intent(SeleccionModuloActivity.this,MenusJuegosActivity.class));
                    break;
                case R.id.itm_actionbar_menu_salir_on:
                    //Salir
                    itmSalir();
                    break;
                case R.id.itm_actionbar_menu_juego:
                    //Ayuda y sugerencias
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.utng.edu.mx")));
                    //startActivity(new Intent(SeleccionModuloActivity.this, ActivityJuego.class));
                    break;
                case R.id.itm_actionbar_noticias:
                    startActivity(new Intent(SeleccionModuloActivity.this,UltimasNoticiasActivity.class));
            }
        }else {
            switch (item.getItemId()) {
                case  R.id.itm_actionbar_menu_acerca_de_off:
                    //acerca de
                    startActivity(new Intent(SeleccionModuloActivity.this,AcercaDeMenuActivity.class));
                    break;
                case R.id.itm_actionbar_video:
                    startActivity(new Intent(SeleccionModuloActivity.this,VideoActivity.class));
                    break;
                case R.id.itm_actionbar_juegos:
                    startActivity(new Intent(SeleccionModuloActivity.this,MenusJuegosActivity.class));
                    break;
                case R.id.itm_actionbar_menu_ayu_suge_off:
                    //ayuda y sugerencias
                    startActivity(new Intent(SeleccionModuloActivity.this,AyudaSugerenciasMenuActivity.class));
                    break;
                case R.id.itm_actionbar_herramientas:
                    startActivity(new Intent(SeleccionModuloActivity.this,HerramientasActivity.class));
                    break;
                case R.id.itm_actionbar_noticias:
                    startActivity(new Intent(SeleccionModuloActivity.this,UltimasNoticiasActivity.class));
                    break;

                case R.id.itm_actionbar_menu_salir_off:
                    //Salir
                    itmSalir();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void itmSalir(){
        finish();
        Intent intent1=new Intent(Intent.ACTION_MAIN);
        intent1.addCategory(Intent.CATEGORY_HOME);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

    ///////ProgresBar Inicio
    @Override
    protected Dialog onCreateDialog(int i) {
        _progressDialog = new ProgressDialog(this);
        _progressDialog.setTitle(getResources().getString(R.string.tiulo_progreso_menu_bar));
        _progressDialog.setIcon(R.mipmap.ic_launcher);
        _progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        _progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.mensaje_ocultar), new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mp.start();
                    }
                });
        return _progressDialog;
    }

    ///////ProgresBar Inicio

    //------------

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

    //------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
    protected static int getCalificacionParceada(int puntos){
        switch (puntos){
            case 0:
                puntos=0;
                return puntos;
            case 1:
                puntos=3;
                return puntos;
            case 2:
                puntos=6;
                return puntos;
            case 3:
                puntos=9;
                return puntos;
            case 4:
                puntos=12;
                return puntos;
            case 5:
                puntos=15;
                return puntos;
            case 6:
                puntos=18;
                return puntos;
            case 7:
                puntos=21;
                return puntos;
            case 8:
                puntos=24;
                return puntos;
            case 9:
                puntos=27;
                return puntos;
            case 10:
                puntos=30;
                return puntos;
            case 11:
                puntos=33;
                return puntos;
            case 12:
                puntos=36;
                return puntos;
            case 13:
                puntos=39;
                return puntos;
            case 14:
                puntos=42;
                return puntos;
            case 15:
                puntos=45;
                return puntos;
            case 16:
                puntos=48;
                return puntos;
            case 17:
                puntos=51;
                return puntos;
            case 18:
                puntos=54;
                return puntos;
            case 19:
                puntos=57;
                return puntos;
            case 20:
                puntos=60;
                return puntos;
            case 21:
                puntos=63;
                return puntos;
            case 22:
                puntos=66;
                return puntos;
            case 23:
                puntos=69;
                return puntos;
            case 24:
                puntos=72;
                return puntos;
            case 25:
                puntos=75;
                return puntos;
            case 26:
                puntos=78;
                return puntos;
            case 27:
                puntos=81;
                return puntos;
            case 28:
                puntos=84;
                return puntos;
            case 29:
                puntos=90;
                return puntos;
            case 30:
                puntos=100;
                return puntos;
            case 31:
                puntos=101;
                return puntos;

        }
        return puntos;
    }
}
