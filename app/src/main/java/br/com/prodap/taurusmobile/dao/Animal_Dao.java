package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Animal_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Animal;

/**
 * Created by João on 1/9/2016.
 */
public class Animal_Dao extends Banco {
    private SQLiteDatabase db;
    private Animal_Adapter a_adapter;

    public Animal_Dao(Context context) {
        super(context);
    }

    public List<Animal> selectAllAnimais(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        a_adapter = new Animal_Adapter();

        Class classe = table.getClass();
        List<Animal> listadd = new ArrayList<Animal>();
        String sql = String.format("SELECT * FROM %s ORDER BY codigo", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        listadd = a_adapter.arrayAnimais(c);

        banco.close();
        return listadd;
    }
}
