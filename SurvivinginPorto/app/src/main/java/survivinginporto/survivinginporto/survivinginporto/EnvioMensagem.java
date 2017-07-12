package survivinginporto.survivinginporto.survivinginporto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by user1 on 23/06/2016.
 */public class EnvioMensagem extends Fragment implements View.OnClickListener{
    String json_url;
    String JSON_STRING;


    public static final String MyPREFERENCES2 = "MyPrefs2";
    public static final String Name2 = "nameKey2";
    public static final String Phone2 = "phoneKey2";

    public static String campo;
    public static EditText destino, escrever;
    public static TextView txt1;
    public static Spinner spinner;
    Button enviaMensagem;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.enviomensagem_frag, container, false);


        enviaMensagem = (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao5);


        enviaMensagem.setOnClickListener(this);


        return v;
    }

    public void onClick(View v) {


        switch (v.getId()) {

            case survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao5: {
                destino = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.destino);
                escrever = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.escrever);


                String destino1 = destino.getText().toString();
                String escrever1 = escrever.getText().toString();
                if (destino1.matches("")|escrever1.matches(""))
                {
                    Toast.makeText(getContext(), "Preencha todos os campos, por favor.", Toast.LENGTH_SHORT).show();
                }
                else {
                    MarkerBD5 lista = new MarkerBD5();
                    lista.marcadoresMapa();
                }


            }
        }
    }



    class MarkerBD5
    {

        String json_url;
        String JSON_STRING;


        public void marcadoresMapa()
        {
            SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login2.MyPREFERENCES, Context.MODE_PRIVATE);
               String username = sharedpreferences.getString("nameKey", null);

            destino = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.destino);
            escrever = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.escrever);


            String destino1 = destino.getText().toString();
            String escrever1 = escrever.getText().toString();

            try {
                json_url = "http://survivinginporto.atwebpages.com/AndroidOTP/selectMovel.php";
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("destino1", "UTF-8") + "=" + URLEncoder.encode(destino1, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                String result = stringBuilder.toString().trim();


                if(!(result.contains("phone")))
                {
                    Toast.makeText(getContext(), "Falha no envio da mensagem!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Insira um username correcto, por favor!", Toast.LENGTH_SHORT).show();
                }


                JSONObject jsonObject = null;

                jsonObject = new JSONObject(result);

                JSONArray jsonArray = null;

                jsonArray = jsonObject.getJSONArray("result");

               // txt1.setText("");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject JO = jsonArray.getJSONObject(i);


                    int phone = JO.getInt("phone");


                    sendOtp(phone, username, escrever1);
                    Toast.makeText(getContext(), "Mensagem enviada com sucesso!", Toast.LENGTH_SHORT).show();
                    destino.setText("");
                    escrever.setText("");

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Falha no envio!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {

                e.printStackTrace();
                Toast.makeText(getContext(), "Falha no envio!", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Falha no envio!", Toast.LENGTH_SHORT).show();
            }


        }
        public void displayExceptionMessage(String msg)
        {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

        }
    }

    private void sendOtp(int numero, String username, String mensagem)
    {

        //Your authentication key
        String authkey = "jgHVgYfofoXRhjr6Ts6D";
//Password
        String pwd = "survporto";
//Multiple mobiles numbers separated by comma
        String mobiles = String.valueOf(numero);
//Your message to send, Add URL encoding here.
        String message = username +": "+mensagem;
//Remetente
        String rem = "SurvInPorto";
        // String uni= "1";
        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;

//encoding message
        // String encoded_message = URLEncoder.encode(message, "UTF-8");
        String encoded_message = null;
        try {
            encoded_message = URLEncoder.encode(message,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//Send SMS API
        String mainUrl= "http://www.smsbulk.pt/api-http-v1/sms_send.php?";

//Prepare parameter string
        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append("uid=" + authkey);
        sbPostData.append("&pwd=" + pwd);
        sbPostData.append("&tel=" + mobiles);
        sbPostData.append("&msg=" + encoded_message);
        //sbPostData.append("&msg=" + message);
        sbPostData.append("&rem=" + rem);
        //sbPostData.append("&uni=" + uni);


//final string
        mainUrl = sbPostData.toString();
        try {
            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", "" + response);

            //finally close connection
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
