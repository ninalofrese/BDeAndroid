package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Exercício 1
        //retorna as cores
        Observable<String> cores = Observable.create(emitter -> {
            emitter.onNext("azul");
            emitter.onNext("rosa");
            emitter.onNext("vermelho");
            emitter.onNext("branco");
            emitter.onNext("preto");
            emitter.onNext("verde");
            emitter.onNext("amarelo");
        });

        cores.subscribe(s -> {
            Log.wtf("CORES", s);
        });

        //Exercício 2
        //retorna os pares
        Observable.range(0, 100)
                .filter(integer -> integer % 2 == 0)
                .subscribeOn(Schedulers.io()) // subscribeOn, indica em que Thread será realizado o trabalho
                .observeOn(AndroidSchedulers.mainThread()) //observeOn. indica em Thread será mostrado os dados apos serem buscados/processados
                .subscribe(integer -> {
                    Log.wtf("PARES", integer.toString());
                });

        //Exercício 3
        //resultado é erro de divide by zero
        Observable.range(0, 100)
                .map(integer -> integer / (integer - 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    Log.wtf("DIVISAO", integer.toString());
                }, throwable -> {
                    Log.wtf("DIVISAO", "Erro reactivex " + throwable.getMessage());
                });

        //Teste de flowable
        //Ele despreza tudo entre 128 e 98999 - ou seja, guarda um buffer dos 1000 mais recentes
        Flowable.range(0, 100_000)
                .onBackpressureBuffer(
                        1000,
                        () -> {
                        }, BackpressureOverflowStrategy.DROP_OLDEST)
                .observeOn(Schedulers.computation())
                .subscribe(integer -> {
                    Log.wtf("FLOW", integer.toString());
                }, throwable -> {
                    Log.wtf("FLOW", "Erro " + throwable.getMessage());
                });
    }
}
