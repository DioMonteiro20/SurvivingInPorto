package survivinginporto.survivinginporto.survivinginporto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by user1 on 21/06/2016.
 */

public class Login2 extends AsyncTask<String, Void, String> {
    AlertDialog alertDialog;
    Context ctx;
    Login2(Context ctx)
    {
        this.ctx =ctx;
    }
    EditText ed1, ed2, ed3;
    Intent intent3;


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected String doInBackground(String... params) {

        String login_url = "http://survivinginporto.atwebpages.com/AndroidOTP/confirm2.php";



        String username = params[0];
        String password = params[1];
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            System.out.println(response);
            return response;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    @Override
    protected void onProgressUpdate(Void... values) {
         super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {


        ed1= (EditText) Login.ed1;
        ed2= (EditText) Login.ed2;


     //SHARED PREFERECES:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

      SharedPreferences sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


         String test = "Login success";


     String n = ed1.getText().toString();
        String ph = ed2.getText().toString();


        if (result.contains(test))
        {

             Toast.makeText(ctx, "Login com sucesso!", Toast.LENGTH_LONG).show();


                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Name, n);
                editor.putString(Phone, ph);
                editor.commit();


            ctx.startActivity(new Intent(ctx, MainActivity.class));
            ((Activity) ctx).finish();


        }

else{
            System.out.println("Dados incorrectos");
            Toast.makeText(ctx, "Dados Incorrectos/Conta não verificada", Toast.LENGTH_LONG).show();
        }




    }



}






