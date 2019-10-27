package br.com.digitalhouse.appmytasks.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.digitalhouse.appmytasks.R;
import br.com.digitalhouse.appmytasks.viewmodel.TodasTarefasFragmentViewModel;
import br.com.digitalhouse.appmytasks.views.adapter.RecyclerOnClick;
import br.com.digitalhouse.appmytasks.views.adapter.RecyclerViewTarefaAdapter;
import br.com.digitalhouse.appmytasks.model.data.Database;
import br.com.digitalhouse.appmytasks.model.data.TarefaDao;
import br.com.digitalhouse.appmytasks.model.Tarefa;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodasTarefasFragment extends Fragment implements RecyclerOnClick {
    private RecyclerView recyclerView;
    private RecyclerViewTarefaAdapter adapter;
    private List<Tarefa> tarefaList = new ArrayList<>();
    private TodasTarefasFragmentViewModel viewModel;

    public TodasTarefasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todas_tarefas, container, false);
        initViews(view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.buscaTodasTarefas();

        viewModel.retornaListaTarefas().observe(this, tarefas -> {
            adapter.atualizaLista(tarefas);
        });

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewTodasTarefas);
        adapter = new RecyclerViewTarefaAdapter(tarefaList, this);
        viewModel = ViewModelProviders.of(this).get(TodasTarefasFragmentViewModel.class);
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
