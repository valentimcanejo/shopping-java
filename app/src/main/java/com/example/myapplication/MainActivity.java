package com.example.myapplication;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private GerenciadorProdutos gerenciadorTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Button verProdutosButton = findViewById(R.id.verProdutosButton);
        gerenciadorTarefas = GerenciadorProdutos.getInstance();

        gerenciadorTarefas.adicionarItens();
        verProdutosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Carrinho.class));
            }
        });
    }
}