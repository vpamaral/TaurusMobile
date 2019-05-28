package br.com.prodap.taurusmobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Relatorio_Parto;
import br.com.prodap.taurusmobile.adapter.Parto_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.tb.Vacas_Gestantes;
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

    public List<Relatorio_Parto> selectRelatorioPartos(Context ctx, String Tabela, Object table) {
        Banco banco = new Banco(ctx);
        p_adapter = new Parto_Adapter();

        Class classe = table.getClass();
        List<Relatorio_Parto> parto_list = new ArrayList<Relatorio_Parto>();
        String sql = String.format("select case \n" +
                "when p.data_parto is null \n" +
                "     then '' \n" +
                "else  substr(p.data_parto,1,2)\n" +
                "      || '/'\n" +
                "      ||substr(p.data_parto,4,2)\n" +
                "      || '/'\n" +
                "      ||substr(p.data_parto,7,4) \n" +
                "end as data_parto, p.sexo_parto, count(p.id_fk_animal) as qtd_partos\n" +
                "from parto p\n" +
                "where p.sync_status = 1" +
                "\n" +
                "group by  case \n" +
                "             when p.data_parto is null \n" +
                "                  then '' \n" +
                "             else  substr(p.data_parto,1,2)\n" +
                "                   || '/'\n" +
                "                   ||substr(p.data_parto,4,2)\n" +
                "                   || '/'\n" +
                "                   ||substr(p.data_parto,7,4) \n" +
                "        end, p.sexo_parto\n" +
                "order by " +
                "case "  +
                "     when p.data_parto is null \n" +
                "          then '' \n" +
                "     else  date(substr(p.data_parto,7,4) \n" +
                "           || '-' \n" +
                "           ||substr(p.data_parto,4,2) \n" +
                "           || '-' \n" +
                "           ||substr(p.data_parto,1,2)) \n" +
                "end desc", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        parto_list = p_adapter.RelatorioPartoPreencheArrayCursor(c);

        banco.close();
        return parto_list;
    }

    public List<Vacas_Gestantes> selectVacasGestantes(Context ctx, String Tabela, Object table, String data_referencia) {
        Banco banco = new Banco(ctx);
        p_adapter = new Parto_Adapter();

        Class classe = table.getClass();
        List<Vacas_Gestantes> parto_list = new ArrayList<Vacas_Gestantes>();
        String sql = String.format("select p.id_pk, p.codigo, case when pa.data_parto is null then '' else pa.data_parto end as data_ultimo_dg, p.data_parto_provavel\n" +
                                        "from animal p \n" +

                                        "left join parto pa on pa.id_fk_animal = p.id_pk \n" +
                                        "where p.situacao_reprodutiva = 'GESTANTE' " +
                                        "and DATE(substr(p.data_parto_provavel,7,4) ||'-' ||substr(p.data_parto_provavel,4,2) ||'-' ||substr(p.data_parto_provavel,1,2)) >= DATE('"+ data_referencia  +"', '-15 days') " +
                                        "and DATE(substr(p.data_parto_provavel,7,4) ||'-' ||substr(p.data_parto_provavel,4,2) ||'-' ||substr(p.data_parto_provavel,1,2)) <= DATE('"+ data_referencia  +"', '+15 days') " +
                                        "\n" +
                                        "order by pa.data_parto desc, p.codigo", Tabela);

        Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

        parto_list = p_adapter.VacasGestantesPreencheArrayCursor(c);

        banco.close();
        return parto_list;
    }

    public String sendPartos(Context ctx, String data_atual)
    {
        Banco banco = new Banco(ctx);

        String result = "";
        String sql = String.format("SELECT\n" +
                                    "    pc.*\n" +
                                    "    , CAST(JulianDay('%s') " +
                                             "- JulianDay( datetime(substr(pc.data_identificacao, 7, 4) " +
                                             "|| '-' || substr(pc.data_identificacao, 4, 2) || '-' " +
                                             "|| substr(pc.data_identificacao, 1, 2))) AS date) As dias\n" +
                                    "FROM \n" +
                                    "    Parto_Cria pc \n" +
                                    "WHERE \n" +
                                    "       pc.sync_status  = 0\n" +
                                    "   AND dias > 7"
                                        , data_atual
                                );

        try
        {
            Cursor c = banco.getReadableDatabase().rawQuery(sql, null);

            if (c != null)
            {
                boolean v = c.moveToNext();

                if (v)
                {
                    result = "SIM";
                }
                else
                {
                    result = "NÃO";
                }
            }
        }
        catch (Exception e)
        {
            banco.close();
            throw e;
        }

        banco.close();
        return result;
    }


    public String maxDataPartoProvavel(Context ctx)
    {
        Banco banco = new Banco(ctx);

        String result = "";
        String sql = String.format("select MAX(DATE(substr(p.data_parto_provavel,7,4) ||'-' ||substr(p.data_parto_provavel,4,2) ||'-' ||substr(p.data_parto_provavel,1,2))) as data_parto_provavel_max\n" +
                "from animal p \n" +
                "left join parto pa on pa.id_fk_animal = p.id_pk \n" +
                "where p.situacao_reprodutiva = 'GESTANTE' "
        );

        try
        {
            Cursor c = banco.getReadableDatabase().rawQuery(sql, null);

            if (c != null) {

                boolean v = c.moveToNext();

                if (v)
                {
                    String teste = c.getString(c.getColumnIndex("data_parto_provavel_max"));
                    result = teste;
                }
            }
        }
        catch (Exception e)
        {
            banco.close();
            throw e;
        }

        banco.close();
        return result;
    }
}
