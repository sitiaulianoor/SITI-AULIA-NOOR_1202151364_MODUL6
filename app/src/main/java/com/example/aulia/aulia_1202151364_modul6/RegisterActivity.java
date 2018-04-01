package com.example.aulia.aulia_1202151364_modul6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aulia.aulia_1202151364_modul6.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText registerEmail, registerPassword;
    Button btnRegister;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.rEmail);
        registerPassword = findViewById(R.id.rPass);
        btnRegister = findViewById(R.id.bDaftar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerFirebase();
            }
        });
    }

    private void registerFirebase() {
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();

        // memeriksa jika field email dan password kosong
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Your email is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Your password is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(),
                    "Your password is short!", Toast.LENGTH_SHORT).show();
            return;
        }

        // create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this,
                                "Success create user" + task.isSuccessful(),
                                Toast.LENGTH_SHORT).show();

                        // if register user is not success
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        // if register success
                        else {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
