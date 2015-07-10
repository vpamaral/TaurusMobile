package br.com.taurusmobile.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class MensagemUtil {
	
	private static ProgressDialog progress;
	
	public MensagemUtil(){
	}
	
	/**
	 * Método de criação de mensagens rápidas.
	 * 
	 * @param Toast
	 * @param activity
	 * @param msg
	 * 
	 */
	public static void addMsg(MessageDialog window, Activity activity, String msg) {
		
		if(window == MessageDialog.Toast){
			Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		}
	}
	
	
	/**
	 * Método de criação de mensagens rápidas.
	 * 
	 * @param Toast
	 * @param Contexto
	 * @param msg
	 * 
	 */
	public static void addMsg(MessageDialog window, Context ctx, String msg) {
		
		if(window == MessageDialog.Toast){
			Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Método de criação de mensagens com botão "Ok"
	 * 
	 * @param messageDialog
	 * @param activity
	 * @param titulo
	 * @param msg
	 * @param icone
	 */
	public static void addMsg(MessageDialog window, Activity activity, String msg, String titulo, int icone){
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setTitle(titulo);
		builderDialog.setMessage(msg);
		builderDialog.setNeutralButton("Ok", null);
		builderDialog.show();
	}
	
	/**
	 * Método para criação de uma mensagem de diálogo com opções de sim ou não
	 * 
	 * @param alertDialogo
	 * @param activity
	 * @param titulo
	 * @param msg
	 * @param icone
	 * @param listener
	 */
	public static void addMsg(Activity activity, String titulo, 
		String msg, OnClickListener listener) {
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setTitle(titulo);
		builderDialog.setMessage(msg);
		builderDialog.setPositiveButton("Sim", listener);
		builderDialog.setNegativeButton("Não", null);
		builderDialog.show();
	}
	
	/**
	 * Método para criação de mensagens rápidas
	 * 
	 * @param ProgressDialog
	 * @param contex
	 * @param titulo
	 * @param msg
	 *
	 */
	public static void addMsg(Context ctx, String titulo, 
		String msg) {
		progress = ProgressDialog.show(ctx, titulo, msg);
	}
	
	/**
	 * Método para criação de mensagens rápidas
	 * 
	 * @param ProgressDialog
	 * @param activity
	 * @param titulo
	 * @param msg
	 *
	 */
	public static void addMsg(Activity activity, String titulo, String msg) {
		progress = ProgressDialog.show(activity, titulo, msg);
	}
	
	public static void closeProgress() {
			progress.dismiss();
	}
}
