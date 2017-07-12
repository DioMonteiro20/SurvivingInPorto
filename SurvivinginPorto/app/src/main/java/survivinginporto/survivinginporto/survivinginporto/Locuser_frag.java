package survivinginporto.survivinginporto.survivinginporto;

/**
 * Created by user1 on 25/06/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
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
import java.net.URLEncoder;


public class Locuser_frag extends FragmentActivity implements OnMapReadyCallback,ActivityCompat.OnRequestPermissionsResultCallback {
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(survivinginporto.survivinginporto.survivinginporto.R.layout.locuser_frag);
        toolbar = (Toolbar) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(survivinginporto.survivinginporto.survivinginporto.R.id.map);
        mapFragment.getMapAsync(this);
        toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.imageView1).setVisibility(View.VISIBLE);
        toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.imageView10).setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");

        mTitle.setTypeface(myFont);
        mTitle.setText("Localizar..");
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        final LatLng PORTO = new LatLng(41.148017, -8.611103);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(PORTO));

        //posiçao da camara

        CameraPosition position = new CameraPosition.Builder().target(PORTO).zoom(16).build();

        //animar a camara para a posicao definida

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));


        mMap.setMyLocationEnabled(true);


        mMap.clear();
        MarkerBD carregaLocal = new MarkerBD();
        carregaLocal.marcadoresMapa();
        System.out.println("CARREGA LOCALIZAÇOES");






        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);

    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(final Location location) {



            SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
            String username2 = sharedpreferences.getString("nameKey", null);
            System.out.println("SHAREDPREFS OK: " + username2+":::::::::::::::::::::::::::::::::::::::::::::::");


            SelectporID selectporID = new SelectporID();

            int userid2;

            userid2=selectporID.select(username2);
            System.out.println("USER ID = "+userid2+":::::::::::::::::::::::::::::::::::::::::::::::::::::::");

            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            String userid = toString().valueOf(userid2);

            //DELETE NA BD - com envio de id
            DeleteBDUSERS deleteBDUSERS = new DeleteBDUSERS();
            deleteBDUSERS.deleteuser(userid);


            //INSERT NA BD - com envio das coordenadas
            InsertBDUSERS insertBDUSERS = new InsertBDUSERS();
            insertBDUSERS.insertuser(latitude, longitude, userid);

            //INSERT NA BD que recebe todas as Localizações
            InsertAllLoc insertAllLoc = new InsertAllLoc();
            insertAllLoc.insereTodasLoc(latitude,longitude,userid);

            mMap.clear();
            MarkerBD carregaLocal = new MarkerBD();
            carregaLocal.marcadoresMapa();


        }

        ;

    };



    public void refresh(View v)
    {

        ImageView imageView = (ImageView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.imageView10);

        imageView.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {

                mMap.clear();
                MarkerBD carregaLocal = new MarkerBD();
                carregaLocal.marcadoresMapa();
                System.out.println("CARREGA LOCALIZAÇOES");
            }


        });
    }

    public void voltarRest(View v) {

        ImageView imageView = (ImageView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.imageView1);

        imageView.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                finish();

            }


        });
    }

    //CARREGAR OS LOCAIS DA BD --> MAPA
    class MarkerBD
    {

        String json_url;
        String JSON_STRING;


        // FAZ O SELECT DA BD E MANDA PARA O MAPA OS MARCADORES COM OS USERS


        public void marcadoresMapa() {
            try {

                json_url = "http://survivinginporto.atwebpages.com/MARCADORSELECT.php";

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



                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(dados);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("dados");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject JO = jsonArray.getJSONObject(i);

                    String username = JO.getString("username");
                    int numerotel = JO.getInt("phone");
                    String email = JO.getString("email");
                    String pais = JO.getString("pais");
                    String universidade = JO.getString("universidade");
                    String genero = JO.getString("genero");
                    String localidade = JO.getString("localidade");
                    String latitude = JO.getString("latitude");
                    String longitude = JO.getString("longitude");
                    int horaMarker = JO.getInt("hora");
                    int minMarker = JO.getInt("min");
                    int userid = JO.getInt("userid");


                    RecebeHoraServer recebeHoraServer = new RecebeHoraServer();
                    recebeHoraServer.horaMin();
                    int horaCorrente = recebeHoraServer.getHoraCorrente();
                    int minCorrente = recebeHoraServer.getMinCorrente();
                    System.out.println("HORA CORRENTE: "+horaCorrente+":::::::::::::::::::::::::::::::::::::::::::::");
                    System.out.println("MIN CORRENTE: "+minCorrente+":::::::::::::::::::::::::::::::::::::::::::::");
                    int difHora = horaCorrente - horaMarker;
                    int difMin = minCorrente - minMarker;


                    Double lat;
                    Double longt;

                    lat = Double.parseDouble(latitude);
                    longt = Double.parseDouble(longitude);
                    String marcador = username+" ("+genero+")"+" "+pais+" "+"há "+difMin+"min";



                    String descricao = localidade+" univ: "+
                            universidade+" "+" Num: "+
                            numerotel;



                    final LatLng user = new LatLng(lat, longt);

                    if (difHora == 0 && difMin >= 0)
                    {
                        //horas e minutos do servidor e marcador recém-adicionado sao iguais (diferem por segundos)

                        //hora igual mas minuto corrente maior que minuto do marcador, condiçao para os marcadores previamente adicionados
                        //que estao a ser carregados
                        mMap.addMarker(new MarkerOptions().position(user).title(marcador).snippet(descricao));
                    }



                    if (difHora == 1 && difMin < 0)
                    {
                        //por exemplo: horacorrente: 2h01min e horaMarcador: 1h05min , o resultado da diferença é
                        //no caso das horas 1h e no caso dos minutos -4 min, ou seja, ainda nao passou uma hora completa
                        // apesar de ja ter incrementado 1h, o que significa que o marcador será gerado na mesma.


                        int minRestantes = 60 + (difMin); //neste caso os minutos restantes sao 60 - 4 = 56.
                        System.out.println("MIN RESTANTES É  60"+difMin+" = "+minRestantes+" ::::::::::::::::::::::::::::::::::::::::::");
                        String marcadorCondicao = username+" ("+genero+")"+" "+pais+" "+"há "+minRestantes+"min";

                        mMap.addMarker(new MarkerOptions().position(user).title(marcadorCondicao).snippet(descricao));
                    }



                    if(difHora>=1 && difMin>=0 || difHora<0)
                    {
                        //caso a diferença de hora for maior que 1 E a diferença de minutos maior ou igual a 0,
                        //significa que ja passou 1h, logo o marcador será apagado da base de dados
                        //OU caso a diferença de hora seja menor que 0, significa que ja passou um dia = marcador apagado
                        String idUtilizador = toString().valueOf(userid);
                        DeleteBDUSERS deleteMarcador = new DeleteBDUSERS();
                        deleteMarcador.deleteuser(idUtilizador);
                    }
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





//:::::::::::::::::::::::::::::://::::::::::::::::::::::::::::::::::::::::::::::::::::





    public class SelectporID
    {

        String json_url;
        String JSON_STRING;

        int id;


        public int select(String... params) {
            String login_url = "http://survivinginporto.atwebpages.com/SelectbyID.php";


            System.out.println("1");
            try {
                System.out.println("INICIA SELECT POR ID::::::::::::::::::::::::::::::::::::::::::");
                String username = params[0];


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
                System.out.println("2");




                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("id2");


                for (int i = 0; i < jsonArray.length(); i++) {

                    System.out.println("4");
                    JSONObject JO = jsonArray.getJSONObject(i);


                    id=JO.getInt("id");


                    System.out.println("ID = " + id + "::::::::::::::::::::::::::::::::::::::::::::::::::");
                }


                System.out.println("FIM DO SELECT POR ID::::::::::::::::::::::::::::::::::::::::::");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return id;
        }

    }



}