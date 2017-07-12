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
public class ContentFragment extends Fragment
{
    String json_url;
    String JSON_STRING;
    public Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.content_fragment,container,false);


        new BackgroundDB8() .execute();



        return v;
    }




    class BackgroundDB8 extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;



        protected String doInBackground(Void... params) {
            try {

                json_url = "http://survivinginporto.atwebpages.com/selectSurv1.php";
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
            json_url = "survivinginporto.atwebpages.com/selectSurv1.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }



        @Override
        protected void onPostExecute(String habitacoes)
        {
            TextView txt1 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv1);




            try
            {
                JSONObject jsonObject = new JSONObject(habitacoes);
                JSONArray jsonArray = jsonObject.getJSONArray("habitacoes");


                for(int i=0;i<jsonArray.length();i++)
                {

                    JSONObject JO = jsonArray.getJSONObject(i);


                    String tipo = JO.getString("tipo");
                    String morada = JO.getString("morada");
                    String descricao = JO.getString("descricao");
                    String custo = JO.getString("custo");
                    String latitude = JO.getString("latitude");
                    String longitude = JO.getString("longitude");
                    String responsavel = JO.getString("responsavel");
                    String contacto = JO.getString("contacto");

                    txt1.append("Tipo: "+ tipo +"\t\t\n" + "Morada: "+morada + "\t\t\n" + "Descrição: " + descricao + "\t\t\n" + "Custo(€): " + custo + "\t\t\n" + "Responsável: " + responsavel + "\t\t\n" + "Contacto: " +contacto+"\t\t\n" + "\n");




                }
                //Envia para classe Mapa Eventos
                Button verMapa = (Button) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao);

                verMapa.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {

                        Intent myIntent = new Intent(getActivity(), Mapa_Hab.class);
                        getActivity().startActivity(myIntent);
                    }

                });

                //Envia para formulário
                TextView txt11 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv11);

                txt11.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {



                        Toast.makeText(getActivity(), "Complete os seguintes campos, por favor.", Toast.LENGTH_SHORT).show();
                        EnvioHab EnvioHab = new EnvioHab();
                        android.support.v4.app.FragmentTransaction EnvioHabTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        EnvioHabTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, EnvioHab);
                        EnvioHabTransaction.commit();



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
