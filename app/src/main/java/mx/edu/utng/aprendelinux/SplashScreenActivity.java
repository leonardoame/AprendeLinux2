package mx.edu.utng.aprendelinux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class SplashScreenActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY=2000;//Duracion
    private DBAdapter dbAdapter;
    //metodo onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen_layout);

        initComponents();
    }

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();

        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                if (dbAdapter.existUsu()) {
                    startActivity(new Intent().setClass( SplashScreenActivity.this, FormLoginActivity.class));
                }else {
                    startActivity(new Intent().setClass( SplashScreenActivity.this, FormCreateLoginActivity.class));
                }
                finish();
            }
        };

        Timer timer=new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
