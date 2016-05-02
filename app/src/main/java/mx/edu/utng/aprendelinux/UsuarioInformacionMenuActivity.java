package mx.edu.utng.aprendelinux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class UsuarioInformacionMenuActivity extends AppCompatActivity {

    private TextView txvInfoResNombre,txvInfoResCorreo,txvInfoResPuntos;
    private DBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_information_menu_layout);
        initComponets();
    }

    private void initComponets() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        txvInfoResNombre =(TextView) findViewById(R.id.txv_info_res_nombre);
        txvInfoResCorreo =(TextView) findViewById(R.id.txv_info_res_correo);
        txvInfoResPuntos =(TextView) findViewById(R.id.edt_info_res_puntos);

        int puntos=dbAdapter.getProgresoModulos(FormLoginActivity.ID_USU_LOGEADO);
        int  avances=SeleccionModuloActivity.getCalificacionParceada(puntos);//
        txvInfoResPuntos.setText(""+avances+" pt");
        txvInfoResNombre.setText(FormLoginActivity.NOMBRE_USU_LOGEADO);
        txvInfoResCorreo.setText(FormLoginActivity.CORREO_USU_LOGEADO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
