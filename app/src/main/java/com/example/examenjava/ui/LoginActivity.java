package com.example.examenjava.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.examenjava.R;
import com.example.examenjava.commons.Constantes;
import com.example.examenjava.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText etNick;
    Button btnStart;
    String nick;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //conexion a firestore

        db = FirebaseFirestore.getInstance();

        etNick = findViewById(R.id.editTextNick);
        btnStart = findViewById(R.id.buttonStart);


        //cambiar tipo de fuente

        Typeface typeface = Typeface.createFromAsset(getAssets(), "pixel.ttf");
        etNick.setTypeface(typeface);
        btnStart.setTypeface(typeface);
        //eventos: evento click

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nick = etNick.getText().toString();

                if(nick.isEmpty()){
                    etNick.setError("El nombre de usuario es obligatorio");
                }else if(nick.length() < 3) {
                    etNick.setError("Minimo 3 caracteres");
                }else{
                    addNickAndStart();
                }

            }
        });


    }

    private void addNickAndStart() {
        db.collection("users").whereEqualTo("nick",nick)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            etNick.setError("El nick no esta disponible");
                        }else{
                            addNickToFirestore();
                        }
                    }
                });


    }

    private void addNickToFirestore() {
        User nuevoUsuario = new User(nick,0  );
        db.collection("users")
                .add(nuevoUsuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        etNick.setText("");
                        Intent i = new Intent(LoginActivity.this, GameActivity.class);
                        i.putExtra(Constantes.EXTRA_NICK, nick);
                        i.putExtra(Constantes.EXTRA_ID, documentReference.getId());

                        startActivity(i);
                    }
                });

    }

}
