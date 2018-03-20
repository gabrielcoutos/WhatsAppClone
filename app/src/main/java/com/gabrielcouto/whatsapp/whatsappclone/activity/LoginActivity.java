package com.gabrielcouto.whatsapp.whatsappclone.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gabrielcouto.whatsapp.whatsappclone.Manifest;
import com.gabrielcouto.whatsapp.whatsappclone.R;
import com.gabrielcouto.whatsapp.whatsappclone.helper.Permissao;
import com.gabrielcouto.whatsapp.whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button entrar;
    private String [] permissoesNecessarias = new String[]{
            android.Manifest.permission.SEND_SMS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissao(1,this,permissoesNecessarias);
        instanciarObj();

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void instanciarObj() {
        entrar = findViewById(R.id.btnEntrarId);
        email = findViewById(R.id.editEmailLoginId);
        senha = findViewById(R.id.editSenhaLogin);

    }
    public void  abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this,CadastroUsuarioActivity.class);
        startActivity(intent);
    }


}