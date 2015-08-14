package br.com.prodap.taurusmobile.util;

public enum MessageDialog {
	
	Toast(1), Yes(2), YesOk(3); 
	
	private final int valor; 
	
	MessageDialog(int valorOpcao){
		valor = valorOpcao; 
	} 
	
	public int getValor(){ 
		return valor; 
	}
	
}
