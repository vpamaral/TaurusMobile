package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Parto_Cria_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.util.Validator_Exception;

/**
 * Created by João on 1/17/2016.
 */
public class Parto_Cria_Dao extends Banco {
    private SQLiteDatabase db;
    private Parto_Cria_Adapter pc_adapter;

    public Parto_Cria_Dao(Context context) {
        super(context);
    }

    public boolean ifExistCodMatriz(String cod_matriz_invalido) throws Validator_Exception {
        boolean result = false;
        String sql = String.format(
                                        "SELECT COUNT(cod_matriz_invalido) " +
                                        "FROM Parto_Cria " +
                                        "WHERE cod_matriz_invalido = '%s'" +
                                        "AND sync_status = 0 " +
                                        "AND cod_matriz_invalido <> 0"
                                        , cod_matriz_invalido
                                  );
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

    public boolean ifExistIdFkMae(long id_fk_mae) throws Validator_Exception {
        boolean result = false;
        String sql = String.format(
                                        "SELECT COUNT(id_fk_animal_mae) " +
                                        "FROM Parto_Cria " +
                                        "WHERE id_fk_animal_mae = '%s'" +
                                        "AND sync_status = 0 "
                                        , id_fk_mae
                                   );
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

    public List<Parto_Cria> selectAllPartosCria(Context ctx, String Tabela, Object table)
    {
        Banco banco = new Banco(ctx);
        pc_adapter = new Parto_Cria_Adapter();

        Class classe = table.getClass();
        List<Parto_Cria> pc_list = new ArrayList<Parto_Cria>();

        String sql = String.format(
                                        "SELECT * FROM %s " +
                                        "WHERE sync_status = 0 ORDER BY codigo_cria"
                                        , Tabela
                                   );

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        pc_list = pc_adapter.arrayPartoCria(c);

        banco.close();
        return pc_list;
    }

    public boolean ifExistIdentificador(String identificador) {
        boolean result = false;
        String sql = String.format(
                "SELECT COUNT(identificador) " +
                        "FROM Parto_Cria " +
                        "WHERE identificador = '%s'" +
                        "AND sync_status BETWEEN 0 AND 1 "
                , identificador
        );
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

    public boolean ifExistSisbov(String sisbov) {
        boolean result = false;
        String sql = String.format(
                                        "SELECT COUNT(sisbov) " +
                                        "FROM Parto_Cria " +
                                        "WHERE sisbov = '%s'" +
                                        "AND sync_status BETWEEN 0 AND 1 "
                                        , sisbov
                                   );
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

    public boolean ifExistCodigo(String codigo_cria) {
        boolean result = false;
        String sql = String.format(
                                        "SELECT COUNT(codigo_cria) " +
                                        "FROM Parto_Cria " +
                                        "WHERE codigo_cria = '%s'" +
                                        "AND sync_status BETWEEN 0 AND 1 "
                                        , codigo_cria
                                   );
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
