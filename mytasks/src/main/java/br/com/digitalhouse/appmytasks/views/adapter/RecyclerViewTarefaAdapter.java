package br.com.digitalhouse.appmytasks.views.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import br.com.digitalhouse.appmytasks.R;
import br.com.digitalhouse.appmytasks.model.Tarefa;

public class RecyclerViewTarefaAdapter extends RecyclerView.Adapter<RecyclerViewTarefaAdapter.ViewHolder> {
    private List<Tarefa> tarefaList;
    private RecyclerOnClick listener;

    public RecyclerViewTarefaAdapter(List<Tarefa> tarefaList, RecyclerOnClick listener) {
        this.tarefaList = tarefaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_tarefas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = tarefaList.get(position);
        holder.onBind(tarefa);

        holder.deleteButton.setOnClickListener(view -> {
            if (listener != null) {
                listener.excluirTarefa(tarefa);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tarefaList.size();
    }

    public void atualizaLista(List<Tarefa> tarefas) {
        tarefaList.clear();
        tarefaList = tarefas;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nome;
        private TextView detalheText;
        boolean open;
        private ToggleButton toggleButton;
        private ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textViewNomeTarefa);
            detalheText = itemView.findViewById(R.id.detalhe_text);
            open = false;
            toggleButton = itemView.findViewById(R.id.complete_button);
            deleteButton = itemView.findViewById(R.id.delete_task);
        }

        public void onBind(Tarefa tarefa) {
            nome.setText(tarefa.getNome());
            detalheText.setText(tarefa.getDescricao());

            Drawable complete = itemView.getResources().getDrawable(R.drawable.ic_check_circle);
            Drawable notCompleted = itemView.getResources().getDrawable(R.drawable.ic_unchecked_circle);

            detalheText.setVisibility(View.GONE);
            open = false;

            itemView.setOnClickListener(view -> {
                if (open) {
                    detalheText.setVisibility(View.GONE);
                    open = false;
                } else {
                    detalheText.setVisibility(View.VISIBLE);
                    open = true;
                }

            });

            if (tarefa.isComplete()) {
                toggleButton.setChecked(true);
                toggleButton.setBackgroundDrawable(complete);
            } else {
                toggleButton.setChecked(false);
                toggleButton.setBackgroundDrawable(notCompleted);
            }

            toggleButton.setOnCheckedChangeListener((compoundButton, b) -> {
                if (toggleButton.isChecked()) {
                    toggleButton.setBackgroundDrawable(complete);
                    tarefa.setComplete(true);
                    listener.completartarefa(tarefa);
                    Snackbar.make(compoundButton, "Tarefa completada", Snackbar.LENGTH_SHORT).show();
                } else {
                    toggleButton.setBackgroundDrawable(notCompleted);
                    tarefa.setComplete(false);
                    listener.completartarefa(tarefa);
                }
            });
        }
    }
}
