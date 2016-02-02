package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Animal;

/**
 * Created by João on 1/9/2016.
 */
public class AnimalDao extends Banco {
    private SQLiteDatabase db;
    private AnimalAdapter a_adapter;

    public AnimalDao(Context context) {
        super(context);
    }

    public List<Animal> selectAllAnimais(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        a_adapter = new AnimalAdapter();

        Class classe = table.getClass();
        List<Animal> listadd = new ArrayList<Animal>();
        String sql = String.format("SELECT * FROM %s ORDER BY codigo", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        listadd = a_adapter.arrayAnimais(c);

        banco.close();
        return listadd;
    }
}
