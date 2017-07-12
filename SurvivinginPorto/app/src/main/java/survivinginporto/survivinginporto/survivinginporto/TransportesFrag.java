package survivinginporto.survivinginporto.survivinginporto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by user1 on 27/05/2016.
 */
public class TransportesFrag extends Fragment
{

    TextView txt1;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.transp_frag, container, false);


        return v;

    }


}
