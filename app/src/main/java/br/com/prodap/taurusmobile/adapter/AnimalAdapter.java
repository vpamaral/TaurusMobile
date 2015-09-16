package br.com.prodap.taurusmobile.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
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

	public Animal AnimalCursor(Cursor c) {
		Animal animal = new Animal();

		while (c.moveToNext()) {
			animal.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
			animal.setId_fk_cria(c.getLong(c.getColumnIndex("id_fk_cria")));
			animal.setCodigo(c.getString(c.getColumnIndex("codigo")));
			animal.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			animal.setIdentificador(c.getString(c
					.getColumnIndex("identificador")));
			if (c.getString(c.getColumnIndex("codigo_ferro")) != null) {
				animal.setCodigo_ferro(c.getString(c
						.getColumnIndex("codigo_ferro")));
			} else {
				animal.setCodigo_ferro("-");
			}
			animal.setData_nascimento(c.getString(c
					.getColumnIndex("data_nascimento")));
			animal.setCategoria(c.getString(c.getColumnIndex("categoria")));
			animal.setRaca(c.getString(c.getColumnIndex("raca")));
			animal.setPeso_atual(c.getDouble(c.getColumnIndex("peso_atual")));
			animal.setRaca_reprod(c.getString(c.getColumnIndex("raca_reprod")));
		}
		return animal;
	}

	public List<Animal> AnimalPreencheArrayCursor(Cursor c) {
		List<Animal> listaAnimal = new ArrayList<Animal>();
		while (c.moveToNext()) {

			Animal animal = new Animal();
			animal.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
			animal.setId_fk_cria(c.getLong(c.getColumnIndex("id_fk_cria")));
			animal.setCodigo(c.getString(c.getColumnIndex("codigo")));
			animal.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			animal.setIdentificador(c.getString(c
					.getColumnIndex("identificador")));
			if (c.getString(c.getColumnIndex("codigo_ferro")) != null) {
				animal.setCodigo_ferro(c.getString(c
						.getColumnIndex("codigo_ferro")));
			} else {
				animal.setCodigo_ferro("-");
			}
			animal.setData_nascimento(c.getString(c
					.getColumnIndex("data_nascimento")));
			animal.setCategoria(c.getString(c.getColumnIndex("categoria")));
			animal.setRaca(c.getString(c.getColumnIndex("raca")));
			animal.setPeso_atual(c.getDouble(c.getColumnIndex("peso_atual")));
			animal.setRaca_reprod(c.getString(c.getColumnIndex("raca_reprod")));

			listaAnimal.add(animal);
		}

		return listaAnimal;
	}

	public ArrayList<Animal> AnimalPreencheArrayHelper(Animal[] AnimalArray) {
		ArrayList<Animal> listaAnimal = new ArrayList<Animal>();
		for (int i = 0; i < AnimalArray.length; i++) {

			Animal animal = new Animal();
			animal.setId_pk(AnimalArray[i].getId_pk());
			animal.setId_fk_cria(AnimalArray[i].getId_fk_cria());
			animal.setCodigo(AnimalArray[i].getCodigo());
			animal.setSisbov(AnimalArray[i].getSisbov());
			animal.setIdentificador(AnimalArray[i].getIdentificador());
			animal.setCodigo_ferro(AnimalArray[i].getCodigo_ferro() != null
									? AnimalArray[i].getCodigo_ferro() : "");
			animal.setData_nascimento(AnimalArray[i].getData_nascimento());
			animal.setCategoria(AnimalArray[i].getCategoria());
			animal.setRaca(AnimalArray[i].getRaca());
			animal.setPeso_atual(AnimalArray[i].getPeso_atual());
			animal.setRaca_reprod(AnimalArray[i].getRaca_reprod());

			listaAnimal.add(animal);
		}
		return listaAnimal;
	}

	public Animal AnimalHelper(Animal animalTB) {
		Animal animal = new Animal();

		animal.setId_pk(animalTB.getId_pk());
		animal.setId_fk_cria(animalTB.getId_fk_cria());
		animal.setCodigo(animalTB.getCodigo());
		animal.setSisbov(animalTB.getSisbov() != null ? animalTB.getSisbov() : "");
		animal.setIdentificador(animalTB.getIdentificador() != null ? animalTB.getIdentificador() : "");
		animal.setCodigo_ferro(animalTB.getCodigo_ferro() != null ? animalTB.getCodigo_ferro() : "");
		animal.setData_nascimento(animalTB.getData_nascimento());
		animal.setCategoria(animalTB.getCategoria());
		animal.setRaca(animalTB.getRaca());
		animal.setPeso_atual(animalTB.getPeso_atual());
		animal.setRaca_reprod(animalTB.getRaca_reprod());

		return animal;
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
		sisbov.setText(animal.toString());

		return line;
	}
}
