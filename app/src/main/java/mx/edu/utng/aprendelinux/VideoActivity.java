package mx.edu.utng.aprendelinux;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;


public class VideoActivity extends AppCompatActivity {
    private MediaController mediaController;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        final Button vdvVideo  = (Button) findViewById(R.id.vdv_video);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(vdvVideo);



            uri = Uri.parse("https://www.youtube.com/watch?v=uSzqvNBduyc");


        vdvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

    }




        //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxxx
        //Se inicializan los controles para el dieo



}
