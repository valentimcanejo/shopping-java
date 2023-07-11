package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AbrirBD extends SQLiteOpenHelper {

    private static final String NOME_BD = "produto";
    private static final int VERSAO_BD = 1;
    String estrutura =
            "CREATE TABLE produto(" +
                    "_id integer primary key autoincrement, " +
                    "nome text not null)";

    public AbrirBD(Context c){
        super(c, NOME_BD, null, VERSAO_BD);
    }

    public void onCreate(SQLiteDatabase bd){
        bd.execSQL(estrutura);
    }

    public void onUpgrade(SQLiteDatabase bd, int vi, int vf){
        bd.execSQL("drop table produto");
        onCreate(bd);
    }
}