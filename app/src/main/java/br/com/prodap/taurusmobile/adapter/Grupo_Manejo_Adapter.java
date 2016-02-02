package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Grupo_Manejo;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Grupo_Manejo_Adapter extends BaseAdapter {

    private List<Grupo_Manejo> grupos;
    private Activity activity;

    public Grupo_Manejo_Adapter() {

    }

    public Grupo_Manejo_Adapter(List<Grupo_Manejo> grupos, Activity activity) {
        this.grupos = grupos;
        this.activity = activity;
    }

    public Grupo_Manejo GrupoCursor(Cursor c) {
        Grupo_Manejo grupo_tb = new Grupo_Manejo();

        while (c.moveToNext()) {

            grupo_tb.setCodigo(c.getString(c.getColumnIndex("codigo")));
        }
        return grupo_tb;
    }

    public List<Grupo_Manejo> grupoCursor(Cursor c) {
        List<Grupo_Manejo> listGrupos = new ArrayList<Grupo_Manejo>();
        while (c.moveToNext()) {

            Grupo_Manejo grupo_tb = new Grupo_Manejo();
            grupo_tb.setCodigo(c.getString(c.getColumnIndex("codigo")));

            listGrupos.add(grupo_tb);
        }

        return listGrupos;
    }

    public ArrayList<Grupo_Manejo> arrayGrupo(Grupo_Manejo[] grupoArray) {
        ArrayList<Grupo_Manejo> listGrupos = new ArrayList<Grupo_Manejo>();
        for (int i = 0; i < grupoArray.length; i++) {

            Grupo_Manejo grupo_tb = new Grupo_Manejo();

            grupo_tb.setCodigo(grupoArray[i].getCodigo());

            listGrupos.add(grupo_tb);
        }
        return listGrupos;
    }

    public Grupo_Manejo grupoHelper(Grupo_Manejo grupo_tb) {
        Grupo_Manejo g_tb = new Grupo_Manejo();

        g_tb.setCodigo(grupo_tb.getCodigo());

        return g_tb;
    }

    @Override
    public int getCount() {
        return grupos.size();
    }

    @Override
    public Object getItem(int position) {
        return grupos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(grupos.get(position).getCodigo()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
