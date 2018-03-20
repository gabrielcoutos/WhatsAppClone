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
    private EditText telefone;
    private EditText nome;
    private EditText codigoPais;
    private EditText codigoArea;
    private Button cadastrar;
    private String [] permissoesNecessarias = new String[]{
            android.Manifest.permission.SEND_SMS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissao(1,this,permissoesNecessarias);
        instanciarObj();
        mascarasNumeros();
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = nome.getText().toString();
                String telefoneTodo =codigoPais.getText().toString()+codigoArea.getText().toString()+telefone.getText().toString();
                String telefoneSemFormatacao = telefoneTodo.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");
                telefoneSemFormatacao="5554";
                Preferencias preferencias = new Preferencias(getApplicationContext());
                String token = gerarToken(nomeUsuario,telefoneSemFormatacao,preferencias);
                String sms ="Código de confirmação ";
                boolean envio = enviarSms("+"+telefoneSemFormatacao,sms+token);
                if(envio){
                    Intent intent = new Intent(LoginActivity.this,ValidadorActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this,"erro",Toast.LENGTH_SHORT).show();

                }
               /* HashMap<String,String> dados = preferencias.getDadosUsuario();
                Log.i("nome",dados.get(Preferencias.CHAVE_NOME)+"/"+dados.get(Preferencias.CHAVE_TOKEN));*/
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

    private String gerarToken(String nome,String telefone,Preferencias p){
        Random r = new Random();
        int numero = r.nextInt(9999-1000)+1000;
        String token = String.valueOf(numero);
        p.salvarUsuario(nome,telefone,token);

        return token;

    }

    private boolean enviarSms(String telefone,String mensagem){
        boolean allRight;
        try{
            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(telefone,null,mensagem,null,null);
            allRight = true;

        }catch (Exception e){
            e.printStackTrace();
            allRight= false;
        }
        return allRight;

    }


    public void onRequestPermissionsResult(int requestCode,String [] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        for(int resultado:grantResults){
            if(resultado== PackageManager.PERMISSION_DENIED){
                alertaPermissao();
            }
        }
    }
    private void alertaPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissao negada");
        builder.setMessage("Necessário aceitar as permissoes");
        builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}