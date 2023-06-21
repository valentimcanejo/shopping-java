package com.example.myapplication;
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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Loja extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private GerenciadorProdutos gerenciadosProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loja);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = findViewById(R.id.lojaListView);
        gerenciadosProdutos = GerenciadorProdutos.getInstance();
        List<String> listaProdutos = gerenciadosProdutos.getListaNomesLoja();
        adapter = new ArrayAdapter<String>(this, R.layout.item_loja, R.id.textViewNomeProdutoLoja, listaProdutos) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                String nomeProduto = listaProdutos.get(position);
                String precoProduto = gerenciadosProdutos.getListaLoja().get(position).getPreco();

                ImageView img = itemView.findViewById(R.id.imageViewLoja);
                switch (nomeProduto) {
                    case "Processador":
                        img.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.processador));
                        break;
                    case "Placa de Vídeo":
                        img.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.placavideo));
                        break;
                    case "Placa Mãe":
                        img.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.placamae));
                        break;
                    case "Memória RAM":
                        img.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.ram));
                        break;
                    case "Gabinete":
                        img.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.gabinete));
                        break;
                    case "SSD":
                        img.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.ssd));
                        break;
                    default:
                        img.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.processador));
                        break;
                }


                TextView textViewNomeProduto = itemView.findViewById(R.id.textViewNomeProdutoLoja);
                TextView textViewPrecoProduto = itemView.findViewById(R.id.textViewPrecoProdutoLoja);

                textViewNomeProduto.setText(nomeProduto);
                textViewPrecoProduto.setText(precoProduto);

                View adicionarAoCarrinho = itemView.findViewById(R.id.adicionarAoCarrinho);
                adicionarAoCarrinho.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gerenciadosProdutos.adicionarAoCarrinho(nomeProduto, precoProduto);
                        //gerenciadosProdutos.removerDaLoja(position);
                        Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content),
                                "Item adicionado ao carrinho!", Snackbar.LENGTH_SHORT);

                        mySnackbar.getView().setBackgroundColor(getColor(R.color.teal_200));

                        mySnackbar.show();
                    }
                });


                return itemView;
            }
        };
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    protected void onResume() {
        super.onResume();
        List<String> listProdutos = gerenciadosProdutos.getListaNomesLoja();
        adapter.clear();
        adapter.addAll(listProdutos);
        adapter.notifyDataSetChanged();

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