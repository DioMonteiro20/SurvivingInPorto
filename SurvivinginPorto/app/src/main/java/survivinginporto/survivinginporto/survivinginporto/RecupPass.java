package survivinginporto.survivinginporto.survivinginporto;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by user1 on 23/06/2016.
 */public class RecupPass extends Fragment implements View.OnClickListener {
    String json_url;
    String JSON_STRING;


    public static final String MyPREFERENCES2 = "MyPrefs2";
    public static final String Name2 = "nameKey2";
    public static final String Phone2 = "phoneKey2";

    public static String campo;
    public static EditText destino, escrever;
    public static TextView txt1;
    public static Spinner spinner;
    Button recupPass;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.recupass_frag, container, false);

        recupPass = (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao15);


        recupPass.setOnClickListener(this);


        return v;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao15: {
                destino = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.destino1);

                String destino1 = destino.getText().toString();


                if (destino1.matches(""))
                {
                    Toast.makeText(getContext(), "Preencha, por favor, o número móvel que tem associado à conta.", Toast.LENGTH_SHORT).show();

                }else if(destino1.length()<9)
                {
                    Toast.makeText(getContext(), "O contacto tem que ter 9 algarismos, volte a introduzir, por favor.", Toast.LENGTH_SHORT).show();
                }
                else {
                    MarkerBD5 recup = new MarkerBD5();
                    recup.marcadoresMapa();
                }

            }
        }
    }


    class MarkerBD5
    {

        String json_url;
        String JSON_STRING;



        public void marcadoresMapa() {
            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 15; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            String password = sb.toString();


            destino = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.destino1);

            String destino1 = destino.getText().toString();


            try {
                json_url = "http://survivinginporto.atwebpages.com/AndroidOTP/recupPass.php";
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("destino1", "UTF-8") + "=" + URLEncoder.encode(destino1, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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


                String test = "Enviada";

                if (result.contains(test))
                {
                    sendOtp1(Integer.parseInt(destino1), password);
                    Toast.makeText(getContext(), "A nova password foi enviada para o seu telemóvel!", Toast.LENGTH_SHORT).show();
                    destino.setText("");
                }
                else
                {
                    Toast.makeText(getContext(), "Este número móvel não se encontra registado", Toast.LENGTH_SHORT).show();
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private void sendOtp1(int numero, String password) {

            //Your authentication key
            String authkey = "jgHVgYfofoXRhjr6Ts6D";
//Password
            String pwd = "survporto";
//Multiple mobiles numbers separated by comma
            String mobiles = String.valueOf(numero);
//Your message to send, Add URL encoding here.
            String message = "A sua nova password e: " + password;
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
                encoded_message = URLEncoder.encode(message, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//Send SMS API
            String mainUrl = "http://www.smsbulk.pt/api-http-v1/sms_send.php?";

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
}
