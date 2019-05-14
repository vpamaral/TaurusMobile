package br.com.prodap.taurusmobile.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Animal;

/**
 * Created by Prodap on 02/02/2017.
 */

public class Animal_Helper
{
    public Animal cursorAnimais(Cursor c)
    {
        Animal a_tb = new Animal();

        while (c.moveToNext())
        {
            a_tb = getAnimal(c);
        }
        return a_tb;
    }

    public List<Animal> arrayAnimais(Cursor c)
    {
        List<Animal> a_list = new ArrayList<Animal>();

        while (c.moveToNext())
        {
            a_list.add(getAnimal(c));
        }
        return a_list;
    }

    @NonNull
    public ContentValues getDadosAnimal(Animal a_tb)
    {
        ContentValues a_dados = new ContentValues();

        a_dados.put("id_pk", a_tb.getId_pk());
        a_dados.put("codigo", a_tb.getCodigo());
        a_dados.put("codigo_ferro", a_tb.getCodigo_ferro());
        a_dados.put("data_nascimento", a_tb.getData_nascimento());
        a_dados.put("identificador", a_tb.getIdentificador());
        a_dados.put("sexo", a_tb.getSexo());
        a_dados.put("situacao_reprodutiva", a_tb.getSituacao_reprodutiva());
        a_dados.put("data_ultimo_dg", a_tb.getData_ultimo_dg());
        a_dados.put("data_parto_provavel", a_tb.getData_parto_provavel());

        return a_dados;
    }

    @NonNull
    private Animal getAnimal(Cursor c)
    {
        Animal a_tb = new Animal();
        a_tb.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
        a_tb.setCodigo(c.getString(c.getColumnIndex("codigo")));
        a_tb.setIdentificador(c.getString(c.getColumnIndex("identificador")) != null ? c.getString(c.getColumnIndex("identificador")) : "");
        a_tb.setCodigo_ferro(c.getString(c.getColumnIndex("codigo_ferro")) != null ? c.getString(c.getColumnIndex("codigo_ferro")) : "");
        a_tb.setData_nascimento(c.getString(c.getColumnIndex("data_nascimento")));
        a_tb.setSexo(c.getString(c.getColumnIndex("sexo")) != null ? c.getString(c.getColumnIndex("sexo")) : "");
        a_tb.setSituacao_reprodutiva(c.getString(c.getColumnIndex("situacao_reprodutiva")) != null ? c.getString(c.getColumnIndex("situacao_reprodutiva")) : "");
        a_tb.setData_ultimo_dg(c.getString(c.getColumnIndex("data_ultimo_dg")) != null ? c.getString(c.getColumnIndex("data_ultimo_dg")) : "");
        a_tb.setData_parto_provavel(c.getString(c.getColumnIndex("data_parto_provavel")) != null ? c.getString(c.getColumnIndex("data_parto_provavel")) : "");

        //a_tb.setId_fk_cria(c.getLong(c.getColumnIndex("id_fk_cria")));
        //a_tb.setSisbov(c.getString(c.getColumnIndex("sisbov")) != null ? c.getString(c.getColumnIndex("sisbov")) : "");
        //a_tb.setCategoria(c.getString(c.getColumnIndex("categoria")));
        //a_tb.setRaca(c.getString(c.getColumnIndex("raca")));
        //a_tb.setPeso_atual(c.getDouble(c.getColumnIndex("peso_atual")));
        //a_tb.setRaca_reprod(c.getString(c.getColumnIndex("raca_reprod")));

        return a_tb;
    }

    public ArrayList<Animal> arrayAnimais(Animal[] AnimalArray)
    {
        ArrayList<Animal> a_list = new ArrayList<Animal>();

        for (int i = 0; i < AnimalArray.length; i++)
        {
            Animal a_tb = new Animal();
            a_tb.setId_pk(AnimalArray[i].getId_pk());
            a_tb.setCodigo(AnimalArray[i].getCodigo());
            a_tb.setIdentificador(AnimalArray[i].getIdentificador() != null ? AnimalArray[i].getIdentificador() : "");
            a_tb.setCodigo_ferro(AnimalArray[i].getCodigo_ferro() != null ? AnimalArray[i].getCodigo_ferro() : "");
            a_tb.setData_nascimento(AnimalArray[i].getData_nascimento());
            a_tb.setSexo(AnimalArray[i].getSexo() != null ? AnimalArray[i].getSexo() : "");
            a_tb.setSituacao_reprodutiva(AnimalArray[i].getSituacao_reprodutiva() != null ? AnimalArray[i].getSituacao_reprodutiva() : "");
            a_tb.setData_ultimo_dg(AnimalArray[i].getData_ultimo_dg() != null ? AnimalArray[i].getData_ultimo_dg() : "");
            a_tb.setData_parto_provavel(AnimalArray[i].getData_parto_provavel() != null ? AnimalArray[i].getData_parto_provavel() : "");

            //animal.setId_fk_cria(AnimalArray[i].getId_fk_cria());
            //animal.setSisbov(AnimalArray[i].getSisbov() != null	? AnimalArray[i].getSisbov() : "");
            //animal.setCategoria(AnimalArray[i].getCategoria());
            //animal.setRaca(AnimalArray[i].getRaca());
            //animal.setPeso_atual(AnimalArray[i].getPeso_atual());
            //animal.setRaca_reprod(AnimalArray[i].getRaca_reprod());

            a_list.add(a_tb);
        }
        return a_list;
    }

    public Animal animalHelper(Animal a_tb)
    {
        Animal animal_tb = new Animal();

        animal_tb.setId_pk(a_tb.getId_pk());
        animal_tb.setCodigo(a_tb.getCodigo());
        animal_tb.setIdentificador(a_tb.getIdentificador() != null ? a_tb.getIdentificador() : "0");
        animal_tb.setCodigo_ferro(a_tb.getCodigo_ferro() != null ? a_tb.getCodigo_ferro() : "0");
        animal_tb.setData_nascimento(a_tb.getData_nascimento());
        animal_tb.setSexo(a_tb.getSexo() != null ? a_tb.getSexo() : "");
        animal_tb.setSituacao_reprodutiva(a_tb.getSituacao_reprodutiva() != null ? a_tb.getSituacao_reprodutiva() : "");
        animal_tb.setData_ultimo_dg(a_tb.getData_ultimo_dg() != null ? a_tb.getData_ultimo_dg() : "");
        animal_tb.setData_parto_provavel(a_tb.getData_parto_provavel() != null ? a_tb.getData_parto_provavel() : "");

        //animal_tb.setId_fk_cria(animalTB.getId_fk_cria());
        //animal_tb.setSisbov(animalTB.getSisbov() != null ? animalTB.getSisbov() : "");
        //animal_tb.setCategoria(animalTB.getCategoria());
        //animal_tb.setRaca(animalTB.getRaca());
        //animal_tb.setPeso_atual(animalTB.getPeso_atual());
        //animal_tb.setRaca_reprod(animalTB.getRaca_reprod());

        return animal_tb;
    }
}
