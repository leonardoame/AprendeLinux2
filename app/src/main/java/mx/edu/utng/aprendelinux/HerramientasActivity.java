package mx.edu.utng.aprendelinux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HerramientasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);
        findViewById(R.id.btn_calculadora).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HerramientasActivity.this, Calculadora.class));
            }
        });
        findViewById(R.id.btn_cronometro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HerramientasActivity.this, Cronometro.class));
            }
        });
    }
}
