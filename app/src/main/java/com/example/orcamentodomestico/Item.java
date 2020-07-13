package com.example.orcamentodomestico;

import java.io.Serializable;

public class Item implements Serializable {
    String nome;
    String valor;

    public Item(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
