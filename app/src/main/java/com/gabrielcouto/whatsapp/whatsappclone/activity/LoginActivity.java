package com.gabrielcouto.whatsapp.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gabrielcouto.whatsapp.whatsappclone.R;
import com.gabrielcouto.whatsapp.whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText telefone;
    private EditText nome;
    private EditText codigoPais;
    private EditText codigoArea;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instanciarObj();
        mascarasNumeros();
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = nome.getText().toString();
                String telefoneTodo =codigoPais.getText().toString()+codigoArea.getText().toString()+telefone.getText().toString();
                String telefoneSemFormatacao = telefoneTodo.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");
                Preferencias preferencias = new Preferencias(getApplicationContext());
                gerarToken(nomeUsuario,telefoneSemFormatacao,preferencias);
                HashMap<String,String> dados = preferencias.getDadosUsuario();
                Log.i("nome",dados.get(Preferencias.CHAVE_NOME)+"/"+dados.get(Preferencias.CHAVE_TOKEN));
            }
        });


    }

    private void instanciarObj() {
        telefone = findViewById(R.id.textoNumeroId);
        nome = findViewById(R.id.textoNomeId);
        codigoPais = findViewById(R.id.textoPaisId);
        codigoArea = findViewById(R.id.textoDddId);
        cadastrar = findViewById(R.id.btnCadastrarId);
    }
    private void mascarasNumeros(){
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher watcher = new MaskTextWatcher(telefone, simpleMaskFormatter);
        telefone.addTextChangedListener(watcher);

        SimpleMaskFormatter maskFormatter = new SimpleMaskFormatter("+NN");
        MaskTextWatcher watcher1 = new MaskTextWatcher(codigoPais,maskFormatter);
        codigoPais.addTextChangedListener(watcher1);

        SimpleMaskFormatter formatter = new SimpleMaskFormatter("NN");
        MaskTextWatcher watcher2 = new MaskTextWatcher(codigoArea,formatter);
        codigoArea.addTextChangedListener(watcher2);
    }

    private void gerarToken(String nome,String telefone,Preferencias p){
        Random r = new Random();
        int numero = r.nextInt(9999-1000)+1000;
        String token = String.valueOf(numero);
        p.salvarUsuario(nome,telefone,token);

    }
}
