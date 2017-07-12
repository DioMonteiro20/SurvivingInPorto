package survivinginporto.survivinginporto.survivinginporto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ricardo on 29/06/2016.
 */
public class RecebeHoraServer {
    private int horaCorrente;
    private int minCorrente;
    String json_url;
    String JSON_STRING;


    // FAZ O SELECT DA BD E MANDA PARA O MAPA OS MARCADORES COM OS USERS

    public int getHoraCorrente() {
        return horaCorrente;
    }

    public void setHoraCorrente(int horaCorrente) {
        this.horaCorrente = horaCorrente;
    }

    public int getMinCorrente() {
        return minCorrente;
    }

    public void setMinCorrente(int minCorrente) {
        this.minCorrente = minCorrente;
    }



    public void horaMin() {

        try {

            json_url = "http://survivinginporto.atwebpages.com/horaCorrente.php";

            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }


            httpURLConnection.disconnect();
            String dados = stringBuilder.toString().trim();




            JSONObject   jsonObject = new JSONObject(dados);



            JSONArray   jsonArray = jsonObject.getJSONArray("hora");




            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject JO = jsonArray.getJSONObject(i);


                setHoraCorrente(JO.getInt("horaCorrente"));
                setMinCorrente(JO.getInt("minCorrente"));

                System.out.println("RECEBEHORASERVER HORA CORRENTE = "+horaCorrente+"::::::::::::::::::::::");
                System.out.println("RECEBEHORASERVER MIN CORRENTE = "+minCorrente+"::::::::::::::::::::::");
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}



