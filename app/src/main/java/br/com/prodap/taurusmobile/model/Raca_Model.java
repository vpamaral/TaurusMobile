package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Raca_Adapter;
import br.com.prodap.taurusmobile.dao.Raca_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Raca;
import br.com.prodap.taurusmobile.util.Validator_Exception;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Raca_Model extends Banco_Service {

    private Banco banco;
    private Raca_Adapter raca_adapter;
    private SQLiteDatabase db;
    private Raca raca_tb;
    private Raca_Dao raca_dao;

    public Raca_Model(Context ctx) {
        raca_adapter = new Raca_Adapter();
    }
    @Override
    public void validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) throws Validator_Exception {

    }

    @Override
    public List<Raca> selectAll(Context ctx, String Tabela, Object table) {
        raca_dao = new Raca_Dao(ctx);

        return raca_dao.selectAllRacas(ctx, Tabela, table);
    }

    @Override
    public <T> T selectID(Context ctx, String Tabela, Object table, long id) {

        return null;
    }
}
