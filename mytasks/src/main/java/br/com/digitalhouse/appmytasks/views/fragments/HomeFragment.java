package br.com.digitalhouse.appmytasks.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.digitalhouse.appmytasks.R;
import br.com.digitalhouse.appmytasks.model.Tarefa;
import br.com.digitalhouse.appmytasks.viewmodel.HomeFragmentViewModel;
import br.com.digitalhouse.appmytasks.views.adapter.RecyclerOnClick;
import br.com.digitalhouse.appmytasks.views.adapter.RecyclerViewTarefaAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements RecyclerOnClick {
    private RecyclerView recyclerView;
    private RecyclerViewTarefaAdapter adapter;
    private List<Tarefa> tarefaList = new ArrayList<>();
    private HomeFragmentViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.buscaTarefasRecentes();

        viewModel.retornaListaTarefas().observe(this, tarefas -> {
            adapter.atualizaLista(tarefas);
        });

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewUltimasTarefas);
        adapter = new RecyclerViewTarefaAdapter(tarefaList, this);
        viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
    }

    @Override
    public void excluirTarefa(Tarefa tarefa) {
        viewModel.deletarTarefa(tarefa);
    }

    @Override
    public void completartarefa(Tarefa tarefa) {
        viewModel.concluirTarefa(tarefa);
    }
}
