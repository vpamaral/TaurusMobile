package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

public class Animal implements Serializable {
	
	private long id_pk;
	//private long id_fk_cria;
	private String codigo;
	//private String sisbov;
	private String identificador;
	private String codigo_ferro;
	private String data_nascimento;
	//private String categoria;
	//private String raca;
	//private double peso_atual;
	//private String raca_reprod;

	public long getId_pk() {
		return id_pk;
	}

	public void setId_pk(long id_pk) {
		this.id_pk = id_pk;
	}

	/*public long getId_fk_cria() {
		return id_fk_cria;
	}

	public void setId_fk_cria(long id_fk_cria) {
		this.id_fk_cria = id_fk_cria;
	}*/

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/*public String getSisbov() {
		return sisbov;
	}

	public void setSisbov(String sisbov) {
		this.sisbov = sisbov;
	}*/

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getCodigo_ferro() {
		return codigo_ferro;
	}

	public void setCodigo_ferro(String codigo_ferro) {
		this.codigo_ferro = codigo_ferro;
	}

	public String getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	/*public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public double getPeso_atual() {
		return peso_atual;
	}

	public void setPeso_atual(double peso_atual) {
		this.peso_atual = peso_atual;
	}

	public String getRaca_reprod() {
		return raca_reprod;
	}

	public void setRaca_reprod(String raca_reprod) {
		this.raca_reprod = raca_reprod;
	}*/

	@Override
	public String toString() {
		return codigo;
	}
}
