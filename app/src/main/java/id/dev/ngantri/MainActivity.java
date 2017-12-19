package id.dev.ngantri;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import id.dev.ngantri.model.Antrian;

public class MainActivity extends AppCompatActivity {

    EditText editTextNama;
    EditText editTextKeperluan;
    Button buttonAntri;
    TextView editTextNOmer;
    TextView textVieSaatIni;
    CardView cardView;
    Antrian dataModel = new Antrian();
    DatabaseReference databaseReference;
    ProgressDialog progres;
    Integer temp ;
    SharedPreferences pref;
    String nomerAngka ="0";
    ArrayList<Antrian> antrianArrayList=new ArrayList<Antrian>();
    private static final  int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNama = (EditText)findViewById(R.id.editNama);
        editTextKeperluan = (EditText)findViewById(R.id.editKeperluan);
        cardView = (CardView)findViewById(R.id.cardView);
        textVieSaatIni =(TextView)findViewById(R.id.textViewSaatIni);
        buttonAntri = (Button)findViewById(R.id.btnAmbilAntrian);
        progres = new ProgressDialog(MainActivity.this);
        editTextNOmer = (TextView) findViewById(R.id.editNomer);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        textVieSaatIni.setText("10");


        final ArrayList<String> nomer2 = new ArrayList<>();
        final ArrayList<Boolean> status = new ArrayList<>();

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                NomerIndek.clear();
//                nomer2.clear();
////                status.clear();
//                temp = 0;
//                for(DataSnapshot data : dataSnapshot.child("Daftar").getChildren()){
//                    Antrian antrian = dataSnapshot.getValue(Antrian.class);
//                    NomerIndek.add(antrian);
//                    Log.d("DANCOK", ""+antrian.getNo());
//                    antrianArrayList.add(antrian);
//                    nomer2.add(antrian.getNo());
//                    status.add(antrian.getStatus());
//                }
//
//                Log.d("Nomer",nomer2.size()+"");
//                Log.d("Size",NomerIndek.size()+"");
//                textVieSaatIni.setVisibility(View.VISIBLE);
//                            textVieSaatIni.setText(NomerIndek.size() + " ");
//                temp = NomerIndek.size()+1;
////                            textVieSaatIni.setText(temp);
//                Log.d("status",status+"");
//                int tes = Integer.parseInt(nomerAngka);
////                for(int i =0;i <nomer2.size();i++){
////                    if (status.get(i)){
////                        Log.d("Di sini nomer" ,nomer2.get(i).toString() + "");
////                    }
////                }
//                if(tes != 0 && temp  == tes){
//                    notification();
//                }
//                pref = getSharedPreferences("PREFERENSE",MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("Nomer",temp.toString());
//                editor.commit();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        databaseReference.child("Daftar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                antrianArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Antrian antrian=dataSnapshot1.getValue(Antrian.class);
                    //antrian.setKey(dataSnapshot1.getKey());

                    antrianArrayList.add(antrian);
                    Log.d("nomer",antrian.getNo());
                }
                textVieSaatIni.setVisibility(View.VISIBLE);
                            textVieSaatIni.setText(antrianArrayList.size());
                temp = antrianArrayList.size()+1;
//                            textVieSaatIni.setText(temp);
                Log.d("status",status+"");
                int tes = Integer.parseInt(nomerAngka);
//                for(int i =0;i <nomer2.size();i++){
//                    if (status.get(i)){
//                        Log.d("Di sini nomer" ,nomer2.get(i).toString() + "");
//                    }
//                }
                if(tes != 0 && temp  == tes){
                    notification();
                }
                pref = getSharedPreferences("PREFERENSE",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Nomer",temp.toString());
                editor.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buttonAntri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextNama.getText().toString().isEmpty()){
                    editTextNama.setError("Nama anda harus diisi");
                }else if(editTextKeperluan.getText().toString().isEmpty()){
                    editTextKeperluan.setError("Keperluan anda harus diisi!");
                }else{
                    progres.setTitle("Sedang Menghitung nomer antrian");
                    progres.setMessage("Harap Tunggu");
                    progres.show();
                    progres.setCancelable(false);
                    progres.setCanceledOnTouchOutside(false);

                    final  Sharepreferense sharepreferense = new Sharepreferense(MainActivity.this);
                    nomerAngka = sharepreferense.getNomer() ;
                    Log.d("angkaAwal",temp+"");
                    Log.d("angka",nomerAngka);
                    addData(editTextNama.getText().toString(),editTextKeperluan.getText().toString(),nomerAngka);
                    progres.dismiss();
                }

            }
        });
    }

    private void addData(String Nama, String keperluan,String No) {
//            progres.dismiss();
            dataModel.setNo(No);
            dataModel.setNama(Nama);
            dataModel.setKeperluan(keperluan);
            dataModel.setStatus(false);
            dataModel.setTanggal("13/12/2007");
            databaseReference.child("Daftar").child(No).setValue(dataModel);
            cardView.setVisibility(View.VISIBLE);
            editTextNOmer.setText(No);
    }
    private void notification() {

        Intent intentNotife = new Intent(getApplicationContext(),MainActivity.class);
        intentNotife.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(),0,intentNotife,0);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ngantri")
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setContentText("Antrian Anda Sudah Dekat");

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);

        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}
