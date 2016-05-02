package mx.edu.utng.aprendelinux;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class SeleccionTemaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TextView txvNombreModulo;
    private ListView lsvMenuSecundario;
    private String[] itemMenuSecundario;
    private ArrayAdapter<String> arrayAdapter;
    private Bundle valoresRecibidos;
    private Bundle bundle;
    private Intent intent;
    ////ProgresBar Inicio
    private ProgressDialog _progressDialog;
    private int _progress = 0;
    private Handler _progressHandler;
    ////ProgresBar Fin

    //---Solo xmientras

    private AlertDialog.Builder builder;
    private String mensajeError="";

    //----
    private DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ProgresBar Inicio
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        //ProgresBar fin
        setContentView(R.layout.selection_modulo_layout);
        initComponents();
    }

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        txvNombreModulo = (TextView) findViewById(R.id.txv_modulo);
        lsvMenuSecundario = (ListView) findViewById(R.id.lsv_menu_secundario);
        valoresRecibidos = getIntent().getExtras();

        //llenado de datos
        txvNombreModulo.setText(valoresRecibidos.getString("elegido"));//Se le coloca el nombre del modulo.

        switch (valoresRecibidos.getInt("posicion")) {//switch para colocar la lista de los temas del modulo.
            case 0:
                itemMenuSecundario = getResources().getStringArray(R.array.item_menu_secundario_modulo1);
                break;
            case 1:
                itemMenuSecundario = getResources().getStringArray(R.array.item_menu_secundario_modulo2);
                break;
            case 2:
                itemMenuSecundario = getResources().getStringArray(R.array.item_menu_secundario_modulo3);
                break;
            case 3:
                itemMenuSecundario = getResources().getStringArray(R.array.item_menu_secundario_modulo4);
                break;
            case 4:
                itemMenuSecundario = getResources().getStringArray(R.array.item_menu_secundario_examen_final);
                break;
            default:
                break;

        }
        arrayAdapter = new ArrayAdapter(
                getApplication(),R.layout.item_list_layout,R.id.txv_item, itemMenuSecundario);

        lsvMenuSecundario.setAdapter(arrayAdapter);
        lsvMenuSecundario.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //------Solo xmientras
        mensajeError="";
        String titulo=getResources().getString(R.string.title_menssage_error_dos);
        builder = new AlertDialog.Builder(this);
        //------Datos enviados
        bundle = new Bundle();
        bundle.putString("nombreModulo", valoresRecibidos.getString("elegido"));//nombre del modulo
        bundle.putInt("modulo", valoresRecibidos.getInt("posicion"));//Se pasa la posicon del modulo.
        bundle.putString("temaElegido", itemMenuSecundario[position]);//Se pasa el nombre del tema elegido
        bundle.putInt("posicionTema", position);//Se pasa la posicopn del tema
        bundle.putBoolean("logeo",valoresRecibidos.getBoolean("logeo"));
        //------
        if (valoresRecibidos.getBoolean("logeo")) {
            int idModul=dbAdapter.idPrimerModuloIns(FormLoginActivity.ID_USU_LOGEADO,"Modulo 1");
            boolean activo;
            int pasarSiguiente=0;
            int pasarErr=0;

            switch (valoresRecibidos.getInt("posicion")) {//switch para colocar la lista de los temas del modulo.
                case 0://Modulo 1
                    switch (position){
                        case 0://El subtema del tema uno
                            activo=dbAdapter.temaActivo(1,1,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 1:
                            activo=dbAdapter.temaActivo(1,2,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 2:
                            activo=dbAdapter.temaActivo(1,3,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 3:
                            activo=dbAdapter.temaActivo(1,4,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 4:
                            activo=dbAdapter.temaActivo(1,5,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 5:
                            activo=dbAdapter.temaActivo(1,6,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 6:
                            activo=dbAdapter.temaActivo(1,7,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 7://-------------------------------------------------------------------Exam
                            activo=dbAdapter.temaActivo(1,8,idModul);
                            Log.e("Examen modulo 1", "onItemClick: "+activo );
                            if (activo) {
                                bundle.putInt("numeroExamne", 1);
                                startActivity(new Intent(SeleccionTemaActivity.this, ExamenFinalActivity.class).putExtras(bundle));
                            }else {
                                MensajeError("Para poder accesar a este examen necesita concluir con exito los temas anteriores", "Examen Bloquado");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 1://Modulo 2
                    switch (position){
                        case 0:
                            activo=dbAdapter.temaActivo(2,1,idModul);
                            //Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 1:
                            activo=dbAdapter.temaActivo(2,2,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 2:
                            activo=dbAdapter.temaActivo(2,3,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 3:
                            activo=dbAdapter.temaActivo(2,4,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 4:
                            activo=dbAdapter.temaActivo(2,5,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 5:
                            activo=dbAdapter.temaActivo(2,6,idModul);
                            Log.e("Examen modulo 2", "onItemClick: " + activo);
                            if (activo) {
                                bundle.putInt("numeroExamne", 2);
                                startActivity(new Intent(SeleccionTemaActivity.this, ExamenFinalActivity.class).putExtras(bundle));
                            }else {
                                MensajeError("Para poder accesar a este examen necesita concluir con exito los temas anteriores", "Examen Bloquado");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 2://Modulo 3
                    switch (position){
                        case 0:
                            activo=dbAdapter.temaActivo(3,1,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 1:
                            activo=dbAdapter.temaActivo(3,2,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 2:
                            activo=dbAdapter.temaActivo(3,3,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 3:
                            activo=dbAdapter.temaActivo(3,4,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 4:
                            activo=dbAdapter.temaActivo(3,5,idModul);
                            Log.e("Examen modulo 3", "onItemClick: "+activo );
                            if (activo) {
                                bundle.putInt("numeroExamne", 3);
                                startActivity(new Intent(SeleccionTemaActivity.this, ExamenFinalActivity.class).putExtras(bundle));
                            }else {
                                MensajeError("Para poder accesar a este examen necesita concluir con exito los temas anteriores", "Examen Bloquado");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 3: //Modulo 4
                    switch (position){
                        case 0:
                            activo=dbAdapter.temaActivo(4,1,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 1:
                            activo=dbAdapter.temaActivo(4,2,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 2:
                            activo=dbAdapter.temaActivo(4,3,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 3:
                            activo=dbAdapter.temaActivo(4,4,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 4:
                            activo=dbAdapter.temaActivo(4,5,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 5:
                            activo=dbAdapter.temaActivo(4,6,idModul);
                            Log.e("Temas activos", "onItemClick: "+activo );
                            if (activo){pasarSiguiente++;}
                            else{pasarErr++;}
                            break;
                        case 6:
                            activo=dbAdapter.temaActivo(4,5,idModul);
                            Log.e("Examen modulo 4", "onItemClick: "+activo );
                            if (activo) {
                                bundle.putInt("numeroExamne", 4);
                                startActivity(new Intent(SeleccionTemaActivity.this, ExamenFinalActivity.class).putExtras(bundle));
                            }else {
                                MensajeError("Para poder accesar a este examen necesita concluir con exito los temas anteriores", "Examen Bloquado");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            if (pasarSiguiente!=0){
                intent = new Intent(SeleccionTemaActivity.this, SeleccionTabActivity.class).putExtras(bundle);
                startActivity(intent);
                pasarSiguiente=0;
            }
            if (pasarErr!=0){
                MensajeError("Para poder accesar a este tema necesita concluir con exito el anterios", "Tema Bloquado");
                pasarErr=0;
            }
        }else {
            if (position==0){
                intent = new Intent(SeleccionTemaActivity.this, SeleccionTabActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }else if (position==8){
                MensajeError("Para poder accesar a este examen necesita registrarse en la aplicación", "Examen Bloquado");
            }else {
                MensajeError("Para poder accesar a este modulo necesita registrarse en la aplicación", "Tema Bloquado");
            }
        }
       /* if (position==0||position==1||position==2||position==3||position==4||position==5){

        }else {
            MensajeError("Para poder accesar a este modulo necesita \naver concluido con exito el anterior", "Tema Bloquado");
        }
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (valoresRecibidos.getBoolean("logeo")) {
            inflater.inflate(R.menu.menu_with_logeo, menu);
        } else {
            inflater.inflate(R.menu.menu_without_logeo, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (valoresRecibidos.getBoolean("logeo")){
            switch (item.getItemId()) {
                case R.id.itm_actionbar_menu_progreso_on:
                    ///////ProgresBar Inicio
                    int puntos=dbAdapter.getProgresoModulos(FormLoginActivity.ID_USU_LOGEADO);
                    int  avances=SeleccionModuloActivity.getCalificacionParceada(puntos);//
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
                    startActivity(new Intent(SeleccionTemaActivity.this,UsuarioInformacionMenuActivity.class));
                    break;
                case R.id.itm_actionbar_menu_configuraciones_on:
                    //Configuración
                    startActivity(new Intent(SeleccionTemaActivity.this,ConfiguracionActivity.class));
                    break;
                case R.id.itm_actionbar_menu_calificar_app:
                    //Calificar
                    startActivity(new Intent(SeleccionTemaActivity.this,CalificarAppMenuActivity.class));
                    break;
                case R.id.itm_actionbar_menu_acerca_de_on:
                    //Aserca de
                    startActivity(new Intent(SeleccionTemaActivity.this,AcercaDeMenuActivity.class));
                    break;
                case R.id.itm_actionbar_menu_ayu_suge_on:
                    //Ayuda y sugerencias
                    startActivity(new Intent(SeleccionTemaActivity.this,AyudaSugerenciasMenuActivity.class));
                    break;
                case R.id.itm_actionbar_herramientas:
                    startActivity(new Intent(SeleccionTemaActivity.this,HerramientasActivity.class));
                    break;
                case R.id.itm_actionbar_juegos:
                    startActivity(new Intent(SeleccionTemaActivity.this,MenusJuegosActivity.class));
                    break;
                case R.id.itm_actionbar_video:
                    startActivity(new Intent(SeleccionTemaActivity.this,VideoActivity.class));
                    break;

                case R.id.itm_actionbar_menu_salir_on:
                    //Salir
                    itmSalir();
                    break;
                case R.id.itm_actionbar_menu_juego:
                    //Ayuda y sugerencias
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.utng.edu.mx/")));
                    //startActivity(new Intent(SeleccionModuloActivity.this, ActivityJuego.class));
                    break;
            }
        }else {
            switch (item.getItemId()) {
                case  R.id.itm_actionbar_menu_acerca_de_off:
                    //acerca de
                    startActivity(new Intent(SeleccionTemaActivity.this,AcercaDeMenuActivity.class));
                    break;
                case R.id.itm_actionbar_video:
                    startActivity(new Intent(SeleccionTemaActivity.this,VideoActivity.class));
                    break;
                case R.id.itm_actionbar_menu_ayu_suge_off:
                    //ayuda y sugerencias
                    startActivity(new Intent(SeleccionTemaActivity.this,AyudaSugerenciasMenuActivity.class));
                    break;
                case R.id.itm_actionbar_herramientas:
                    startActivity(new Intent(SeleccionTemaActivity.this,HerramientasActivity.class));
                    break;
                case R.id.itm_actionbar_juegos:
                    startActivity(new Intent(SeleccionTemaActivity.this,MenusJuegosActivity.class));
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
    protected Dialog onCreateDialog(int i){
        _progressDialog = new ProgressDialog(this);
        _progressDialog.setTitle(getResources().getString(R.string.tiulo_progreso_menu_bar));
        _progressDialog.setIcon(R.mipmap.ic_launcher);
        _progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        _progressDialog.setButton(DialogInterface.BUTTON_POSITIVE,getResources().getString(R.string.mensaje_ocultar), new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

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


}