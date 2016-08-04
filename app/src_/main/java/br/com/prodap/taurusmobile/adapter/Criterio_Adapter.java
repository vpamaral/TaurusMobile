package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Criterio;

/**
 * Created by Prodap on 28/07/2016.
 */
public class Criterio_Adapter extends BaseAdapter {

    private List<Criterio> criterio_list;
    private Activity activity;

    public Criterio_Adapter() { }

    public Criterio_Adapter(List<Criterio> criterio_list, Activity activity) {
        this.criterio_list = criterio_list;
        this.activity = activity;
    }

    public Criterio criterioCursor(Cursor c) {
        Criterio c_tb = new Criterio();

        while (c.moveToNext()) {

            c_tb.setCriterio(c.getString(c.getColumnIndex("criterio")));
        }
        return c_tb;
    }

    public List<Criterio> pastoCursor(Cursor c) {
        List<Criterio> c_list = new ArrayList<Criterio>();
        while (c.moveToNext()) {

            Criterio c_tb = new Criterio();
            c_tb.setCriterio(c.getString(c.getColumnIndex("criterio")));

            c_list.add(c_tb);
        }

        return c_list;
    }

    public ArrayList<Criterio> arrayCriterio(Criterio[] c_array) {
        ArrayList<Criterio> c_list = new ArrayList<Criterio>();
        for (int i = 0; i < c_array.length; i++) {

            Criterio c_tb = new Criterio();

            c_tb.setCriterio(c_array[i].getCriterio());

            c_list.add(c_tb);
        }
        return c_list;
    }

    public Criterio criterioHelper(Criterio c_tb) {
        Criterio criterio_tb = new Criterio();

        criterio_tb.setCriterio(c_tb.getCriterio());

        return criterio_tb;
    }

    public ContentValues getDadosCriterio(Criterio c_tb) {
        ContentValues dados = new ContentValues();
        dados.put("nome", c_tb.getCriterio());
        return dados;
    }

    @Override
    public int getCount() {
        return criterio_list.size();
    }

    @Override
    public Object getItem(int position) {
        return criterio_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(criterio_list.get(position).getCriterio()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
