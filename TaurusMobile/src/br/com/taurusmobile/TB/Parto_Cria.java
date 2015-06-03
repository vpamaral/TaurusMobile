package br.com.taurusmobile.TB;

import java.io.Serializable;

public class Parto_Cria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id_fk_animal_mae;
	private double peso_cria;
	private String codigo_cria;
	private String sexo;

	public long getId_fk_animal_mae() {
		return id_fk_animal_mae;
	}

	public void setId_fk_animal_mae(long id_fk_animal_mae) {
		this.id_fk_animal_mae = id_fk_animal_mae;
	}

	public double getPeso_cria() {
		return peso_cria;
	}

	public void setPeso_cria(double peso_cria) {
		this.peso_cria = peso_cria;
	}

	public String getCodigo_cria() {
		return codigo_cria;
	}

	public void setCodigo_cria(String codigo_cria) {
		this.codigo_cria = codigo_cria;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}