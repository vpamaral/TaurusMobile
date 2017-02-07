package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.helper.Configuracao_Helper;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Configuracao;

/**
 * Created by João on 1/17/2016.
 */
public class Configuracao_Dao extends Banco
{
    private SQLiteDatabase db;
    private Configuracao_Helper c_helper;
    private Configuracao c_tb;
    private Banco banco;

    public Configuracao_Dao(Context context) {
        super(context);
    }

    public List<Configuracao> selectAllConfiguracoes(Context ctx, String Tabela, Object table)
    {
        banco       = new Banco(ctx);
        c_helper    = new Configuracao_Helper();

        Class classe = table.getClass();
        List<Configuracao> c_list = new ArrayList<Configuracao>();
        String sql = String.format("SELECT * FROM %s", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        c_list  = c_helper.arrayConfiguracao(c);

        banco.close();
        return c_list;
    }

    public Configuracao selectPK(Context ctx, String Tabela, Object table, long id_pk)
    {
        banco                                       = new Banco(ctx);
        c_helper                                    = new Configuracao_Helper();
        c_tb                                        = new Configuracao();
        Class classe                                = table.getClass();
        List<Configuracao> c_list                   = new ArrayList<Configuracao>();

        String sql                                  = String.format("SELECT * FROM %s WHERE id_pk = %s", Tabela, id_pk);

        Cursor c                                    = banco.getWritableDatabase().rawQuery(sql, null);
        c_tb                                        = c_helper.cursorConfiguracao(c);

        banco.close();

        return c_tb;
    }
}
