package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Parto_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.util.Validator_Exception;

/**
 * Created by João on 1/17/2016.
 */
public class Parto_Dao extends Banco {
    private SQLiteDatabase db;
    private Parto_Adapter p_adapter;

    public Parto_Dao(Context context) {
        super(context);
    }


    public List<Parto> selectAllPartos(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        p_adapter = new Parto_Adapter();

        Class classe = table.getClass();
        List<Parto> parto_list = new ArrayList<Parto>();
        String sql = String.format("SELECT * FROM %s", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        parto_list = p_adapter.PartoPreencheArrayCursor(c);

        banco.close();
        return parto_list;
    }
}
