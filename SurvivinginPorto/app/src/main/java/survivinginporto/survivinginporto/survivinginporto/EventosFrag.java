package survivinginporto.survivinginporto.survivinginporto;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user1 on 22/05/2016.
 */
public class EventosFrag extends Fragment
{
    String json_url;
    String JSON_STRING;
    public Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.eventos_frag,container,false);


        new BackgroundDB8() .execute();



        return v;
    }




    class BackgroundDB8 extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;



        protected String doInBackground(Void... params) {
            try {

                json_url = "http://survivinginporto.atwebpages.com/selectEventos.php";
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            json_url = "survivinginporto.atwebpages.com/selectEventos.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }



        @Override
        protected void onPostExecute(String eventos)
        {
            TextView txt7 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv7);




            try
            {
                JSONObject jsonObject = new JSONObject(eventos);
                JSONArray jsonArray = jsonObject.getJSONArray("eventos");


                for(int i=0;i<jsonArray.length();i++)
                {

                    JSONObject JO = jsonArray.getJSONObject(i);

                    String data = JO.getString("data");
                    String hora = JO.getString("hora");
                    String nome = JO.getString("nome");
                    String descricao = JO.getString("descricao");
                    String local = JO.getString("local");
                    String custo = JO.getString("custo");


                    txt7.append("Data: "+ data +"\t\t" + "Hora: "+hora  + "\t\t\n" + "Nome: " + nome + "\t\t\n" + "Descrição: " + descricao + "\t\t\n" + "Local: " + local + "\t\t\n" + "Custo(€): " +custo+"\t\t\n" + "\n");




                }
                //Envia para classe Mapa Eventos
                Button verMapa5 = (Button) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao10);

                verMapa5.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {

                        Intent myIntent10 = new Intent(getActivity(), Mapa_Eventos.class);
                        getActivity().startActivity(myIntent10);
                    }

                });

                //Envia para formulário
                TextView txt3 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv3);

                txt3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        Toast.makeText(getActivity(), "Complete os seguintes campos, por favor.", Toast.LENGTH_SHORT).show();
                        EnvioEvento EnvioEvento = new EnvioEvento();
                        android.support.v4.app.FragmentTransaction EnvioEventoTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        EnvioEventoTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, EnvioEvento);
                        EnvioEventoTransaction.commit();

                    }
                });


            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }

        }

    }



}