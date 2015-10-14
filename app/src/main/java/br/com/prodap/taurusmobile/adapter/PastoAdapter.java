package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Pasto;

/**
 * Created by Suporte on 07/10/2015.
 */
public class PastoAdapter extends BaseAdapter {

    private List<Pasto> pastos;
    private Activity activity;

    public PastoAdapter() {

    }

    public PastoAdapter(List<Pasto> pastos, Activity activity) {
        this.pastos = pastos;
        this.activity = activity;
    }

    public Pasto PastoCursor(Cursor c) {
        Pasto pasto = new Pasto();

        while (c.moveToNext()) {
            pasto.setPasto(c.getString(c.getColumnIndex("pasto")));
        }
        return pasto;
    }

    public List<Pasto> PastoPreencheArrayCursor(Cursor c) {
        List<Pasto> listaPasto = new ArrayList<Pasto>();
        while (c.moveToNext()) {

            Pasto pasto = new Pasto();

            pasto.setPasto(c.getString(c.getColumnIndex("pasto")));

            listaPasto.add(pasto);
        }

        return listaPasto;
    }

    public ArrayList<Pasto> PastoPreencheArrayHelper(Pasto[] PastoArray) {
        ArrayList<Pasto> listaPasto = new ArrayList<Pasto>();
        for (int i = 0; i < PastoArray.length; i++) {

            Pasto pasto = new Pasto();

            pasto.setPasto(PastoArray[i].getPasto());

            listaPasto.add(pasto);
        }
        return listaPasto;
    }

    public Pasto PastoHelper(Pasto pasto_tb) {
        Pasto pasto = new Pasto();

        pasto.setPasto(pasto_tb.getPasto());

        return pasto;
    }

    public String PastoArqHelper(Pasto pasto_tb){
        String conteudo = "";

        conteudo = String.valueOf(pasto_tb.getPasto())+ "|";

        return conteudo;
    }

    @Override
    public int getCount() {
        return pastos.size();
    }

    @Override
    public Object getItem(int position) {
        return pastos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(pastos.get(position).getPasto()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}