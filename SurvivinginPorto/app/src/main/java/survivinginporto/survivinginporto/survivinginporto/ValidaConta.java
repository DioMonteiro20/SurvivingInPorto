package survivinginporto.survivinginporto.survivinginporto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.net.URLEncoder;

/**
 * Created by user1 on 23/06/2016.
 */public class ValidaConta extends Fragment implements View.OnClickListener{
    String json_url;
    String JSON_STRING;


    public static String campo;
    public static EditText username1,password, codconf;
    public static TextView txt1;
    public static Spinner spinner;
    Button validaConta;
    EditText ed1, ed2, ed3;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.confirmotp_frag, container, false);


        validaConta = (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao16);


        validaConta.setOnClickListener(this);


        return v;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao16: {
                username1 = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.destino3);
                password = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.escrever5);
                codconf = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.escrever6);


                String username2 = username1.getText().toString();
                String password1 = password.getText().toString();
                String codconf1 = codconf.getText().toString();

                if (username2.matches("")|password1.matches("")|codconf1.matches(""))
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


        public void marcadoresMapa() {



            username1 = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.destino3);
            password = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.escrever5);
            codconf = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.escrever6);


            String username2 = username1.getText().toString();
            String password1 = password.getText().toString();
            String codconf1 = codconf.getText().toString();




            try {
                json_url = "http://survivinginporto.atwebpages.com/AndroidOTP/confirm3.php";
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username2", "UTF-8") + "=" + URLEncoder.encode(username2, "UTF-8") + "&" +
                        URLEncoder.encode("codconf1", "UTF-8") + "=" + URLEncoder.encode(codconf1, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                // String response = "";

                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                String result = stringBuilder.toString().trim();


                String test = "success";




                if (result.contains(test)) {

                    Toast.makeText(getContext(), "Conta validada com sucesso!", Toast.LENGTH_LONG).show();
                    //System.out.println("Alert"+ result);

                    String MyPREFERENCES = "MyPrefs";
                    String Name = "nameKey";
                    String Phone = "phoneKey";
                    ed1= (EditText) Registo.ed1;
                    ed2= (EditText) Registo.ed2;
                    ed1=(EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.destino3);
                    ed2=(EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.escrever5);

                    SharedPreferences sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String n = ed1.getText().toString();
                    String ph = ed2.getText().toString();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, n);
                    editor.putString(Phone, ph);
                    editor.commit();

                    getContext().startActivity(new Intent(getContext(), MainActivity.class));


                } else {
                    System.out.println("Dados incorrectos");
                    Toast.makeText(getContext(), "Dados Incorrectos/Conta não verificada", Toast.LENGTH_LONG).show();
                }

            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }

    }
}