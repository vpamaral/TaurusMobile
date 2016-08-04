package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

public class Parto_Cria implements Serializable
{
	private long id_fk_parto;
	private long id_fk_animal_mae;
	private int sync_status;
	private String peso_cria;
	private String codigo_cria;
	private String sexo;
	private String sisbov;
	private String identificador;
	private String grupo_manejo;
	private String data_identificacao;
	private String raca_cria;
	private String repasse;
	private String tipo_parto;
	private String cod_matriz_invalido;
	private String pasto;
	private String codigo_ferro_cria;
	private String criterio;

	public long getId_fk_parto() {
		return id_fk_parto;
	}

	public void setId_fk_parto(long id_fk_parto) {
		this.id_fk_parto = id_fk_parto;
	}

	public String getCod_matriz_invalido() {
		return cod_matriz_invalido;
	}

	public void setCod_matriz_invalido(String cod_matriz_invalido) {
		this.cod_matriz_invalido = cod_matriz_invalido;
	}

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
		return codigo_cria;
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

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public String getCodigo_ferro_cria() {
		return codigo_ferro_cria;
	}

	public void setCodigo_ferro_cria(String codigo_ferro_cria) {
		this.codigo_ferro_cria = codigo_ferro_cria;
	}
}