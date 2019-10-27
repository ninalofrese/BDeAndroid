package br.com.digitalhouse.appmytasks.views.adapter;

import br.com.digitalhouse.appmytasks.model.Tarefa;

public interface RecyclerOnClick {
    void excluirTarefa(Tarefa tarefa);

    void completartarefa(Tarefa tarefa);
}
