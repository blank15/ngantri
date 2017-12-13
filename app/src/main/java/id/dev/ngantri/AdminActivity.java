package id.dev.ngantri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.dev.ngantri.model.Antrian;

public class AdminActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Integer> nomorAntrian=new ArrayList<Integer>();
    ArrayList<String> namaPengantri=new ArrayList<String>();
    ArrayList<Antrian> antrianArrayList=new ArrayList<Antrian>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        nomorAntrian.add(1);
        namaPengantri.add("Yudha Pratama");

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewDaftarntrian);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference=FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Daftar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Antrian antrian=dataSnapshot1.getValue(Antrian.class);
                    antrian.setKey(dataSnapshot1.getKey());

                    antrianArrayList.add(antrian);
                }
                RecyclerViewDaftarAntrian adapter=new RecyclerViewDaftarAntrian(AdminActivity.this, antrianArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }
}
