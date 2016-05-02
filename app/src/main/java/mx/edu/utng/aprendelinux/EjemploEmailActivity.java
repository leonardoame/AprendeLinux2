package mx.edu.utng.aprendelinux;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class EjemploEmailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnEnviarEmalDespues,btnEnviarEmailHaora;
    private TextView txvMensaje;
    private DBAdapter dbAdapter;
    //----

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejemplo_email_layout);
        initComponents();
    }

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        txvMensaje=(TextView)findViewById(R.id.txv_mensaje);
        btnEnviarEmalDespues=(Button)findViewById(R.id.btn_enviar_email_despues);
        btnEnviarEmailHaora=(Button)findViewById(R.id.btn_enviar_email_haora);
        //Escuchadores

        String s1=getResources().getString(R.string.html_curso_concluido_parte_uno);
        String s2=getResources().getString(R.string.html_curso_concluido_parte_dos);
        String contDelTem=s1+FormLoginActivity.NOMBRE_USU_LOGEADO+s2;

        CharSequence textoInterpretado = Html.fromHtml(contDelTem);
        txvMensaje.setText(textoInterpretado);
        btnEnviarEmalDespues.setOnClickListener(this);
        btnEnviarEmailHaora.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enviar_email_despues:
                Toast.makeText(EjemploEmailActivity.this, "DESPUES", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_enviar_email_haora:

                String para = FormLoginActivity.CORREO_USU_LOGEADO;
                int cal1=dbAdapter.traerCalificacion(1,FormLoginActivity.ID_USU_LOGEADO);
                int cal2=dbAdapter.traerCalificacion(2,FormLoginActivity.ID_USU_LOGEADO);
                int cal3=dbAdapter.traerCalificacion(2,FormLoginActivity.ID_USU_LOGEADO);
                int cal4=dbAdapter.traerCalificacion(3,FormLoginActivity.ID_USU_LOGEADO);
                int cal5=dbAdapter.traerCalificacion(4,FormLoginActivity.ID_USU_LOGEADO);
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{para});
                //email.putExtra(Intent.EXTRA_STREAM,Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.certificado));
                email.putExtra(Intent.EXTRA_SUBJECT,"Estatus del curso de: "+FormLoginActivity.NOMBRE_USU_LOGEADO);
                email.putExtra(Intent.EXTRA_TEXT,   "----------------------------------------------------------------\n" +
                                                    "-------------Calificación por módulo------------- \n" +
                                                    "----------------------------------------------------------------\n\n" +
                                                    "\t\t\t\tMÓDULO 1:  "+cal1+
                                                  "\n\t\t\t\tMÓDULO 2:  "+cal2+
                                                  "\n\t\t\t\tMÓDULO 3:  "+cal3+
                                                  "\n\t\t\t\tMÓDULO 4:  "+cal4+
                                                  "\n\t\t\t\tMÓDULO 5:  "+cal5);

                //email.setType("image/png");
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, getResources().getString(R.string.escoge_medio_preferencia)));

                break;
            default:
                Log.e("Switch", "onClick: No entro " );
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}