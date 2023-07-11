package com.example.myapplication;

public class Produto {
    private int id;
    private String nome;
    private String preco;

    public Produto(String nome, String preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public String getPreco() {
        return preco;
    }

    public int getId() {
        return id;
    }
}