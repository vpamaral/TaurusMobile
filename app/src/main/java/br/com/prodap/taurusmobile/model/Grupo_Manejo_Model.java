package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Grupo_Manejo_Adapter;
import br.com.prodap.taurusmobile.dao.Grupo_Manejo_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;
import br.com.prodap.taurusmobile.tb.Grupo_Manejo;
import br.com.prodap.taurusmobile.util.ValidatorException;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Grupo_Manejo_Model extends BancoService {

    private Banco banco;
    private Grupo_Manejo_Adapter grupo_adapter;
    private SQLiteDatabase db;
    private Grupo_Manejo grupo_tb;
    private Grupo_Manejo_Dao grupo_dao;

    public Grupo_Manejo_Model(Context ctx) {
        grupo_adapter = new Grupo_Manejo_Adapter();
    }
    @Override
    public void validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) throws ValidatorException {

    }

    @Override
    public List<Grupo_Manejo> selectAll(Context ctx, String Tabela, Object table) {
        grupo_dao = new Grupo_Manejo_Dao(ctx);

        return grupo_dao.selectAllGrupos(ctx, Tabela, table);
    }

    @Override
    public <T> T selectID(Context ctx, String Tabela, Object table, long id) {

        return null;
    }
}
