package br.com.prodap.taurusmobile.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.ui.R;

public class AnimalAdapter extends BaseAdapter {
	private List<Animal> animais;
	private Activity activity;

	public AnimalAdapter() {}

	public AnimalAdapter(List<Animal> animais, Activity activity) {
		this.animais = animais;
		this.activity = activity;
	}

	public Animal cursorAnimais(Cursor c) {
		Animal a_tb = new Animal();
		while (c.moveToNext()) {
			a_tb = getAnimal(c);
		}
		return a_tb;
	}

	public List<Animal> arrayCursorAnimais(Cursor c) {
		List<Animal> a_list = new ArrayList<Animal>();
		while (c.moveToNext()) {
			a_list.add(getAnimal(c));
		}
		return a_list;
	}

	@NonNull
	private Animal getAnimal(Cursor c) {
		Animal a_tb = new Animal();
		a_tb.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
		a_tb.setCodigo(c.getString(c.getColumnIndex("codigo")));
		a_tb.setIdentificador(c.getString(c.getColumnIndex("identificador")) != null ? c.getString(c.getColumnIndex("identificador")) : "");
		a_tb.setCodigo_ferro(c.getString(c.getColumnIndex("codigo_ferro")) != null ? c.getString(c.getColumnIndex("codigo_ferro")) : "");
		a_tb.setData_nascimento(c.getString(c.getColumnIndex("data_nascimento")));

		//a_tb.setId_fk_cria(c.getLong(c.getColumnIndex("id_fk_cria")));
		//a_tb.setSisbov(c.getString(c.getColumnIndex("sisbov")) != null ? c.getString(c.getColumnIndex("sisbov")) : "");
		//a_tb.setCategoria(c.getString(c.getColumnIndex("categoria")));
		//a_tb.setRaca(c.getString(c.getColumnIndex("raca")));
		//a_tb.setPeso_atual(c.getDouble(c.getColumnIndex("peso_atual")));
		//a_tb.setRaca_reprod(c.getString(c.getColumnIndex("raca_reprod")));

		return a_tb;
	}

	public ArrayList<Animal> arrayAnimais(Animal[] AnimalArray) {
		ArrayList<Animal> a_list = new ArrayList<Animal>();
		for (int i = 0; i < AnimalArray.length; i++) {
			Animal a_tb = new Animal();
			a_tb.setId_pk(AnimalArray[i].getId_pk());
			a_tb.setCodigo(AnimalArray[i].getCodigo());
			a_tb.setIdentificador(AnimalArray[i].getIdentificador() != null ? AnimalArray[i].getIdentificador() : "");
			a_tb.setCodigo_ferro(AnimalArray[i].getCodigo_ferro() != null ? AnimalArray[i].getCodigo_ferro() : "");
			a_tb.setData_nascimento(AnimalArray[i].getData_nascimento());

			//animal.setId_fk_cria(AnimalArray[i].getId_fk_cria());
			//animal.setSisbov(AnimalArray[i].getSisbov() != null	? AnimalArray[i].getSisbov() : "");
			//animal.setCategoria(AnimalArray[i].getCategoria());
			//animal.setRaca(AnimalArray[i].getRaca());
			//animal.setPeso_atual(AnimalArray[i].getPeso_atual());
			//animal.setRaca_reprod(AnimalArray[i].getRaca_reprod());

			a_list.add(a_tb);
		}
		return a_list;
	}

	public Animal animalHelper(Animal a_tb) {
		Animal animal_tb = new Animal();

		animal_tb.setId_pk(a_tb.getId_pk());
		animal_tb.setCodigo(a_tb.getCodigo());
		animal_tb.setIdentificador(a_tb.getIdentificador() != null ? a_tb.getIdentificador() : "");
		animal_tb.setCodigo_ferro(a_tb.getCodigo_ferro() != null ? a_tb.getCodigo_ferro() : "");
		animal_tb.setData_nascimento(a_tb.getData_nascimento());

		//animal_tb.setId_fk_cria(animalTB.getId_fk_cria());
		//animal_tb.setSisbov(animalTB.getSisbov() != null ? animalTB.getSisbov() : "");
		//animal_tb.setCategoria(animalTB.getCategoria());
		//animal_tb.setRaca(animalTB.getRaca());
		//animal_tb.setPeso_atual(animalTB.getPeso_atual());
		//animal_tb.setRaca_reprod(animalTB.getRaca_reprod());

		return animal_tb;
	}

	@Override
	public int getCount() {
		return animais.size();
	}

	@Override
	public Object getItem(int position) {
		return animais.get(position);
	}

	@Override
	public long getItemId(int position) {
		return animais.get(position).getId_pk();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Animal animal = animais.get(position);

		LayoutInflater inflater = activity.getLayoutInflater();
		View line = inflater.inflate(R.layout.activity_lista_animais, null);

		/*if (position % 2 == 0) {
			line.setBackgroundColor(activity.getResources().
					getColor(R.color.linha_par));
		}else
		{
			line.setBackgroundColor(activity.getResources().
					getColor(R.color.linha_impar));
		}*/

		TextView sisbov = (TextView) line.findViewById(R.id.lblSisbov);
		sisbov.setText(animal.getCodigo());

		return line;
	}
}
