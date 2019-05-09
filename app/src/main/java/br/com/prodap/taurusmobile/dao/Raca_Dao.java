package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Raca_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Raca;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Raca_Dao extends Banco {
    private SQLiteDatabase db;
    private Raca_Adapter raca_adapter;

    public Raca_Dao(Context context) {
        super(context);
    }


    public List<Raca> selectAllRacas(Context ctx, String Tabela, Object table)
    {
        Banco banco = new Banco(ctx);
        raca_adapter = new Raca_Adapter();

        Class classe = table.getClass();
        List<Raca> raca_list = new ArrayList<Raca>();
        String sql = String.format("SELECT * FROM %s ORDER BY nome", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        raca_list = raca_adapter.racaCursor(c);

        banco.close();
        return raca_list;
    }
}
