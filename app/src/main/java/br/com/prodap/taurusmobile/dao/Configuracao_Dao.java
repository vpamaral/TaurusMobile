package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Configuracao;

/**
 * Created by João on 1/17/2016.
 */
public class Configuracao_Dao extends Banco {
    private SQLiteDatabase db;
    private Configuracao_Adapter c_adapter;

    public Configuracao_Dao(Context context) {
        super(context);
    }

    public List<Configuracao> selectAllConfiguracoes(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        c_adapter   = new Configuracao_Adapter();

        Class classe = table.getClass();
        List<Configuracao> listadd = new ArrayList<Configuracao>();
        String sql = String.format("SELECT * FROM %s", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        listadd = c_adapter.arrayConfiguracoes(c);

        banco.close();
        return listadd;
    }
}
