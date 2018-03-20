package com.gabrielcouto.whatsapp.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gabrielcouto.whatsapp.whatsappclone.R;
import com.gabrielcouto.whatsapp.whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {
        private EditText codigo;
        private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);
        codigo = findViewById(R.id.texttoValidacaoId);
        botao = findViewById(R.id.btnValidarId);

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mascara = new MaskTextWatcher(codigo,simpleMaskFormatter);
        codigo.addTextChangedListener(mascara);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigoValidacao =codigo.getText().toString();
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String,String> dados = preferencias.getDadosUsuario();
                if(!dados.isEmpty()){
                    if(codigoValidacao.equals(dados.get(Preferencias.CHAVE_TOKEN))){
                        Toast.makeText(ValidadorActivity.this,"Deu certo",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ValidadorActivity.this,"Deu certo Nao",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
