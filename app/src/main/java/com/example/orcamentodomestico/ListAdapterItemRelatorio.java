package com.example.orcamentodomestico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapterItemRelatorio extends ArrayAdapter<Item> {

    private Context contexto;
    private ArrayList<Item> lista;

    public ListAdapterItemRelatorio(Context contexto, ArrayList<Item> lista) {
        super(contexto, R.layout.item_relatorio, lista);
        this.contexto = contexto;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public void add(Item object) {
        lista.add(object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item itemPosicao = this.lista.get(position);

        LayoutInflater inflater =
                (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.item_relatorio, parent, false);

        TextView tvNomesLancamentos = convertView.findViewById(R.id.tvNomesLancamentos);
        TextView tvValoresLancamentos = convertView.findViewById(R.id.tvValoresLancamentos);

        if (itemPosicao.getNome() == "") {
            tvNomesLancamentos.setText("Nenhum nome informado");
            tvValoresLancamentos.setText(itemPosicao.valor);
        } else {
            tvNomesLancamentos.setText(itemPosicao.nome);
            tvValoresLancamentos.setText(itemPosicao.valor);
        }

        return convertView;
    }
}
