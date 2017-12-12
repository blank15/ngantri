package id.dev.ngantri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Integer> nomorAntrian=new ArrayList<Integer>();
    ArrayList<String> namaPengantri=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        nomorAntrian.add(1);
        nomorAntrian.add(2);
        nomorAntrian.add(3);
        nomorAntrian.add(4);
        nomorAntrian.add(5);
        nomorAntrian.add(6);
        nomorAntrian.add(7);
        nomorAntrian.add(8);
        nomorAntrian.add(9);
        nomorAntrian.add(10);
        namaPengantri.add("Yudha Pratama");
        namaPengantri.add("Bahtiyar Istiyarno");
        namaPengantri.add("Yudistiro Septian");
        namaPengantri.add("Muhammad Kodrat");
        namaPengantri.add("Rino Ridlo");
        namaPengantri.add("Yudha Pratama");
        namaPengantri.add("Bahtiyar Istiyarno");
        namaPengantri.add("Yudistiro Septian");
        namaPengantri.add("Muhammad Kodrat");
        namaPengantri.add("Rino Ridlo");

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewDaftarntrian);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewDaftarAntrian adapter=new RecyclerViewDaftarAntrian(this, nomorAntrian, namaPengantri);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
