package com.example.orcamentodomestico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.text.TextUtils.isEmpty;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);

        edtEmail = findViewById(R.id.emailReset);
        Button btnResetPassword = findViewById(R.id.btnReset);
        Button btnBack = findViewById(R.id.btn_back);

        mAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email = edtEmail.getText().toString().trim();

                        if (isEmpty(email)) {
                            Toast.makeText(getApplicationContext(), "Digite seu email!", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        mAuth
                                .sendPasswordResetEmail(email)
                                .addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Toast.makeText(
                                                            getApplicationContext(),
                                                            "Acesse o link enviado para seu email!",
                                                            Toast.LENGTH_LONG)
                                                            .show();

                                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(
                                                            getApplicationContext(),
                                                            "Redefinição de senha falhou! Tente novamente!",
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }
                                        });
                    }
                });

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
}
