package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Configuracoes;

/**
 * Created by João on 1/17/2016.
 */
public class ConfiguracoesDao extends Banco {
    private SQLiteDatabase db;
    private ConfiguracoesAdapter c_adapter;

    public ConfiguracoesDao(Context context) {
        super(context);
    }

    public List<Configuracoes> selectAllConfiguracoes(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        c_adapter   = new ConfiguracoesAdapter();

        Class classe = table.getClass();
        List<Configuracoes> listadd = new ArrayList<Configuracoes>();
        String sql = String.format("SELECT * FROM %s", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        listadd = c_adapter.arrayConfiguracoes(c);

        banco.close();
        return listadd;
    }
}
