package com.example.myapplication;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProdutos {
    private static GerenciadorProdutos instance;
    private List<Produto> listaLoja;
    private List<Produto> listaCarrinho;

    private GerenciadorProdutos() {
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

    public void adicionarItens(){
        Produto processador = new Produto("Processador", "$150.00");
        Produto placavideo = new Produto("Placa de Vídeo", "$300.00");
        Produto placamae = new Produto("Placa Mãe", "$100.00");
        Produto ram = new Produto("Memória RAM", "$50.00");
        Produto ssd = new Produto("SSD", "$120.00");
        Produto gabinete = new Produto("Gabinete", "80.00");
        listaLoja.add(processador);
        listaLoja.add(placavideo);
        listaLoja.add(placamae);
        listaLoja.add(ram);
        listaLoja.add(ssd);
        listaLoja.add(gabinete);

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