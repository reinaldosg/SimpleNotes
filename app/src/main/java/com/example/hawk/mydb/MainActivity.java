package com.example.hawk.mydb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hawk.mydb.entity.Tugas;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rvTugas;
    FloatingActionButton fabAdd;

    private LinkedList<Tugas> list;
    private TugasAdapter adapter;
    private CatatanHelper catatanHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TugasKuliah");
        rvTugas = findViewById(R.id.rv_notes);
        rvTugas.setLayoutManager(new LinearLayoutManager(this));
//        rvTugas.setHasFixedSize(true);

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        catatanHelper = new CatatanHelper(this);
        catatanHelper.open();

        list = new LinkedList<>();
        adapter = new TugasAdapter(this);
        adapter.setTugasLinkedList(list);
        rvTugas.setAdapter(adapter);
    }

    private class LoadCatatanAsync extends AsyncTask<Void,Void,ArrayList<Tugas>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(list.size()>0){
                list.clear();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Tugas> tugases) {
            super.onPostExecute(tugases);

            list.addAll(tugases);
            adapter.setTugasLinkedList(list);
            adapter.notifyDataSetChanged();

            if(list.size() == 0){
                Toast.makeText(getApplicationContext(),"Tidak ada data",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected ArrayList<Tugas> doInBackground(Void... voids) {
            return catatanHelper.query();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FormAddUpdateActivity.REQUEST_ADD){
            if(resultCode == FormAddUpdateActivity.RESULT_ADD){
                new LoadCatatanAsync().execute();
                Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan",Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == FormAddUpdateActivity.REQUEST_UPDATE){
            if(resultCode == FormAddUpdateActivity.RESULT_UPDATE) {
                new LoadCatatanAsync().execute();
                Toast.makeText(getApplicationContext(), "Data berhasil Diubah", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == FormAddUpdateActivity.RESULT_DELETE){
                int position = data.getIntExtra(FormAddUpdateActivity.EXTRA_POSITION, 0);
                list.remove(position);
                adapter.setTugasLinkedList(list);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Data berhasil dihapus",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(catatanHelper !=null){
            catatanHelper.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add){
            Intent intent = new Intent(MainActivity.this, FormAddUpdateActivity.class);
            startActivityForResult(intent, FormAddUpdateActivity.REQUEST_ADD);
        }
    }
}
