package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.helper.Configuracao_Helper;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Configuracao_Activity extends Activity
{
	private Configuracao c_tb;
	private Configuracao_Helper c_helper;
	private Configuracao_Model c_model;
	private List<Configuracao> c_list;
	private Mensagem_Util md;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_configuracao);

		source();

		setTitle("Configuração do Sistema");

		Intent intent   = getIntent();
		c_tb            = (Configuracao) intent.getSerializableExtra("c_tb");

		if (c_tb != null)
		{
			c_helper.FillForm(this, c_tb);
		}
	}

	private void source()
	{
		c_helper  = new Configuracao_Helper(this);
		c_tb      = new Configuracao();
		c_model   = new Configuracao_Model(this);
	}

	private void loadMenuPrincipal()
	{
		Intent intent = new Intent(Configuracao_Activity.this, Menu_Principal_Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void btn_confirmar_Click(View v)
	{
		c_tb      = c_helper.getConfig(this);
		newConfiguracao(c_tb);
	}

	public void newConfiguracao(final Configuracao c_tb)
	{
		try
		{
			c_list = c_model.selectAll(this, "Configuracao", c_tb);

			if (c_list.size() > 0 )
			{
				c_model.validate(this, "Configuracao", c_tb, Constantes.VALIDATION_TYPE_UPDATE);
				updateConfiguracao(c_tb);
				Mensagem_Util.addMsg(Message_Dialog.Toast, Configuracao_Activity.this, "Dados atualisados com sucesso.");
                loadMenuPrincipal();
			}
			else
			{
				c_tb.setId_auto(1);
				c_model.validate(this, "Configuracao", c_tb, Constantes.VALIDATION_TYPE_INSERT);
				insertConfiguracao(c_tb);
				Mensagem_Util.addMsg(Message_Dialog.Toast, Configuracao_Activity.this, "Dados inserido com sucesso");
                loadMenuPrincipal();
			}
		}
		catch (final Validator_Exception e)
		{
			if (e.getException_code() == Validator_Exception.MESSAGE_TYPE_QUESTION)
			{
				md.addMsg(Configuracao_Activity.this, "Aviso" , e.getMessage(), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

					}
				});
				return;
			}
			else
			{
				md.addMsg(Message_Dialog.Yes, Configuracao_Activity.this, e.getMessage(), "Aviso", 1);
				return;
			}
		}
	}

	private void insertConfiguracao(Configuracao c_tb)
	{
		c_model = new Configuracao_Model(this);
		c_model.insert(this, "Configuracao", c_helper.getDadosConfiguracao(c_tb));
	}

	private void updateConfiguracao(Configuracao c_tb)
	{
		c_model = new Configuracao_Model(this);
		c_model.update(this, "Configuracao", c_helper.getDadosConfiguracao(c_tb));
	}

    public void btn_leitor_qrcode_Click(View v)
    {
        Intent intent = new Intent(Configuracao_Activity.this, Leitor_Activity.class);
        startActivity(intent);
    }

	public void btn_cancelar_Click(View v)
	{
		finish();
	}
}
