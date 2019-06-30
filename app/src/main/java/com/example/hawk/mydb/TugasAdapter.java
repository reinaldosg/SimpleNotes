package com.example.hawk.mydb;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hawk.mydb.entity.Tugas;

import java.util.LinkedList;

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.TugasViewHolder>{

    private LinkedList<Tugas> tugasLinkedList;
    private Activity activity;

    public LinkedList<Tugas> getTugasLinkedList() {
        return tugasLinkedList;
    }

    public void setTugasLinkedList(LinkedList<Tugas> tugasLinkedList) {
        this.tugasLinkedList = tugasLinkedList;
    }

    public TugasAdapter(Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public TugasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tugas,viewGroup,false);
        return new TugasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TugasViewHolder holder, int position) {
        holder.tvMatkul.setText(getTugasLinkedList().get(position).getMataKuliah());
        holder.tvDeskripsi.setText(getTugasLinkedList().get(position).getDeskripsi());
        holder.tvTanggal.setText(getTugasLinkedList().get(position).getTglPengumpulan());

        holder.cvTugas.setOnClickListener(new CustomOnClickListener(position, new CustomOnClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity,FormAddUpdateActivity.class);
                intent.putExtra(FormAddUpdateActivity.EXTRA_POSITION,position);
                intent.putExtra(FormAddUpdateActivity.EXTRA_NOTE,getTugasLinkedList().get(position));

                activity.startActivityForResult(intent,FormAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        String ukuran = Integer.toString(getTugasLinkedList().size());
        Log.d("linkedlist",ukuran);
        return getTugasLinkedList().size();
    }

    public class TugasViewHolder extends RecyclerView.ViewHolder{

        TextView tvMatkul, tvDeskripsi, tvTanggal;
        CardView cvTugas;

        public TugasViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatkul = itemView.findViewById(R.id.tv_item_title);
            tvDeskripsi = itemView.findViewById(R.id.tv_item_description);
            tvTanggal = itemView.findViewById(R.id.tv_item_date);

            cvTugas = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
