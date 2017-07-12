package survivinginporto.survivinginporto.survivinginporto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user1 on 21/05/2016.
 */
public class Entrada extends AppCompatActivity
{
    // Timer da splash screen
    private static int TIME_OUT = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(survivinginporto.survivinginporto.survivinginporto.R.layout.entrada);



       new Handler().postDelayed(new Runnable() {
            //*//*
             //* A mostrar a imagem survporto com um timer.
             //*//*

            @Override
            public void run() {


                SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("nameKey",null);
                String password = sharedpreferences.getString("phoneKey",null);


                    Intent i = new Intent(Entrada.this, MainActivity.class);
                    startActivity(i);

                finish();

            }
        }, TIME_OUT);
    }


}
