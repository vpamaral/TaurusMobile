package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.util.ValidatorException;

/**
 * Created by João on 1/2/2016.
 */
public class Pasto_Dao extends Banco {
    private SQLiteDatabase db;
    private Pasto_Adapter pasto_adapter;

    public Pasto_Dao(Context context) {
        super(context);
    }

    public boolean ifExistPastoInsert(String pasto) throws ValidatorException {
        boolean result = false;
        String sql = String.format("SELECT COUNT(pasto) FROM Pasto WHERE pasto = '%s'", pasto);
        db = getReadableDatabase();
        try
        {
            Cursor c = db.rawQuery(sql, null);

            if (c != null) {
                c.moveToFirst();
                if (c.getInt (0) == 0) {
                    result = false;
                } else {
                    result = true;
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return result;
    }

    public boolean ifExistPastoUpdate(String pasto, long id) throws ValidatorException {
        boolean result = false;
        try {
            String sql = String.format("SELECT COUNT(pasto) FROM Pasto WHERE pasto = '%s' AND id_auto <> '%s'", pasto, id);
            db = getReadableDatabase();
            Cursor cur = db.rawQuery(sql, null);

            if (cur != null) {
                cur.moveToFirst();
                if (cur.getInt(0) == 0) {
                    result = false;
                } else {
                    result = true;
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return result;
    }

    public List<Pasto> selectAllPastos(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        pasto_adapter = new Pasto_Adapter();

        Class classe = table.getClass();
        List<Pasto> pasto_list = new ArrayList<Pasto>();
        String sql = String.format("SELECT * FROM %s", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        pasto_list = pasto_adapter.pastoCursor(c);

        banco.close();
        return pasto_list;
    }
}
