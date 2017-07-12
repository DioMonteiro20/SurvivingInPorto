package survivinginporto.survivinginporto.survivinginporto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by user1 on 23/06/2016.
 */public class ListUserFrag extends Fragment implements View.OnClickListener{
    String json_url;
    String JSON_STRING;


    public static final String MyPREFERENCES2 = "MyPrefs2";
    public static final String Name2 = "nameKey2";
    public static final String Phone2 = "phoneKey2";

    public static String campo;
    public static EditText procura;
    public static TextView txt1;
    public static Spinner spinner;
    Button listaUsers;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.userlist_frag, container, false);


        listaUsers= (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao4);


        String [] values =
                {"username", "pais", "universidade", "genero", "localidade",};
        Spinner spinner = (Spinner) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new YourItemSelectedListener());


        listaUsers.setOnClickListener(this);


        return v;
    }
    public void onClick(View v) {

        switch (v.getId()) {

            case survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao4: {
                procura = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.procura);

                String procura1 = procura.getText().toString();
                if (procura1.matches(""))
                {
                    Toast.makeText(getContext(), "Preencha o campo a pesquisar, por favor.", Toast.LENGTH_SHORT).show();
                    //return;
                }
                else {
                    MarkerBD2 lista = new MarkerBD2();
                    lista.marcadoresMapa();

                }

            }
        }
    }

    public class YourItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selected = parent.getItemAtPosition(pos).toString();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Seleccionou: " + selected, Toast.LENGTH_LONG).show();
            campo = selected;
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }



    class MarkerBD2
    {

        String json_url;
        String JSON_STRING;


        public void marcadoresMapa()
        {
            procura = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.procura);
            txt1 = (TextView) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv1);

            String procura1 = procura.getText().toString();


            try {
                json_url = "http://survivinginporto.atwebpages.com/AndroidOTP/selectFiltro.php";
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("campo", "UTF-8") + "=" + URLEncoder.encode(campo, "UTF-8") + "&" +
                        URLEncoder.encode("procura1", "UTF-8") + "=" + URLEncoder.encode(procura1, "UTF-8");
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



                if(!(result.contains("username")))
                {
                    Toast.makeText(getContext(), "Sem resultados para a pesquisa efetuada.", Toast.LENGTH_SHORT).show();
                }

                JSONObject jsonObject = null;

                    jsonObject = new JSONObject(result);

                JSONArray jsonArray = null;

                    jsonArray = jsonObject.getJSONArray("result");

                txt1.setText("");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject JO = jsonArray.getJSONObject(i);

                    String username = JO.getString("username");
                    int phone = JO.getInt("phone");
                    String email = JO.getString("email");
                    String pais = JO.getString("pais");
                    String universidade = JO.getString("universidade");
                    String genero = JO.getString("genero");
                    String localidade = JO.getString("localidade");


                    txt1.append("Username: " + username + "\t\t\n" + "Telemóvel: " + phone + "\t\t\n" + "Email: " + email + "\t\t\n" + "País de Origem: " + pais + "\t\t\n" + "Universidade: " + universidade + "\t\t\n" + "Género: " + genero + "\t\t\n" + "Localidade: " + localidade + "\t\t\n" + "\n");


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
}
