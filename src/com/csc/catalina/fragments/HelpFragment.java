
package com.csc.catalina.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csc.catalina.R;

public class HelpFragment extends BaseFragment {

    private static final String LOG_TAG = HelpFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // askTitle = (EditText) view.findViewById(R.id.askTitle);
        super.onViewCreated(view, savedInstanceState);
    }

}
