package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Pasto;
import br.com.prodap.taurusmobile.adapter.PastoAdapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;

/**
 * Created by Suporte on 07/10/2015.
 */
public class PastoModel extends BancoService {

    private Banco banco;
    private PastoAdapter pasto_adapter;
    private SQLiteDatabase db;
    private Pasto pasto;

    public PastoModel(Context ctx) {
        pasto_adapter = new PastoAdapter();
    }

    @Override
    public boolean validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) {
        // TODO Auto-generated method stub
        return false;
    }

    public Pasto selectByCodigo(Context ctx, Integer codigo) {
        banco = new Banco(ctx);

        db = banco.getReadableDatabase();

        Cursor cursor = db.query("Pasto", null, "id_auto=?",
                new String[] { codigo.toString() }, null, null, "id_auto");

        pasto = pasto_adapter.PastoCursor(cursor);

        return pasto;
    }

    public void removerByAnimal(Context ctx, Long codigo){
        banco = new Banco(ctx);

        db = banco.getWritableDatabase();

        db.delete("Pasto", "id_auto=?", new String[]{codigo.toString()});
    }

    @Override
    public List<Pasto> selectAll(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);

        Class classe = table.getClass();
        List<Pasto> listadd = new ArrayList<Pasto>();
        String sql = "SELECT * FROM " + Tabela;

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        listadd = pasto_adapter.PastoPreencheArrayCursor(c);

        banco.close();
        return listadd;
    }

    @Override
    public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
