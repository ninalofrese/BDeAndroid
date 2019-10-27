package br.com.digitalhouse.appmytasks.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.digitalhouse.appmytasks.model.Tarefa;
import br.com.digitalhouse.appmytasks.model.data.Database;
import br.com.digitalhouse.appmytasks.model.data.TarefaDao;
import br.com.digitalhouse.appmytasks.views.adapter.RecyclerOnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<List<Tarefa>> listaTarefas = new MutableLiveData<>();
    private TarefaDao tarefaDao = Database.getDatabase(getApplication()).tarefaDao();
    private CompositeDisposable disposable = new CompositeDisposable();

    //Construtor da classe que oferece contexto
    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Tarefa>> retornaListaTarefas() {
        return listaTarefas;
    }

    public void buscaTarefasRecentes() {
        disposable.add(
                tarefaDao.last5Tarefas()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tarefas -> {
                            //altera o valor da listaTarefas do tipo mutable
                            listaTarefas.setValue(tarefas);
                        }, throwable -> {
                            Log.wtf("LOG", "Busca tarefas recentes " + throwable.getMessage());
                        })
        );
    }

    public void deletarTarefa(Tarefa tarefa) {
        new Thread(() -> {
            if (tarefa != null) {
                tarefaDao.excluirTarefa(tarefa);
            }
        }).start();
    }

    public void concluirTarefa(Tarefa tarefa) {
        new Thread(() -> {
            if (tarefa != null) {
                tarefaDao.updateComplete(tarefa.getId(), tarefa.isComplete());
            }
        }).start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
