package br.com.taurusmobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.taurusmobile.TB.Animal;

public class AnimalAdapter {

	public AnimalAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Animal> AnimalPreencheArrayCursor(Cursor c)
	{
		List<Animal> listaAnimal = new ArrayList<Animal>();
		while(c.moveToNext()) {

			Animal animal = new Animal();
			animal.setId_pk(c.getInt(c.getColumnIndex("id_pk")));
			animal.setId_fk_cria(c.getInt(c.getColumnIndex("id_fk_cria")));
			animal.setCodigo(c.getString(c.getColumnIndex("codigo")));
			animal.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			animal.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			if (c.getString(c.getColumnIndex("codigo_ferro")) != null) {
				animal.setCodigo_ferro(c.getString(c.getColumnIndex("codigo_ferro")));
			} else {
				animal.setCodigo_ferro("-");
			}
			animal.setData_nascimento(c.getString(c.getColumnIndex("data_nascimento")));
			animal.setCategoria(c.getString(c.getColumnIndex("categoria")));
			animal.setRaca(c.getString(c.getColumnIndex("raca")));
			animal.setPeso_atual(c.getDouble(c.getColumnIndex("peso_atual")));
			animal.setGrau_sangue(c.getString(c.getColumnIndex("grau_sangue")));

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
			if (AnimalArray[i].getCodigo_ferro() != null) {
				animal.setCodigo_ferro(AnimalArray[i].getCodigo_ferro());
			} else {
				animal.setCodigo_ferro("-");
			}
			animal.setData_nascimento(AnimalArray[i].getData_nascimento());
			animal.setCategoria(AnimalArray[i].getCategoria());
			animal.setRaca(AnimalArray[i].getRaca());
			animal.setPeso_atual(AnimalArray[i].getPeso_atual());
			animal.setGrau_sangue(AnimalArray[i].getGrau_sangue());

			listaAnimal.add(animal);
		}
		return listaAnimal;
	}

	public Animal AnimalHelper(Animal animalTB) {
		Animal animal = new Animal();

		animal.setId_pk(animalTB.getId_pk());
		animal.setId_fk_cria(animalTB.getId_fk_cria());
		animal.setCodigo(animalTB.getCodigo());
		animal.setSisbov(animalTB.getSisbov());
		animal.setIdentificador(animalTB.getIdentificador());
		if (animalTB.getCodigo_ferro() != null) {
			animal.setCodigo_ferro(animalTB.getCodigo_ferro());
		} else {
			animal.setCodigo_ferro("-");
		}
		animal.setData_nascimento(animalTB.getData_nascimento());
		animal.setCategoria(animalTB.getCategoria());
		animal.setRaca(animalTB.getRaca());
		animal.setPeso_atual(animalTB.getPeso_atual());
		animal.setGrau_sangue(animalTB.getGrau_sangue());

		return animal;
	}
}
