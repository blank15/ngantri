package id.dev.ngantri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPass;
    Button buttonLogin;
    SharedPreferences pref;
    TextView textViewRegis;
    ProgressDialog progres;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    public GoogleApiClient googleApiClient;
    public static final int RequestSignInCode = 7;
//    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = Login.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPass = (EditText)findViewById(R.id.editTextPasword);
        textViewRegis = (TextView)findViewById(R.id.textViewNewMember);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

        textViewRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Registrasi.class);
                startActivity(intent);
            }
        });

        fAuth = FirebaseAuth.getInstance();
        progres = new ProgressDialog(Login.this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().isEmpty()){
                    editTextEmail.setError("Email tidak boleh kosong!");
                }else if(editTextPass.getText().toString().isEmpty()){
                    editTextPass.setError("Password tidak boleh kosong");
                }else if(editTextEmail.getText().toString().equals("admin") && editTextPass.getText().toString().equals("admin")){
                    Intent intent=new Intent(Login.this, AdminActivity.class);
                    startActivity(intent);
                }else if(editTextEmail.getText().toString().equals("user") && editTextPass.getText().toString().equals("user")){
                    Intent intent=new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }else {

                    progres.setTitle("Login");
                    progres.setMessage("Harap Tunggu");
                    progres.show();
                    progres.setCancelable(false);
                    progres.setCanceledOnTouchOutside(false);
                    signIn(editTextEmail.getText().toString(),editTextPass.getText().toString());
                }
            }
        });

        Button buttonLoginFb=(Button)findViewById(R.id.buttonLoginFacebook);
        Button buttonLoginGoogle=(Button)findViewById(R.id.buttonLoginGoogle);

        //config google sign in
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(Login.this)
                .enableAutoManage(Login.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        buttonLoginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Belum bisa di gunakan ,karena nama package aplikasi belum ada di playstore", Toast.LENGTH_SHORT).show();
            }
        });
        buttonLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RequestSignInCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestSignInCode){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()){

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);
            }

        }
    }

    private void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        Toast.makeText(Login.this,""+ authCredential.getProvider(),Toast.LENGTH_LONG).show();
        fAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progres.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Gagal Login!", Toast.LENGTH_SHORT).show();
                        } else {
                            pref = getSharedPreferences("PREFERENSE", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("Login", "ya");
                            editor.commit();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void signIn(final String email, final String password) {
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progres.dismiss();
                Log.d("Ket",task.isSuccessful()+" " + email + " " +password);
                if(!task.isSuccessful()){
                    Toast.makeText(Login.this,"Email atau password tidak Cocok!",Toast.LENGTH_SHORT).show();
                }else{
                    pref = getSharedPreferences("PREFERENSE",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Login","ya");
                    editor.commit();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
