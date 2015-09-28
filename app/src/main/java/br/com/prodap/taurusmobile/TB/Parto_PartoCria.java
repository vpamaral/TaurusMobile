package br.com.prodap.taurusmobile.TB;

import java.io.Serializable;

public class Parto_PartoCria implements Serializable {

	private long id_auto;
	private long id_fk_animal_mae;
	private int sync_status;
	private String peso_cria;
	private String codigo_cria;
	private String sexo;
	private long id_fk_animal;
	private String data_parto;
	private String sexo_parto;
	private String perda_gestacao;
	private String sisbov;
	private String identificador;
	private String grupo_manejo;
	private String data_identificacao;
	private String raca_cria;
	private String repasse;
	private String tipo_parto;
	private String pasto;

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

	public long getId_fk_animal() {
		return id_fk_animal;
	}

	public void setId_fk_animal(long id_fk_animal) {
		this.id_fk_animal = id_fk_animal;
	}

	public String getData_parto() {
		return data_parto;
	}

	public void setData_parto(String data_parto) {
		this.data_parto = data_parto;
	}

	public String getSexo_parto() {
		return sexo_parto;
	}

	public void setSexo_parto(String sexo_parto) {
		this.sexo_parto = sexo_parto;
	}

	public String getPerda_gestacao() {
		return perda_gestacao;
	}

	public void setPerda_gestacao(String perda_gestacao) {
		this.perda_gestacao = perda_gestacao;
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

	public String getRepasse() {
		return repasse;
	}

	public void setRepasse(String repasse) {
		this.repasse = repasse;
	}

	public String getTipo_parto() {
		return tipo_parto;
	}

	public void setTipo_parto(String tipo_parto) {
		this.tipo_parto = tipo_parto;
	}

	public int getSync_status() {
		return sync_status;
	}

	public void setSync_status(int sync_status) {
		this.sync_status = sync_status;
	}

	public String getPasto() {
		return pasto;
	}

	public void setPasto(String pasto) {
		this.pasto = pasto;
	}

	public long getId_auto() {
		return id_auto;
	}

	public void setId_auto(long id_auto) {
		this.id_auto = id_auto;
	}
}
