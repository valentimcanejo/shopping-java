package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BD {
    private SQLiteDatabase bd;

    public BD(Context c) {

        AbrirBD bdCore = new AbrirBD(c);
        bd = bdCore.getWritableDatabase();
    }

    public void inserir(Produto a) {

        ContentValues cv = new ContentValues();
        cv.put("nome", a.getNome());

        bd.insert("produto", null, cv);
    }

    public boolean produtoExiste(Produto a) {

        String[] colunas = new String[]{"_id", "nome"};
        Cursor c = bd.query(
                "produto",
                colunas,
                "and nome = \'" + a.getNome() + "\'",
                null,
                null,
                null,
                "nome ASC"
        );

        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }

        //return (c.getCount() > 0) ? true : false;
    }

    public void deletar(Produto a) {
        bd.delete(
                "produto",
                "_id = ?",
                new String[]{"" + a.getId()}
        );
    }

}