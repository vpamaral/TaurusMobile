package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Raca;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Raca_Adapter extends BaseAdapter {

    private List<Raca> racas;
    private Activity activity;

    public Raca_Adapter() {

    }

    public Raca_Adapter(List<Raca> racas, Activity activity) {
        this.racas = racas;
        this.activity = activity;
    }

    public Raca RacaCursor(Cursor c) {
        Raca raca_tb = new Raca();

        while (c.moveToNext()) {

            raca_tb.setCodigo(c.getString(c.getColumnIndex("codigo")));
            raca_tb.setNome(c.getString(c.getColumnIndex("nome")));
        }
        return raca_tb;
    }

    public List<Raca> racaCursor(Cursor c) {
        List<Raca> listRacas = new ArrayList<Raca>();
        while (c.moveToNext()) {

            Raca raca_tb = new Raca();
            raca_tb.setCodigo(c.getString(c.getColumnIndex("codigo")));
            raca_tb.setNome(c.getString(c.getColumnIndex("nome")));

            listRacas.add(raca_tb);
        }

        return listRacas;
    }

    public ArrayList<Raca> arrayRaca(Raca[] racaArray) {
        ArrayList<Raca> listRacas = new ArrayList<Raca>();
        for (int i = 0; i < racaArray.length; i++) {

            Raca raca_tb = new Raca();

            raca_tb.setCodigo(racaArray[i].getCodigo());
            raca_tb.setNome(racaArray[i].getNome());

            listRacas.add(raca_tb);
        }
        return listRacas;
    }

    public ContentValues getDadosRaca(Raca raca)
    {
        ContentValues dados = new ContentValues();
        dados.put("codigo", raca.getCodigo());
        dados.put("nome", raca.getNome());
        return dados;
    }

    public Raca grupoHelper(Raca raca_tb) {
        Raca g_tb = new Raca();

        g_tb.setCodigo(raca_tb.getCodigo());
        g_tb.setNome(raca_tb.getNome());

        return g_tb;
    }

    @Override
    public int getCount() {
        return racas.size();
    }

    @Override
    public Object getItem(int position) {
        return racas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(racas.get(position).getCodigo()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
