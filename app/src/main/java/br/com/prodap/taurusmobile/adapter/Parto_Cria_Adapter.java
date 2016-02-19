package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.view.R;

public class Parto_Cria_Adapter extends BaseAdapter{
	private List<Parto_Cria> partos_cria;
	private Activity activity;

	public Parto_Cria_Adapter() {

	}

	public Parto_Cria_Adapter(List<Parto_Cria> partos_cria, Activity activity) {
		this.partos_cria = partos_cria;
		this.activity = activity;
	}
	
	public Parto_Cria PartoCriaCursor(Cursor c) {
		Parto_Cria parto_cria = new Parto_Cria();

		while (c.moveToNext()) {
			parto_cria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			parto_cria.setId_fk_parto(c.getLong(c.getColumnIndex("id_fk_parto")));
			parto_cria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			parto_cria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			parto_cria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			parto_cria.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			parto_cria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));
			parto_cria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			parto_cria.setSexo(c.getString(c.getColumnIndex("sexo")));
			parto_cria.setRaca_cria(c.getString(c.getColumnIndex("raca_cria")));
			parto_cria.setData_identificacao(c.getString(c.getColumnIndex("data_identificacao")));
			parto_cria.setRepasse(c.getString(c.getColumnIndex("repasse")));
			parto_cria.setTipo_parto(c.getString(c.getColumnIndex("tipo_parto")));
			parto_cria.setCod_matriz_invalido(c.getString(c.getColumnIndex("cod_matriz_invalido")));
			parto_cria.setPasto(c.getString(c.getColumnIndex("pasto")));

		}
		return parto_cria;
	}

	@NonNull
	public ContentValues getDadosCria(Parto_Cria c_tb) {
		ContentValues c_dados = new ContentValues();
		c_dados.put("id_fk_animal_mae", c_tb.getId_fk_animal_mae());
		c_dados.put("id_fk_parto", c_tb.getId_fk_parto());
		c_dados.put("data_identificacao", c_tb.getData_identificacao());
		c_dados.put("repasse", c_tb.getRepasse());
		c_dados.put("sisbov", c_tb.getSync_status());
		c_dados.put("raca_cria",  c_tb.getRaca_cria());
		c_dados.put("identificador", c_tb.getIdentificador());
		c_dados.put("grupo_manejo", c_tb.getGrupo_manejo());
		c_dados.put("sync_status", c_tb.getSync_status());
		c_dados.put("peso_cria", c_tb.getPeso_cria());
		c_dados.put("codigo_cria",  c_tb.getCodigo_cria());
		c_dados.put("sexo", c_tb.getSexo());
		c_dados.put("tipo_parto", c_tb.getTipo_parto());
		c_dados.put("cod_matriz_invalido", c_tb.getCod_matriz_invalido());
		c_dados.put("pasto",  c_tb.getPasto());

		return c_dados;
	}

	public List<Parto_Cria> arrayPartoCria(Cursor c) {
		List<Parto_Cria> listaPartoCria = new ArrayList<Parto_Cria>();
		while (c.moveToNext()) {

			Parto_Cria parto_cria = new Parto_Cria();

			parto_cria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			parto_cria.setId_fk_parto(c.getLong(c.getColumnIndex("id_fk_parto")));
			parto_cria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			parto_cria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			parto_cria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			parto_cria.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			parto_cria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));			
			parto_cria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			parto_cria.setSexo(c.getString(c.getColumnIndex("sexo")));
			parto_cria.setRaca_cria(c.getString(c.getColumnIndex("raca_cria")));
			parto_cria.setData_identificacao(c.getString(c.getColumnIndex("data_identificacao")));
			parto_cria.setRepasse(c.getString(c.getColumnIndex("repasse")));
			parto_cria.setTipo_parto(c.getString(c.getColumnIndex("tipo_parto")));
			parto_cria.setCod_matriz_invalido(c.getString(c.getColumnIndex("cod_matriz_invalido")));
			parto_cria.setPasto(c.getString(c.getColumnIndex("pasto")));

			listaPartoCria.add(parto_cria);
		}

		return listaPartoCria;
	}

	public ArrayList<Parto_Cria> PartoCriaPreencheArrayHelper(Parto_Cria[] PartoCriaArray) {
		ArrayList<Parto_Cria> listaPartoCria = new ArrayList<Parto_Cria>();
		for (int i = 0; i < PartoCriaArray.length; i++) {

			Parto_Cria parto_cria = new Parto_Cria();

			parto_cria.setId_fk_animal_mae(PartoCriaArray[i].getId_fk_animal_mae());
			parto_cria.setId_fk_parto(PartoCriaArray[i].getId_fk_parto());
			parto_cria.setSisbov(PartoCriaArray[i].getSisbov());
			parto_cria.setIdentificador(PartoCriaArray[i].getIdentificador());
			parto_cria.setGrupo_manejo(PartoCriaArray[i].getGrupo_manejo());
			parto_cria.setSync_status(PartoCriaArray[i].getSync_status());
			parto_cria.setPeso_cria(PartoCriaArray[i].getPeso_cria());
			parto_cria.setCodigo_cria(PartoCriaArray[i].getCodigo_cria());
			parto_cria.setSexo(PartoCriaArray[i].getSexo());
			parto_cria.setRaca_cria(PartoCriaArray[i].getRaca_cria());
			parto_cria.setData_identificacao(PartoCriaArray[i].getData_identificacao());
			parto_cria.setRepasse(PartoCriaArray[i].getRepasse());
			parto_cria.setTipo_parto(PartoCriaArray[i].getTipo_parto());
			parto_cria.setCod_matriz_invalido(PartoCriaArray[i].getCod_matriz_invalido());
			parto_cria.setPasto(PartoCriaArray[i].getPasto());

			listaPartoCria.add(parto_cria);
		}
		return listaPartoCria;
	}

	public Parto_Cria PartoCriaHelper(Parto_Cria parto_cria_tb) {
		Parto_Cria parto_cria = new Parto_Cria();

		parto_cria.setId_fk_animal_mae(parto_cria_tb.getId_fk_animal_mae());
		parto_cria.setId_fk_parto(parto_cria_tb.getId_fk_parto());
		parto_cria.setSisbov(parto_cria_tb.getSisbov());
		parto_cria.setIdentificador(parto_cria_tb.getIdentificador());
		parto_cria.setGrupo_manejo(parto_cria_tb.getGrupo_manejo());
		parto_cria.setSync_status(parto_cria_tb.getSync_status());
		parto_cria.setPeso_cria(parto_cria_tb.getPeso_cria());
		parto_cria.setCodigo_cria(parto_cria_tb.getCodigo_cria());
		parto_cria.setSexo(parto_cria_tb.getSexo());
		parto_cria.setRaca_cria(parto_cria_tb.getRaca_cria());
		parto_cria.setData_identificacao(parto_cria_tb.getData_identificacao());
		parto_cria.setRepasse(parto_cria_tb.getRepasse());
		parto_cria.setTipo_parto(parto_cria_tb.getTipo_parto());
		parto_cria.setCod_matriz_invalido(parto_cria_tb.getCod_matriz_invalido());
		parto_cria.setPasto(parto_cria_tb.getPasto());

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
