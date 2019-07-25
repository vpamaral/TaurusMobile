package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Animal_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Animal;

/**
 * Created by João on 1/9/2016.
 */
public class Animal_Dao extends Banco {
    private SQLiteDatabase db;
    private Animal_Adapter a_adapter;

    public Animal_Dao(Context context) {
        super(context);
    }

    public List<Animal> selectAllAnimais(Context ctx, String Tabela, Object table, String _hash)
    {
        Banco banco = new Banco(ctx);
        a_adapter = new Animal_Adapter();

        String hash = "";
        if(_hash.toLowerCase().contains("alternativo"))
            hash = "codigo_ferro";
        else if(_hash.toLowerCase().contains("identificador"))
            hash = "identificador";
        else
            hash = "codigo";

        Class classe = table.getClass();
        List<Animal> listadd = new ArrayList<Animal>();
        String sql = String.format("SELECT %s as hash, * FROM %s ORDER BY codigo", hash, Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        listadd = a_adapter.arrayAnimais(c);

        banco.close();
        return listadd;
    }

    public List<Animal> selectAllbySexo(Context ctx, String Tabela, Object table, String sexo, String _hash)
    {
        Banco banco = new Banco(ctx);
        a_adapter = new Animal_Adapter();

        String hash = "";
        if(_hash.toLowerCase().contains("alternativo"))
            hash = "codigo_ferro";
        else if(_hash.toLowerCase().contains("identificador"))
            hash = "identificador";
        else
            hash = "codigo";

        Class classe = table.getClass();
        List<Animal> listadd = new ArrayList<Animal>();
        String sql = String.format("SELECT %s as hash, * FROM %s WHERE sexo = '%s' ORDER BY codigo", hash, Tabela, sexo);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        listadd = a_adapter.arrayAnimais(c);

        banco.close();
        return listadd;
    }

    public boolean ifExistAnimalVivo(String codigo, String identificador) {
        boolean result = false;
        String sql = String.format("SELECT COUNT(id_pk) FROM Animal WHERE codigo = '%s' AND identificador = '%s'", codigo, identificador);

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
}
