package br.com.prodap.taurusmobile.TB;

import java.io.Serializable;

public class Parto_Cria implements Serializable {

	private long id_fk_animal_mae;
	private int fgStatus;
	private String peso_cria;
	private String codigo_cria;
	private String sexo;
	private String sisbov;
	private String identificador;
	private String grupo_manejo;
	private String data_identificacao;
	private String raca_cria;

	public long getId_fk_animal_mae() {
		return id_fk_animal_mae;
	}

	public void setId_fk_animal_mae(long id_fk_animal_mae) {
		this.id_fk_animal_mae = id_fk_animal_mae;
	}

	public String getPeso_cria() {
		return peso_cria;
	}

	public void setPeso_cria(String peso_cria) {
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

	public int getFgStatus() {
		return fgStatus;
	}

	public void setFgStatus(int fgStatus) {
		this.fgStatus = fgStatus;
	}

	public String getSisbov() {
		return sisbov;
	}

	public void setSisbov(String sisbov) {
		this.sisbov = sisbov;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getGrupo_manejo() {
		return grupo_manejo;
	}

	public void setGrupo_manejo(String grupo_manejo) {
		this.grupo_manejo = grupo_manejo;
	}

	public String getData_identificacao() {
		return data_identificacao;
	}

	public void setData_identificacao(String data_identificacao) {
		this.data_identificacao = data_identificacao;
	}

	public String getRaca_cria() {
		return raca_cria;
	}

	public void setRaca_cria(String raca_cria) {
		this.raca_cria = raca_cria;
	}

	@Override
	public String toString() {
		return codigo_cria + " - " + identificador;
	}
}