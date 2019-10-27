package br.com.digitalhouse.appmytasks.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.digitalhouse.appmytasks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalheFragment extends Fragment {
    private TextView detalheTarefa;

    public DetalheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhe, container, false);
        detalheTarefa = view.findViewById(R.id.detalhe_text);

        return view;
    }

}
