package survivinginporto.survivinginporto.survivinginporto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import android.app.AlertDialog;
import android.app.ProgressDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextNacional;
    private EditText editTextUniversidade;
    private EditText editTextGenero;
    private EditText editTextLocalidade;

    private EditText editTextConfirmOtp;
    EditText ed1, ed2;

    private AppCompatButton buttonRegister;
    private AppCompatButton buttonConfirm;


    //Volley RequestQueue
    private RequestQueue requestQueue;

    //String variables to hold username password and phone
    private String username;
    private String password;
    private String phone;
    private String email;
    private String nacionalidade;
    private String universidade;
    private String genero;
    private String localidade;



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Button bu = null;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(survivinginporto.survivinginporto.survivinginporto.R.layout.activity_main);


        //permite correr o https para envio de sms's
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Inicializa a toolbar e configura-a como actionbar
        toolbar = (Toolbar) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Inicializa a NavigationView
        navigationView = (NavigationView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.navigation_view);


        //Initializing Views
        editTextUsername = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);
        editTextPhone = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPhone);


        buttonRegister = (AppCompatButton) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.buttonRegister);

        //Initializing the RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sharedpreferences = getSharedPreferences(Login2.MyPREFERENCES, Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("nameKey", null);
        String password = sharedpreferences.getString("phoneKey", null);


        if (username != null && password != null) {

            Toast.makeText(getApplicationContext(), "Bem-vindo ao Surviving in Porto!", Toast.LENGTH_SHORT).show();
            Welcome welcome = new Welcome();
            android.support.v4.app.FragmentTransaction welcomeTransaction = getSupportFragmentManager().beginTransaction();
            welcomeTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, welcome);
            welcomeTransaction.commit();


        } else {

            DrawerLayout mDrawer = (DrawerLayout) MainActivity.this.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.drawer);
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            navigationView.setVisibility(View.GONE);

            Toast.makeText(getApplicationContext(), "Página de Login", Toast.LENGTH_SHORT).show();
            Login login = new Login();
            android.support.v4.app.FragmentTransaction loginTransaction = getSupportFragmentManager().beginTransaction();
            loginTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, login);
            loginTransaction.commit();
        }



        TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");

        mTitle.setTypeface(myFont);
        mTitle.setText("Surviving in Porto");

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.habitacoes:
                        Toast.makeText(getApplicationContext(), "Acedeu a Habitações e Hospedagem", Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, fragment);
                        fragmentTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Habitações e Hospedagem");
                        return true;

                    // For rest of the options we just show a toast on click

                    case survivinginporto.survivinginporto.survivinginporto.R.id.trans:
                        Toast.makeText(getApplicationContext(), "Acedeu a Transportes", Toast.LENGTH_SHORT).show();
                        TransportesFrag TransportesFrag = new TransportesFrag();
                        android.support.v4.app.FragmentTransaction TranspFragTransaction = getSupportFragmentManager().beginTransaction();
                        TranspFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, TransportesFrag);
                        TranspFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Transportes");


                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.guia:
                        Toast.makeText(getApplicationContext(), "Acedeu ao Guia da Cidade", Toast.LENGTH_SHORT).show();
                        GuiaFrag GuiaFrag = new GuiaFrag();
                        android.support.v4.app.FragmentTransaction GuiaFragTransaction = getSupportFragmentManager().beginTransaction();
                        GuiaFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, GuiaFrag);
                        GuiaFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Guia da Cidade");

                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.rest:
                        Toast.makeText(getApplicationContext(), "Acedeu a Restauração e Gastronomia", Toast.LENGTH_SHORT).show();
                        RestFrag RestFrag = new RestFrag();
                        android.support.v4.app.FragmentTransaction RestFragTransaction = getSupportFragmentManager().beginTransaction();
                        RestFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, RestFrag);
                        RestFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Restauração e Gastronomia");

                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.moveis:
                        Toast.makeText(getApplicationContext(), "Acedeu a Comunicações Móveis", Toast.LENGTH_SHORT).show();
                        MovelFrag MovelFrag = new MovelFrag();
                        android.support.v4.app.FragmentTransaction MovelFragTransaction = getSupportFragmentManager().beginTransaction();
                        MovelFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, MovelFrag);
                        MovelFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Comunicações Móveis");
                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.eventos:
                        Toast.makeText(getApplicationContext(), "Acedeu a Festas/Eventos", Toast.LENGTH_SHORT).show();
                        EventosFrag EventosFrag = new EventosFrag();
                        android.support.v4.app.FragmentTransaction EventosFragTransaction = getSupportFragmentManager().beginTransaction();
                        EventosFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, EventosFrag);
                        EventosFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Festas/Eventos");
                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.desc:
                        Toast.makeText(getApplicationContext(), "Acedeu a Cartão Erasmus/Descontos", Toast.LENGTH_SHORT).show();
                        DescontosFrag DescontosFrag = new DescontosFrag();
                        android.support.v4.app.FragmentTransaction DescontosFragTransaction = getSupportFragmentManager().beginTransaction();
                        DescontosFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, DescontosFrag);
                        DescontosFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Cartão Erasmus/Descontos");
                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.loc:

                        Toast.makeText(getApplicationContext(), "Acedeu a Localizar Utilizadores, certifique-se que tem a opção de localização ativa e preferencialmente os dados móveis, por favor!", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Acedeu a Localizar Utilizadores, certifique-se que tem a opção de localização ativa e preferencialmente os dados móveis, por favor!", Toast.LENGTH_LONG).show();
                        Intent locateusers = new Intent(getApplicationContext(),Locuser_frag.class);
                        startActivity(locateusers);


                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.list:
                        Toast.makeText(getApplicationContext(), "Acedeu a Listagem de Utilizadores", Toast.LENGTH_SHORT).show();

                        ListUserFrag ListUserFrag = new ListUserFrag();
                        android.support.v4.app.FragmentTransaction ListUserFragTransaction = getSupportFragmentManager().beginTransaction();
                        ListUserFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, ListUserFrag);
                        ListUserFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Utilizadores");
                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.perfil:
                        Toast.makeText(getApplicationContext(), "Acedeu ao Perfil", Toast.LENGTH_SHORT).show();
                        DadosFrag DadosFrag = new DadosFrag();
                        android.support.v4.app.FragmentTransaction DadosFragTransaction = getSupportFragmentManager().beginTransaction();
                        DadosFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, DadosFrag);
                        DadosFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Perfil");
                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.conta:
                        Toast.makeText(getApplicationContext(), "Acedeu à Conta", Toast.LENGTH_SHORT).show();
                        ContaFrag ContaFrag = new ContaFrag();
                        android.support.v4.app.FragmentTransaction ContaFragTransaction = getSupportFragmentManager().beginTransaction();
                        ContaFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, ContaFrag);
                        ContaFragTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Conta");
                        return true;
                    case survivinginporto.survivinginporto.survivinginporto.R.id.mensagem:
                        Toast.makeText(getApplicationContext(), "Acedeu a Enviar Mensagem", Toast.LENGTH_SHORT).show();
                        EnvioMensagem EnvioMensagem = new EnvioMensagem();
                        android.support.v4.app.FragmentTransaction EnvioMensagemTransaction = getSupportFragmentManager().beginTransaction();
                        EnvioMensagemTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, EnvioMensagem);
                        EnvioMensagemTransaction.commit();

                        mTitle.setTypeface(myFont);
                        mTitle.setText("Enviar mensagem..");
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Seleccione uma opção.", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, survivinginporto.survivinginporto.survivinginporto.R.string.openDrawer, survivinginporto.survivinginporto.survivinginporto.R.string.closeDrawer) {


            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void logout(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //

        getMenuInflater().inflate(survivinginporto.survivinginporto.survivinginporto.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
        SharedPreferences sharedpreferences = getSharedPreferences(Login2.MyPREFERENCES, Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("nameKey", null);
        String password = sharedpreferences.getString("phoneKey", null);

        if (username != null && password != null) {

            //noinspection SimplifiableIfStatement
            if (id == survivinginporto.survivinginporto.survivinginporto.R.id.action_settings) {


                Toast.makeText(getApplicationContext(), "Sobre Surviving in Porto", Toast.LENGTH_SHORT).show();
                Welcome welcome = new Welcome();
                android.support.v4.app.FragmentTransaction welcomeTransaction = getSupportFragmentManager().beginTransaction();
                welcomeTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, welcome);
                welcomeTransaction.commit();

                mTitle.setTypeface(myFont);
                mTitle.setText("Surviving in Porto");
                return true;
            }
            else if (id == survivinginporto.survivinginporto.survivinginporto.R.id.contactos) {
                mTitle.setTypeface(myFont);
                mTitle.setText("Contacte-nos");
                Toast.makeText(getApplicationContext(), "Contacte-nos", Toast.LENGTH_SHORT).show();
                ContactosFrag contactosFrag = new ContactosFrag();
                android.support.v4.app.FragmentTransaction contactosFragTransaction = getSupportFragmentManager().beginTransaction();
                contactosFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, contactosFrag);
                contactosFragTransaction.commit();
            }
        } else {

            if (id == survivinginporto.survivinginporto.survivinginporto.R.id.action_settings)
            {
                navigationView.setVisibility(View.GONE);
               // volt.findViewById(R.id.linkSignup10).setVisibility(View.GONE);


            Toast.makeText(getApplicationContext(), "Sobre Surviving in Porto", Toast.LENGTH_SHORT).show();

                WelcomenologFrag WelcomenologFrag = new WelcomenologFrag();
                android.support.v4.app.FragmentTransaction WelcomenologFragTransaction = getSupportFragmentManager().beginTransaction();
                WelcomenologFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, WelcomenologFrag);
                WelcomenologFragTransaction.commit();

                mTitle.setTypeface(myFont);
            mTitle.setText("Surviving in Porto");
            return true;
        }
            else if (id == survivinginporto.survivinginporto.survivinginporto.R.id.contactos) {
                navigationView.setVisibility(View.GONE);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                mTitle.setTypeface(myFont);
                mTitle.setText("Contacte-nos");
                Toast.makeText(getApplicationContext(), "Contacte-nos", Toast.LENGTH_SHORT).show();
                CtcnologFrag CtcnologFrag = new CtcnologFrag();
                android.support.v4.app.FragmentTransaction CtcnologFragTransaction = getSupportFragmentManager().beginTransaction();
                CtcnologFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, CtcnologFrag);
                CtcnologFragTransaction.commit();


                navigationView.setVisibility(View.GONE);
            }

    }

        return super.onOptionsItemSelected(item);
    }

    public void loginWel(View v)
    {
        TextView txt28 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup10);
        navigationView.setVisibility(View.GONE);
        txt28.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Página de Login", Toast.LENGTH_SHORT).show();
                Login login = new Login();
                android.support.v4.app.FragmentTransaction loginTransaction = getSupportFragmentManager().beginTransaction();
                loginTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, login);
                loginTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Login");

            }
        });

    }
    public void loginCon(View v)
    {
        TextView txt27 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup14);
        navigationView.setVisibility(View.GONE);
        txt27.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Consume input from header view. This disables the unwanted ripple effect.
                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Página de Login", Toast.LENGTH_SHORT).show();
                Login login = new Login();
                android.support.v4.app.FragmentTransaction loginTransaction = getSupportFragmentManager().beginTransaction();
                loginTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, login);
                loginTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Login");

            }
        });

    }
    public void loginRecup(View v)
    {
        TextView txt30 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup13);
        navigationView.setVisibility(View.GONE);
        txt30.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Consume input from header view. This disables the unwanted ripple effect.
                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Página de Login", Toast.LENGTH_SHORT).show();
                Login login = new Login();
                android.support.v4.app.FragmentTransaction loginTransaction = getSupportFragmentManager().beginTransaction();
                loginTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, login);
                loginTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Login");

            }
        });

    }
    public void loginVal(View v)
    {
        TextView txt31 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup14);
        navigationView.setVisibility(View.GONE);
        txt31.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Consume input from header view. This disables the unwanted ripple effect.
                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Página de Login", Toast.LENGTH_SHORT).show();
                Login login = new Login();
                android.support.v4.app.FragmentTransaction loginTransaction = getSupportFragmentManager().beginTransaction();
                loginTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, login);
                loginTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Login");

            }
        });

    }
    public void play(View v) {
        TextView txt4 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv4);
        txt4.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.moveme"));
                startActivity(browserIntent);
            }


        });
    }

    public void move(View v) {
        TextView txt3 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv3);
        txt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.moveme");
                startActivity(LaunchIntent);

            }
        });
    }

    public void maisInfo(View v) {
        Button button = (Button) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv5);
                ImageView trans = (ImageView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.imageView2);
                tv.setVisibility(View.VISIBLE);
                trans.setVisibility(View.VISIBLE);
            }
        });
    }

    //@Override

    public void registerButton(View v) {

        //Calling register method on register button click
        navigationView.setVisibility(View.GONE);
        editTextUsername = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);
        editTextPhone = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPhone);
        editTextEmail = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextEmail);
        editTextNacional = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextNacional);
        editTextUniversidade = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUniversidade);
        editTextGenero = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextGenero);
        editTextLocalidade = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextLocalidade);

        //Getting user data
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        nacionalidade = editTextNacional.getText().toString().trim();
        universidade = editTextUniversidade.getText().toString().trim();
        genero = editTextGenero.getText().toString().trim();
        localidade = editTextLocalidade.getText().toString().trim();
        Button button = (Button) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (username.matches("")|password.matches("")|phone.matches("")|genero.matches(""))
                {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos assinalados como obrigatórios (*)", Toast.LENGTH_SHORT).show();
                    //return;
                }else if(phone.length()<9)
                {
                    Toast.makeText(MainActivity.this, "O contacto tem que ter 9 algarismos, volte a introduzir, por favor.", Toast.LENGTH_SHORT).show();
                }
                else {
                    register();

                }
            }
        });
    }

    public void abreRegisto(View v) {
        //Calling register method on register button click

        TextView txt3 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup);
        navigationView.setVisibility(View.GONE);
        txt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Consume input from header view. This disables the unwanted ripple effect.
                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Página de Registo", Toast.LENGTH_SHORT).show();
                Registo registo = new Registo();
                android.support.v4.app.FragmentTransaction registoTransaction = getSupportFragmentManager().beginTransaction();
                registoTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, registo);
                registoTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Registo");

            }
        });
    }
    public void abreRecup(View v) {
        //Calling register method on register button click

        TextView txt15 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup2);
        navigationView.setVisibility(View.GONE);
        txt15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Consume input from header view. This disables the unwanted ripple effect.
                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Insira, por favor, o número móvel registado.", Toast.LENGTH_SHORT).show();
                RecupPass RecupPass = new RecupPass();
                android.support.v4.app.FragmentTransaction RecupPassTransaction = getSupportFragmentManager().beginTransaction();
                RecupPassTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, RecupPass);
                RecupPassTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Recuperação de Password");
            }
        });
    }
    public void validaConta(View v) {
        //Calling register method on register button click

        TextView txt15 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup3);
        navigationView.setVisibility(View.GONE);
        txt15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Consume input from header view. This disables the unwanted ripple effect.
                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Insira, por favor, os seguintes dados.", Toast.LENGTH_SHORT).show();
                ValidaConta ValidaConta = new ValidaConta();
                android.support.v4.app.FragmentTransaction ValidaContaTransaction = getSupportFragmentManager().beginTransaction();
                ValidaContaTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, ValidaConta);
                ValidaContaTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Validação de Conta");

            }
        });
    }
    public void login(View v)
    {
        TextView txt16 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.linkSignup6);
        navigationView.setVisibility(View.GONE);
        txt16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                navigationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Consume input from header view. This disables the unwanted ripple effect.
                    }});
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Página de Login", Toast.LENGTH_SHORT).show();
                Login login = new Login();
                android.support.v4.app.FragmentTransaction loginTransaction = getSupportFragmentManager().beginTransaction();
                loginTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, login);
                loginTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Login");
            }
        });

    }
    public void termos(View v) {
        TextView txt5 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv5);
        txt5.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v)
            {
                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                Toast.makeText(getApplicationContext(), "Página de Termos e Políticas", Toast.LENGTH_SHORT).show();
                TermosFrag termosFrag = new TermosFrag();
                android.support.v4.app.FragmentTransaction termosFragTransaction = getSupportFragmentManager().beginTransaction();
                termosFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, termosFrag);
                termosFragTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Termos e Políticas");
            }


        });
    }
    public void termosRegisto(View v) {
        TextView txt5 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv5);

        txt5.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v)
            {

                TextView mTitle = (TextView) toolbar.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.toolbar_title);
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/SaginawBold.ttf");
                navigationView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Página de Termos e Políticas", Toast.LENGTH_SHORT).show();
                TermosnologFrag TermosnologFrag = new TermosnologFrag();
                android.support.v4.app.FragmentTransaction TermosnologFragTransaction = getSupportFragmentManager().beginTransaction();
                TermosnologFragTransaction.replace(survivinginporto.survivinginporto.survivinginporto.R.id.frame, TermosnologFrag);
                TermosnologFragTransaction.commit();
                mTitle.setTypeface(myFont);
                mTitle.setText("Termos e Políticas");
            }


        });
    }
    public void filtro(View v) {

        Button button = (Button) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao4);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new ListUserFrag();

            }
        });
    }

    public void cartao(View v) {

        TextView txt8 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv8);
        txt8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.esnporto.org/old/content/esn-card-registration"));
                startActivity(browserIntent);

            }
        });
    }
    public void descontos(View v) {

        TextView txt9 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv9);
        txt9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://esncard.org/discover?f[0]=field_discount_location%3A2203"));
                startActivity(browserIntent);

            }
        });
    }
    public void sitErasmus(View v) {

        TextView txt9 = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv10);
        txt9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://erasmus.wtf.pt/"));
                startActivity(browserIntent);

            }
        });
    }

    public void maisInfo2(View v) {
        Button button = (Button) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.nomeDoBotao20);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.txtv2);
                tv.setVisibility(View.VISIBLE);

            }
        });
    }


    //REGISTA OS DADOS INTRODUZIDOS NO LOGIN PELO UTILIZADOR E INVOCA O SENDOTP() E DEPOIS O CONFIRMOTP()
    //this method will register the user
    private void register() {

        //Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Registando", "Por favor aguarde...", false, false);

        editTextUsername = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);
        editTextPhone = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPhone);
        editTextEmail = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextEmail);
        editTextNacional = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextNacional);
        editTextUniversidade = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUniversidade);
        editTextGenero = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextGenero);
        editTextLocalidade = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextLocalidade);

        final int otp;

        //Getting user data
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        nacionalidade = editTextNacional.getText().toString().trim();
        universidade = editTextUniversidade.getText().toString().trim();
        genero = editTextGenero.getText().toString().trim();
        localidade = editTextLocalidade.getText().toString().trim();

        Random random = new Random();

        otp = random.nextInt(900000) + 100000;


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();

                            try {
                                //Creating the json object from the response
                                JSONObject jsonResponse = new JSONObject(response);

                                //If it is success
                                if (jsonResponse.getString(Config.TAG_RESPONSE).equalsIgnoreCase("Success")) {
                                    //Asking user to confirm otp
                                    sendOtp(phone, otp);
                                    confirmOtp();

                                } else {
                                    //If not successful user may already have registered
                                    Toast.makeText(MainActivity.this, "Username ou Número de Telemóvel já registado.", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding the parameters to the request
                    params.put(Config.KEY_USERNAME, username);
                    params.put(Config.KEY_PASSWORD, password);
                    params.put(Config.KEY_PHONE, phone);
                    params.put(Config.KEY_OTP, String.valueOf(otp));
                    params.put(Config.KEY_EMAIL, email);
                    params.put(Config.KEY_NACIONAL, nacionalidade);
                    params.put(Config.KEY_UNIVERSIDADE, universidade);
                    params.put(Config.KEY_GENERO, genero);
                    params.put(Config.KEY_LOCALIDADE, localidade);
                    return params;
                }
            };

            //Adding request the the queue
            requestQueue.add(stringRequest);

        }

    //CONFIRMA QUE O OTP INTRODUZIDO É IGUAL AO QUE EXISTE NO REGISTO DO UTILIZADOR NA BD E COLOCA-O COMO VERIFICADO.
    //This method would confirm the otp
    private void confirmOtp() throws JSONException {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.confregisto_frag, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextOtp);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        //Dialog dialog = new Dialog(context)
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


        //On the click of the confirm button from alert dialog
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                alertDialog.dismiss();

                //Displaying a progressbar
                final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "A autenticar..", "Por favor aguarde enquanto confirmamos o código introduzido", false, false);

                //Getting the user entered otp from edittext
                final String otp = editTextConfirmOtp.getText().toString().trim();

                //Creating an string request
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CONFIRM_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //if the server response is success
                                if (response.equalsIgnoreCase("success")) {
                                    //dismissing the progressbar
                                    loading.dismiss();

                                    String MyPREFERENCES = "MyPrefs";
                                    String Name = "nameKey";
                                    String Phone = "phoneKey";
                                    ed1= (EditText) Registo.ed1;
                                    ed2= (EditText) Registo.ed2;
                                    ed1 = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextUsername);
                                    ed2 = (EditText) findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editTextPassword);

                                    SharedPreferences sharedpreferences = MainActivity.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                    String n = ed1.getText().toString();
                                    String ph = ed2.getText().toString();
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString(Name, n);
                                    editor.putString(Phone, ph);
                                    // editor.putString(Email, e);
                                    editor.commit();

                                    //Starting a new activity
                                    startActivity(new Intent(MainActivity.this, RegistoSucesso.class));
                                    finish();
                                } else {
                                    //Displaying a toast if the otp entered is wrong
                                    Toast.makeText(MainActivity.this, "Código de confirmação incorrecto, por favor, volte a introduzir.", Toast.LENGTH_LONG).show();
                                    try {
                                        //Asking user to enter otp again
                                        confirmOtp();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        //Adding the parameters otp and username
                        params.put(Config.KEY_OTP, otp);
                        params.put(Config.KEY_USERNAME, username);
                        return params;
                    }
                };

                //Adding the request to the queue
                requestQueue.add(stringRequest);
            }

        });

    }

     private void sendOtp(String numero, int cod)
    {
        //Your authentication key
        String authkey = "jgHVgYfofoXRhjr6Ts6D";
//Password
        String pwd = "survporto";
//Multiple mobiles numbers separated by comma
        String mobiles = numero;
//Your message to send, Add URL encoding here.
        String message = "Bem-vindo ao Surviving in Porto! O seu codigo de confirmacao e: " + cod;
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