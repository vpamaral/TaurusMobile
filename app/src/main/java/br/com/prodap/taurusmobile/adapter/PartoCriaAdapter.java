package br.com.prodap.taurusmobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Parto;
import br.com.prodap.taurusmobile.TB.Parto_Cria;
import br.com.prodap.taurusmobile.ui.R;

public class PartoCriaAdapter extends BaseAdapter{
	private List<Parto_Cria> partos_cria;
	private Activity activity;

	public PartoCriaAdapter() {

	}

	public PartoCriaAdapter(List<Parto_Cria> partos_cria, Activity activity) {
		this.partos_cria = partos_cria;
		this.activity = activity;
	}
	
	public Parto_Cria PartoCriaCursor(Cursor c) {
		Parto_Cria parto_cria = new Parto_Cria();

		while (c.moveToNext()) {
			parto_cria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			parto_cria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			parto_cria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			parto_cria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			parto_cria.setFgStatus(c.getInt(c.getColumnIndex("fgStatus")));
			parto_cria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));
			parto_cria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			parto_cria.setSexo(c.getString(c.getColumnIndex("sexo")));
			parto_cria.setRaca_cria(c.getString(c.getColumnIndex("raca_cria")));
			parto_cria.setData_identificacao(c.getString(c.getColumnIndex("data_identificacao")));
			parto_cria.setRepasse(c.getString(c.getColumnIndex("repasse")));
			parto_cria.setTipo_parto(c.getString(c.getColumnIndex("tipo_parto")));

		}
		return parto_cria;
	}

	public List<Parto_Cria> PartoCriaPreencheArrayCursor(Cursor c) {
		List<Parto_Cria> listaPartoCria = new ArrayList<Parto_Cria>();
		while (c.moveToNext()) {

			Parto_Cria parto_cria = new Parto_Cria();

			parto_cria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			parto_cria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			parto_cria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			parto_cria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			parto_cria.setFgStatus(c.getInt(c.getColumnIndex("fgStatus")));
			parto_cria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));			
			parto_cria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			parto_cria.setSexo(c.getString(c.getColumnIndex("sexo")));
			parto_cria.setRaca_cria(c.getString(c.getColumnIndex("raca_cria")));
			parto_cria.setData_identificacao(c.getString(c.getColumnIndex("data_identificacao")));
			parto_cria.setRepasse(c.getString(c.getColumnIndex("repasse")));
			parto_cria.setTipo_parto(c.getString(c.getColumnIndex("tipo_parto")));

			listaPartoCria.add(parto_cria);
		}

		return listaPartoCria;
	}

	public ArrayList<Parto_Cria> PartoCriaPreencheArrayHelper(Parto_Cria[] PartoCriaArray) {
		ArrayList<Parto_Cria> listaPartoCria = new ArrayList<Parto_Cria>();
		for (int i = 0; i < PartoCriaArray.length; i++) {

			Parto_Cria parto_cria = new Parto_Cria();

			parto_cria.setId_fk_animal_mae(PartoCriaArray[i].getId_fk_animal_mae());
			parto_cria.setSisbov(PartoCriaArray[i].getSisbov());
			parto_cria.setIdentificador(PartoCriaArray[i].getIdentificador());
			parto_cria.setGrupo_manejo(PartoCriaArray[i].getGrupo_manejo());
			parto_cria.setFgStatus(PartoCriaArray[i].getFgStatus());
			parto_cria.setPeso_cria(PartoCriaArray[i].getPeso_cria());
			parto_cria.setCodigo_cria(PartoCriaArray[i].getCodigo_cria());
			parto_cria.setSexo(PartoCriaArray[i].getSexo());
			parto_cria.setRaca_cria(PartoCriaArray[i].getRaca_cria());
			parto_cria.setData_identificacao(PartoCriaArray[i].getData_identificacao());
			parto_cria.setRepasse(PartoCriaArray[i].getRepasse());
			parto_cria.setTipo_parto(PartoCriaArray[i].getTipo_parto());

			listaPartoCria.add(parto_cria);
		}
		return listaPartoCria;
	}

	public Parto_Cria PartoCriaHelper(Parto_Cria parto_cria_tb) {
		Parto_Cria parto_cria = new Parto_Cria();

		parto_cria.setId_fk_animal_mae(parto_cria_tb.getId_fk_animal_mae());
		parto_cria.setSisbov(parto_cria_tb.getSisbov());
		parto_cria.setIdentificador(parto_cria_tb.getIdentificador());
		parto_cria.setGrupo_manejo(parto_cria_tb.getGrupo_manejo());
		parto_cria.setFgStatus(parto_cria_tb.getFgStatus());
		parto_cria.setPeso_cria(parto_cria_tb.getPeso_cria());
		parto_cria.setCodigo_cria(parto_cria_tb.getCodigo_cria());
		parto_cria.setSexo(parto_cria_tb.getSexo());
		parto_cria.setRaca_cria(parto_cria_tb.getRaca_cria());
		parto_cria.setData_identificacao(parto_cria_tb.getData_identificacao());
		parto_cria.setRepasse(parto_cria_tb.getRepasse());
		parto_cria.setTipo_parto(parto_cria_tb.getTipo_parto());

		return parto_cria;
	}

	@Override
	public int getCount() {
		return partos_cria.size();
	}

	@Override
	public Object getItem(int position) {
		return partos_cria.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(partos_cria.get(position).getCodigo_cria());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Parto_Cria parto_cria = partos_cria.get(position);

		LayoutInflater inflater = activity.getLayoutInflater();
		View line = inflater.inflate(R.layout.activity_lista_partos_cria, null);

		/*if (position % 2 == 0) {
			line.setBackgroundColor(activity.getResources().
					getColor(R.color.linha_par));
		}else
		{
			line.setBackgroundColor(activity.getResources().
					getColor(R.color.linha_impar));
		}*/

		TextView sisbov = (TextView) line.findViewById(R.id.lblCodCria);
		sisbov.setText(parto_cria.toString());

		return line;
	}
}
