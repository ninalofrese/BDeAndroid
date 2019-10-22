package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

       Observable.range(0,100)
               .filter(integer -> integer % 2 == 0)
               .subscribeOn(Schedulers.io()) // subscribeOn, indica em que Thread será realizado o trabalho
               .observeOn(AndroidSchedulers.mainThread()) //observeOn. indica em Thread será mostrado os dados apos serem buscados/processados
               .subscribe(integer -> {
                  Log.wtf("PARES", integer.toString());
               });

    }
}
