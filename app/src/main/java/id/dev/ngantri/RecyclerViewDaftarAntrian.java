package id.dev.ngantri;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import id.dev.ngantri.model.Antrian;

/**
 * Created by kikiosha on 12/12/17.
 */

public class RecyclerViewDaftarAntrian extends RecyclerView.Adapter<RecyclerViewDaftarAntrian.ViewHolder> {
    Context context;
    ArrayList<Integer> nomorAntriaan=new ArrayList<Integer>();
    ArrayList<String> namaPengantri=new ArrayList<String>();
    ArrayList<Antrian> antrianArrayList=new ArrayList<Antrian>();
    DatabaseReference database;

    public RecyclerViewDaftarAntrian(Context context, ArrayList<Antrian> antrianArrayList) {
        this.context = context;
        this.antrianArrayList = antrianArrayList;
    }

    public RecyclerViewDaftarAntrian(Context context, ArrayList<Integer> nomorAntriaan, ArrayList<String> namaPengantri) {
        this.context = context;
        this.nomorAntriaan = nomorAntriaan;
        this.namaPengantri = namaPengantri;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewDaftarAntrian.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_antrian, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textViewNomorAntrian.setText(""+antrianArrayList.get(position).getNo());
        holder.textViewNamaPentgantri.setText(""+antrianArrayList.get(position).getNama());
        if (antrianArrayList.get(position).getStatus()==true){
            holder.cardView.setClickable(false);
            holder.cardView.setBackgroundColor(Color.RED);
        } else {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] charSequence={"Clear"};
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Pilihan");
                    builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0 :
                                    Antrian antrian=new Antrian();
                                    antrian.setKey(antrianArrayList.get(position).getKey());
                                    antrian.setNama(antrianArrayList.get(position).getNama());
                                    antrian.setKeperluan(antrianArrayList.get(position).getKeperluan());
                                    antrian.setNo(antrianArrayList.get(position).getNo());
                                    antrian.setTanggal(antrianArrayList.get(position).getTanggal());
                                    antrian.setStatus(true);

                                    updateBarang(antrian);
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return antrianArrayList.size();
    }

    private void updateBarang(Antrian antrian) {
        /**
         * Baris kode yang digunakan untuk mengupdate data barang
         * yang sudah dimasukkan di Firebase Realtime Database
         */
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Daftar") //akses parent index, ibaratnya seperti nama tabel
                .child(antrian.getNo())
                .setValue(antrian) //set value barang yang baru
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Berhasil!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNomorAntrian, textViewNamaPentgantri;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNomorAntrian=(TextView)itemView.findViewById(R.id.nomorAntrian);
            textViewNamaPentgantri=(TextView)itemView.findViewById(R.id.namaPengantri);
            cardView=(CardView)itemView.findViewById(R.id.cardviewAntrian);
        }
    }
}
