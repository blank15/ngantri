package id.dev.ngantri;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import id.dev.ngantri.RestAPI.PushAPI;
import id.dev.ngantri.model.Antrian;
import id.dev.ngantri.model.DataNotifikasi;
import id.dev.ngantri.model.Notifikasi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kikiosha on 12/12/17.
 */

public class RecyclerViewDaftarAntrian extends RecyclerView.Adapter<RecyclerViewDaftarAntrian.ViewHolder> {
    Context context;
    ArrayList<Integer> nomorAntriaan=new ArrayList<Integer>();
    ArrayList<String> namaPengantri=new ArrayList<String>();
    ArrayList<Antrian> antrianArrayList=new ArrayList<Antrian>();
    DatabaseReference database;
    public static final String BASE_URL="https://fcm.googleapis.com/";

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
                                    antrian.setToken(antrianArrayList.get(position).getToken());

                                    updateBarang(antrian);

                                    Retrofit retrofit=new Retrofit.Builder()
                                            .baseUrl(BASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    PushAPI pushAPI=retrofit.create(PushAPI.class);


                                    DataNotifikasi dataNotifikasi=new DataNotifikasi();
                                    dataNotifikasi.setTitle("Ngantri");
                                    dataNotifikasi.setText("Saat Ini Adalah Nomor Antrian Anda");

                                    Call<Notifikasi> call=pushAPI.setNotif(new Notifikasi(dataNotifikasi, antrianArrayList.get(position).getToken()));
                                    call.enqueue(new Callback<Notifikasi>() {
                                        @Override
                                        public void onResponse(Call<Notifikasi> call, Response<Notifikasi> response) {
                                            Log.d("Push No ngantri", "Berhasil");
                                        }

                                        @Override
                                        public void onFailure(Call<Notifikasi> call, Throwable t) {
                                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                            Log.d("Response",t.toString());
                                        }
                                    });

                                    try{
                                        DataNotifikasi dataNotifikasi2=new DataNotifikasi();
                                        dataNotifikasi2.setTitle("Ngantri");
                                        dataNotifikasi2.setText("Antrian Anda Sudah Dekat, Saat Ini Sudah No Antrian "+antrianArrayList.get(position).getNo());

                                        Call<Notifikasi> call2=pushAPI.setNotif(new Notifikasi(dataNotifikasi2, antrianArrayList.get(position+1).getToken()));
                                        call2.enqueue(new Callback<Notifikasi>() {
                                            @Override
                                            public void onResponse(Call<Notifikasi> call, Response<Notifikasi> response) {
                                                Log.d("Push No waiting", "Berhasil");
                                                Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<Notifikasi> call, Throwable t) {
                                                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                                Log.d("Response",t.toString());
                                            }
                                        });
                                    } catch (IndexOutOfBoundsException e){
                                        e.printStackTrace();
                                    }
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
                        Log.d("Update", "Berhasil");
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
