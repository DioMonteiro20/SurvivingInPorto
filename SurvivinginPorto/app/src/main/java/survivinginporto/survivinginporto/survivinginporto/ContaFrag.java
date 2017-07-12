package survivinginporto.survivinginporto.survivinginporto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ContaFrag extends Fragment implements View.OnClickListener
{
    String json_url;
    String JSON_STRING;

    EditText ed1, ed2, ed3;
    Button apagaConta, logOff;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.conta_frag,container,false);

        apagaConta= (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao);

        logOff= (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao1);

        apagaConta.setOnClickListener(this);
        logOff.setOnClickListener(this);




        return v;
    }

    public void onClick(View v) {

        switch (v.getId())
        {

            case survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao:
            {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                BackgroundDB back = new BackgroundDB(getContext());
                                back.execute();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Tem a certeza que pretende eliminar a sua conta?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();


                break;


            }
            case survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao1:
            {
                Toast.makeText(getContext(), "Logoff com sucesso!", Toast.LENGTH_LONG).show();

                SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                  SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();


                getContext().startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                break;
            }
        }
    }


    class BackgroundDB extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;
        Context ctx;
        BackgroundDB(Context ctx)
        {
            this.ctx =ctx;
        }

        protected String doInBackground(Void... params) {
            String login_url = "http://survivinginporto.atwebpages.com/AndroidOTP/deleteConta.php";
            SharedPreferences sharedpreferences = ctx.getSharedPreferences(Login2.MyPREFERENCES, Context.MODE_PRIVATE);
            String username = sharedpreferences.getString("nameKey", null);
            String password = sharedpreferences.getString("phoneKey", null);


            try {

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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


        }



        @Override
        protected void onPostExecute(String result)
        {
            TextView txt1 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv1);


            String test = "Apagada";


            if (result.contains(test))
            {

                Toast.makeText(ctx, "Conta apagada com sucesso!", Toast.LENGTH_LONG).show();

                SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();


                ctx.startActivity(new Intent(ctx, MainActivity.class));


            }

            else{
                System.out.println("Falhou a apagar");
            }




        }



    }



}
