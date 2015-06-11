package br.com.taurusmobile.util;

public enum MesageDialog {
	
	Toast(1), Yes(2), YesOk(3); 
	
	private final int valor; 
	
	MesageDialog(int valorOpcao){
		valor = valorOpcao; 
	} 
	
	public int getValor(){ 
		return valor; 
	}
	
}
