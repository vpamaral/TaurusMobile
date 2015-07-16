package br.com.taurusmobile.task;

import br.com.taurusmobile.TB.Configuracoes;
import br.com.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.taurusmobile.model.ConfiguracoesModel;
import br.com.taurusmobile.ui.ConfiguracoesQRCodeActivity;
import br.com.taurusmobile.util.MensagemUtil;
import br.com.taurusmobile.util.MessageDialog;
import android.content.Context;
import android.os.AsyncTask;

public class CadastrarQRCode extends AsyncTask<Void, Void, Void>{
	
	private Context ctx;
	private ConfiguracoesAdapter config_adapter;
	
	public CadastrarQRCode(Context ctx){
		this.ctx = ctx;
	}
	
	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Cadastrando informações.");
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		ConfiguracoesModel configuracaoModel = new ConfiguracoesModel(ctx);
		config_adapter = new ConfiguracoesAdapter();
		Configuracoes configuracaoTB =  new Configuracoes();
		configuracaoTB.setTipo(ConfiguracoesQRCodeActivity.edtTipo.getText().toString());
		configuracaoTB.setEndereco(ConfiguracoesQRCodeActivity.edtEndereco.getText().toString());
		try {
			configuracaoModel.insert(ctx, "Configuracao", config_adapter.configurarHelper(configuracaoTB));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		MensagemUtil.closeProgress();
		MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Informações cadastradas");
	}
}
