package com.example.wanderson.acheivagamap.Presenter;

/**
 * Created by Helio on 08/08/2017.
 */

public abstract class GenericDao<T> {
    public abstract boolean salvar(T t);
    public abstract void pesquisar(String nome);
    public abstract boolean deletar(int id);
    public abstract boolean listar(T t);

}
