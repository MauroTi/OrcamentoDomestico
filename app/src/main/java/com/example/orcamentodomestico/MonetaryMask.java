package com.example.orcamentodomestico;

import android.icu.math.BigDecimal;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class MonetaryMask implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;
    private final Locale locale;

    public MonetaryMask(EditText editText, Locale locale) {
        this.editTextWeakReference = new WeakReference<EditText>(editText);
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    public MonetaryMask(EditText editText) {
        this.editTextWeakReference = new WeakReference<EditText>(editText);
        this.locale = Locale.getDefault();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();
        if (editText == null) return;
        editText.removeTextChangedListener(this);

        BigDecimal parsed = parseToBigDecimal(editable.toString(), locale);
        String formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);

        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private BigDecimal parseToBigDecimal(String value, Locale locale) {
        String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());

        String cleanString = value.replaceAll(replaceable, "");

        return new BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
        );
    }
}


