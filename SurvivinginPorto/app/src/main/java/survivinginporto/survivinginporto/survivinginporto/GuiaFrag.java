package survivinginporto.survivinginporto.survivinginporto;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
public class GuiaFrag extends Fragment
{
    String json_url;
    String JSON_STRING;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.guia_frag,container,false);


        new BackgroundDB() .execute();

        return v;
    }




    class BackgroundDB extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;



        protected String doInBackground(Void... params) {
            try {

                json_url = "http://survivinginporto.atwebpages.com/selectGuia.php";
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
            json_url = "survivinginporto.atwebpages.com/selectGuia.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }



        @Override
        protected void onPostExecute(String guia)
        {
            TextView txt1 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv1);


            try
            {
                JSONObject jsonObject = new JSONObject(guia);
                JSONArray jsonArray = jsonObject.getJSONArray("guia");


                for(int i=0;i<jsonArray.length();i++)
                {

                    JSONObject JO = jsonArray.getJSONObject(i);

                    //get an output on the screen
                    int id = JO.getInt("id");
                    String nome = JO.getString("nome");
                    String descricao = JO.getString("descricao");


                    txt1.append(nome + "\t\t\n" +descricao+ "\t\t" + "\n\n");




                }

                Button verMapa = (Button) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao);

                verMapa.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {

                        Intent myIntent = new Intent(getActivity(), Mapa_Guia.class);
                        getActivity().startActivity(myIntent);
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
