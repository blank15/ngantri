package id.dev.ngantri;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewNomorAntrian.setText(""+antrianArrayList.get(position).getNo());
        holder.textViewNamaPentgantri.setText(""+antrianArrayList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return antrianArrayList.size();
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
