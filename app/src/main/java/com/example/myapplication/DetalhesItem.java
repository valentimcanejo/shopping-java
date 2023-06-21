package com.example.myapplication;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.TextView;

public class DetalhesItem extends AppCompatActivity {
    public static final String NOME_PRODUTO = "nome_produto";
    public static final String PRECO_PRODUTO = "preco_produto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView nomeProdutoTextView = findViewById(R.id.nomeProdutoTextView);
        TextView precoProdutoTextView = findViewById(R.id.precoProdutoTextView);

        String nomeProduto = getIntent().getStringExtra(NOME_PRODUTO);
        String precoProduto = getIntent().getStringExtra(PRECO_PRODUTO);
        nomeProdutoTextView.setText(nomeProduto);
        precoProdutoTextView.setText(precoProduto);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}