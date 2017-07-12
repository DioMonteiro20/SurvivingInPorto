package survivinginporto.survivinginporto.survivinginporto;

/**
 * Created by user1 on 19/06/2016.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;

public class Login extends Fragment implements View.OnClickListener {


    String json_url;
    String JSON_STRING;


    AlertDialog alertDialog;



    public static EditText ed1, ed2, ed3;
    Button b;
    Intent in;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";

    SharedPreferences sharedpreferences;

    private RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.login_frag, container, false);

        b = (Button) v.findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.button);
        b.setOnClickListener(this);

        return v;
    }


    public void onClick(View v) {
        ed1 = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editText);
        ed2 = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editText2);
        switch (v.getId()) {

            case survivinginporto.survivinginporto.survivinginporto.R.id.button: {
                String username = ed1.getText().toString().trim();
                String password = ed2.getText().toString().trim();
                if (username.matches("")|password.matches(""))
                {
                    Toast.makeText(getContext(), "Preencha o Username e a Password, por favor.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Login2 login = new Login2(getContext());

                    String n = ed1.getText().toString();
                    String ph = ed2.getText().toString();

                    login.execute(n, ph);


                }

            }
        }
    }

public void shared()
{
    sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
     SharedPreferences.Editor editor = sharedpreferences.edit();
    ed1 = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editText);
    ed2 = (EditText) getView().findViewById(survivinginporto.survivinginporto.survivinginporto.R.id.editText2);

    String n = ed1.getText().toString();
    String ph = ed2.getText().toString();

                editor.putString(Name, n);
                editor.putString(Phone, ph);
                editor.commit();

}

}



