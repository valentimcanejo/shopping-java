package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BD {
    private SQLiteDatabase bd;
    private static BD instance;
    private List<Produto> listaLoja;
    private List<Produto> listaCarrinho;


    public BD(Context c) {

        AbrirBD bdCore = new AbrirBD(c);
        bd = bdCore.getWritableDatabase();
        listaCarrinho = new ArrayList<>();
        listaLoja = new ArrayList<>();
    }

    public List<String> getListaNomesCarrinho() {
        List<String> lista = new ArrayList<>();
        for (Produto item : listaCarrinho) {
            lista.add(item.getNome());
        }
        return lista;
    }

    public void inserir(Produto a) {

        ContentValues cv = new ContentValues();
        cv.put("nome", a.getNome());
        cv.put("preco", a.getPreco());

        bd.insert("produto", null, cv);
        listaCarrinho.add(a);
    }

    public List<Produto> getListaLoja() {
        return listaLoja;
    }

    public List<Produto> getListaCarrinho() {
        return listaCarrinho;
    }

    public List<Produto> getProdutos() {
        List<Produto> produtos = new ArrayList<>();


        Cursor cursor = bd.rawQuery("SELECT * FROM produto", null);
        if (cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(1);
                String preco = cursor.getString(2);

                Produto produto = new Produto(nome, preco);
                produtos.add(produto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();

        return produtos;
    }


}