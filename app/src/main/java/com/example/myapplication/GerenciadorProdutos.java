package com.example.myapplication;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProdutos {
    private static GerenciadorProdutos instance;
    private List<Produto> listaLoja;
    private List<Produto> listaCarrinho;

    public GerenciadorProdutos() {
        listaCarrinho = new ArrayList<>();
        listaLoja = new ArrayList<>();
    }

    public static GerenciadorProdutos getInstance() {
        if (instance == null) {
            instance = new GerenciadorProdutos();
        }
        return instance;
    }

    public void adicionarAoCarrinho(String nome, String preco) {
        Produto item = new Produto(nome, preco);
        listaCarrinho.add(item);
    }

    public void adicionarTarefa(String nome, String preco) {
        Produto item = new Produto(nome, preco);
        listaLoja.add(item);
    }

    public void removerDaLoja(int position) {
        //Produto item = new Produto(nome, descricao);
        listaLoja.remove(position);
    }


    public List<Produto> getListaLoja() {
        return listaLoja;
    }
    public List<Produto> getListaCarrinho() {
        return listaCarrinho;
    }

    public void adicionarItens(List<Produto> items){
for(int i = 0; i< items.size();i++){
    listaLoja.add(items.get(i));
}


    }

    public List<String> getListaNomesLoja() {
        List<String> lista = new ArrayList<>();
        for (Produto item : listaLoja) {
            lista.add(item.getNome());
        }
        return lista;
    }

    public List<String> getListaNomesCarrinho() {
        List<String> lista = new ArrayList<>();
        for (Produto item : listaCarrinho) {
            lista.add(item.getNome());
        }
        return lista;
    }
}