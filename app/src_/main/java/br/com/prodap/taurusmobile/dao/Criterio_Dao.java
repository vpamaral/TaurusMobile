package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Criterio_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Criterio;

/**
 * Created by Prodap on 28/07/2016.
 */
public class Criterio_Dao extends Banco
{
    private SQLiteDatabase db;
    private Criterio_Adapter c_adapter;

    public Criterio_Dao(Context context)
    {
        super(context);
    }

    public List<Criterio> selectAllCriterios(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        c_adapter = new Criterio_Adapter();

        Class classe = table.getClass();
        List<Criterio> c_list = new ArrayList<Criterio>();

        String sql = String.format("SELECT c.criterio "
                                 + "FROM "
                                 +      "%s AS c"
                                        , Tabela
                                  );

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        c_list = c_adapter.pastoCursor(c);

        banco.close();
        return c_list;
    }
}
