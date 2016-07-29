package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.dao.Pasto_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.util.Validator_Exception;

/**
 * Created by Suporte on 07/10/2015.
 */
public class Pasto_Model extends Banco_Service {

    private Banco banco;
    private Pasto_Adapter pasto_adapter;
    private SQLiteDatabase db;
    private Pasto pasto;
    private Pasto_Dao p_dao;

    public Pasto_Model(Context ctx) {
        pasto_adapter = new Pasto_Adapter();
    }
    @Override
    public void validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) throws Validator_Exception {
        p_dao = new Pasto_Dao(ctx);
        pasto = (Pasto)table;

        try {
                if (pasto.getNome().equals(""))
                {
                    //ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_NULO");
                    Validator_Exception ve = new Validator_Exception("O campo Nome não pode ser vazio!");
                    ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
                    ve.setException_args(new Object[] {});
                    throw ve;
                }

            if (p_dao.ifExistPastoInsert(pasto.getNome().toString()))
            {
                //ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
                Validator_Exception ve = new Validator_Exception("O Nome do pasto não pode ser duplicado!");
                ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
                ve.setException_args(new Object[] {});
                throw ve;
            }

//            if (p_dao.ifExistPastoUpdate(pasto.getPasto().toString(), pasto.getId_auto()))
//            {
//                //ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
//                ValidatorException ve = new ValidatorException("O Nome do pasto não pode ser duplicado!");
//                ve.setException_code(ValidatorException.MESSAGE_TYPE_WARNING);
//                ve.setException_args(new Object[] {});
//                throw ve;
//            }
        } catch (Validator_Exception e) {
            throw e;
        } catch (Exception e) {
            Log.i("PASTO", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public List<Pasto> selectAll(Context ctx, String Tabela, Object table) {
        p_dao = new Pasto_Dao(ctx);

        return p_dao.selectAllPastos(ctx, Tabela, table);
    }

    @Override
    public <T> T selectID(Context ctx, String Tabela, Object table, long id) {

        return null;
    }
}
