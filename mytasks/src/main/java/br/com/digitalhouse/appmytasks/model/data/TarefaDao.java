package br.com.digitalhouse.appmytasks.model.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.digitalhouse.appmytasks.model.Tarefa;
import io.reactivex.Observable;

@Dao
public interface TarefaDao {

    @Insert
    void insereTarefa(Tarefa tarefa);

    @Update
    void updateTarefa(Tarefa tarefa);

    @Delete
    void excluirTarefa(Tarefa tarefa);

    @Query("SELECT * FROM tarefas")
    Observable<List<Tarefa>> getAllTarefas();

    @Query("SELECT * FROM tarefas ORDER BY id DESC LIMIT 5")
    Observable<List<Tarefa>> last5Tarefas();

    @Query("DELETE FROM tarefas")
    public void nukeTable();

    @Query("UPDATE tarefas SET complete = :complete WHERE id = :id")
    void updateComplete(long id, boolean complete);

}
