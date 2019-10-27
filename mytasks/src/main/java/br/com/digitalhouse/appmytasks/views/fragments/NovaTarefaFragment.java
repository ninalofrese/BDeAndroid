package br.com.digitalhouse.appmytasks.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import br.com.digitalhouse.appmytasks.R;
import br.com.digitalhouse.appmytasks.model.data.Database;
import br.com.digitalhouse.appmytasks.model.data.TarefaDao;
import br.com.digitalhouse.appmytasks.model.Tarefa;
import br.com.digitalhouse.appmytasks.viewmodel.NovaTarefaFragmentModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NovaTarefaFragment extends Fragment {
    private TextInputLayout nome;
    private TextInputLayout descricao;
    private FloatingActionButton btnAdd;
    private NovaTarefaFragmentModel viewModel;

    public NovaTarefaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nova_tarefa, container, false);

        initViews(view);

        btnAdd.setOnClickListener(v -> {
            String nomeTarefa = nome.getEditText().getText().toString();
            String descricaoTarefa = descricao.getEditText().getText().toString();

            if (!nomeTarefa.isEmpty() && !descricaoTarefa.isEmpty()) {
                nome.setErrorEnabled(false);
                descricao.setErrorEnabled(false);

                Tarefa novaTarefa = new Tarefa(nomeTarefa, descricaoTarefa, false);

                viewModel.insereTarefaBancoDados(novaTarefa);

                nome.getEditText().setText("");
                descricao.getEditText().setText("");

                Snackbar.make(view, "Tarefa adicionada com sucesso", Snackbar.LENGTH_SHORT).show();
                nome.requestFocus();
            } else {
                nome.setError("Insira um nome para a tarefa");
                descricao.setError("Insira uma descrição para a tarefa");
            }

        });

        return view;
    }

    private void initViews(View view) {
        nome = view.findViewById(R.id.textInpuLayoutNome);
        descricao = view.findViewById(R.id.textInputLayoutDescricao);
        btnAdd = view.findViewById(R.id.floatingActionButtonSalvar);
        viewModel = ViewModelProviders.of(this).get(NovaTarefaFragmentModel.class);
    }


}
