package survivinginporto.survivinginporto.survivinginporto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * Created by user1 on 14/06/2016.
 */


public class Registo extends Fragment {
    public static EditText ed1, ed2, ed3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.registo_frag,container,false);


        return v;
    }

}
