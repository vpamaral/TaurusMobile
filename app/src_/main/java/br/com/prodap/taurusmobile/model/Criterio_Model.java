package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Criterio_Adapter;
import br.com.prodap.taurusmobile.dao.Criterio_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Criterio;
import br.com.prodap.taurusmobile.util.Validator_Exception;

/**
 * Created by Prodap on 28/07/2016.
 */
public class Criterio_Model extends Banco_Service {

    private Banco banco;
    private Criterio_Adapter c_adapter;
    private SQLiteDatabase db;
    private Criterio c_tb;
    private Criterio_Dao c_dao;

    public Criterio_Model(Context ctx)
    {
        c_adapter = new Criterio_Adapter();
    }

    @Override
    public void validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) throws Validator_Exception {

    }

    @Override
    public List<Criterio> selectAll(Context ctx, String Tabela, Object table) {
        c_dao = new Criterio_Dao(ctx);

        return c_dao.selectAllCriterios(ctx, Tabela, table);
    }

    @Override
    public <T> T selectID(Context ctx, String Tabela, Object table, long id) {

        return null;
    }
}
