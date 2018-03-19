package com.gabrielcouto.whatsapp.whatsappclone.helper;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {
    private Context context;
    private SharedPreferences preferences;
    private final String PREF ="WHATS_DADOS";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    public static final String CHAVE_NOME="nome";
    public static final String CHAVE_TELEFONE="telefone";
    public static final String CHAVE_TOKEN="token";

    public Preferencias(Context contexto) {
        context = contexto;
        preferences = context.getSharedPreferences(PREF,MODE);
        editor = preferences.edit();
    }
    public void salvarUsuario(String nomeUsuario,String telefoneUsuario,String token){
        editor.putString(CHAVE_NOME,nomeUsuario);
        editor.putString(CHAVE_TELEFONE,telefoneUsuario);
        editor.putString(CHAVE_TOKEN,token);
        editor.commit();


    }
    public HashMap<String,String> getDadosUsuario(){
        HashMap<String,String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(CHAVE_NOME,preferences.getString(CHAVE_NOME,null));
        dadosUsuario.put(CHAVE_TELEFONE,preferences.getString(CHAVE_TELEFONE,null));
        dadosUsuario.put(CHAVE_TOKEN,preferences.getString(CHAVE_TOKEN,null));
        return dadosUsuario;
    }
}
