package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

public class Configuracao implements Serializable  {

	private long id_auto;
	private String tipo;
	private String endereco;
	private String valida_identificador;
	private String valida_manejo;
	private String valida_sisbov;
	private String valida_cod_alternativo;
	private String valida_criterio;
	private String valida_pasto;
	private String ultima_atualizacao;

	public long getId_auto() {
		return id_auto;
	}
	public void setId_auto(long id_auto) {
		this.id_auto = id_auto;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getValida_identificador() {
		return valida_identificador;
	}
	public void setValida_identificador(String valida_identificador) {
		this.valida_identificador = valida_identificador;
	}

	public String getValida_manejo() {
		return valida_manejo;
	}
	public void setValida_manejo(String valida_manejo) {
		this.valida_manejo = valida_manejo;
	}

	public String getValida_sisbov() {
		return valida_sisbov;
	}
	public void setValida_sisbov(String valida_sisbov) {
		this.valida_sisbov = valida_sisbov;
	}

	public String getValida_cod_alternativo() {
		return valida_cod_alternativo;
	}
	public void setValida_cod_alternativo(String valida_cod_alternativo) {
		this.valida_cod_alternativo = valida_cod_alternativo;
	}

	public String getValida_criterio() {
		return valida_criterio;
	}
	public void setValida_criterio(String valida_criterio) {
		this.valida_criterio = valida_criterio;
	}

	public String getValida_pasto() {
		return valida_pasto;
	}
	public void setValida_pasto(String valida_pasto) {
		this.valida_pasto = valida_pasto;
	}

	public String getUltimaAtualizacao() {
		return ultima_atualizacao;
	}
	public void setUltimaAtualizacao(String ultima_atualizacao) {
		this.ultima_atualizacao = ultima_atualizacao;
	}

}
