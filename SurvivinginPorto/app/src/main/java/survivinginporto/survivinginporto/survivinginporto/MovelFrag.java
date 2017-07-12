package survivinginporto.survivinginporto.survivinginporto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user1 on 24/05/2016.
 */
public class MovelFrag extends Fragment
{


        String json_url;
        String JSON_STRING;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View v = inflater.inflate(survivinginporto.survivinginporto.survivinginporto.R.layout.movel_frag,container,false);


            return v;
        }


    }


