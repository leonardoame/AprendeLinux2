package mx.edu.utng.aprendelinux;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class AyudaSugerenciasMenuActivity extends AppCompatActivity {
 
	private Button btnEnviar;
	private EditText edtPara;
	private EditText edtTema;
	private EditText edtMensage;
	private MediaPlayer mp;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ayuda_sugerencias_menu_layout);
 
		btnEnviar = (Button) findViewById(R.id.buttonSend);
		edtPara = (EditText) findViewById(R.id.editTextTo);
		edtTema = (EditText) findViewById(R.id.editTextSubject);
		edtMensage = (EditText) findViewById(R.id.editTextMessage);
		mp=MediaPlayer.create(this,R.raw.cli_d);
 
		btnEnviar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mp.start();
				String para = edtPara.getText().toString();
				String subject = edtTema.getText().toString();
				String message = edtMensage.getText().toString();

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[]{para});
				//email.putExtra(Intent.EXTRA_CC, new String[]{ para});
				//email.putExtra(Intent.EXTRA_BCC, new String[]{para});
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);

				//need this para prompts email client only
				email.setType("message/rfc822");

				startActivity(Intent.createChooser(email, getResources().getString(R.string.escoge_medio_preferencia)));
			}
		});
	}
}