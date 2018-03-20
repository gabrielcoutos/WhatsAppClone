package com.gabrielcouto.whatsapp.whatsappclone.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gabriel Couto on 20/03/2018.
 */

public final class ConfiguracaoFirebase {
    private static DatabaseReference reference;
    private static FirebaseAuth auth;

    public static DatabaseReference getReference() {

        if (reference == null) {
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    public static FirebaseAuth getFireBaseAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
