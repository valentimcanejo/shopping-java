package com.example.myapplication;
import android.content.Intent;
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
import java.util.List;
import android.widget.AdapterView;
import android.widget.TextView;

public class Carrinho extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private GerenciadorProdutos gerenciadosProdutos;

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
        List<String> listaProdutos = gerenciadosProdutos.getListaNomesCarrinho();
        adapter = new ArrayAdapter<String>(this, R.layout.item_carrinho, R.id.textViewNomeProduto, listaProdutos) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                String taskName = listaProdutos.get(position);
                String taskDescription = gerenciadosProdutos.getListaCarrinho().get(position).getPreco();

                ImageView img = itemView.findViewById(R.id.imageViewAttachment);
                switch (taskName) {
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

                textViewNomeProduto.setText(taskName);
                textViewPrecoProduto.setText(taskDescription);

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


        View addTaskButton = findViewById(R.id.adicionarProdutoButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Carrinho.this, Loja.class));
            }
        });

        View finalizarCompra = findViewById(R.id.finalizarCompra);
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
        List<String> listProdutos = gerenciadosProdutos.getListaNomesCarrinho();
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