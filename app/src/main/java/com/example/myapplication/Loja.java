package com.example.myapplication;

import android.annotation.SuppressLint;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Loja extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private GerenciadorProdutos gerenciadosProdutos;
    private JSONArray arrayDeProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loja);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new Thread(new Runnable() {

            @Override
            public void run() {
                ListView listView = findViewById(R.id.lojaListView);
                gerenciadosProdutos = GerenciadorProdutos.getInstance();
                List<String> listaProdutos = gerenciadosProdutos.getListaNomesLoja();

                HttpURLConnection urlConnection;
                BufferedReader reader;
                try {
                    URL url = new URL("https://ifrn-ddm.vercel.app/api/items");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setReadTimeout(15000);
                    urlConnection.connect();
                    InputStream inputStream;
                    if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        inputStream = urlConnection.getInputStream();
                    } else {
                        inputStream = urlConnection.getErrorStream();
                    }

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();

                    // Converter a resposta JSON para objetos Java
                    String jsonResponse = stringBuilder.toString();
                    JSONObject resArray = new JSONObject(jsonResponse);

                    arrayDeProdutos = resArray.getJSONArray("data");
                    //System.out.println(retornaNome());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                List<Produto> items = new ArrayList<>();
                for (int i = 0; i < arrayDeProdutos.length(); i++) {
                    try {
                        JSONObject produtoJson = arrayDeProdutos.getJSONObject(i);
                        String nomeProduto = produtoJson.getString("nome");
                        String precoProduto = produtoJson.getString("preco");

                        Produto produto = new Produto(nomeProduto, precoProduto);
                        items.add(produto);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter = new ArrayAdapter<String>(Loja.this, R.layout.item_loja, R.id.textViewNomeProdutoLoja, listaProdutos) {
                            @SuppressLint("UseCompatLoadingForDrawables")
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View itemView = super.getView(position, convertView, parent);
                                String nomeProduto = items.get(position).getNome();
                                String precoProduto = items.get(position).getPreco();

                                ImageView img = itemView.findViewById(R.id.imageViewLoja);
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


                                TextView textViewNomeProduto = itemView.findViewById(R.id.textViewNomeProdutoLoja);
                                TextView textViewPrecoProduto = itemView.findViewById(R.id.textViewPrecoProdutoLoja);


                                textViewNomeProduto.setText(retornaNome(position));

                                textViewPrecoProduto.setText(precoProduto);

                                View adicionarAoCarrinho = itemView.findViewById(R.id.adicionarAoCarrinho);
                                adicionarAoCarrinho.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        gerenciadosProdutos.adicionarAoCarrinho(nomeProduto, precoProduto);
                                        Produto novoProduto = new Produto(nomeProduto, precoProduto);
                                        BD bd = new BD(getBaseContext());
                                        bd.inserir(novoProduto);

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
                });
            }
        }).start();


    }

    public String retornaNome(int position) {
        String result = "";
        try {

            if (arrayDeProdutos != null) {

                return arrayDeProdutos.getJSONObject(position).getString("nome");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            List<String> listProdutos = gerenciadosProdutos.getListaNomesLoja();
            adapter.clear();
            adapter.addAll(listProdutos);
            adapter.notifyDataSetChanged();
        }

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