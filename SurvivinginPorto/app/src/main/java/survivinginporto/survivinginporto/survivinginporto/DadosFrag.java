package survivinginporto.survivinginporto.survivinginporto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by user1 on 23/06/2016.
 */
public class DadosFrag extends Fragment
{
    String json_url;
    String JSON_STRING;


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.dados_frag,container,false);

        BackgroundDB back = new BackgroundDB(getContext());
        back.execute();

        return v;
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
            String login_url = "http://survivinginporto.atwebpages.com/AndroidOTP/selectDados.php";
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
        protected void onPreExecute() {
            json_url = "survivinginporto.atwebpages.com/AndroidOTP/selectDados.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }



        @Override
        protected void onPostExecute(String result)
        {
            TextView txt1 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv1);


            try
            {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("result");


                for(int i=0;i<jsonArray.length();i++)
                {

                    JSONObject JO = jsonArray.getJSONObject(i);

                    //get an output on the screen
                    String username = JO.getString("username");
                    int phone = JO.getInt("phone");
                    String email = JO.getString("email");
                    String pais = JO.getString("pais");
                    String universidade = JO.getString("universidade");
                    String genero = JO.getString("genero");
                    String localidade = JO.getString("localidade");




                    txt1.append("Username: "+ username + "\t\t\n" +"Telemóvel: "+ phone + "\t\t\n" +"Email: "+email+"\t\t\n" +"País de Origem: "+pais+"\t\t\n" +"Universidade: "+universidade+"\t\t\n" +"Género: "+genero+"\t\t\n" +"Localidade: "+localidade+ "\t\t\n" + "\n");

                }

                Button alteraDados = (Button) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao21);

                alteraDados.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {

                        Toast.makeText(getActivity(), "Altere os campos que deseja, preenchendo os obrigatórios, por favor.", Toast.LENGTH_SHORT).show();
                        EnvioDados   EnvioDados = new   EnvioDados();
                        android.support.v4.app.FragmentTransaction   EnvioDadosTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        EnvioDadosTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame,   EnvioDados);
                        EnvioDadosTransaction.commit();
                    }

                });

            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }

        }

    }













    public class EnvioDados extends Fragment implements View.OnClickListener{
        String json_url;
        String JSON_STRING;


        public static final String MyPREFERENCES2 = "MyPrefs2";
        public static final String Name2 = "nameKey2";
        public static final String Phone2 = "phoneKey2";


       public EditText username, password,phone,email,pais,universidade,genero,localidade;
        Button envioDados;


        @Nullable
        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.formdados_frag, container, false);


            envioDados = (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.buttonRegister21);


            envioDados.setOnClickListener(this);


            return v;
        }

        public void onClick(View v) {

            switch (v.getId()) {

                case survivinginporto.survivinginporto.survivinginporto.R.id.buttonRegister21: {


                    username = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
                    password= (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);
                    phone = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPhone);
                    email = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextEmail);
                    pais = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextNacional);
                    universidade = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextCusto);
                    genero = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextGen);
                    localidade = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextArea);


                    //Getting user data
                    String username1 = username.getText().toString();
                    String password1 = password.getText().toString();
                    String phone1 = phone.getText().toString();
                    String email1 = email.getText().toString();
                    String pais1 = pais.getText().toString();
                    String universidade1 = universidade.getText().toString();
                    String genero1 = genero.getText().toString();
                    String localidade1 = localidade.getText().toString();
                    if (username1.matches("")|password1.matches("")|phone1.matches("")|pais1.matches("")|genero1.matches(""))
                    {
                        Toast.makeText(getContext(), "Preencha todos os campos assinalados como obrigatórios (*).", Toast.LENGTH_SHORT).show();
                        //return;
                    }else if(phone.length()<9)
                    {
                        Toast.makeText(getContext(), "O contacto tem que ter 9 algarismos, volte a introduzir, por favor.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        MarkerBD11 enviadados = new MarkerBD11();
                        enviadados.marcadoresMapa();
                    }


                }
            }
        }



        class MarkerBD11
        {

            String json_url;
            String JSON_STRING;


            public void marcadoresMapa()
            {


                username = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
                password= (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);
                phone = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPhone);
                email = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextEmail);
                pais = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextNacional);
                universidade = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextCusto);
                genero = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextGen);
                localidade = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextArea);





                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                String phone1 = phone.getText().toString();
                String email1 = email.getText().toString();
                String pais1 = pais.getText().toString();
                String universidade1 = universidade.getText().toString();
                String genero1 = genero.getText().toString();
                String localidade1 = localidade.getText().toString();

                try {
                    json_url = "http://survivinginporto.atwebpages.com/inserirDados.php";
                    URL url = new URL(json_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("username1", "UTF-8") + "=" + URLEncoder.encode(username1, "UTF-8")+ "&" +
                            URLEncoder.encode("password1", "UTF-8") + "=" + URLEncoder.encode(password1, "UTF-8")+ "&" +
                            URLEncoder.encode("phone1", "UTF-8") + "=" + URLEncoder.encode(phone1, "UTF-8")+ "&" +
                            URLEncoder.encode("email1", "UTF-8") + "=" + URLEncoder.encode(email1, "UTF-8")+ "&" +
                            URLEncoder.encode("pais1", "UTF-8") + "=" + URLEncoder.encode(pais1, "UTF-8")+ "&" +
                            URLEncoder.encode("universidade1", "UTF-8") + "=" + URLEncoder.encode(universidade1, "UTF-8")+ "&" +
                            URLEncoder.encode("genero1", "UTF-8") + "=" + URLEncoder.encode(genero1, "UTF-8")+ "&" +
                            URLEncoder.encode("localidade1", "UTF-8") + "=" + URLEncoder.encode(localidade1, "UTF-8");
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

                    Toast.makeText(getContext(), "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
                    DadosFrag DadosFrag = new DadosFrag();
                    android.support.v4.app.FragmentTransaction DadosFragTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    DadosFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, DadosFrag);
                    DadosFragTransaction.commit();


                }
                catch (UnsupportedEncodingException e1) {
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


}
