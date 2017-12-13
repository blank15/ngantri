package id.dev.ngantri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText editTextNama;
    EditText editTextKeperluan;
    Button buttonAntri;
    DataModel dataModel = new DataModel();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNama = (EditText)findViewById(R.id.editNama);
        editTextKeperluan = (EditText)findViewById(R.id.editKeperluan);
        buttonAntri = (Button)findViewById(R.id.btnAmbilAntrian);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonAntri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(editTextNama.getText().toString(),editTextKeperluan.getText().toString());
            }
        });
    }

    private void addData(String nama, String keperluan) {
            dataModel.setNama(nama);
            dataModel.setKeperluan(keperluan);
            dataModel.setStatus("false");
            dataModel.setNomer("01");
            dataModel.setTanggal("12/12/2017");
            databaseReference.child("daftar").child(UUID.randomUUID().toString()).setValue(dataModel);
    }
}
