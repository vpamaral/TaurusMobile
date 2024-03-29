package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Parto_Parto_Cria;
import br.com.prodap.taurusmobile.R;

public class Parto_Parto_Cria_Adapter extends BaseAdapter {
	private List<Parto_Parto_Cria> partos_cria;
	private Activity activity;

	public Parto_Parto_Cria_Adapter() {

	}

	public Parto_Parto_Cria_Adapter(List<Parto_Parto_Cria> partos_cria, Activity activity) {

		this.partos_cria = partos_cria;
		this.activity = activity;
	}

	public Parto_Parto_Cria P_PartoCriaCursor(Cursor c) {
		Parto_Parto_Cria p_partoCria = new Parto_Parto_Cria();
		while (c.moveToNext()) {
			p_partoCria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			p_partoCria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));
			p_partoCria.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			p_partoCria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			p_partoCria.setSexo(c.getString(c.getColumnIndex("sexo")));
			p_partoCria.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			p_partoCria.setData_parto(c.getString(c.getColumnIndex("data_parto")));
			p_partoCria.setPerda_gestacao(c.getString(c.getColumnIndex("perda_gestacao")));
			p_partoCria.setSexo_parto(c.getString(c.getColumnIndex("sexo_parto")));
			p_partoCria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			p_partoCria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			p_partoCria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			p_partoCria.setData_identificacao(c.getString(c.getColumnIndex("data_identificacao")));
			p_partoCria.setRaca_cria(c.getString(c.getColumnIndex("raca_cria")));
			p_partoCria.setRepasse(c.getString(c.getColumnIndex("repasse")));
			p_partoCria.setTipo_parto(c.getString(c.getColumnIndex("tipo_parto")));
			p_partoCria.setCod_matriz_invalido(c.getString(c.getColumnIndex("cod_matriz_invalido")));
			p_partoCria.setPasto(c.getString(c.getColumnIndex("pasto")));
			p_partoCria.setCodigo_ferro_cria(c.getString(c.getColumnIndex("codigo_ferro_cria")));
			p_partoCria.setCriterio(c.getString(c.getColumnIndex("criterio")));
		}

		return p_partoCria;
	}

	public List<Parto_Parto_Cria> P_PartoCriaPreencheArrayCursor(Cursor c)
	{
		List<Parto_Parto_Cria> listaPartoCria = new ArrayList<Parto_Parto_Cria>();

		while (c.moveToNext())
		{
			Parto_Parto_Cria p_partoCria = new Parto_Parto_Cria();

			p_partoCria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			p_partoCria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));
			p_partoCria.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			p_partoCria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			p_partoCria.setSexo(c.getString(c.getColumnIndex("sexo")));
			p_partoCria.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			p_partoCria.setData_parto(c.getString(c.getColumnIndex("data_parto")));
			p_partoCria.setPerda_gestacao(c.getString(c.getColumnIndex("perda_gestacao")));
			p_partoCria.setSexo_parto(c.getString(c.getColumnIndex("sexo_parto")));
			p_partoCria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			p_partoCria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			p_partoCria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			p_partoCria.setData_identificacao(c.getString(c.getColumnIndex("data_identificacao")));
			p_partoCria.setRaca_cria(c.getString(c.getColumnIndex("raca_cria")));
			p_partoCria.setRepasse(c.getString(c.getColumnIndex("repasse")));
			p_partoCria.setTipo_parto(c.getString(c.getColumnIndex("tipo_parto")));
			p_partoCria.setCod_matriz_invalido(c.getString(c.getColumnIndex("cod_matriz_invalido")));
			p_partoCria.setPasto(c.getString(c.getColumnIndex("pasto")));
			p_partoCria.setCodigo_ferro_cria(c.getString(c.getColumnIndex("codigo_ferro_cria")));
			p_partoCria.setCriterio(c.getString(c.getColumnIndex("criterio")));

			listaPartoCria.add(p_partoCria);
		}

		return listaPartoCria;
	}

	public ArrayList<Parto_Parto_Cria> P_PartoCriaPreencheArrayHelper(
			Parto_Parto_Cria[] PartoCriaArray) {
		ArrayList<Parto_Parto_Cria> listaPartoCria = new ArrayList<Parto_Parto_Cria>();
		for (int i = 0; i < PartoCriaArray.length; i++) {

			Parto_Parto_Cria p_partoCria = new Parto_Parto_Cria();

			//p_partoCria.setId_auto(PartoCriaArray[i].getId_auto());
			p_partoCria.setId_fk_animal_mae(PartoCriaArray[i].getId_fk_animal_mae());
			p_partoCria.setPeso_cria(PartoCriaArray[i].getPeso_cria());
			p_partoCria.setSync_status(PartoCriaArray[i].getSync_status());
			p_partoCria.setCodigo_cria(PartoCriaArray[i].getCodigo_cria());
			p_partoCria.setSexo(PartoCriaArray[i].getSexo());
			p_partoCria.setId_fk_animal(PartoCriaArray[i].getId_fk_animal());
			p_partoCria.setData_parto(PartoCriaArray[i].getData_parto());
			p_partoCria.setPerda_gestacao(PartoCriaArray[i].getPerda_gestacao());
			p_partoCria.setSexo_parto(PartoCriaArray[i].getSexo_parto());
			p_partoCria.setSisbov(PartoCriaArray[i].getSisbov());
			p_partoCria.setIdentificador(PartoCriaArray[i].getIdentificador());
			p_partoCria.setGrupo_manejo(PartoCriaArray[i].getGrupo_manejo());
			p_partoCria.setData_identificacao(PartoCriaArray[i].getData_identificacao());
			p_partoCria.setRaca_cria(PartoCriaArray[i].getRaca_cria());
			p_partoCria.setRepasse(PartoCriaArray[i].getRepasse());
			p_partoCria.setTipo_parto(PartoCriaArray[i].getTipo_parto());
			p_partoCria.setCod_matriz_invalido(PartoCriaArray[i].getCod_matriz_invalido());
			p_partoCria.setPasto(PartoCriaArray[i].getPasto());
			p_partoCria.setCodigo_ferro_cria(PartoCriaArray[i].getCodigo_ferro_cria());

			listaPartoCria.add(p_partoCria);
		}
		return listaPartoCria;
	}

	public Parto_Parto_Cria P_PartoCriaHelper(Parto_Parto_Cria p_partoCria_tb) {
		Parto_Parto_Cria p_partoCria = new Parto_Parto_Cria();

		p_partoCria.setId_fk_animal_mae(p_partoCria_tb.getId_fk_animal_mae());
		p_partoCria.setPeso_cria(p_partoCria_tb.getPeso_cria());
		p_partoCria.setSync_status(p_partoCria_tb.getSync_status());
		p_partoCria.setCodigo_cria(p_partoCria_tb.getCodigo_cria());
		p_partoCria.setSexo(p_partoCria_tb.getSexo());
		p_partoCria.setId_fk_animal(p_partoCria_tb.getId_fk_animal());
		p_partoCria.setData_parto(p_partoCria_tb.getData_parto());
		p_partoCria.setPerda_gestacao(p_partoCria_tb.getPerda_gestacao());
		p_partoCria.setSexo_parto(p_partoCria_tb.getSexo_parto());
		p_partoCria.setSisbov(p_partoCria_tb.getSisbov());
		p_partoCria.setIdentificador(p_partoCria_tb.getIdentificador());
		p_partoCria.setGrupo_manejo(p_partoCria_tb.getGrupo_manejo());
		p_partoCria.setData_identificacao(p_partoCria_tb.getData_identificacao());
		p_partoCria.setRaca_cria(p_partoCria_tb.getRaca_cria());
		p_partoCria.setRepasse(p_partoCria_tb.getRepasse());
		p_partoCria.setTipo_parto(p_partoCria_tb.getTipo_parto());
		p_partoCria.setCod_matriz_invalido(p_partoCria_tb.getCod_matriz_invalido());
		p_partoCria.setPasto(p_partoCria_tb.getPasto());
		p_partoCria.setCodigo_ferro_cria(p_partoCria_tb.getCodigo_ferro_cria());

		return p_partoCria;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return partos_cria.get(position);
	}

	/*@Override
	public long getItemId(int position) {
		return Long.parseLong(partos_cria.get(position).getCodigo_cria());
	}*/

	@Override
	public long getItemId(int position) {
		return Long.parseLong(partos_cria.get(position).getCodigo_cria());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Parto_Parto_Cria p_pCria_tb = partos_cria.get(position);

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
		sisbov.setText(p_pCria_tb.toString());

		return line;
	}
}
