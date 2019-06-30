package com.example.hawk.mydb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hawk.mydb.entity.Tugas;

public class FormAddUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtTitle, edtDescription, edtDate;
    Button btnSubmit;

    public static String EXTRA_NOTE = "extra_note";
    public static String EXTRA_POSITION= "extra_position";

    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private Tugas tugas;
    private int position;
    private CatatanHelper catatanHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_update);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        edtDate = findViewById(R.id.edt_tanggal);

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        catatanHelper = new CatatanHelper(this);
        catatanHelper.open();

        tugas = getIntent().getParcelableExtra(EXTRA_NOTE);

        if(tugas!=null){
            position = getIntent().getIntExtra(EXTRA_POSITION,0);
            isEdit = true;
        }

        String actionBarTitle = null;
        String btnTitle = null;

        if(isEdit){
            actionBarTitle = "Ubah";
            btnTitle = "Update";
            edtTitle.setText(tugas.getMataKuliah());
            edtDescription.setText(tugas.getDeskripsi());
            edtDate.setText(tugas.getTglPengumpulan());
        }else{
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setText(btnTitle);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_submit){
            String matkul = edtTitle.getText().toString();
            String deskripsi = edtDescription.getText().toString().trim();
            String tgl = edtDate.getText().toString().trim();

            boolean isEmpty = false;

            if(TextUtils.isEmpty(matkul)) {
                isEmpty = true;
                edtTitle.setError("Tidak boleh Kosong");
            }
            if(!isEmpty){
                Tugas tugasBaru = new Tugas();
                tugasBaru.setMataKuliah(matkul);
                tugasBaru.setDeskripsi(deskripsi);
                tugasBaru.setTglPengumpulan(tgl);

                Intent intent = new Intent();

                if(isEdit){
                    tugasBaru.setId(tugas.getId());
                    catatanHelper.update(tugasBaru);
                    intent.putExtra(EXTRA_POSITION,position);
                    setResult(RESULT_UPDATE, intent);
                    finish();
                } else{
                    catatanHelper.insert(tugasBaru);
                    setResult(RESULT_ADD);
                    finish();
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                if(isEdit){
                    catatanHelper.delete(tugas.getId());
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_POSITION,position);
                    setResult(RESULT_DELETE,intent);
                }
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
