package survivinginporto.survivinginporto.survivinginporto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

/**
 * Created by user1 on 14/06/2016.
 */
public class RegistoSucesso extends AppCompatActivity
{
    private static int TIME_OUT = 3000;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(survivinginporto.survivinginporto.survivinginporto.R.layout.sucessregisto_frag);
        rl=(RelativeLayout) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.RelativeLayout);

        new Handler().postDelayed(new Runnable() {
            /*
             * A mostrar a imagem survporto com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, TIME_OUT);
    }



}
