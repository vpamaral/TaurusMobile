package br.com.prodap.taurusmobile.tb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Parto_Cria_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;

public class Parto_Cria implements Serializable {

	//private long id_auto;
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
	private String pasto;

	/*public long getId_auto() {
		return id_auto;
	}

	public void setId_auto(long id_auto) {
		this.id_auto = id_auto;
	}*/

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
		return codigo_cria + " - " + identificador;
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

	public static class Parto_CriaModel extends BancoService {

        private Banco banco;
        private SQLiteDatabase db;
        private Parto_Cria_Adapter partoCria_adapter;
        private Parto_Cria parto_cria;

        public Parto_CriaModel(Context ctx) {
            partoCria_adapter = new Parto_Cria_Adapter();
        }


        @Override
        public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) {

        }

        public void removerByMae(Context ctx, Long codigo){
            banco = new Banco(ctx);

            db = banco.getWritableDatabase();

            db.delete("Parto_Cria", "id_fk_animal_mae=?", new String[]{codigo.toString()});
        }

        public Parto_Cria selectByCodigo(Context ctx, Integer codigo) {
            banco = new Banco(ctx);

            db = banco.getReadableDatabase();
            Cursor cursor = db.query("Parto_Cria", null, "id_auto=?",
                    new String[] { codigo.toString() }, null, null, "id_auto");

            parto_cria = partoCria_adapter.PartoCriaCursor(cursor);

            return parto_cria;
        }

        @Override
        public List<Parto_Cria> selectAll(Context ctx, String Tabela, Object table) {
            Banco banco = new Banco(ctx);

            Class classe = table.getClass();
            List<Parto_Cria> listadd = new ArrayList<Parto_Cria>();
            String sql = String.format("SELECT * FROM %s WHERE sync_status = 0  ORDER BY codigo_cria ", Tabela);

            Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

            listadd = partoCria_adapter.PartoCriaPreencheArrayCursor(c);

            banco.close();
            return listadd;
        }

        @Override
        public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}