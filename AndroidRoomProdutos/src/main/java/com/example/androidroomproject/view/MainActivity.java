package com.example.androidroomproject.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidroomproject.R;
import com.example.androidroomproject.adapter.RecyclerViewAdapter;
import com.example.androidroomproject.data.Database;
import com.example.androidroomproject.data.ProdutoDao;
import com.example.androidroomproject.interfaces.OnClick;
import com.example.androidroomproject.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements OnClick {
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnDelete;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Produto> listaProdutos = new ArrayList<>();
    private TextInputLayout inputNomeProduto;
    private TextInputLayout inputPrecoProduto;
    private ProdutoDao produtoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(v -> {
            String nome = inputNomeProduto.getEditText().getText().toString();
            double preco = Double.parseDouble(inputPrecoProduto.getEditText().getText().toString());

            //Ele vai criar a nova thread para executar isso, e depois que terminar vai para a thread principal
            new Thread(() -> {
                Produto produto = new Produto(nome, preco);

                if (produto != null) {
                    //Implementar o insert no BD
                    produtoDao.insereProduto(produto);

                    //Limpa os inputs
                    inputNomeProduto.getEditText().setText(" ");
                    inputPrecoProduto.getEditText().setText(" ");

                    //atualiza lista de produtos
                    buscaTodosProdutos();
                }

            }).start();

            //Listar os dados salvos no bd
            buscaTodosProdutos();

        });

        btnDelete.setOnClickListener(v -> {
            String nome = inputNomeProduto.getEditText().getText().toString();

            new Thread(() -> {

                Produto produto = produtoDao.getByNome(nome);

                if (produto != null) {
                    produtoDao.deleteProduto(produto);
                } else {
                    Log.i("TAG", "Deleta produto");
                }

            }).start();

            inputNomeProduto.getEditText().setText(" ");
            inputPrecoProduto.getEditText().setText(" ");

            buscaTodosProdutos();

        });

    }

    private void initViews() {
        btnAdd = findViewById(R.id.floatingActionButtonAdd);
        btnDelete = findViewById(R.id.floatingActionButtonDelete);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(listaProdutos, this);
        inputNomeProduto = findViewById(R.id.textInputLayoutNome);
        inputPrecoProduto = findViewById(R.id.textInputLayoutPreco);
        produtoDao = Database.getDatabase(this).produtoDao();
    }

    @Override
    public void onClick(Produto produto) {
        inputNomeProduto.getEditText().setText(produto.getNomeProduto());
        String preco = String.valueOf(produto.getPreco());
        inputPrecoProduto.getEditText().setText(preco);
    }

    //Implementar o método de pesquisa dos dados

    private void buscaTodosProdutos() {
        produtoDao.getAllProdutos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(produtos -> {
                    adapter.atualizaListaProduto(produtos);
                }, throwable -> {
                    Log.i("TAG", "método getAllProdutos" + throwable.getMessage());
                });
    }

}
