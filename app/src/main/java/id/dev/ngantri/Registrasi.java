package id.dev.ngantri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrasi extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    TextView textViewSignin;
    Button buttonRegistrasi;
    ProgressDialog progres;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        editTextEmail = (EditText)findViewById(R.id.editTextEmailReg);
        editTextPassword = (EditText)findViewById(R.id.editTextPaswordReg);
        buttonRegistrasi = (Button)findViewById(R.id.buttonRegis);
        textViewSignin = (TextView) findViewById(R.id.textViewLogin);
        fAuth = FirebaseAuth.getInstance();
        progres = new ProgressDialog(Registrasi.this);
        buttonRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().isEmpty()){
                    editTextEmail.setError("Email tidak boleh kosong!");
                }else if(editTextPassword.getText().toString().isEmpty()){
                    editTextPassword.setError("Password tidak boleh kosong");
                }else {

                    progres.setTitle("Login");
                    progres.setMessage("Harap Tunggu");
                    progres.show();
                    progres.setCancelable(false);
                    progres.setCanceledOnTouchOutside(false);
                    signUp(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                }
            }
        });

        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registrasi.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void signUp(String e, String pss) {
        fAuth.createUserWithEmailAndPassword(e, pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progres.dismiss();
                Log.d("errror",task.getException() + "");
                if(!task.isSuccessful()){

                    Toast.makeText(Registrasi.this,"Email atau password sudah terdaftar!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Registrasi.this,"Pendaftaran berhasil silahkan login!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registrasi.this,Login.class);
                    startActivity(intent);
                }
            }
        });
    }
}
