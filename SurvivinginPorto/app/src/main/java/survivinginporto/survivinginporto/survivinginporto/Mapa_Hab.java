package survivinginporto.survivinginporto.survivinginporto;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class Mapa_Hab extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(survivinginporto.survivinginporto.survivinginporto.R.layout.mapa_hab);
        toolbar = (Toolbar) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar);


        TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
        mTitle.setTypeface(myFont);
        mTitle.setText("Habitações");
        toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.imageView1).setVisibility(View.VISIBLE);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(survivinginporto.survivinginporto.survivinginporto.R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        final LatLng PORTO = new LatLng(41.148017, -8.611103);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(PORTO));


        CameraPosition position = new CameraPosition.Builder().target(PORTO).zoom(16).build();

        //animar a camara para a posicao definida

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

        //ativa a localizaçao atual

        googleMap.setMyLocationEnabled(true);


        MarkerBD8 carregaLocal8 = new MarkerBD8();
        carregaLocal8.marcadoresMapa();

    }

    //CARREGAR OS LOCAIS DA BD --> MAPA
    class MarkerBD8
    {

        String json_url;
        String JSON_STRING;


        public void marcadoresMapa() {
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


                httpURLConnection.disconnect();
                String habitacoes = stringBuilder.toString().trim();


                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(habitacoes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("habitacoes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject JO = jsonArray.getJSONObject(i);

                    int id = JO.getInt("id");

                    //get an output on the screen

                    String tipo = null;
                    tipo=JO.getString("tipo");

                    String morada = null;
                    morada=JO.getString("morada");

                    String descricao= null;
                    descricao=JO.getString("descricao");

                    String custo = null;
                    custo=JO.getString("custo");


                    String responsavel = null;
                    responsavel=JO.getString("responsavel");
                    String contacto = null;
                    contacto =JO.getString("contacto");

                    String latitude = null;

                    latitude = JO.getString("latitude");

                    String longitude = null;

                    longitude = JO.getString("longitude");




                    Double lat;
                    Double longt;

                    lat = Double.parseDouble(latitude);
                    longt = Double.parseDouble(longitude);
                    String marcador = toString().valueOf(tipo+" Valor: "+custo+"€");
                    String descricao1 = "Responsável: "+responsavel+" Contacto: "+contacto;

                    final LatLng rest = new LatLng(lat, longt);

                    mMap.addMarker(new MarkerOptions().position(rest).title(marcador).snippet(descricao1));


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

    public void voltarRest(View v) {

        ImageView imageView = (ImageView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.imageView1);

        imageView.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                finish();

            }


        });
    }


}

