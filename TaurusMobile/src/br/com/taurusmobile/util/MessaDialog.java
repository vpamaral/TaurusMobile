package br.com.taurusmobile.util;

public enum MessaDialog {
	
	Toast(1), Yes(2), YesOk(3); 
	
	private final int valor; 
	
	MessaDialog(int valorOpcao){
		valor = valorOpcao; 
	} 
	
	public int getValor(){ 
		return valor; 
	}
	
}
