package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView;
import android.widget.TextView;

public class Carrinho extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private GerenciadorProdutos gerenciadosProdutos;
    private List<Produto> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrinho);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = findViewById(R.id.listaProdutosCarrinho);
        gerenciadosProdutos = GerenciadorProdutos.getInstance();
        //List<String> listaProdutos = gerenciadosProdutos.getListaNomesCarrinho();
        BD bd = new BD(getBaseContext());
        //banco = new BD(getBaseContext());

        lista = bd.getProdutos();
        List<String> listaProdutos = new ArrayList<>();
        for (Produto produto : lista) {
            listaProdutos.add(produto.getNome());

        }

        adapter = new ArrayAdapter<String>(this, R.layout.item_carrinho, R.id.textViewNomeProduto, listaProdutos) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);

                //String nomeProduto = listaProdutos.get(position);
                String nomeProduto = lista.get(position).getNome();
                String precoProduto = lista.get(position).getPreco();
                System.out.println(position);
                ImageView img = itemView.findViewById(R.id.imageViewAttachment);
                switch (nomeProduto) {
                    case "Processador":
                        img.setImageDrawable(itemView.getContext().getDrawable(R.drawable.processador));
                        break;
                    case "Placa de Vídeo":
                        img.setImageDrawable(itemView.getContext().getDrawable(R.drawable.placavideo));
                        break;
                    case "Placa Mãe":
                        img.setImageDrawable(itemView.getContext().getDrawable(R.drawable.placamae));
                        break;
                    case "Memória RAM":
                        img.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ram));
                        break;
                    case "Gabinete":
                        img.setImageDrawable(itemView.getContext().getDrawable(R.drawable.gabinete));
                        break;
                    case "SSD":
                        img.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ssd));
                        break;
                    default:
                        img.setImageDrawable(itemView.getContext().getDrawable(R.drawable.processador));
                        break;
                }


                TextView textViewNomeProduto = itemView.findViewById(R.id.textViewNomeProduto);
                TextView textViewPrecoProduto = itemView.findViewById(R.id.textViewPrecoProduto);

                textViewNomeProduto.setText(nomeProduto);
                textViewPrecoProduto.setText(precoProduto);

                return itemView;
            }
        };

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nomeProduto = adapter.getItem(position);
                String precoProduto = gerenciadosProdutos.getListaCarrinho().get(position).getPreco();

                Intent intent = new Intent(Carrinho.this, DetalhesItem.class);
                intent.putExtra(DetalhesItem.NOME_PRODUTO, nomeProduto);
                intent.putExtra(DetalhesItem.PRECO_PRODUTO, precoProduto);
                startActivity(intent);
            }
        });

        adapter.notifyDataSetChanged();


        View adicionarProduto = findViewById(R.id.adicionarProdutoButton);
        adicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Carrinho.this, Loja.class));
            }
        });

        View finalizarCompra = findViewById(R.id.finalizarCompra);
        finalizarCompra.setEnabled(listaProdutos.size() > 0);

        finalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Carrinho.this, TelaFinal.class));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        BD bd = new BD(getBaseContext());
        lista = bd.getProdutos();

        List<String> listaProdutos = new ArrayList<>();
        for (Produto produto : lista) {
            listaProdutos.add(produto.getNome());
        }

        adapter.clear();
        adapter.addAll(listaProdutos);
        adapter.notifyDataSetChanged();

        View finalizarCompra = findViewById(R.id.finalizarCompra);
        finalizarCompra.setEnabled(listaProdutos.size() > 0);
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