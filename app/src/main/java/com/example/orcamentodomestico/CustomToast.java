package com.example.orcamentodomestico;

import android.content.Context;
import android.widget.Toast;

public class CustomToast {
    Context context;
    String message;
    public CustomToast(Context context, String message){
        this.context = context;
        this.message = message;
    }

    public void showToast(){
        Toast.makeText(this.context, this.message, Toast.LENGTH_SHORT);
    }
}
