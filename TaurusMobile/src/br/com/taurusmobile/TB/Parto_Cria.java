package br.com.taurusmobile.TB;

import java.io.Serializable;

public class Parto_Cria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id_fk_cria_parto;
	private long id_fk_animal_mae;
	private long id_fk_animal_cria;
	private double peso_cria;
	private String __codigo_cria;
	private String __sexo;
	private String __grau_sangue;
	private long __id_fk_animal_pai;

	public long getId_fk_cria_parto() {
		return id_fk_cria_parto;
	}

	public void setId_fk_cria_parto(long id_fk_cria_parto) {
		this.id_fk_cria_parto = id_fk_cria_parto;
	}

	public long getId_fk_animal_mae() {
		return id_fk_animal_mae;
	}

	public void setId_fk_animal_mae(long id_fk_animal_mae) {
		this.id_fk_animal_mae = id_fk_animal_mae;
	}

	public long getId_fk_animal_cria() {
		return id_fk_animal_cria;
	}

	public void setId_fk_animal_cria(long id_fk_animal_cria) {
		this.id_fk_animal_cria = id_fk_animal_cria;
	}

	public double getPeso_cria() {
		return peso_cria;
	}

	public void setPeso_cria(double peso_cria) {
		this.peso_cria = peso_cria;
	}

	public String get__codigo_cria() {
		return __codigo_cria;
	}

	public void set__codigo_cria(String __codigo_cria) {
		this.__codigo_cria = __codigo_cria;
	}

	public String get__sexo() {
		return __sexo;
	}

	public void set__sexo(String __sexo) {
		this.__sexo = __sexo;
	}

	public String get__grau_sangue() {
		return __grau_sangue;
	}

	public void set__grau_sangue(String __grau_sangue) {
		this.__grau_sangue = __grau_sangue;
	}

	public long get__id_fk_animal_pai() {
		return __id_fk_animal_pai;
	}

	public void set__id_fk_animal_pai(long __id_fk_animal_pai) {
		this.__id_fk_animal_pai = __id_fk_animal_pai;
	}

}