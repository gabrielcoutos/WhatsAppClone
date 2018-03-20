package com.gabrielcouto.whatsapp.whatsappclone.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gabrielcouto.whatsapp.whatsappclone.R;
import com.gabrielcouto.whatsapp.whatsappclone.helper.ConfiguracaoFirebase;
import com.gabrielcouto.whatsapp.whatsappclone.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private EditText nome;
    private EditText emailCadastro;
    private EditText senhaCadastro;
    private Button cadastrar;
    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        getInstancia();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(emailCadastro.getText().toString());
                usuario.setSenha(senhaCadastro.getText().toString());
                cadastrarUsuario();

            }
        });
    }

    private void getInstancia(){
        nome = findViewById(R.id.nomeCadastroId);
        emailCadastro = findViewById(R.id.editEmailCadastroId);
        senhaCadastro = findViewById(R.id.editSenhaCadastroId);
        cadastrar = findViewById(R.id.btnCadastroId);
    }
    private void cadastrarUsuario(){
        auth = ConfiguracaoFirebase.getFireBaseAuth();
        auth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this,"Cadastro com sucesso",Toast.LENGTH_SHORT).show();
                    usuario.setId(task.getResult().getUser().getUid());
                    usuario.salvar();
                }else{
                    Toast.makeText(CadastroUsuarioActivity.this,"Cadastro sem sucesso",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
