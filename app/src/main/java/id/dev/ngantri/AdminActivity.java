package id.dev.ngantri;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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
    Button buttonReset;
    ArrayList<Integer> nomorAntrian=new ArrayList<Integer>();
    ArrayList<String> namaPengantri=new ArrayList<String>();
    ArrayList<Antrian> antrianArrayList=new ArrayList<Antrian>();
    private static final  int NOTIFICATION_ID = 1;

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
                antrianArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Antrian antrian=dataSnapshot1.getValue(Antrian.class);
                    //antrian.setKey(dataSnapshot1.getKey());

                    antrianArrayList.add(antrian);
                }
                RecyclerViewDaftarAntrian adapter=new RecyclerViewDaftarAntrian(AdminActivity.this, antrianArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //notification();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });

        buttonReset=(Button)findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Daftar").removeValue();
            }
        });
    }
}
