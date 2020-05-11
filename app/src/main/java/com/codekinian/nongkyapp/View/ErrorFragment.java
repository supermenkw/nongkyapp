package com.codekinian.nongkyapp.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codekinian.nongkyapp.R;
import com.codekinian.nongkyapp.View.Main.MainFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorFragment extends Fragment {
    private Button btnRefresh;

    public ErrorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.error_layout, container, false);
        btnRefresh = (Button) view.findViewById(R.id.load_refresh);

        btnRefresh.setOnClickListener(view1 -> {
            Fragment fragment = null;
            Class fragmentClass = MainFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        });
        return view;
    }

}
