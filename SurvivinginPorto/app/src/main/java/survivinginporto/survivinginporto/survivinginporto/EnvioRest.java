package survivinginporto.survivinginporto.survivinginporto;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by user1 on 23/06/2016.
 */public class EnvioRest extends Fragment implements View.OnClickListener{
    String json_url;
    String JSON_STRING;
    //Context ctx;
    // DadosFrag dadosFrag;

    public static final String MyPREFERENCES2 = "MyPrefs2";
    public static final String Name2 = "nameKey2";
    public static final String Phone2 = "phoneKey2";

    public static String campo;
    public static EditText nome, descricao,latitude, longitude, responsavel, contacto;
    public static TextView txt1;
    public static Spinner spinner;

    Button envioRest;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.formrest_frag, container, false);

        envioRest = (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.botaoRest);


        envioRest.setOnClickListener(this);


        return v;
    }

    public void onClick(View v) {


        switch (v.getId()) {

            case survivinginporto.survivinginporto.survivinginporto.R.id.botaoRest: {
                nome = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
                descricao = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);
                latitude = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPhone);
                longitude = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextEmail);
                responsavel = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextNacional);
                contacto = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextCusto);

                String nome1 = nome.getText().toString();
                String descricao1 = descricao.getText().toString();
                String latitude1 = latitude.getText().toString();
                String longitude1 = longitude.getText().toString();
                String responsavel1 = responsavel.getText().toString();
                String contacto1 = contacto.getText().toString();
                double value1 = Double.parseDouble(latitude1);
                double value = Double.parseDouble(longitude1);

                if (nome1.matches("") | descricao1.matches("") | latitude1.matches("") | longitude1.matches("") | responsavel1.matches("") | contacto1.matches("")) {
                    Toast.makeText(getContext(), "Preencha todos os campos obrigatórios, por favor.", Toast.LENGTH_SHORT).show();
                    //return;
                } else if (contacto1.length() < 9) {
                    Toast.makeText(getContext(), "O contacto tem que ter 9 algarismos, volte a introduzir, por favor.", Toast.LENGTH_SHORT).show();
                } else if (latitude1.matches("0") || longitude1.matches("0")) {
                    Toast.makeText(getContext(), "A latitude e a longitude não podem ter '0' no valor.", Toast.LENGTH_SHORT).show();
                }
                else if (value % 1 == 0)
                {

                    Toast.makeText(getContext(), "Introduza, por favor, um valor decimal na longitude.", Toast.LENGTH_SHORT).show();


                }
                else if (value >= 0)
                {
                    Toast.makeText(getContext(), "Introduza, por favor, um valor negativo na longitude!", Toast.LENGTH_SHORT).show();
                }
                else if (value1 % 1 == 0)
                {
                    Toast.makeText(getContext(), "Introduza, por favor, um valor decimal na latitude.", Toast.LENGTH_SHORT).show();
                }else {
                    MarkerBD11 enviarest = new MarkerBD11();
                    enviarest.marcadoresMapa();


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


            nome = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
            descricao= (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);
            latitude = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPhone);
            longitude = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextEmail);
            responsavel = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextNacional);
            contacto = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextCusto);





            String nome1 = nome.getText().toString();
            String descricao1 = descricao.getText().toString();
            String latitude1 = latitude.getText().toString();
            String longitude1 = longitude.getText().toString();
            String responsavel1 = responsavel.getText().toString();
            String contacto1 = contacto.getText().toString();

            try {
                json_url = "http://survivinginporto.atwebpages.com/inserirRest.php";
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("nome1", "UTF-8") + "=" + URLEncoder.encode(nome1, "UTF-8")+ "&" +
                        URLEncoder.encode("descricao1", "UTF-8") + "=" + URLEncoder.encode(descricao1, "UTF-8")+ "&" +
                        URLEncoder.encode("latitude1", "UTF-8") + "=" + URLEncoder.encode(latitude1, "UTF-8")+ "&" +
                        URLEncoder.encode("longitude1", "UTF-8") + "=" + URLEncoder.encode(longitude1, "UTF-8")+ "&" +
                        URLEncoder.encode("responsavel1", "UTF-8") + "=" + URLEncoder.encode(responsavel1, "UTF-8")+ "&" +
                        URLEncoder.encode("contacto1", "UTF-8") + "=" + URLEncoder.encode(contacto1, "UTF-8");
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

                Toast.makeText(getContext(), "Formulário submetido com sucesso! Vamos analisar os dados e contacta-lo.", Toast.LENGTH_SHORT).show();
                RestFrag RestFrag = new RestFrag();
                android.support.v4.app.FragmentTransaction RestFragTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                RestFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, RestFrag);
                RestFragTransaction.commit();


            } catch (IOException e1) {
                e1.printStackTrace();
            }




        }
    }

}
