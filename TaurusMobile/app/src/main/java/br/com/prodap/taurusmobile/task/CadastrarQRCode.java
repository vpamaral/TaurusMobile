package br.com.prodap.taurusmobile.task;

import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.ui.ConfiguracoesQRCodeActivity;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
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
		MensagemUtil.addMsg(ctx, "Aguarde...", "Cadastrando informa??es.");
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
		MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Informa??es cadastradas");
	}
}
