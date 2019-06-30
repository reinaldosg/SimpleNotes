package com.example.hawk.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.hawk.mydb.entity.Tugas;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.hawk.mydb.DatabaseContract.CatatanKuliahColumns.DATE;
import static com.example.hawk.mydb.DatabaseContract.CatatanKuliahColumns.DESCRIPTION;
import static com.example.hawk.mydb.DatabaseContract.CatatanKuliahColumns.MATKUL;
import static com.example.hawk.mydb.DatabaseContract.TABLE_CATATAN_KULIAH;

public class CatatanHelper {
    private static String DATABASE_TABLE = TABLE_CATATAN_KULIAH;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public CatatanHelper(Context context) {
        this.context = context;
    }
    public CatatanHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        database.close();
    }

    public ArrayList<Tugas> query(){
        ArrayList<Tugas> arrayList =new ArrayList<Tugas>();
        Cursor cursor = database.query(DATABASE_TABLE,
        null,null,null,null,
        null,_ID+ " DESC",null);

        cursor.moveToFirst();
        Tugas tugas;

        if (cursor.getCount() >0){
            do{
                tugas = new Tugas();
                tugas.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tugas.setMataKuliah(cursor.getString(cursor.getColumnIndexOrThrow(MATKUL)));
                tugas.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tugas.setTglPengumpulan(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(tugas);
                cursor.moveToNext();
            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Tugas tugas){
        ContentValues initialValues = new ContentValues();
        initialValues.put(MATKUL, tugas.getMataKuliah());
        initialValues.put(DESCRIPTION, tugas.getDeskripsi());
        initialValues.put(DATE, tugas.getTglPengumpulan());

        return database.insert(DATABASE_TABLE,null,initialValues);
    }

    public int update(Tugas tugas){
        ContentValues args = new ContentValues();
        args.put(MATKUL,tugas.getMataKuliah());
        args.put(DESCRIPTION,tugas.getDeskripsi());
        args.put(DATE,tugas.getTglPengumpulan());

        return database.update(DATABASE_TABLE,args,_ID+"= '"+tugas.getId() + "'",null);
    }

    public int delete(int id){
        return database.delete(DATABASE_TABLE,_ID+"= '"+id+"'",null);
    }
}
