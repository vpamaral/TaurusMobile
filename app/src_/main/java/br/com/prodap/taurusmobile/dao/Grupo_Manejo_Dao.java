package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Grupo_Manejo_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Grupo_Manejo;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Grupo_Manejo_Dao extends Banco {
    private SQLiteDatabase db;
    private Grupo_Manejo_Adapter grupo_adapter;

    public Grupo_Manejo_Dao(Context context) {
        super(context);
    }


    public List<Grupo_Manejo> selectAllGrupos(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        grupo_adapter = new Grupo_Manejo_Adapter();

        Class classe = table.getClass();
        List<Grupo_Manejo> grupo_list = new ArrayList<Grupo_Manejo>();
        String sql = String.format("SELECT * FROM %s", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        grupo_list = grupo_adapter.grupoCursor(c);

        banco.close();
        return grupo_list;
    }
}
